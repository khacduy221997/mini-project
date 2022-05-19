package grass.micro.apps.auth.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import grass.micro.apps.web.dto.StatusTimestampDto;

public class UserDto extends StatusTimestampDto implements Serializable {
    private static final long serialVersionUID = 7080250186030178126L;

    private String fullname;

    @JsonProperty("department_id")
    private Integer departmentId;

    @JsonProperty("department_name")
    private String departmentName;

    @JsonProperty("company_id")
    private Integer companyId;

    @JsonProperty("company_name")
    private String companyName;

    private String address;

    private String mobile;
    
    private String telephone;
    
    @JsonProperty("telephone_ext")
    private String telephoneExt;
    
    private AccountDto account = new AccountDto();

    @JsonInclude(value = Include.NON_NULL)
    private List<RoleDto> roles;
    
    /**
     * get value of <b>fullname</b>.
     * 
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Set value to <b>fullname</b>.
     * 
     * @param fullname
     *            the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * get value of <b>departmentId</b>.
     * 
     * @return the departmentId
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     * Set value to <b>departmentId</b>.
     * 
     * @param departmentId
     *            the departmentId to set
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * get value of <b>departmentName</b>.
     * 
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Set value to <b>departmentName</b>.
     * 
     * @param departmentName
     *            the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * get value of <b>companyId</b>.
     * 
     * @return the companyId
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * Set value to <b>companyId</b>.
     * 
     * @param companyId
     *            the companyId to set
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * get value of <b>companyName</b>.
     * 
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set value to <b>companyName</b>.
     * 
     * @param companyName
     *            the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * get value of <b>address</b>.
     * 
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set value to <b>address</b>.
     * 
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get value of <b>mobile</b>.
     * 
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Set value to <b>mobile</b>.
     * 
     * @param mobile
     *            the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * get value of <b>account</b>.
     * 
     * @return the account
     */
    public AccountDto getAccount() {
        return account;
    }

    /**
     * Set value to <b>account</b>.
     * 
     * @param account
     *            the account to set
     */
    public void setAccount(AccountDto account) {
        this.account = account;
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
    public List<RoleDto> getRoles() {
        return roles;
    }

    /**
     * Set value to <b>roles</b>.
     * @param roles the roles to set
     */
    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto [fullname=" + fullname + ", departmentId=" + departmentId + ", departmentName=" + departmentName
                + ", companyId=" + companyId + ", companyName=" + companyName + ", address=" + address + ", mobile="
                + mobile + ", telephone=" + telephone + ", telephoneExt=" + telephoneExt + ", account=" + account
                + ", roles=" + roles + "]";
    }

}
