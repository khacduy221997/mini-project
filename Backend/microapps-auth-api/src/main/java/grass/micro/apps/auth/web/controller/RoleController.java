package grass.micro.apps.auth.web.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import grass.micro.apps.annotation.AppJsonSchema;
import grass.micro.apps.annotation.Logged;
import grass.micro.apps.annotation.Validation;
import grass.micro.apps.auth.web.dto.RoleDto;
import grass.micro.apps.auth.web.form.RoleForm;
import grass.micro.apps.auth.web.form.validator.role.RoleExistedValidator;
import grass.micro.apps.auth.web.form.validator.role.RoleNameExistedValidator;
import grass.micro.apps.auth.web.form.validator.role.RoleUpdateStatusValidator;
import grass.micro.apps.auth.web.util.AuthAppConstants;
import grass.micro.apps.auth.web.util.ControllerAction;
import grass.micro.apps.auth.web.util.DtoFetchingUtils;
import grass.micro.apps.auth.web.util.ErrorCode;
import grass.micro.apps.model.auth.AccountType;
import grass.micro.apps.model.auth.Permission;
import grass.micro.apps.model.auth.Role;
import grass.micro.apps.model.auth.RoleType;
import grass.micro.apps.model.auth.util.CommonPermissionConstants;
import grass.micro.apps.model.base.Status;
import grass.micro.apps.model.util.EntityUpdateTracker;
import grass.micro.apps.service.auth.PermissionService;
import grass.micro.apps.service.auth.RoleService;
import grass.micro.apps.web.component.ErrorsKeyConverter;
import grass.micro.apps.web.controller.support.AppControllerSupport;
import grass.micro.apps.web.dto.RpcResponse;
import grass.micro.apps.web.util.RequestUtils;

@RestController
public class RoleController {
    private static Logger logger = Logger.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ErrorsKeyConverter errorsProcessor;

    /**
     * Get list of Roles.
     * 
     * @param request
     *            {@link HttpServletRequest}
     * @param model
     *            {@link Model}
     * @return JSON Data for List of Roles.
     */
    @GetMapping(value = ControllerAction.APP_ROLE_ACTION)
    @RequiresPermissions(value = CommonPermissionConstants.ROLE_VIEW)
    @Logged
    public ResponseEntity<?> listRole(HttpServletRequest request, Model model) {
        RpcResponse result = new RpcResponse(ControllerAction.APP_ROLE_ACTION);
        List<Role> roles = this.roleService.getAllAvailable();
        List<RoleDto> dtos = DtoFetchingUtils.fetchRoles(roles);
        result.addAttribute("roles", dtos);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<RpcResponse>(result, status);
    }

    @PostMapping(value = ControllerAction.APP_ROLE_ACTION)
    @RequiresPermissions(value = CommonPermissionConstants.ROLE_CREATE)
    @Logged
    @Validation(schema = @AppJsonSchema(AuthAppConstants.APP_SCHEMA_ROLE_CREATE), validators = {
            RoleNameExistedValidator.class })
    public ResponseEntity<?> addRole(HttpServletRequest request, @RequestBody RoleForm form, BindingResult errors) {
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                Role role = new Role();
                role.setRoleName(form.getRoleName());
                role.setRoleType(RoleType.NORMAL_ROLE);
                role.setDescription(form.getDescription());
                role.setStatus(Status.ACTIVE);
                role.setAccountsTypes(AccountType.STAFF.getValue());
                List<Permission> permissions = RoleController.this.permissionService.get(form.getPermissions());
                role.setPermissions(permissions);

                try {
                    role = RoleController.this.roleService.create(role);
                    getRpcResponse().addAttribute("role", DtoFetchingUtils.fetchRole(role));
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                    errors.reject(ErrorCode.APP_1000_SYSTEM_ERROR);
                }
            }
        };

        return support.doSupport(request, null, errors, errorsProcessor);
    }
    
    @GetMapping(value = ControllerAction.APP_ROLE_DETAIL_ACTION)
    @RequiresPermissions(value = CommonPermissionConstants.ROLE_VIEW)
    @Logged
    @Validation(validators = { RoleExistedValidator.class })
    public ResponseEntity<?> getRoleDetail(@PathVariable("id") Integer id, HttpServletRequest request) {
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                try {
                    Role role = RoleController.this.roleService.get(id);
                    this.getRpcResponse().addAttribute("role", DtoFetchingUtils.fetchRole(role));
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                    errors.reject(ErrorCode.APP_1000_SYSTEM_ERROR); // service failure
                }
            }
        };
        return support.doSupport(request, null, RequestUtils.getInstance().getBindingResult(), errorsProcessor);
    }

    @PutMapping(value = ControllerAction.APP_ROLE_DETAIL_ACTION, consumes = "application/json")
    @RequiresPermissions(value = CommonPermissionConstants.ROLE_EDIT)
    @Logged
    @Validation(schema = @AppJsonSchema(AuthAppConstants.APP_SCHEMA_ROLE_UPDATE), validators = { RoleExistedValidator.class, RoleNameExistedValidator.class })
    public ResponseEntity<?> updateRole(@PathVariable("id") Integer id, HttpServletRequest request,
            @RequestBody @Valid RoleForm form, BindingResult errors) {
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                try {
                    Role role = RoleController.this.roleService.get(id);
                    role.setRoleName(form.getRoleName());
                    role.setDescription(form.getDescription());
                    role.setAccountsTypes(AccountType.STAFF.getValue());
                    List<Permission> permissions = RoleController.this.permissionService.get(form.getPermissions());
                    role.setPermissions(permissions);
                    
                    EntityUpdateTracker.getInstance().track(Role.class, role.getId(), role.getUpdateCount());
                    RoleController.this.roleService.update(role);
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                    errors.reject(ErrorCode.APP_1000_SYSTEM_ERROR); // service failure
                }
            }
        };
        return support.doSupport(request, null, errors, errorsProcessor);
    }

    @GetMapping(value = ControllerAction.APP_ROLE_DETAIL_ACTION + "/lock")
    @RequiresPermissions(value = CommonPermissionConstants.ROLE_EDIT)
    @Logged
    @Validation(validators = { RoleExistedValidator.class, RoleUpdateStatusValidator.class })
    public ResponseEntity<?> lockRole(@PathVariable("id") Integer id, HttpServletRequest request) {
        final BindingResult errors = RequestUtils.getInstance().getBindingResult();
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                RoleController.this.updateStatus(id, Status.INACTIVE, (BindingResult) errors);
            }
        };
        return support.doSupport(request, null, errors, errorsProcessor);
    }

    @GetMapping(value = ControllerAction.APP_ROLE_DETAIL_ACTION + "/unlock")
    @RequiresPermissions(value = CommonPermissionConstants.ROLE_EDIT)
    @Logged
    @Validation(validators = { RoleExistedValidator.class, RoleUpdateStatusValidator.class })
    public ResponseEntity<?> unlockRole(@PathVariable("id") Integer id, HttpServletRequest request) {
        final BindingResult errors = RequestUtils.getInstance().getBindingResult();
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                RoleController.this.updateStatus(id, Status.ACTIVE, (BindingResult) errors);
            }
        };
        return support.doSupport(request, null, errors, errorsProcessor);
    }

    @DeleteMapping(value = ControllerAction.APP_ROLE_DETAIL_ACTION)
    @RequiresPermissions(value = CommonPermissionConstants.ROLE_DELETE)
    @Logged
    @Validation(validators = { RoleExistedValidator.class, RoleUpdateStatusValidator.class })
    public ResponseEntity<?> deleteRole(@PathVariable("id") Integer id, HttpServletRequest request) {
        final BindingResult errors = RequestUtils.getInstance().getBindingResult();
        AppControllerSupport support = new AppControllerSupport() {
            @Override
            public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
                    ErrorsKeyConverter errorsProcessor) {
                RoleController.this.updateStatus(id, Status.DELETED, (BindingResult) errors);
            }
        };
        return support.doSupport(request, null, errors, errorsProcessor);
    }

    private void updateStatus(int id, Status status, BindingResult errors) {
        Role role = RoleController.this.roleService.get(id);
        role.setStatus(status);
        EntityUpdateTracker.getInstance().track(Role.class, role.getId(), role.getUpdateCount());
        RoleController.this.roleService.update(role);
    }
}
