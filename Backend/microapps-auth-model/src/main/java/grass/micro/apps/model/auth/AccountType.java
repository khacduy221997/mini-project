package grass.micro.apps.model.auth;

public enum AccountType {
    STAFF(0), CUSTOMER(1);

    private int value;

    /**
     * @param value
     */
    private AccountType(int value) {
        this.value = value;
    }

    public static AccountType fromValue(int value) {
        AccountType type = STAFF;
        
        switch (value) {
            case 0:
                type = STAFF;
                break;
            case 1:
                type = CUSTOMER;
                break;
            default:
                break;
        }
        
        return type;
    }

    /**
     * get value of <b>value</b>.
     * 
     * @return the value
     */
    public int getValue() {
        return value;
    }

}
