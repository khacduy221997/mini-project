package grass.micro.apps.auth.web.form.validator.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.Serializable;

import grass.micro.apps.auth.web.form.UserForm;
import grass.micro.apps.auth.web.util.ErrorCode;
import grass.micro.apps.model.auth.Account;
import grass.micro.apps.model.auth.AccountType;
import grass.micro.apps.model.auth.User;
import grass.micro.apps.service.auth.AccountService;
import grass.micro.apps.service.auth.UserService;
import grass.micro.apps.web.form.validator.AbstractFormValidator;
import grass.micro.apps.web.util.WebConstants;

@Component
public class UserEdittingValidator extends AbstractFormValidator {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private UserService userService;
    
    @Override
    public boolean support(Serializable form) {
        return form instanceof UserForm;
    }

    @Override
    public boolean doValidate(Serializable form, Errors errors) {
        Integer id = (Integer) RequestContextHolder.getRequestAttributes().getAttribute(WebConstants.APPS_API_RAW_PATH_VARIABLE_KEY,
                RequestAttributes.SCOPE_REQUEST);
        
        if (id == null) {
            errors.reject(ErrorCode.APP_1000_SYSTEM_ERROR);
            return false;
        }
        
        UserForm userForm = (UserForm) form;
        User user = this.userService.getById(id);
        Account account = this.accountService.getByEmail(userForm.getEmail(), AccountType.STAFF);
        if (account != null && !account.getId().equals(user.getAccount().getId())
                && account.getEmail().equals(userForm.getEmail())) {
            errors.reject(ErrorCode.APP_2051_USER_WITH_EMAIL_EXISTED);
        } else if (account != null && !account.getId().equals(user.getAccount().getId())
                && account.getLoginName().equals(userForm.getLoginName())) {
            errors.reject(ErrorCode.APP_2052_USER_WITH_LOGIN_NAME_EXISTED);
        }
        
        return !errors.hasErrors();
    }

}
