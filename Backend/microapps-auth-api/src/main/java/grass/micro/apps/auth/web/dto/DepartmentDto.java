package grass.micro.apps.auth.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import grass.micro.apps.web.dto.StatusTimestampDto;

public class DepartmentDto extends StatusTimestampDto implements Serializable {

    private static final long serialVersionUID = 5646740888335330294L;

    @JsonProperty("short_name")
    private String shortName;

    private String fullname;

    @JsonInclude(value = Include.NON_NULL)
    private CompanyDto company;

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
     * get value of <b>company</b>.
     * 
     * @return the company
     */
    public CompanyDto getCompany() {
        return company;
    }

    /**
     * Set value to <b>company</b>.
     * 
     * @param company
     *            the company to set
     */
    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "DepartmentDto [shortName=" + shortName + ", fullname=" + fullname + ", company=" + company + "]";
    }

}
