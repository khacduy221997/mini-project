package grass.micro.apps.auth.web.form.validator.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.Serializable;

import grass.micro.apps.auth.web.util.ErrorCode;
import grass.micro.apps.model.auth.Role;
import grass.micro.apps.model.auth.RoleType;
import grass.micro.apps.service.auth.RoleService;
import grass.micro.apps.web.form.validator.AbstractFormValidator;
import grass.micro.apps.web.util.WebConstants;

@Component
public class RoleUpdateStatusValidator extends AbstractFormValidator {
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
        if (role != null && role.getStaffCount() > 0 && !RoleType.NORMAL_ROLE.equals(role.getRoleType())) {
            errors.reject(ErrorCode.APP_2074_ROLE_CANNOT_CHANGE_STATUS, new Object[] {"" + id}, ErrorCode.APP_2074_ROLE_CANNOT_CHANGE_STATUS);
        }

        return !errors.hasErrors();
    }
}
