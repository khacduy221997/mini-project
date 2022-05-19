package grass.micro.apps.model.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import grass.micro.apps.model.base.StatusLockAuditableEntity;

@Entity
@Table(name = "td_account_2fa", uniqueConstraints = { @UniqueConstraint(columnNames = { "auth_type", "account_id" }) })
public class AccountTwoFactor extends StatusLockAuditableEntity {

    private static final long serialVersionUID = 5801434124851410070L;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "auth_type")
    private TwoFactorType twoFactorType;

    @Column(name = "data")
    private String data;

    /**
     * get value of <b>accountId</b>.
     * 
     * @return the accountId
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * Set value to <b>accountId</b>.
     * 
     * @param accountId
     *            the accountId to set
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * get value of <b>twoFactorType</b>.
     * 
     * @return the twoFactorType
     */
    public TwoFactorType getTwoFactorType() {
        return twoFactorType;
    }

    /**
     * Set value to <b>twoFactorType</b>.
     * 
     * @param twoFactorType
     *            the twoFactorType to set
     */
    public void setTwoFactorType(TwoFactorType twoFactorType) {
        this.twoFactorType = twoFactorType;
    }

    /**
     * get value of <b>data</b>.
     * 
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * Set value to <b>data</b>.
     * 
     * @param data
     *            the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AccountTwoFactor [accountId=" + accountId + ", twoFactorType=" + twoFactorType + ", data=" + data + "]";
    }
}
