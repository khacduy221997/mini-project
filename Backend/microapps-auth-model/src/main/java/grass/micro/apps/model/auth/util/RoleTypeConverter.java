package grass.micro.apps.model.auth.util;

import javax.persistence.AttributeConverter;

import grass.micro.apps.model.auth.RoleType;

public class RoleTypeConverter implements AttributeConverter<RoleType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RoleType type) {
        return type.getValue();
    }

    @Override
    public RoleType convertToEntityAttribute(Integer value) {
        return RoleType.fromValue(value);
    }

}
