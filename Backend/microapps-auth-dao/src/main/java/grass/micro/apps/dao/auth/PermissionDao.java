package grass.micro.apps.dao.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import grass.micro.apps.model.auth.Permission;

public interface PermissionDao extends JpaRepository<Permission, Integer>, PermissionDaoCustomMethods {

}
