package grass.micro.apps.auth.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PermissionDto implements Serializable {

    private static final long serialVersionUID = 2833753741542393864L;

    private Integer id;

    @JsonProperty("permission_name")
    private String permissionName;

    private String description;

    /**
     * get value of <b>id</b>.
     * 
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set value to <b>id</b>.
     * 
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * get value of <b>permissionName</b>.
     * 
     * @return the permissionName
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * Set value to <b>permissionName</b>.
     * 
     * @param permissionName
     *            the permissionName to set
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    /**
     * get value of <b>description</b>.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set value to <b>description</b>.
     * 
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PermissionDto [id=" + id + ", permissionName=" + permissionName + ", description=" + description + "]";
    }

}
