package grass.micro.apps.model.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import grass.micro.apps.model.auth.util.RoleTypeConverter;
import grass.micro.apps.model.base.Status;
import grass.micro.apps.model.base.StatusLockAuditableEntity;

@Entity
@Table(name = "t_role")
public class Role extends StatusLockAuditableEntity implements Serializable {

    public static final int APP_ROLE_FOR_STAFF = 1;
    public static final int APP_ROLE_FOR_CUSTOMER = 2;

    private static final long serialVersionUID = 3953930388323756231L;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    /**
     * Role Description under I18N. Sample: role.operator.admin
     */
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "role_type", nullable = false)
    @Convert(converter = RoleTypeConverter.class)
    private RoleType roleType = RoleType.NORMAL_ROLE;

    /**
     * SUM of 2^[user_type].
     */
    @Column(name = "account_types", nullable = false)
    private int accountsTypes = APP_ROLE_FOR_STAFF;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "td_role_permission", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;

    @Transient
    private int staffCount = 0;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    /** Constructor . */
    public Role() {
    }

    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     *            the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
     * @return the roleType
     */
    public RoleType getRoleType() {
        return roleType;
    }

    /**
     * @param roleType
     *            the roleType to set
     */
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    /**
     * @return the accountsTypes
     */
    public int getAccountsTypes() {
        return accountsTypes;
    }

    /**
     * @param accountsTypes
     *            the accountsTypes to set
     */
    public void setAccountsTypes(int accountsTypes) {
        this.accountsTypes = accountsTypes;
    }

    /**
     * @return the permissions
     */
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions
     *            the permissions to set
     */
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * get value of <b>staffCount</b>.
     * 
     * @return the staffCount
     */
    public int getStaffCount() {
        this.staffCount = 0;
        if (this.users != null && this.users.size() > 0) {
            for (User user : this.users) {
                this.staffCount = !user.getStatus().equals(Status.DELETED) ? 1 : 0;
            }
        }

        return this.staffCount;
    }

    /**
     * get value of <b>users</b>.
     * 
     * @return the users
     */
    public List<User> getUsers() {
        if (this.users == null) {
            return new ArrayList<User>();
        }
        
        return this.users.stream().filter(user -> !user.getStatus().equals(Status.DELETED))
                .collect(Collectors.toList());
    }

    /**
     * Set value to <b>users</b>.
     * 
     * @param users
     *            the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Role [roleName=" + roleName + ", description=" + description + ", roleType=" + roleType
                + ", accountsTypes=" + accountsTypes + ", permissions=" + permissions + "]";
    }

}