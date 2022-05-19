package grass.micro.apps.service.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import grass.micro.apps.dao.auth.AccountTwoFactorDao;
import grass.micro.apps.model.auth.AccountTwoFactor;
import grass.micro.apps.model.auth.TwoFactorType;
import grass.micro.apps.model.dto.SearchCondition;
import grass.micro.apps.service.auth.AccountTwoFactorService;
import grass.micro.apps.service.exception.ServiceException;
import grass.micro.apps.service.impl.GenericServiceImpl;

@Service
public class AccountTwoFactorServiceImpl extends GenericServiceImpl<AccountTwoFactor, Integer>
        implements AccountTwoFactorService {
    @Autowired
    private AccountTwoFactorDao accountTwoFactorDao;

    @Override
    public CrudRepository<AccountTwoFactor, Integer> getDao() {
        return this.accountTwoFactorDao;
    }

    @Override
    public List<AccountTwoFactor> search(SearchCondition condition) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AccountTwoFactor getByAccountIdAndTwoFactorType(int accountId, TwoFactorType type) {
        return this.accountTwoFactorDao.findByAccountIdAndTwoFactorType(accountId, type);
    }

}
