package grass.micro.apps.model.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import grass.micro.apps.model.base.StatusLockAuditableEntity;

@Entity
@Table(name = "m_company")
public class Company extends StatusLockAuditableEntity implements Serializable {

    private static final long serialVersionUID = 7417121909656681338L;

    @Column(name = "short_name", nullable = false, length = 255)
    private String shortName;

    @Column(name = "fullname", nullable = false, length = 512)
    private String fullname;

    @Column(name = "address", nullable = false, length = 2048)
    private String address;

    @Column(name = "tax", nullable = true, length = 32)
    private String tax;

    @Column(name = "fax", nullable = true, length = 32)
    private String fax;

    @Column(name = "telephone", nullable = true, length = 32)
    private String telephone;

    @Column(name = "website", nullable = true, length = 128)
    private String website;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    public Company() {}
    
    /**
     * Constructor from given id.
     * @param id Integer
     */
    public Company(Integer id) {
        this.setId(id);
    }

    /**
     * get value of <b>shortName</b>.
     * 
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Set value to <b>shortName</b>.
     * 
     * @param shortName
     *            the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

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
     * get value of <b>tax</b>.
     * 
     * @return the tax
     */
    public String getTax() {
        return tax;
    }

    /**
     * Set value to <b>tax</b>.
     * 
     * @param tax
     *            the tax to set
     */
    public void setTax(String tax) {
        this.tax = tax;
    }

    /**
     * get value of <b>fax</b>.
     * 
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Set value to <b>fax</b>.
     * 
     * @param fax
     *            the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * get value of <b>telephone</b>.
     * 
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Set value to <b>telephone</b>.
     * 
     * @param telephone
     *            the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * get value of <b>website</b>.
     * 
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Set value to <b>website</b>.
     * 
     * @param website
     *            the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * get value of <b>district</b>.
     * 
     * @return the district
     */
    public District getDistrict() {
        return district;
    }

    /**
     * Set value to <b>district</b>.
     * 
     * @param district
     *            the district to set
     */
    public void setDistrict(District district) {
        this.district = district;
    }

    /**
     * get value of <b>city</b>.
     * 
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * Set value to <b>city</b>.
     * 
     * @param city
     *            the city to set
     */
    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Company [shortName=" + shortName + ", fullname=" + fullname + ", address=" + address + ", tax=" + tax
                + ", fax=" + fax + ", telephone=" + telephone + ", website=" + website + ", district=" + district
                + ", city=" + city + "]";
    }

}
