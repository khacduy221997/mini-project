package grass.micro.apps.auth.web.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import grass.micro.apps.annotation.AppJsonSchema;
import grass.micro.apps.annotation.Logged;
import grass.micro.apps.annotation.Validation;
import grass.micro.apps.auth.web.form.AuthorizationForm;
import grass.micro.apps.auth.web.form.ForgotPasswordForm;
import grass.micro.apps.auth.web.form.ResetPasswordForm;
import grass.micro.apps.auth.web.form.UserPasswordForm;
import grass.micro.apps.auth.web.util.AuthAppConstants;
import grass.micro.apps.auth.web.util.ControllerAction;
import grass.micro.apps.auth.web.util.DtoFetchingUtils;
import grass.micro.apps.auth.web.util.ErrorCode;
import grass.micro.apps.component.SystemConfiguration;
import grass.micro.apps.model.auth.Account;
import grass.micro.apps.model.auth.AccountTwoFactor;
import grass.micro.apps.model.auth.AccountType;
import grass.micro.apps.model.auth.TwoFactorType;
import grass.micro.apps.model.base.Status;
import grass.micro.apps.model.util.EntityUpdateTracker;
import grass.micro.apps.service.EmailService;
import grass.micro.apps.service.auth.AccountService;
import grass.micro.apps.service.auth.AccountTwoFactorService;
import grass.micro.apps.service.auth.UserService;
import grass.micro.apps.service.exception.AccountNotFoundException;
import grass.micro.apps.service.exception.ServiceException;
import grass.micro.apps.util.PropertiesConstants;
import grass.micro.apps.util.SystemUtils;
import grass.micro.apps.web.component.ErrorsKeyConverter;
import grass.micro.apps.web.controller.GeneralController;
import grass.micro.apps.web.controller.support.AppControllerSupport;
import grass.micro.apps.web.dto.RpcResponse;
import grass.micro.apps.web.util.RequestUtils;

@RestController
@RequestMapping(value = ControllerAction.APP_AUTH_ACTION)
public class AuthorizationController extends GeneralController {
	private Logger logger = Logger.getLogger(AuthorizationController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountTwoFactorService account2faService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private SystemConfiguration config;

	@Autowired
	private ErrorsKeyConverter errorsProcessor;

	@PostMapping(value = ControllerAction.APP_AUTHORIZE_ACTION)
	@Logged
	public ResponseEntity<?> authorize(HttpServletRequest request, @Valid @RequestBody AuthorizationForm form,
			Locale locale) {
		RpcResponse rs = new RpcResponse(ControllerAction.APP_AUTHORIZE_ACTION);
		HttpStatus status = HttpStatus.OK;
		Account account = this.accountService.login(form.getLoginName(), form.getPassword());
		if (account == null) {
			status = HttpStatus.UNAUTHORIZED;
			rs.addAttribute("message", "UNAUTHORIZED");
		} else {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("email", account.getEmail());
			map.put("accountId", account.getId());
			int timeout = config.getInt(PropertiesConstants.APP_SESSION_TIMEOUT_KEY,
					PropertiesConstants.APP_DEFAULT_SESSION_TIMEOUT);
			String token = SystemUtils.getInstance().createJWT("AUTH_" + account.getId(),
					AuthAppConstants.APP_BRANCH_NAME, "Store Logged-in User", map, timeout * 60 * 1000);
			rs.addAttribute("token", token);
			rs.addAttribute("user", DtoFetchingUtils.fetchUser(userService.getByAccountId(account.getId())));
		}

		return new ResponseEntity<>(rs, status);
	}

	/**
	 * This is the function that's used to send email for user to reset new
	 * password.
	 * 
	 * @param email String
	 * @return message
	 */
	@PostMapping(value = ControllerAction.APP_AUTH_FORGOT_PASSWORD_ACTION)
	@Logged
	public ResponseEntity<?> forgotPasswordFromEmail(HttpServletRequest request, @RequestBody ForgotPasswordForm form,
			BindingResult errors) {
		AppControllerSupport support = new AppControllerSupport() {
			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				String email = form.getEmail();
				if (email == null || email.isEmpty() || !email.matches(PropertiesConstants.APP_EMAIL_PATTERN)) {
					errors.reject(ErrorCode.APP_2021_EMAIL_INVALID, ErrorCode.APP_2021_EMAIL_INVALID);
				} else {
					Account account = AuthorizationController.this.accountService.getByEmail(email, AccountType.STAFF);
					if (account == null || Status.DELETED.equals(account.getStatus())) {
						errors.reject(ErrorCode.APP_2022_ACCOUNT_NOT_EXIST, ErrorCode.APP_2022_ACCOUNT_NOT_EXIST);
					} else if (Status.INACTIVE.equals(account.getStatus())) {
						errors.reject(ErrorCode.APP_2023_ACCOUNT_IS_BLOCKED, ErrorCode.APP_2023_ACCOUNT_IS_BLOCKED);
					} else {
						try {
							int expiration = PropertiesConstants.APP_DEFAULT_FORGOTPASSWORD_EXPIRATION;
							try {
								expiration = Integer.parseInt(SystemConfiguration.getInstance()
										.getProperty(PropertiesConstants.APP_FORGOTPASSWORD_EXPIRATION_KEY));
							} catch (Exception ex) {
								throw new ServiceException("Can not generate expiration");
							}

							account = AuthorizationController.this.accountService.resetPassword(email,
									AccountType.STAFF, expiration);

							String content = this.generateEmailContent(account.getResetPasswordKey(), expiration,
									account.getEmail(), account);

							String title = "";
							try {
								title = SystemConfiguration.getInstance()
										.getProperty(PropertiesConstants.APP_FORGOT_PASSWORD_EMAIL_TITLE_KEY);
							} catch (Exception ex) {
								title = PropertiesConstants.APP_DEFAULT_FORGOT_PASSWORD_EMAIL_TITLE;
							}

							emailService.sendMail(account.getEmail(), title, content);
						} catch (AccountNotFoundException ex) {
							logger.warn(ex.getMessage(), ex);
							errors.reject(ErrorCode.APP_2022_ACCOUNT_NOT_EXIST, ErrorCode.APP_2022_ACCOUNT_NOT_EXIST);
						} catch (Exception ex) {
							logger.error(ex.getMessage(), ex);
							errors.reject(ErrorCode.APP_1000_SYSTEM_ERROR, ErrorCode.APP_1000_SYSTEM_ERROR);
						}
					}
				}
			}

			private String getContentDefault() {
				String content = null;

				BufferedReader br = null;
				try {
					InputStream stream = this.getClass()
							.getResourceAsStream(PropertiesConstants.APP_FORGOT_PASSWORD_EMAIL_TEMPLATE_PATH);
					br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = br.readLine()) != null) {
						sb.append(line).append(System.getProperty("line.separator"));
					}
					content = sb.toString();
				} catch (Exception ex) {
					logger.error("GET EMAIL CONTENT ERROR: " + ex.getMessage(), ex);
				} finally {
					try {
						br.close();
					} catch (Exception e2) {
						// do nothing
					}
				}

				return content;
			}

			private String generateEmailContent(String resetPasswordKey, int expiration, String email, Account account)
					throws ServiceException {

				String resetUrl = "";
				try {
//					if (AccountType.CUSTOMER.equals(account.getAccountType())) {
//						resetUrl = SystemConfiguration.getInstance().getProperty(PropertiesConstants.APP_CUSTOMER_RESET_URL_KEY);
//					} else {
//						resetUrl = SystemConfiguration.getInstance().getProperty(PropertiesConstants.APP_RESET_URL_KEY);
//					}
					resetUrl = SystemConfiguration.getInstance().getProperty(PropertiesConstants.APP_RESET_URL_KEY);
				} catch (Exception ex) {
					resetUrl = PropertiesConstants.APP_DEFAULT_RESET_URL;
				}

				String siteName = "";
				try {
					siteName = SystemConfiguration.getInstance().getProperty(PropertiesConstants.APP_SITE_NAME);
				} catch (Exception ex) {
					logger.info("Read configure (common.siteName) error");
				}

				if (siteName == null || siteName.isEmpty()) {
					logger.info("Use Default Email Title");
				}

				String content = this.getContentDefault();
				content = content.replace("[[PRODUCT_NAME]]", siteName);
				content = content.replace("[[NAME]]", account.getEmail());
				content = content.replace("[[EXPIRATION]]", String.valueOf(expiration));
				content = content.replace("[[SYSTEM_URL]]", resetUrl + "/" + resetPasswordKey);
				return content;
			}
		};

		return support.doSupport(request, null, errors, errorsProcessor);
	}

	/**
	 * Check reset password key is valid.
	 * 
	 * @param resetPasswordKey resetPasswordKey from query.
	 * @return MASTER_TOOL_USER_PASSWORD_VIEW
	 */
	@GetMapping(value = ControllerAction.APP_AUTH_RESET_PASSWORD_CHECK_KEY_ACTION)
	@Logged
	public ResponseEntity<?> checkResetpasswordKey(@RequestParam(name = "reset_password_key") String resetPasswordKey) {
		RpcResponse result = new RpcResponse(ControllerAction.APP_AUTH_FORGOT_PASSWORD_ACTION);

		HttpStatus status = HttpStatus.OK;
		String errorCode = "";
		if (resetPasswordKey == null || resetPasswordKey.isEmpty()) {
			errorCode = ErrorCode.APP_2031_KEY_REQUIRED;
		} else {
			Account account = this.accountService.getByResetPasswodKey(resetPasswordKey);
			if (account == null) {
				errorCode = ErrorCode.APP_2032_KEY_INVALID;
			} else {
				result.addAttribute("account", DtoFetchingUtils.fetchAccount(account));
			}
		}

		if (!errorCode.isEmpty()) {
			status = HttpStatus.BAD_REQUEST;
			ObjectError error = new ObjectError("resetPasswordKey", new String[] { errorCode }, null, errorCode);
			try {
				errorsProcessor.processGlobalError(null, error);
				result.addAttribute("error", error.getDefaultMessage());
			} catch (Exception ex) {
				logger.warn(ex.getMessage(), ex);
			}
		}

		return new ResponseEntity<RpcResponse>(result, status);
	}

	@PostMapping(value = ControllerAction.APP_AUTH_RESET_PASSWORD_ACTION)
	@Logged
	@Validation(schema = @AppJsonSchema("/schema/reset_password.json"))
	public ResponseEntity<?> resetPassword(HttpServletRequest request, @RequestBody ResetPasswordForm form,
			BindingResult errors) {
		AppControllerSupport support = new AppControllerSupport() {
			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				Account account = AuthorizationController.this.accountService
						.getByResetPasswodKey(form.getResetPasswordKey());
				if (account != null) {
					try {
						accountService.changePassword(account.getId(), form.getNewPassword());
						getRpcResponse().addAttribute("account", DtoFetchingUtils.fetchAccount(account));
					} catch (Exception ex) {
						logger.error(ex.getMessage(), ex);
						errors.reject(ErrorCode.APP_1000_SYSTEM_ERROR);
					}
				} else {
					errors.reject(ErrorCode.APP_2032_KEY_INVALID);
				}
			}
		};

		return support.doSupport(request, null, errors, errorsProcessor);
	}

	@PutMapping(value = "/accounts/{id}/changePassword")
	@Logged
	public ResponseEntity<?> updateAccountPassword(HttpServletRequest request, @PathVariable("id") int id,
			@RequestBody UserPasswordForm userPasswordForm, BindingResult errors) {
		AppControllerSupport support = new AppControllerSupport() {
			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				Account account = accountService.getActivated(id);
				if (account == null) {
					errors.reject(ErrorCode.APP_2022_ACCOUNT_NOT_EXIST, ErrorCode.APP_2022_ACCOUNT_NOT_EXIST);
					return;
				}

				String enscriptedPasswd = SystemUtils.getInstance().generatePassword(userPasswordForm.getOldPassword(),
						account.getSalt());
				if (!enscriptedPasswd.equals(account.getPassword())) {
					errors.reject(ErrorCode.APP_2056_OLD_PASSWORD_NOT_MATCH, ErrorCode.APP_2056_OLD_PASSWORD_NOT_MATCH);
					return;
				}

				account.setPassword(SystemUtils.getInstance().generatePassword(userPasswordForm.getNewPassword(),
						account.getSalt()));
				EntityUpdateTracker.getInstance().track(Account.class, account.getId(), account.getUpdateCount());

				try {
					accountService.update(account);
					getRpcResponse().addAttribute("message", "success");
				} catch (Exception ex) {
					errors.reject(ErrorCode.APP_1000_SYSTEM_ERROR, ErrorCode.APP_1000_SYSTEM_ERROR);
				}
			}
		};
		return support.doSupport(request, null, errors, errorsProcessor);
	}

	@GetMapping(value = "/accounts/{id}/init2fa")
	@Logged
	public ResponseEntity<?> initTwoFactorAuth(HttpServletRequest request, @PathVariable("id") int id) {
		final BindingResult errors = RequestUtils.getInstance().getBindingResult();
		AppControllerSupport support = new AppControllerSupport() {
			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				Account account = accountService.getActivated(id);
				AccountTwoFactor twoFactor = account2faService.getByAccountIdAndTwoFactorType(account.getId(),
						TwoFactorType.GG);
				GoogleAuthenticator gAuth = new GoogleAuthenticator();
				GoogleAuthenticatorKey key = null;
				if (twoFactor == null) {
					twoFactor = new AccountTwoFactor();
					twoFactor.setAccountId(account.getId());
					twoFactor.setTwoFactorType(TwoFactorType.GG);
					key = gAuth.createCredentials();
					twoFactor.setData(key.getKey());
					twoFactor.setStatus(Status.INACTIVE);
					twoFactor = account2faService.create(twoFactor);
				} else {
					key = new GoogleAuthenticatorKey.Builder(twoFactor.getData()).build();
				}

				String toptUrl = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("VNPAY CLOUD", account.getEmail(),
						key);
				getRpcResponse().addAttribute("topt_url", toptUrl);
				getRpcResponse().addAttribute("qr_url", generateQrUrl(toptUrl));
			}
		};
		return support.doSupport(request, null, errors, errorsProcessor);
	}

	private String generateQrUrl(String totpUrl) {
		String result = SystemConfiguration.getInstance().getProperty("qrcode.generator",
				"https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=");
		try {
			result += URLEncoder.encode(totpUrl, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException ex) {
			// do nothing
		}
		return result;
	}

	@GetMapping(value = "/accounts/{id}/confirm2fa/{code}")
	@Logged
	public ResponseEntity<?> confirmTwoFactorAuth(HttpServletRequest request, @PathVariable("id") int id,
			@PathVariable("code") int code) {
		final BindingResult errors = RequestUtils.getInstance().getBindingResult();
		AppControllerSupport support = new AppControllerSupport() {
			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				if (code < 100000 || code > 1000000) {
					errors.reject(ErrorCode.APP_2055_WRONG_VERIFICATION_CODE,
							ErrorCode.APP_2055_WRONG_VERIFICATION_CODE);
					return;
				}

				Account account = accountService.getActivated(id);
				AccountTwoFactor twoFactor = account2faService.getByAccountIdAndTwoFactorType(account.getId(),
						TwoFactorType.GG);

				if (twoFactor == null) {
					errors.reject(ErrorCode.APP_2054_USER_IS_NOT_INIT_2FA, ErrorCode.APP_2054_USER_IS_NOT_INIT_2FA);
					return;
				}

				GoogleAuthenticator gAuth = new GoogleAuthenticator();
				OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
				Date date = Date.from(utc.toInstant());
				if (!gAuth.authorize(twoFactor.getData(), code, date.getTime())) {
					errors.reject(ErrorCode.APP_2055_WRONG_VERIFICATION_CODE,
							ErrorCode.APP_2055_WRONG_VERIFICATION_CODE);
				} else {
					if (!twoFactor.isActivated()) {
						twoFactor.setStatus(Status.ACTIVE);
						account2faService.updatePartial(twoFactor);
					}

					getRpcResponse().addAttribute("message", "success");
				}
			}
		};
		return support.doSupport(request, null, errors, errorsProcessor);
	}

	@GetMapping(value = "/accounts/{id}/authtype")
	@Logged
	public ResponseEntity<?> getAuthenticationType(HttpServletRequest request, @PathVariable("id") int id) {
		final BindingResult errors = RequestUtils.getInstance().getBindingResult();
		AppControllerSupport support = new AppControllerSupport() {

			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				Account account = accountService.getActivated(id);
				AccountTwoFactor twoFactor = account2faService.getByAccountIdAndTwoFactorType(account.getId(),
						TwoFactorType.GG);
				getRpcResponse().addAttribute("account2fa", twoFactor);
			}
		};
		return support.doSupport(request, null, errors, errorsProcessor);
	}

	@GetMapping(value = "/accounts/{id}/disable2fa")
	@Logged
	public ResponseEntity<?> disableTwoFactorAuth(HttpServletRequest request, @PathVariable("id") int id) {
		final BindingResult errors = RequestUtils.getInstance().getBindingResult();
		AppControllerSupport support = new AppControllerSupport() {

			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				Account account = accountService.getActivated(id);
				AccountTwoFactor twoFactor = account2faService.getByAccountIdAndTwoFactorType(account.getId(),
						TwoFactorType.GG);
				twoFactor.setStatus(Status.INACTIVE);
				account2faService.updatePartial(twoFactor);
			}

		};
		return support.doSupport(request, null, errors, errorsProcessor);
	}

	@PostMapping(value = ControllerAction.APP_AUTH_LOGIN_ACTION)
	@ResponseBody
	public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AuthorizationForm loginForm, BindingResult errors) {
		AppControllerSupport support = new AppControllerSupport() {

			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				processLogin(loginForm, request, response, errors, getRpcResponse());
			}
		};

		return support.doSupport(request, response, errors, errorsProcessor);
	}

	protected void processLogin(AuthorizationForm loginForm, HttpServletRequest request, HttpServletResponse response,
			Errors errors, RpcResponse rpcResponse) {
		if (!errors.hasErrors()) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("loginName", "khacduy221997@gmail.com");
			map.put("sessionId", request.getSession(true).getId());
			int timeout = SystemConfiguration.getInstance().getInt(PropertiesConstants.APP_SESSION_TIMEOUT_KEY,
					PropertiesConstants.APP_DEFAULT_SESSION_TIMEOUT);
			String token = SystemUtils.getInstance().createJWT("A_C_", "khacduy221997@gmail.com", "1", map, timeout * 60 * 1000);
			rpcResponse.addAttribute("token", token);
			rpcResponse.addAttribute("customer", "Võ Khắc Duy");
		}
	}

	@PostMapping(value = ControllerAction.APP_AUTH_REGISTER_ACTION)
	@ResponseBody
	public ResponseEntity<?> register(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AuthorizationForm form, BindingResult errors) {
		AppControllerSupport support = new AppControllerSupport() {

			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				processRegister(form, request, response, errors, getRpcResponse());
			}
		};

		return support.doSupport(request, response, errors, errorsProcessor);
	}

	protected void processRegister(AuthorizationForm form, HttpServletRequest request, HttpServletResponse response,
			Errors errors, RpcResponse rpcResponse) {
		System.err.println(form);
		rpcResponse.addAttribute("message", "successfull");
	}

}
