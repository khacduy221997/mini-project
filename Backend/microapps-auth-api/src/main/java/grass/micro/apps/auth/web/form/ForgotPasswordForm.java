package grass.micro.apps.auth.web.form;

import com.fasterxml.jackson.annotation.JsonProperty;

import grass.micro.apps.model.auth.AccountType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ForgotPasswordForm implements Serializable {
    public static Map<String, String> fieldMap = new LinkedHashMap<String, String>();

    static {
        fieldMap.put("email", "email");
        fieldMap.put("accountType", "accountType");
    }

    private static final long serialVersionUID = 675658644345061593L;

    @JsonProperty("email")
    private String email;
    
    @JsonProperty("account_type")
    private AccountType accountType = AccountType.CUSTOMER;

    /**
     * get value of <b>email</b>.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set value to <b>email</b>.
     * 
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "ForgotPasswordForm [email=" + email + ", accountType=" + accountType + "]";
	}

}
