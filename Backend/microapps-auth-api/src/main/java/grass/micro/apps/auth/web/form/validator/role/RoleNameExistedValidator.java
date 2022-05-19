package grass.micro.apps.auth.web.form.validator.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.Serializable;

import grass.micro.apps.auth.web.form.RoleForm;
import grass.micro.apps.auth.web.util.ErrorCode;
import grass.micro.apps.model.auth.Role;
import grass.micro.apps.service.auth.RoleService;
import grass.micro.apps.web.form.validator.AbstractFormValidator;
import grass.micro.apps.web.util.WebConstants;

@Component
public class RoleNameExistedValidator extends AbstractFormValidator {
    @Autowired
    private RoleService roleService;

    @Override
    public boolean support(Serializable form) {
        return form instanceof RoleForm;
    }

    @Override
    public boolean doValidate(Serializable form, Errors errors) {
        RoleForm roleForm = (RoleForm) form;
        Role role = this.roleService.getByRoleName(roleForm.getRoleName());
        Integer id = (Integer) RequestContextHolder.getRequestAttributes()
                .getAttribute(WebConstants.APPS_API_RAW_PATH_VARIABLE_KEY, RequestAttributes.SCOPE_REQUEST);
        
        if (id != null) { // for update
            if (role != null && !role.getId().equals(id) && role.getRoleName().equals(roleForm.getRoleName())) {
                errors.reject(ErrorCode.APP_2072_ROLE_NAME_HAS_BEEN_USED, new Object[] { roleForm.getRoleName() },
                        ErrorCode.APP_2072_ROLE_NAME_HAS_BEEN_USED);
            }
        } else if (role != null && !role.isDeleted()) { // for create
            errors.reject(ErrorCode.APP_2072_ROLE_NAME_HAS_BEEN_USED, new Object[] { roleForm.getRoleName() },
                    ErrorCode.APP_2072_ROLE_NAME_HAS_BEEN_USED);
        }

        return !errors.hasErrors();
    }
}
