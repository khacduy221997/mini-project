package grass.micro.apps.service.auth;

import java.util.List;

import grass.micro.apps.model.auth.Account;
import grass.micro.apps.model.auth.AccountType;
import grass.micro.apps.service.GenericService;
import grass.micro.apps.service.exception.ServiceException;

public interface AccountService extends GenericService<Account, Integer> {

    /**
     * Get activated {@link Account} entity by given email.
     * @param email given email as parameter.
     * @param accountType 
     * @return {@link Account} instance if existed. Otherwise, null will be returned.
     * @throws ServiceException wrapped from persistent layer.
     */
    Account getByEmail(String email, AccountType accountType) throws ServiceException;
    
    /**
     * Get activated {@link Account} entity by given loginName.
     * @param loginName given email as parameter.
     * @return {@link Account} instance if existed. Otherwise, null will be returned.
     * @throws ServiceException wrapped from persistent layer.
     */
    Account getByLoginName(String loginName) throws ServiceException;

    /**
     * Try authenticate a Account with given loginName/password.
     * @param loginName given loginName
     * @param password given password.
     * @return {@link Account} instance if login successful.
     */
    Account login(String loginName, String password);
    
    /**
     * Get {@link Account} by given id.
     * @param id given id.
     * @return {@link Account} instance.
     */
    Account getById(int id);
    
    /**
     * Get all {@link Account}.
     * @return {@link List} of {@link Account} instance.
     */
    List<Account> getAll();
    
    /**
     * Get all available {@link Account} (VALID and DRAFT status).
     * @return {@link List} of {@link Account} instance.
     */
    List<Account> getAllAvailable();

    /**
     * Reset {@link Account} random password by given email.
     * After that, send a notification email to user.
     * @param email given email
     * @param expiration 
     * @param accountType 
     * @return 
     * @throws ServiceException if any issue occur from persistence.
     */
    Account resetPassword(String email, AccountType accountType, int expiration) throws ServiceException;

    /**
     * Change password of Account by given Id and new password. 
     * @param id given user id.
     * @param password new password to change
     * @throws ServiceException if any issue occur from persistence.
     */
    void changePassword(Integer id, String password) throws ServiceException;

    /**
     * Get {@link Account} instance by given resetPasswordKey.
     * @param resetPasswordKey reset password key use to restore user information.
     * @return {@link Account} instance.
     * @throws ServiceException if any issue occur from persistence.
     */
    Account getByResetPasswodKey(String resetPasswordKey) throws ServiceException;
    
    /**
     * Get {@link Account} by given user id.
     * @param id user id
     * @return
     */
    Account getByUserId(int id);

	Account getByCustomerId(int id);


}