package grass.micro.apps.model.auth;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import grass.micro.apps.model.auth.util.CustomerTypeConverter;
import grass.micro.apps.model.base.StatusLockAuditableEntity;
import grass.micro.apps.model.util.JsonToMapConverter;

@Entity
@Table(name = "t_customer")
public class Customer extends StatusLockAuditableEntity {

    private static final long serialVersionUID = -908295375037534582L;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Account.class)
    @JoinTable(name = "td_customer_account", joinColumns =
            @JoinColumn(name = "customer_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "account_id"))
    private List<Account> accounts;

    @Column(name = "customer_type", nullable = true)
    @Convert(converter = CustomerTypeConverter.class)
    private CustomerType customerType;

    @Column(name = "company_id", nullable = true)
    private Integer companyId;

    @Column(name = "department", nullable = true, length = 128)
    private String department;

    @Column(name = "fullname", nullable = false, length = 255)
    private String fullname;
    
    @Column(name = "shortname", nullable = false, length = 32)
    private String shortname;
    
    @Column(name = "provice", nullable = true)
    private Integer provice;
    
    @Column(name = "city", nullable = true, length = 255)
    private String city;
    
    @Column(name = "address", nullable = true, length = 1024)
    private String address;
    
    @Column(name = "telephone", nullable = true, length = 16)
    private String telephone;
    
    @Column(name = "fax", nullable = true, length = 16)
    private String mobile;
    
    @Column(name = "website", nullable = true, length = 128)
    private String website;
    
    @Column(name = "business_license", nullable = true, length = 16)
    private String businessLicense;

    @Lob
    @Column(name = "picture", length = 131072) // 128KB
    private byte[] picture;
    
    @Column(name = "representative_name", nullable = true, length = 255)
    private String representativeName;
    
    @Column(name = "representative_email", nullable = true, length = 255)
    private String representativeEmail;
    
    @Column(name = "representative_phone", nullable = true, length = 16)
    private String representativePhone;
    
    @Column(name = "technical_name", nullable = true, length = 255)
    private String technicalName;
    
    @Column(name = "technical_email", nullable = true, length = 255)
    private String technicalEmail;
    
    @Column(name = "technical_phone", nullable = true, length = 16)
    private String technicalPhone;
    
    @Column(name = "accounting_name", nullable = true, length = 255)
    private String accountingName;
    
    @Column(name = "accounting_email", nullable = true, length = 255)
    private String accounting_email;
    
    @Column(name = "accounting_phone", nullable = true, length = 16)
    private String accountingPhone;
    
    @Column(name = "extra_information")
    @Convert(converter = JsonToMapConverter.class)
    private LinkedHashMap<String, Object> extraInformation;

    /**
     * get value of <b>accounts</b>.
     * @return the accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Set value to <b>accounts</b>.
     * @param accounts the accounts to set
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * get value of <b>customerType</b>.
     * @return the customerType
     */
    public CustomerType getCustomerType() {
        return customerType;
    }

    /**
     * Set value to <b>customerType</b>.
     * @param customerType the customerType to set
     */
    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
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
     * get value of <b>department</b>.
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Set value to <b>department</b>.
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
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
     * get value of <b>shortname</b>.
     * @return the shortname
     */
    public String getShortname() {
        return shortname;
    }

    /**
     * Set value to <b>shortname</b>.
     * @param shortname the shortname to set
     */
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    /**
     * get value of <b>provice</b>.
     * @return the provice
     */
    public Integer getProvice() {
        return provice;
    }

    /**
     * Set value to <b>provice</b>.
     * @param provice the provice to set
     */
    public void setProvice(Integer provice) {
        this.provice = provice;
    }

    /**
     * get value of <b>city</b>.
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set value to <b>city</b>.
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
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
     * get value of <b>website</b>.
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Set value to <b>website</b>.
     * @param website the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * get value of <b>businessLicense</b>.
     * @return the businessLicense
     */
    public String getBusinessLicense() {
        return businessLicense;
    }

    /**
     * Set value to <b>businessLicense</b>.
     * @param businessLicense the businessLicense to set
     */
    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    /**
     * get value of <b>picture</b>.
     * @return the picture
     */
    public byte[] getPicture() {
        return picture;
    }

    /**
     * Set value to <b>picture</b>.
     * @param picture the picture to set
     */
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    /**
     * get value of <b>representativeName</b>.
     * @return the representativeName
     */
    public String getRepresentativeName() {
        return representativeName;
    }

    /**
     * Set value to <b>representativeName</b>.
     * @param representativeName the representativeName to set
     */
    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    /**
     * get value of <b>representativeEmail</b>.
     * @return the representativeEmail
     */
    public String getRepresentativeEmail() {
        return representativeEmail;
    }

    /**
     * Set value to <b>representativeEmail</b>.
     * @param representativeEmail the representativeEmail to set
     */
    public void setRepresentativeEmail(String representativeEmail) {
        this.representativeEmail = representativeEmail;
    }

    /**
     * get value of <b>representativePhone</b>.
     * @return the representativePhone
     */
    public String getRepresentativePhone() {
        return representativePhone;
    }

    /**
     * Set value to <b>representativePhone</b>.
     * @param representativePhone the representativePhone to set
     */
    public void setRepresentativePhone(String representativePhone) {
        this.representativePhone = representativePhone;
    }

    /**
     * get value of <b>technicalName</b>.
     * @return the technicalName
     */
    public String getTechnicalName() {
        return technicalName;
    }

    /**
     * Set value to <b>technicalName</b>.
     * @param technicalName the technicalName to set
     */
    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    /**
     * get value of <b>technicalEmail</b>.
     * @return the technicalEmail
     */
    public String getTechnicalEmail() {
        return technicalEmail;
    }

    /**
     * Set value to <b>technicalEmail</b>.
     * @param technicalEmail the technicalEmail to set
     */
    public void setTechnicalEmail(String technicalEmail) {
        this.technicalEmail = technicalEmail;
    }

    /**
     * get value of <b>technicalPhone</b>.
     * @return the technicalPhone
     */
    public String getTechnicalPhone() {
        return technicalPhone;
    }

    /**
     * Set value to <b>technicalPhone</b>.
     * @param technicalPhone the technicalPhone to set
     */
    public void setTechnicalPhone(String technicalPhone) {
        this.technicalPhone = technicalPhone;
    }

    /**
     * get value of <b>accountingName</b>.
     * @return the accountingName
     */
    public String getAccountingName() {
        return accountingName;
    }

    /**
     * Set value to <b>accountingName</b>.
     * @param accountingName the accountingName to set
     */
    public void setAccountingName(String accountingName) {
        this.accountingName = accountingName;
    }

    /**
     * get value of <b>accounting_email</b>.
     * @return the accounting_email
     */
    public String getAccounting_email() {
        return accounting_email;
    }

    /**
     * Set value to <b>accounting_email</b>.
     * @param accounting_email the accounting_email to set
     */
    public void setAccounting_email(String accounting_email) {
        this.accounting_email = accounting_email;
    }

    /**
     * get value of <b>accountingPhone</b>.
     * @return the accountingPhone
     */
    public String getAccountingPhone() {
        return accountingPhone;
    }

    /**
     * Set value to <b>accountingPhone</b>.
     * @param accountingPhone the accountingPhone to set
     */
    public void setAccountingPhone(String accountingPhone) {
        this.accountingPhone = accountingPhone;
    }

    /**
     * get value of <b>extraInformation</b>.
     * @return the extraInformation
     */
    public LinkedHashMap<String, Object> getExtraInformation() {
        return extraInformation;
    }

    /**
     * Set value to <b>extraInformation</b>.
     * @param extraInformation the extraInformation to set
     */
    public void setExtraInformation(LinkedHashMap<String, Object> extraInformation) {
        this.extraInformation = extraInformation;
    }

    @Override
    public String toString() {
        return "Customer [accounts=" + accounts + ", customerType=" + customerType + ", companyId=" + companyId
                + ", department=" + department + ", fullname=" + fullname + ", shortname=" + shortname + ", provice="
                + provice + ", city=" + city + ", address=" + address + ", telephone=" + telephone + ", mobile="
                + mobile + ", website=" + website + ", businessLicense=" + businessLicense + ", picture="
                + Arrays.toString(picture) + ", representativeName=" + representativeName + ", representativeEmail="
                + representativeEmail + ", representativePhone=" + representativePhone + ", technicalName="
                + technicalName + ", technicalEmail=" + technicalEmail + ", technicalPhone=" + technicalPhone
                + ", accountingName=" + accountingName + ", accounting_email=" + accounting_email + ", accountingPhone="
                + accountingPhone + ", extraInformation=" + extraInformation + "]";
    }
    
}
