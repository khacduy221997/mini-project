package grass.micro.apps.auth.web.controller;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import grass.micro.apps.annotation.AppJsonSchema;
import grass.micro.apps.annotation.GetBody;
import grass.micro.apps.annotation.Logged;
import grass.micro.apps.annotation.Validation;
import grass.micro.apps.auth.web.form.UserForm;
import grass.micro.apps.auth.web.form.UserPasswordForm;
import grass.micro.apps.auth.web.form.validator.user.UserCreationValidator;
import grass.micro.apps.auth.web.form.validator.user.UserEdittingValidator;
import grass.micro.apps.auth.web.form.validator.user.UserExistedValidator;
import grass.micro.apps.auth.web.util.ControllerAction;
import grass.micro.apps.auth.web.util.DtoFetchingUtils;
import grass.micro.apps.auth.web.util.ErrorCode;
import grass.micro.apps.component.SystemConfiguration;
import grass.micro.apps.model.auth.Account;
import grass.micro.apps.model.auth.AccountTwoFactor;
import grass.micro.apps.model.auth.AccountType;
import grass.micro.apps.model.auth.Role;
import grass.micro.apps.model.auth.TwoFactorType;
import grass.micro.apps.model.auth.User;
import grass.micro.apps.model.auth.util.CommonPermissionConstants;
import grass.micro.apps.model.auth.util.PermissionConstants;
import grass.micro.apps.model.base.Status;
import grass.micro.apps.model.util.EntityUpdateTracker;
import grass.micro.apps.service.EmailService;
import grass.micro.apps.service.auth.AccountService;
import grass.micro.apps.service.auth.AccountTwoFactorService;
import grass.micro.apps.service.auth.CompanyService;
import grass.micro.apps.service.auth.DepartmentService;
import grass.micro.apps.service.auth.RoleService;
import grass.micro.apps.service.auth.UserService;
import grass.micro.apps.service.exception.AccountNotFoundException;
import grass.micro.apps.service.exception.ServiceException;
import grass.micro.apps.util.PropertiesConstants;
import grass.micro.apps.util.SystemUtils;
import grass.micro.apps.web.component.ErrorsKeyConverter;
import grass.micro.apps.web.controller.GeneralController;
import grass.micro.apps.web.controller.support.AppControllerCreationSupport;
import grass.micro.apps.web.controller.support.AppControllerListingSupport;
import grass.micro.apps.web.controller.support.AppControllerSupport;
import grass.micro.apps.web.dto.RpcResponse;
import grass.micro.apps.web.form.validator.LimittedForm;
import grass.micro.apps.web.util.RequestUtils;

@RestController
public class UserController extends GeneralController {
    private Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private AccountTwoFactorService account2faService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private ErrorsKeyConverter errorsProcessor;

    @GetMapping(value = ControllerAction.APP_USER_ACTION)
    @RequiresPermissions(value = CommonPermissionConstants.USER_VIEW)
    @Logged
    public ResponseEntity<?> listUser(HttpServletRequest request, HttpServletResponse response,
            @GetBody LimittedForm form, BindingResult errors) {
        AppControllerSupport support = new AppControllerListingSupport() {
            @Override
            public List<? extends Serializable> getEntityList(HttpServletRequest request, HttpServletRequest response,
                    Errors errors, ErrorsKeyConverter errorsProcessor) {
                return UserController.this.userService.getAllAvailable();
            }

            @Override
            public String getAttributeName() {
                return "users";
            }

            @SuppressWarnings("unchecked")
            @Override
            public List<?> fetchEntitiesToDtos(List<? extends Serializable> entities) {
                return DtoFetchingUtils.fetchUsers((List<User>) entities);
            }
        };

        return support.doSupport(request, response, null, errorsProcessor);
    }

    @PostMapping(value = ControllerAction.APP_USER_ACTION)
    @RequiresPermissions(value = CommonPermissionConstants.USER_CREATE)
    @Logged
    @Validation(schema = @AppJsonSchema("/schema/user_create.json"), validators = {
            UserCreationValidator.class })
    public ResponseEntity<?> createNewUser(HttpServletRequest request, @RequestBody UserForm form,
            BindingResult errors) {
        AppControllerSupport support = new AppControllerCreationSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                UserController.this.doCreateUser(form, getRpcResponse(), (BindingResult) errors);
            }
        };

        return support.doSupport(request, null, errors, errorsProcessor);
    }

    private void doCreateUser(UserForm form, RpcResponse result, BindingResult errors) {
        User user = new User();
        // ACOUNT INFO
        String rawPassword = SystemUtils.getInstance().randomPassword();
        user.setAccount(this.createAccount(form, rawPassword));

        // DETAIL INFO
        user.setStatus(Status.ACTIVE);
        user.setFullname(form.getFullname());
        user.setAddress(form.getAddress());
        user.setMobile(form.getMobile());
        user.setTelephone(form.getTelephone());
        user.setTelephoneExt(form.getTelephoneExt());

        user.setCompany(this.companyService.get(form.getCompanyId()));
        if (form.getDepartmentId() != null) {
            user.setDepartment(this.departmentService.get(form.getDepartmentId()));
        }

        // ROLE INFO
        List<Role> roles = new LinkedList<>();
        if (form.getRoles() != null && !form.getRoles().isEmpty()) {
            for (Integer roleId : form.getRoles()) {
                Role role = this.roleService.get(roleId);
                roles.add(role);
            }
        }
        user.setRoles(roles);
        // Create user
        user = this.userService.create(user);
        this.emailService.sendMail(user.getAccount().getEmail(), "New user creation", this.generateEmailContent(user, rawPassword));
        result.addAttribute("user", DtoFetchingUtils.fetchUser(user));
    }

    /*
     * @param form {@link UserForm}
     */
    private Account createAccount(UserForm form, String rawPassword) {
        Account account = new Account();
        account.setStatus(Status.ACTIVE);
        account.setAccountType(AccountType.STAFF);
        account.setEmail(form.getEmail());
        account.setLoginName(form.getLoginName());
        String salt = SystemUtils.getInstance().randomPassword();
        String password = rawPassword;
        try {
            password = SystemUtils.getInstance().md5(rawPassword + salt);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        account.setSalt(salt);
        account.setPassword(password);

        return account;
    }

    @PutMapping(value = ControllerAction.APP_USER_DETAIL_ACTION, consumes = "application/json")
    @RequiresPermissions(value = CommonPermissionConstants.USER_EDIT)
    @Logged
    @Validation(schema = @AppJsonSchema("/schema/user_update.json"), validators = {
            UserExistedValidator.class, UserEdittingValidator.class })
    public ResponseEntity<?> updateUser(HttpServletRequest request, final @PathVariable("id") int id,
            @RequestBody UserForm userForm, BindingResult errors) {
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                User user = UserController.this.userService.getById(id);
                EntityUpdateTracker.getInstance().track(User.class, user.getId(), user.getUpdateCount());
                UserController.this.processDataToUpdateUser(userForm, user, (BindingResult) errors);
                user = UserController.this.userService.update(user);
                getRpcResponse().addAttribute("user", DtoFetchingUtils.fetchUser(user));
            }
        };
        return support.doSupport(request, null, errors, errorsProcessor);
    }

    private void processDataToUpdateUser(UserForm form, User user, BindingResult errors) {
        try {
            // ACOUNT INFO
            Account account = user.getAccount();
            account.setLoginName(form.getLoginName());
            account.setEmail(form.getEmail());
            if (StringUtils.isNotBlank(form.getPassword())) {
                String password = SystemUtils.getInstance().md5(form.getPassword() + account.getSalt());
                account.setPassword(password);
            }

            // DETAIL INFO
            user.setFullname(form.getFullname());
            user.setAddress(form.getAddress());
            user.setMobile(form.getMobile());
            user.setTelephone(form.getTelephone());
            user.setTelephoneExt(form.getTelephoneExt());

            user.setCompany(this.companyService.get(form.getCompanyId()));
            if (form.getDepartmentId() != null) {
                user.setDepartment(this.departmentService.get(form.getDepartmentId()));
            }

            // ROLE INFO
            List<Role> roles = new LinkedList<>();
            if (form.getRoles() != null && !form.getRoles().isEmpty()) {
                for (Integer roleId : form.getRoles()) {
                    Role role = this.roleService.get(roleId);
                    if (role != null) {
                        roles.add(role);
                    }
                }
            }
            user.setRoles(roles);
            user.setPicture(form.getPicture());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            errors.reject(ErrorCode.APP_1000_SYSTEM_ERROR);
        }
    }

    @GetMapping(value = ControllerAction.APP_USER_DETAIL_ACTION + "/lock")
    @RequiresPermissions(value = CommonPermissionConstants.USER_EDIT)
    @Logged
    @Validation(validators = { UserExistedValidator.class })
    public ResponseEntity<?> lockUser(HttpServletRequest request, @PathVariable("id") int id) {
        final BindingResult errors = RequestUtils.getInstance().getBindingResult();
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                UserController.this.saveUserStatusUser(id, getRpcResponse(), Status.INACTIVE);
            }
        };
        return support.doSupport(request, null, errors, errorsProcessor);
    }

    @GetMapping(value = ControllerAction.APP_USER_DETAIL_ACTION + "/unlock")
    @RequiresPermissions(value = CommonPermissionConstants.USER_EDIT)
    @Logged
    @Validation(validators = { UserExistedValidator.class })
    public ResponseEntity<?> unlockUser(HttpServletRequest request, @PathVariable("id") int id) {
        final BindingResult errors = RequestUtils.getInstance().getBindingResult();
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                UserController.this.saveUserStatusUser(id, getRpcResponse(), Status.ACTIVE);
            }
        };
        return support.doSupport(request, null, errors, errorsProcessor);
    }

    @DeleteMapping(value = ControllerAction.APP_USER_DETAIL_ACTION)
    @RequiresPermissions(value = CommonPermissionConstants.USER_DELETE)
    @Logged
    @Validation(validators = { UserExistedValidator.class })
    public ResponseEntity<?> deleteUser(HttpServletRequest request, @PathVariable("id") int id) {
        final BindingResult errors = RequestUtils.getInstance().getBindingResult();
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                UserController.this.saveUserStatusUser(id, getRpcResponse(), Status.DELETED);
            }
        };
        return support.doSupport(request, null, errors, errorsProcessor);
    }

    /*
     * Change user status by id.
     * 
     * @param status Status enum. (LOCK, UNLOCK, DELETE)
     */
    private void saveUserStatusUser(int id, RpcResponse result, Status status) {
        User user = this.userService.getById(id);
        EntityUpdateTracker.getInstance().track(User.class, user.getId(), user.getUpdateCount());
        Account account = user.getAccount();
        account.setStatus(status);
        user.setStatus(status);
        user = this.userService.update(user);
        result.addAttribute("user", DtoFetchingUtils.fetchUser(user));
    }
    
    @GetMapping(value = ControllerAction.APP_USER_DETAIL_ACTION + "/init2fa")
    @Logged
    @Validation(validators = { UserExistedValidator.class })
    @RequiresPermissions(value = CommonPermissionConstants.USER_EDIT)
    public ResponseEntity<?> initTwoFactorAuth(HttpServletRequest request,  @PathVariable("id") int id) {
        final BindingResult errors = RequestUtils.getInstance().getBindingResult();
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                Account account = UserController.this.accountService.getByUserId(id);
                AccountTwoFactor twoFactor = UserController.this.account2faService
                        .getByAccountIdAndTwoFactorType(account.getId(), TwoFactorType.GG);
                GoogleAuthenticator gAuth = new GoogleAuthenticator();
                GoogleAuthenticatorKey key = null;
                if (twoFactor == null) {
                    twoFactor = new AccountTwoFactor();
                    twoFactor.setAccountId(account.getId());
                    twoFactor.setTwoFactorType(TwoFactorType.GG);
                    key = gAuth.createCredentials();
                    twoFactor.setData(key.getKey());
                    twoFactor.setStatus(Status.INACTIVE);
                    twoFactor = UserController.this.account2faService.create(twoFactor);
                } else {
                    key = new GoogleAuthenticatorKey.Builder(twoFactor.getData()).build();
                }
                
                String toptUrl = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("English Center Apps", account.getEmail(), key);
                getRpcResponse().addAttribute("topt_url", toptUrl);
                getRpcResponse().addAttribute("qr_url", generateQrUrl(toptUrl));
            }
        };
        return support.doSupport(request, null, errors, errorsProcessor);
    }
    
    private String generateQrUrl(String totpUrl) {
        String result = SystemConfiguration.getInstance().getProperty("qrcode.generator", "https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=");
        try {
            result += URLEncoder.encode(totpUrl, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            // do nothing
        }
        return result;
    }
    
    @GetMapping(value = ControllerAction.APP_USER_DETAIL_ACTION + "/confirm2fa/{code}")
    @Logged
    @Validation(validators = { UserExistedValidator.class })
    @RequiresPermissions(value = CommonPermissionConstants.USER_EDIT)
    public ResponseEntity<?> confirmTwoFactorAuth(HttpServletRequest request,
            @PathVariable("id") int id, @PathVariable("code") int code) {
        final BindingResult errors = RequestUtils.getInstance().getBindingResult();
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                if (code < 100000 || code > 1000000) {
                    errors.reject(ErrorCode.APP_2055_WRONG_VERIFICATION_CODE, ErrorCode.APP_2055_WRONG_VERIFICATION_CODE);
                    return;
                }
                
                Account account = UserController.this.accountService.getByUserId(id);
                AccountTwoFactor twoFactor = UserController.this.account2faService
                        .getByAccountIdAndTwoFactorType(account.getId(), TwoFactorType.GG);
                
                if (twoFactor == null) {
                    errors.reject(ErrorCode.APP_2054_USER_IS_NOT_INIT_2FA, ErrorCode.APP_2054_USER_IS_NOT_INIT_2FA);
                    return;
                }
                
                GoogleAuthenticator gAuth = new GoogleAuthenticator();
                if (!gAuth.authorize(twoFactor.getData(), code)) {
                    errors.reject(ErrorCode.APP_2055_WRONG_VERIFICATION_CODE, ErrorCode.APP_2055_WRONG_VERIFICATION_CODE);
                } else {
                    if (!twoFactor.isActivated()) {
                        twoFactor.setStatus(Status.ACTIVE);
                        UserController.this.account2faService.updatePartial(twoFactor);
                    }
                    
                    getRpcResponse().addAttribute("message", "success");
                }
            }
        };
        return support.doSupport(request, null, errors, errorsProcessor);
    }
    
    @PutMapping(value = ControllerAction.APP_USER_DETAIL_ACTION + "/changePassword")
    @Logged
    @RequiresPermissions(value = CommonPermissionConstants.USER_EDIT)
    @Validation(schema = @AppJsonSchema("/schema/user_change_pass.json"), validators = { UserExistedValidator.class })
    public ResponseEntity<?> updateUserPassword(HttpServletRequest request,
            @PathVariable("id") int id, @RequestBody UserPasswordForm userPasswordForm, BindingResult errors) {
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                Account account = UserController.this.accountService.getByUserId(id);
                String enscriptedPasswd = SystemUtils.getInstance().generatePassword(userPasswordForm.getOldPassword(), account.getSalt());
                if (!enscriptedPasswd.equals(account.getPassword())) {
                    errors.reject(ErrorCode.APP_2056_OLD_PASSWORD_NOT_MATCH, ErrorCode.APP_2056_OLD_PASSWORD_NOT_MATCH);
                    return;
                }
                
                account.setPassword(SystemUtils.getInstance().generatePassword(userPasswordForm.getNewPassword(), account.getSalt()));
                EntityUpdateTracker.getInstance().track(Account.class, account.getId(), account.getUpdateCount());
                
                try {
                    UserController.this.accountService.update(account);
                    getRpcResponse().addAttribute("message", "success");
                } catch (Exception ex) {
                    errors.reject(ErrorCode.APP_1000_SYSTEM_ERROR, ErrorCode.APP_1000_SYSTEM_ERROR);
                }
            }
        };
        return support.doSupport(request, null, errors, errorsProcessor);
    }
    
    /**
     * This is the function that's used to send email for user to reset new password.
     * 
     * @param email
     *            String
     * @return message
     */
    @GetMapping(value = ControllerAction.APP_USER_DETAIL_ACTION + "/" + ControllerAction.APP_AUTH_RESET_PASSWORD_ACTION)
    @Logged
    @Validation(validators = { UserExistedValidator.class })
    @RequiresPermissions(value = PermissionConstants.USER_EDIT)
    public ResponseEntity<?> forgotPasswordFromEmail(HttpServletRequest request, @PathVariable("id") int id) {
        final BindingResult errors = RequestUtils.getInstance().getBindingResult();
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                try {
                	int expiration = PropertiesConstants.APP_DEFAULT_FORGOTPASSWORD_EXPIRATION;
                    try {
                        expiration = Integer
                                .parseInt(SystemConfiguration.getInstance().getProperty(
                                        PropertiesConstants.APP_FORGOTPASSWORD_EXPIRATION_KEY));
                    } catch (Exception ex) {
                        throw new ServiceException("Can not generate expiration");
                    }
                    
                    Account account = UserController.this.accountService.getByUserId(id);
                    UserController.this.accountService.resetPassword(account.getEmail(), account.getAccountType(), expiration);
                } catch (AccountNotFoundException ex) {
                    logger.warn(ex.getMessage(), ex);
                    errors.reject(ErrorCode.APP_2022_ACCOUNT_NOT_EXIST, ErrorCode.APP_2022_ACCOUNT_NOT_EXIST);
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                    errors.reject(ErrorCode.APP_1000_SYSTEM_ERROR, ErrorCode.APP_1000_SYSTEM_ERROR);
                }
            }
        };
        
        return support.doSupport(request, null, errors, errorsProcessor);
    }
    
    private String generateEmailContent(User user, String rawPassword)
            throws ServiceException {

        String baseUrl = "";
        try {
            baseUrl = SystemConfiguration.getInstance().getProperty(PropertiesConstants.APP_URL_KEY);
        } catch (Exception ex) {
            baseUrl = PropertiesConstants.APP_URL_DEFAULT;
        }

        String productName = "";
        try {
            productName = SystemConfiguration.getInstance().getProperty(PropertiesConstants.APP_SITE_NAME);
        } catch (Exception ex) {
            logger.info("Read configure (common.siteName) error");
        }

        if (productName == null || productName.isEmpty()) {
            logger.info("Use Default Email Title");
        }

        String content = this.getNewUserEmailContent();
        content = content.replace("[[PRODUCT_NAME]]", productName);
        content = content.replace("[[NAME]]", user.getFullname());
        content = content.replace("[[LOGIN_NAME]]", user.getAccount().getLoginName());
        content = content.replace("[[LOGIN_PASSWORD]]", rawPassword);
        content = content.replace("[[SYSTEM_URL]]", baseUrl);
        return content;
    }

    private String getNewUserEmailContent() {
        String content = null;

        BufferedReader br = null;
        try {
            InputStream stream = this.getClass().getResourceAsStream("/new_user.html");
            br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
            content = sb.toString();
        } catch (Exception ex) {
            throw new ServiceException("GET EMAIL CONTENT ERROR: " + ex.getMessage(), ex);
        } finally {
            try {
                br.close();
            } catch (Exception e2) {
                // do nothing
            }
        }

        return content;
    }
}
