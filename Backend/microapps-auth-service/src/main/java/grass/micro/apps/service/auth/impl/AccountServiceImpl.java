package grass.micro.apps.service.auth.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import grass.micro.apps.dao.auth.AccountDao;
import grass.micro.apps.model.auth.Account;
import grass.micro.apps.model.auth.AccountType;
import grass.micro.apps.model.base.Status;
import grass.micro.apps.model.dto.SearchCondition;
import grass.micro.apps.service.auth.AccountService;
import grass.micro.apps.service.exception.AccountNotFoundException;
import grass.micro.apps.service.exception.ServiceException;
import grass.micro.apps.service.impl.GenericServiceImpl;
import grass.micro.apps.util.SystemUtils;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class AccountServiceImpl extends GenericServiceImpl<Account, Integer> implements AccountService {
	private static Logger logger = Logger.getLogger(AccountServiceImpl.class);
    
    @Autowired
    private AccountDao accountDao;

    @Override
    public Account getByEmail(String email, AccountType accountType) {
        try {
        	Account account = this.accountDao.findByEmail(email, accountType);
        	logger.info("Account: 11 " + account);
            return account;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public Account getByLoginName(String loginName) {
        try {
            return this.accountDao.findByLoginName(loginName);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public Account getById(int id) {
        try {
            return this.accountDao.findById(id).orElse(null);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Account> getAll() {
    	List<Account> result = new LinkedList<>();
        
    	try {
            Iterable<Account> temp = this.accountDao.findAll();
            if (temp != null) {
            	for (Account account : temp) {
					result.add(account);
				}
            }
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
        
        return result;
    }
    
    @Override
    public List<Account> getAllAvailable() {
        try {
            return this.accountDao.findAllByStatus(Arrays.asList(Status.ACTIVE));
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public Account login(String loginName, String password) throws ServiceException {
        Account account = null;
        try {
            account = this.accountDao.findByLoginName(loginName);
            if (account != null) {
                String encodedPassword = SystemUtils.getInstance()
                        .md5(password + account.getSalt());
                if (!account.isActivated() || !encodedPassword.equals(account.getPassword())) {
                    account = null;
                }
            }
        } catch (Exception ex) {
        	// roleback
            throw new ServiceException(ex.getMessage(), ex);
        }

        return account;
    }

    @Override
    public CrudRepository<Account, Integer> getDao() {
        return this.accountDao;
    }

    /**
     * 0. Get Account by Email
     * 1. Generate Reset Password
     * 2. Generate Email Content
     * 3. Send email
     * 4. Update Account Information
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Account resetPassword(String email, AccountType accountType, int expiration) {

        Account account = this.getByEmail(email, accountType);
        if (account == null) {
            throw new AccountNotFoundException("No Account found with given email: " + email);
        }

        String resetPasswordKey = "";
        try {
            resetPasswordKey = SystemUtils.getInstance().md5(account.getEmail() + "#" + System.currentTimeMillis());
        } catch (Exception ex) {
            throw new ServiceException("Can not genarate md5 resetPasswordKey");
        }

        account = this.resetPassword(account, resetPasswordKey, expiration);
        return account;
    }

    private Account resetPassword(Account account, String resetPasswordKey, int expiration) {
        LocalDateTime currentTime = SystemUtils.getInstance().getCurrentDate();
        LocalDateTime expiredTime = currentTime.plusHours(expiration);
        
        account.setResetPasswordKey(resetPasswordKey);
        account.setResetPasswordExpired(expiredTime);
        account = accountDao.save(account);
        return account;
    }

    @Override
    public void changePassword(Integer accountId, String password) {
        Account account = this.get(accountId);

        if (account == null) {
            throw new ServiceException("No Account found with given id: " + accountId);
        }

        String passwordMd5 = "";
        try {
            passwordMd5 = SystemUtils.getInstance().md5(password + account.getSalt());
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }

        account.setPassword(passwordMd5);
        account.setResetPasswordKey(null);
        account.setResetPasswordExpired(null);
        accountDao.save(account);
    }

    @Override
    public Account getByResetPasswodKey(String resetPasswordKey) {
        Account account = null;
        try {
            LocalDateTime resetPasswordExpired = SystemUtils.getInstance().getCurrentDate();
            account = this.accountDao.findByResetPasswodKeyAndResetPasswordExpired(resetPasswordKey, resetPasswordExpired);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
        return account;
    }

    @Override
    public Account getByUserId(int id) {
        return this.accountDao.findByUserId(id);
    }
    
	@Override
	public List<Account> search(SearchCondition condition) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getByCustomerId(int id) {
		return this.accountDao.findByCustomerId(id);
	}

}
