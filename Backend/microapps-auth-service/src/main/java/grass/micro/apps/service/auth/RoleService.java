package grass.micro.apps.service.auth;

import java.util.List;

import grass.micro.apps.model.auth.Role;
import grass.micro.apps.service.GenericService;

public interface RoleService extends GenericService<Role, Integer> {
    
    /**
     * Get activate {@link Role} by given roleName.
     * 
     * @param roleName
     * @return
     */
    Role getByRoleName(String roleName);

    List<Role> getAllAvailable();

}