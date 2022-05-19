package grass.micro.apps.model.auth.util;

import javax.persistence.AttributeConverter;

import grass.micro.apps.model.auth.AccountType;

public class AccountTypeConverter implements AttributeConverter<AccountType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AccountType type) {
        return type.getValue();
    }

    @Override
    public AccountType convertToEntityAttribute(Integer value) {
        return AccountType.fromValue(value);
    }

}
