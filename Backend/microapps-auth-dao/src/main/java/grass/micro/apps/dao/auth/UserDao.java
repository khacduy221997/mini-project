package grass.micro.apps.dao.auth;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import grass.micro.apps.dao.base.PersistentCrudRepository;
import grass.micro.apps.model.auth.User;
import grass.micro.apps.model.base.Status;

public interface UserDao extends PersistentCrudRepository<User, Integer>, UserDaoCustomMethods {
    
    @Override
    @Query("SELECT u FROM User u WHERE u.status = grass.micro.apps.model.base.Status.ACTIVE")
    Iterable<User> findAllActivated();
    
    @Override
    @Query("SELECT u FROM User u WHERE u.status != grass.micro.apps.model.base.Status.DELETED")
    Iterable<User> findAllAvailable();
    
    @Query("SELECT u FROM User u WHERE u.id = :id")
    User findById(@Param("id") int id);
    
    @Query("SELECT u FROM User u")
    List<User> findAll();
    
    @Query("SELECT u FROM User u WHERE u.status IN (:statusList)")
	List<User> findAllByStatus(@Param("statusList") List<Status> statusList);

    @Query("SELECT u FROM User u WHERE u.account.id = :accountId"
            + " AND u.status = grass.micro.apps.model.base.Status.ACTIVE")
    User findByAccountId(@Param("accountId") int accountId);
}
