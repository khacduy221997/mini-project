package grass.micro.apps.model.auth;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import grass.micro.apps.model.auth.util.AccountTypeConverter;
import grass.micro.apps.model.base.StatusLockAuditableEntity;
import grass.micro.apps.model.util.LocalDateTimeAttributeConverter;

@Entity
@Table(name = "t_account", uniqueConstraints={
        @UniqueConstraint(columnNames = {"account_type", "email"})})
public class Account extends StatusLockAuditableEntity implements Serializable {

    private static final long serialVersionUID = 3341015079064230087L;

    @Column(name = "account_type", nullable = false)
    @Convert(converter = AccountTypeConverter.class)
    private AccountType accountType = AccountType.STAFF;
    
    @Column(name = "email", nullable = false, length = 128)
    private String email;
    
    @Column(name = "login_name", nullable = false, length = 255)
    private String loginName;
    
    @Column(name = "password", nullable = false, length = 128)
    private String password;
    
    @Column(name = "salt", nullable = false, length = 128)
    private String salt;
    
    @Column(name = "reset_password_key")
    @Size(max = 64)
    private String resetPasswordKey;

    @Column(name = "reset_password_expired", columnDefinition = "DATETIME")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime resetPasswordExpired;

    /**
     * get value of <b>accountType</b>.
     * @return the accountType
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Set value to <b>accountType</b>.
     * @param accountType the accountType to set
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * get value of <b>email</b>.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set value to <b>email</b>.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get value of <b>loginName</b>.
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Set value to <b>loginName</b>.
     * @param loginName the loginName to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * get value of <b>password</b>.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set value to <b>password</b>.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get value of <b>salt</b>.
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Set value to <b>salt</b>.
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * get value of <b>resetPasswordKey</b>.
     * @return the resetPasswordKey
     */
    public String getResetPasswordKey() {
        return resetPasswordKey;
    }

    /**
     * Set value to <b>resetPasswordKey</b>.
     * @param resetPasswordKey the resetPasswordKey to set
     */
    public void setResetPasswordKey(String resetPasswordKey) {
        this.resetPasswordKey = resetPasswordKey;
    }

    /**
     * get value of <b>resetPasswordExpired</b>.
     * @return the resetPasswordExpired
     */
    public LocalDateTime getResetPasswordExpired() {
        return resetPasswordExpired;
    }

    /**
     * Set value to <b>resetPasswordExpired</b>.
     * @param resetPasswordExpired the resetPasswordExpired to set
     */
    public void setResetPasswordExpired(LocalDateTime resetPasswordExpired) {
        this.resetPasswordExpired = resetPasswordExpired;
    }

    @Override
    public String toString() {
        return "Account [accountType=" + accountType + ", email=" + email + ", loginName=" + loginName + ", password="
                + password + ", salt=" + salt + ", resetPasswordKey=" + resetPasswordKey + ", resetPasswordExpired="
                + resetPasswordExpired + ", getId()=" + getId() + "]";
    }
    
}
