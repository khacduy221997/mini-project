package grass.micro.apps.model.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import grass.micro.apps.model.base.AbstractIdentifiedEntity;

@Entity
@Table(name = "m_permission")
public class Permission extends AbstractIdentifiedEntity implements Serializable {

    private static final long serialVersionUID = 3953930388323756231L;

    /** permissionName for Authorization. */
    @Column(name = "permission_name")
    private String permissionName;

    /**
     * Group id of related permission.
     * 
     * @see PermissionConstant
     */
    @Column(name = "permission_group")
    private int permissionGroup;

    /**
     * Group name of related permission.
     * 
     * @see PermissionConstant
     */
    @Column(name = "permission_group_name")
    private String permissionGroupName;

    /**
     * Default Description: [ENG] Lang.
     */
    @Column(name = "description")
    private String description;

    /** Constructor . */
    public Permission() {
    }

    /**
     * @return the permissionName
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * @param permissionName
     *            the permissionName to set
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    /**
     * @return the permissionGroup
     */
    public int getPermissionGroup() {
        return permissionGroup;
    }

    /**
     * @param permissionGroup
     *            the permissionGroup to set
     */
    public void setPermissionGroup(int permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get value of <b>permissionGroupName</b>.
     * 
     * @return the permissionGroupName
     */
    public String getPermissionGroupName() {
        return permissionGroupName;
    }

    /**
     * Set value to <b>permissionGroupName</b>.
     * 
     * @param permissionGroupName
     *            the permissionGroupName to set
     */
    public void setPermissionGroupName(String permissionGroupName) {
        this.permissionGroupName = permissionGroupName;
    }

    @Override
    public String toString() {
        return "Permission [permissionName=" + permissionName + ", permissionGroup=" + permissionGroup
                + ", permissionGroupName=" + permissionGroupName + ", description=" + description + "]";
    }

}