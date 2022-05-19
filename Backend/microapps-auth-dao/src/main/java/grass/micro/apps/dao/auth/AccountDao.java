package grass.micro.apps.dao.auth;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import grass.micro.apps.dao.base.PersistentCrudRepository;
import grass.micro.apps.model.auth.Account;
import grass.micro.apps.model.auth.AccountType;
import grass.micro.apps.model.base.Status;

public interface AccountDao extends PersistentCrudRepository<Account, Integer>, AccountDaoCustomMethods {
    
    @Override
    @Query("SELECT u FROM Account u WHERE u.status = grass.micro.apps.model.base.Status.ACTIVE")
    Iterable<Account> findAllActivated();
    
    @Override
    @Query("SELECT u FROM Account u WHERE u.status != grass.micro.apps.model.base.Status.DELETED")
    Iterable<Account> findAllAvailable();
    
    @Query("SELECT u FROM Account u WHERE u.email = :email and u.accountType = :accountType and u.status != grass.micro.apps.model.base.Status.DELETED")
    Account findByEmail(@Param("email") String email, @Param("accountType") AccountType accountType);
    
    @Query("SELECT u FROM Account u WHERE u.loginName = :loginName and u.status != grass.micro.apps.model.base.Status.DELETED")
    Account findByLoginName(@Param("loginName") String loginName);
    
    @Query("SELECT u FROM Account u WHERE u.resetPasswordKey = :resetPasswordKey"
            + " AND u.resetPasswordExpired > :resetPasswordExpired")
    Account findByResetPasswodKeyAndResetPasswordExpired(@Param("resetPasswordKey") String resetPasswordKey,
            @Param("resetPasswordExpired")  LocalDateTime resetPasswordExpired);

    @Query("SELECT u FROM Account u WHERE u.status IN (:statusList)")
    List<Account> findAllByStatus(@Param("statusList") List<Status> statusList);

    @Query("SELECT a FROM User u LEFT JOIN u.account a WHERE u.id = :id")
    Account findByUserId(@Param("id") int id);
    
    @Query("SELECT a FROM Customer c LEFT JOIN c.accounts a WHERE c.id = :customerId"
			+ " AND a.status = grass.micro.apps.model.base.Status.ACTIVE")
    Account findByCustomerId(@Param("customerId") int customerId);
}
