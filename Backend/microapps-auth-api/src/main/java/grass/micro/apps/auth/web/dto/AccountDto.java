package grass.micro.apps.auth.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import grass.micro.apps.model.auth.AccountType;
import grass.micro.apps.web.dto.StatusTimestampDto;

public class AccountDto extends StatusTimestampDto implements Serializable {

    private static final long serialVersionUID = -8668469809002506735L;

    @JsonProperty("login_name")
    private String loginName;

    private String email;

    @JsonProperty("account_type")
    private AccountType accountType;

    /**
     * get value of <b>loginName</b>.
     * 
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Set value to <b>loginName</b>.
     * 
     * @param loginName
     *            the loginName to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

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

    /**
     * get value of <b>accountType</b>.
     * 
     * @return the accountType
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Set value to <b>accountType</b>.
     * 
     * @param accountType
     *            the accountType to set
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "AccountDto [id=" + super.getId() + ", loginName=" + loginName + ", email=" + email + "]";
    }

}
