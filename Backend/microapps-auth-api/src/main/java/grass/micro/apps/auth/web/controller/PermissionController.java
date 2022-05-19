package grass.micro.apps.auth.web.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import grass.micro.apps.annotation.Logged;
import grass.micro.apps.auth.web.dto.PermissionGroupDto;
import grass.micro.apps.auth.web.util.ControllerAction;
import grass.micro.apps.auth.web.util.DtoFetchingUtils;
import grass.micro.apps.model.auth.Permission;
import grass.micro.apps.model.auth.util.CommonPermissionConstants;
import grass.micro.apps.service.auth.PermissionService;
import grass.micro.apps.web.dto.RpcResponse;

@RestController
public class PermissionController {
    
    @Autowired
    private PermissionService permissionService;

    /**
     * Get list of Permissions.
     * 
     * @param request
     *            {@link HttpServletRequest}
     * @param model
     *            {@link Model}
     * @return JSON Data for List of Permission.
     */
    @GetMapping(value = ControllerAction.APP_PERMISSION_ACTION)
    @RequiresPermissions(value = CommonPermissionConstants.ROLE_VIEW)
    @Logged
    public ResponseEntity<?> listRole(HttpServletRequest request, Model model) {
        RpcResponse result = new RpcResponse(ControllerAction.APP_PERMISSION_ACTION);
        List<Permission> permissions = this.permissionService.getAll();
        List<PermissionGroupDto> dtos = DtoFetchingUtils.fetchPermissions(permissions);
        result.addAttribute("groups", dtos);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<RpcResponse>(result, status);
    }

}
