package grass.micro.apps.dao.auth;

import org.springframework.data.jpa.repository.Query;

import grass.micro.apps.dao.base.PersistentCrudRepository;
import grass.micro.apps.model.auth.Customer;

public interface CustomerDao extends PersistentCrudRepository<Customer, Integer>, CustomerDaoCustomMethods {
	@Override
    @Query("SELECT c FROM Customer c WHERE c.status = grass.micro.apps.model.base.Status.ACTIVE")
    Iterable<Customer> findAllActivated();
	
	@Override
    @Query("SELECT c FROM Customer c WHERE c.status != grass.micro.apps.model.base.Status.DELETED")
    Iterable<Customer> findAllAvailable();
}
