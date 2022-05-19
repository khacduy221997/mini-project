package grass.micro.apps.auth.web.form.validator.role;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import grass.micro.apps.auth.web.util.ErrorCode;
import grass.micro.apps.model.auth.Role;
import grass.micro.apps.service.auth.RoleService;
import grass.micro.apps.web.form.validator.AbstractFormValidator;
import grass.micro.apps.web.util.WebConstants;

@Component
public class RoleExistedValidator extends AbstractFormValidator {
    @Autowired
    private RoleService roleService;

    @Override
    public boolean support(Serializable form) {
        return true;
    }

    @Override
    public boolean doValidate(Serializable form, Errors errors) {
        Integer id = (Integer) RequestContextHolder.getRequestAttributes()
                .getAttribute(WebConstants.APPS_API_RAW_PATH_VARIABLE_KEY, RequestAttributes.SCOPE_REQUEST);

        if (id == null) {
            errors.reject(ErrorCode.APP_1000_SYSTEM_ERROR);
            return false;
        }

        Role role = this.roleService.get(id);
        if (role == null) {
            errors.reject(ErrorCode.APP_2073_ROLE_WAS_NOT_EXISTED, new Object[] {"" + id}, ErrorCode.APP_2073_ROLE_WAS_NOT_EXISTED);
        }

        return !errors.hasErrors();
    }
}
