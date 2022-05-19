package grass.micro.apps.auth.web.util;

public class ControllerAction {
    
    public static final String APP_ERROR_ACTION = "/error";
    
    /*
     * AUTH ACTION 
     */
    public static final String APP_AUTH_ACTION = "/auth";
    public static final String APP_AUTHORIZE_ACTION = "/authorize";
    public static final String APP_AUTH_FORGOT_PASSWORD_ACTION = "/forgot_password";
    public static final String APP_AUTH_RESET_PASSWORD_ACTION = "/reset_password";
    public static final String APP_AUTH_RESET_PASSWORD_CHECK_KEY_ACTION = "/reset_password/check";
    public static final String APP_AUTH_LOGIN_ACTION = "/login";
    public static final String APP_AUTH_REGISTER_ACTION = "/register";
    
    /*
     * COMPANY ACTION
     */
    public static final String APP_COMPANY_ACTION = "/companies";
    public static final String APP_COMPANY_DETAIL_ACTION = "/companies/{id}";
    public static final String APP_COMPANY_DETAIL_DEPARTMENT_ACTION = "/companies/{id}/departments";
    
    /*
     * DEPARTMENT ACTION
     */
    public static final String APP_DEPARTMENT_ACTION = "/departments";
    public static final String APP_DEPARTMENT_DETAIL_ACTION = "/departments/{id}";
    
    /*
     * DEPARTMENT ACTION
     */
    public static final String APP_CITY_ACTION = "/cities";
    public static final String APP_CITY_DETAIL_ACTION = "/cities/{id}";
    
    /*
     * USER ACTION 
     */
    public static final String APP_USER_ACTION = "/users";
    public static final String APP_USER_DETAIL_ACTION = "/users/{id}";
    
    /*
     * ROLE ACTION 
     */
    public static final String APP_ROLE_ACTION = "/roles";
    public static final String APP_ROLE_DETAIL_ACTION = "/roles/{id}";
    
    /*
     * PERMSSION ACTION 
     */
    public static final String APP_PERMISSION_ACTION = "/permissions";
    
    /*
     * SYSTEM ACTION
     */
    public static final String APP_SYSTEM_ACTION = "/s";
    public static final String APP_SETTING_ACTION = "/settings";

    /*
     * CUSTOMER ACTION
     */
    public static final String APP_CUSTOMER_ACTION = "/customers";
    
    /*
     * PRODUCT ACTION
     */
    public static final String APP_PRODUCT_ACTION = "/products/{segment}";
    public static final String APP_PRODUCT_DETAIL_ACTION = "/product-detail";

    /*
     * ORDER ACTION
     */
    public static final String APP_ORDER_ACTION = "/orders";
    
    private ControllerAction() {}
}
