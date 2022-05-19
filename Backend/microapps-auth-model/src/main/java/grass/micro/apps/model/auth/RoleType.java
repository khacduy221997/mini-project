package grass.micro.apps.model.auth;

public enum RoleType {
    NORMAL_ROLE(0), SYSTEM_ROLE(1), DEFAULT_ROLE(2);

    private int value;

    /**
     * @param value
     */
    private RoleType(int value) {
        this.value = value;
    }

    public static RoleType fromValue(int value) {
        RoleType type = NORMAL_ROLE;
        switch (value) {
            case 0:
                type = NORMAL_ROLE;
                break;
            case 1:
                type = SYSTEM_ROLE;
                break;
            case 2:
                type = DEFAULT_ROLE;
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
