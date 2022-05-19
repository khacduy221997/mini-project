package grass.micro.apps.auth.web.util;

public class ErrorCode {
    public static final String APP_1000_SYSTEM_ERROR = "1000";
    public static final String APP_1001_REQUIRED = "1001";
    
    public static final String APP_2011_LOGIN_FAIL = "2011";
    
    public static final String APP_2021_EMAIL_INVALID = "2021";
    public static final String APP_2022_ACCOUNT_NOT_EXIST = "2022";
    public static final String APP_2023_ACCOUNT_IS_BLOCKED = "2023";
    
    public static final String APP_2031_KEY_REQUIRED = "2031";
    public static final String APP_2032_KEY_INVALID = "2032";
    
    public static final String APP_2041_ACCOUNT_EXISTED = "2041";
    public static final String APP_2042_KEY_INVALID = "2042";

    public static final String APP_2051_USER_WITH_EMAIL_EXISTED = "2051";
    public static final String APP_2052_USER_WITH_LOGIN_NAME_EXISTED = "2052";
    public static final String APP_2053_USER_IS_NOT_EXISTED = "2053";
    public static final String APP_2054_USER_IS_NOT_INIT_2FA = "2054";
    public static final String APP_2055_WRONG_VERIFICATION_CODE = "2055";
    public static final String APP_2056_OLD_PASSWORD_NOT_MATCH = "2056";

    public static final String APP_2071_PERMISSIONS_IS_NOT_EMPTY = "2071";
    public static final String APP_2072_ROLE_NAME_HAS_BEEN_USED = "2072";
    public static final String APP_2073_ROLE_WAS_NOT_EXISTED = "2073";
    public static final String APP_2074_ROLE_CANNOT_CHANGE_STATUS = "2074";
    
    private ErrorCode() {}
}
