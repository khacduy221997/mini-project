package grass.micro.apps.model.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import grass.micro.apps.model.base.AbstractIdentifiedEntity;

@Entity
@Table(name = "m_ward")
public class Ward extends AbstractIdentifiedEntity implements Serializable {

    private static final long serialVersionUID = 1119728299534827982L;

    @Column(name = "code", nullable = false, length = 5)
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "`type`", nullable = false, length = 30)
    private String type;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_code", referencedColumnName = "code")
    private District district;

    /**
     * get value of <b>code</b>.
     * 
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set value to <b>code</b>.
     * 
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * get value of <b>name</b>.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set value to <b>name</b>.
     * 
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get value of <b>type</b>.
     * 
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Set value to <b>type</b>.
     * 
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Ward [code=" + code + ", name=" + name + ", type=" + type + ", district=" + district + "]";
    }

}
