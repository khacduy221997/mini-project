package grass.micro.apps.dao.auth;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import grass.micro.apps.dao.base.PersistentCrudRepository;
import grass.micro.apps.model.auth.Department;

public interface DepartmentDao extends PersistentCrudRepository<Department, Integer>, DepartmentDaoCustomMethods {
    @Override
    @Query("SELECT u FROM Department u WHERE u.status = grass.micro.apps.model.base.Status.ACTIVE")
    Iterable<Department> findAllActivated();
    
    @Override
    @Query("SELECT u FROM Department u WHERE u.status != grass.micro.apps.model.base.Status.DELETED")
    Iterable<Department> findAllAvailable();

    // JPQL
    @Query("SELECT u FROM Department u WHERE u.company.id = :companyId AND u.status = grass.micro.apps.model.base.Status.ACTIVE")
    List<Department> findByCompanyId(@Param("companyId") int companyId);

}
