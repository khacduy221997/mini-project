package grass.micro.apps.model.auth.dto;

import java.io.Serializable;

import grass.micro.apps.model.auth.AccountType;
import grass.micro.apps.model.base.Status;
import grass.micro.apps.model.dto.TimestampSearchCondition;

public class StaffSearchCondition extends TimestampSearchCondition implements Serializable {

    private static final long serialVersionUID = 7611662926902179712L;

    private String email;
    private boolean enableEmail;

    private String loginName;
    private boolean enableLoginName;

    private AccountType accountType;
    private boolean enableAccountType;

    private Status status;
    private boolean enableStatus;

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
     * get value of <b>enableEmail</b>.
     * 
     * @return the enableEmail
     */
    public boolean isEnableEmail() {
        return enableEmail;
    }

    /**
     * Set value to <b>enableEmail</b>.
     * 
     * @param enableEmail
     *            the enableEmail to set
     */
    public void setEnableEmail(boolean enableEmail) {
        this.enableEmail = enableEmail;
    }

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
     * get value of <b>enableLoginName</b>.
     * 
     * @return the enableLoginName
     */
    public boolean isEnableLoginName() {
        return enableLoginName;
    }

    /**
     * Set value to <b>enableLoginName</b>.
     * 
     * @param enableLoginName
     *            the enableLoginName to set
     */
    public void setEnableLoginName(boolean enableLoginName) {
        this.enableLoginName = enableLoginName;
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

    /**
     * get value of <b>enableAccountType</b>.
     * 
     * @return the enableAccountType
     */
    public boolean isEnableAccountType() {
        return enableAccountType;
    }

    /**
     * Set value to <b>enableAccountType</b>.
     * 
     * @param enableAccountType
     *            the enableAccountType to set
     */
    public void setEnableAccountType(boolean enableAccountType) {
        this.enableAccountType = enableAccountType;
    }

    /**
     * get value of <b>status</b>.
     * 
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set value to <b>status</b>.
     * 
     * @param status
     *            the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * get value of <b>enableStatus</b>.
     * 
     * @return the enableStatus
     */
    public boolean isEnableStatus() {
        return enableStatus;
    }

    /**
     * Set value to <b>enableStatus</b>.
     * 
     * @param enableStatus
     *            the enableStatus to set
     */
    public void setEnableStatus(boolean enableStatus) {
        this.enableStatus = enableStatus;
    }

    @Override
    public String toString() {
        return "StaffSearchCondition [email=" + email + ", enableEmail=" + enableEmail + ", loginName=" + loginName
                + ", enableLoginName=" + enableLoginName + ", accountType=" + accountType + ", enableAccountType="
                + enableAccountType + ", status=" + status + ", enableStatus=" + enableStatus + ", getCreatedDate()="
                + getCreatedDate() + ", isEnableCreatedDate()=" + isEnableCreatedDate() + ", getUpdatedDate()="
                + getUpdatedDate() + ", isEnableUpdatedDate()=" + isEnableUpdatedDate() + ", getSegment()="
                + getSegment() + ", getOffset()=" + getOffset() + ", isLimitSearch()=" + isLimitSearch() + "]";
    }

}
