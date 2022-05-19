package grass.micro.apps.auth.web.form;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserPasswordForm implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 8700336159263082239L;
    @JsonProperty("old_password")
    private String oldPassword;
    
    @JsonProperty("new_password")
    private String newPassword;

    /**
     * get value of <b>oldPassword</b>.
     * 
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Set value to <b>oldPassword</b>.
     * 
     * @param oldPassword
     *            the oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * get value of <b>newPassword</b>.
     * 
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Set value to <b>newPassword</b>.
     * 
     * @param newPassword
     *            the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "UserPasswordForm [oldPassword=" + oldPassword + ", newPassword=" + newPassword + "]";
    }

}
