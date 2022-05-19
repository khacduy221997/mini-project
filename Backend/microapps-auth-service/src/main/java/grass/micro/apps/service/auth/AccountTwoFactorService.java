package grass.micro.apps.service.auth;

import grass.micro.apps.model.auth.AccountTwoFactor;
import grass.micro.apps.model.auth.TwoFactorType;
import grass.micro.apps.service.GenericService;

public interface AccountTwoFactorService extends GenericService<AccountTwoFactor, Integer> {
    AccountTwoFactor getByAccountIdAndTwoFactorType(int accountId, TwoFactorType type);
}
