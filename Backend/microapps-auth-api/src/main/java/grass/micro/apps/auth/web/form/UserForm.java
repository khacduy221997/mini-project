package grass.micro.apps.auth.web.form;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class UserForm implements Serializable {

    private static final long serialVersionUID = 5938394685838413759L;

    /*
     * ACCOUNT info
     */
    private String email;
    
    @JsonProperty("login_name")
    private String loginName;
    
    private String password;
    
    /*
     * STAFF info
     */
    private String fullname;
    
    @JsonProperty("department_id")
    private Integer departmentId;
    
    @JsonProperty("company_id")
    private Integer companyId;
    
    private String address;

    private String mobile;
    
    private String telephone;
    
    @JsonProperty("telephone_ext")
    private String telephoneExt;
    
    private List<Integer> roles = new LinkedList<>();
    
    private String picture;

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
     * get value of <b>fullname</b>.
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Set value to <b>fullname</b>.
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * get value of <b>departmentId</b>.
     * @return the departmentId
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     * Set value to <b>departmentId</b>.
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * get value of <b>companyId</b>.
     * @return the companyId
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * Set value to <b>companyId</b>.
     * @param companyId the companyId to set
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * get value of <b>address</b>.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set value to <b>address</b>.
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get value of <b>mobile</b>.
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Set value to <b>mobile</b>.
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * get value of <b>telephone</b>.
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Set value to <b>telephone</b>.
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * get value of <b>telephoneExt</b>.
     * @return the telephoneExt
     */
    public String getTelephoneExt() {
        return telephoneExt;
    }

    /**
     * Set value to <b>telephoneExt</b>.
     * @param telephoneExt the telephoneExt to set
     */
    public void setTelephoneExt(String telephoneExt) {
        this.telephoneExt = telephoneExt;
    }

    /**
     * get value of <b>roles</b>.
     * @return the roles
     */
    public List<Integer> getRoles() {
        return roles;
    }

    /**
     * Set value to <b>roles</b>.
     * @param roles the roles to set
     */
    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }

    public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "UserForm [email=" + email + ", loginName=" + loginName + ", password=" + password + ", fullname="
				+ fullname + ", departmentId=" + departmentId + ", companyId=" + companyId + ", address=" + address
				+ ", mobile=" + mobile + ", telephone=" + telephone + ", telephoneExt=" + telephoneExt + ", roles="
				+ roles + ", picture=" + picture + "]";
	}

}
