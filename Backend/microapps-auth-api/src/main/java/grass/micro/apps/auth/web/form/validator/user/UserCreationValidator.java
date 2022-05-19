package grass.micro.apps.auth.web.form.validator.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.io.Serializable;

import grass.micro.apps.auth.web.form.UserForm;
import grass.micro.apps.auth.web.util.ErrorCode;
import grass.micro.apps.model.auth.Account;
import grass.micro.apps.model.auth.AccountType;
import grass.micro.apps.service.auth.AccountService;
import grass.micro.apps.web.form.validator.AbstractFormValidator;

@Component
public class UserCreationValidator extends AbstractFormValidator {
    
    @Autowired
    private AccountService accountService;
    
    @Override
    public boolean support(Serializable form) {
        return form instanceof UserForm;
    }

    @Override
    public boolean doValidate(Serializable form, Errors errors) {
        UserForm userForm = (UserForm) form;
        Account account = this.accountService.getByEmail(userForm.getEmail(), AccountType.STAFF);
        if (account == null) {
            account = accountService.getByLoginName(userForm.getLoginName());
        }

        if (account != null) {
            errors.reject(ErrorCode.APP_2041_ACCOUNT_EXISTED);
        }
        
        return !errors.hasErrors();
    }

}
