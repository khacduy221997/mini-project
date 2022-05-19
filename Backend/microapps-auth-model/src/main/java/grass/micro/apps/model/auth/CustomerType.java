package grass.micro.apps.model.auth;

public enum CustomerType {
    INDIVIDUAL(0), ENTERPRISE(1);

    private int value;

    /**
     * @param value
     */
    private CustomerType(int value) {
        this.value = value;
    }

    public static CustomerType fromValue(int value) {
        CustomerType type = INDIVIDUAL;
        switch (value) {
            case 0:
                type = INDIVIDUAL;
                break;
            case 1:
                type = ENTERPRISE;
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
