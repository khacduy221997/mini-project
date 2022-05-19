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
@Table(name = "m_department")
public class Department extends StatusLockAuditableEntity implements Serializable {

    private static final long serialVersionUID = 452633377316408453L;

    @Column(name = "short_name", nullable = false, unique = true, length = 255)
    private String shortName;

    @Column(name = "fullname", nullable = false, length = 512)
    private String fullname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public Department() {}

    public Department(Integer id) {
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
     * get value of <b>company</b>.
     * 
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Set value to <b>company</b>.
     * 
     * @param company
     *            the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Department [shortName=" + shortName + ", fullname=" + fullname + ", company=" + company + "]";
    }

}
