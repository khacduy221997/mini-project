package grass.micro.apps.auth.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import grass.micro.apps.web.dto.StatusTimestampDto;

public class CompanyDto extends StatusTimestampDto implements Serializable {

    private static final long serialVersionUID = -99472303439437568L;

    @JsonProperty("short_name")
    private String shortName;

    private String fullname;

    private String address;

    private String tax;

    private String fax;

    private String telephone;

    private String website;

    @JsonInclude(value = Include.NON_NULL)
    private CityDto city;

    @JsonInclude(value = Include.NON_NULL)
    private DistrictDto district;

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
     * get value of <b>city</b>.
     * 
     * @return the city
     */
    public CityDto getCity() {
        return city;
    }

    /**
     * Set value to <b>city</b>.
     * 
     * @param city
     *            the city to set
     */
    public void setCity(CityDto city) {
        this.city = city;
    }

    /**
     * get value of <b>district</b>.
     * 
     * @return the district
     */
    public DistrictDto getDistrict() {
        return district;
    }

    /**
     * Set value to <b>district</b>.
     * 
     * @param district
     *            the district to set
     */
    public void setDistrict(DistrictDto district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "CompanyDto [shortName=" + shortName + ", fullname=" + fullname + ", address=" + address + ", tax=" + tax
                + ", fax=" + fax + ", telephone=" + telephone + ", website=" + website + ", city=" + city
                + ", district=" + district + "]";
    }

}
