package grass.micro.apps.dao.auth;

import org.springframework.data.jpa.repository.Query;

import grass.micro.apps.dao.base.PersistentCrudRepository;
import grass.micro.apps.model.auth.Company;

public interface CompanyDao extends PersistentCrudRepository<Company, Integer>, CompanyDaoCustomMethods {
    @Override
    @Query("SELECT u FROM Company u WHERE u.status = grass.micro.apps.model.base.Status.ACTIVE")
    Iterable<Company> findAllActivated();
    
    @Override
    @Query("SELECT u FROM Company u WHERE u.status != grass.micro.apps.model.base.Status.DELETED")
    Iterable<Company> findAllAvailable();
}
