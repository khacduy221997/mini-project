package grass.micro.apps.auth.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import grass.micro.apps.model.auth.RoleType;
import grass.micro.apps.model.base.Status;
import grass.micro.apps.web.dto.StatusTimestampDto;

public class RoleDto extends StatusTimestampDto implements Serializable {
    private static final long serialVersionUID = 7080250186030178126L;

    @JsonProperty("role_name")
    private String roleName;

    private String description;

    @JsonProperty("role_type")
    private RoleType roleType;

    private Status status;

    @JsonProperty("account_types")
    private int accountTypes;

    @JsonProperty("staff_count")
    private int staffCount;

    private List<PermissionDto> permissions;

    /**
     * get value of <b>roleName</b>.
     * 
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Set value to <b>roleName</b>.
     * 
     * @param roleName
     *            the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    /**
     * get value of <b>roleType</b>.
     * 
     * @return the roleType
     */
    public RoleType getRoleType() {
        return roleType;
    }

    /**
     * Set value to <b>roleType</b>.
     * 
     * @param roleType
     *            the roleType to set
     */
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    /**
     * get value of <b>status</b>.
     * 
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set value to <b>status</b>.
     * 
     * @param status
     *            the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * get value of <b>accountTypes</b>.
     * 
     * @return the accountTypes
     */
    public int getAccountTypes() {
        return accountTypes;
    }

    /**
     * Set value to <b>accountTypes</b>.
     * 
     * @param accountTypes
     *            the accountTypes to set
     */
    public void setAccountTypes(int accountTypes) {
        this.accountTypes = accountTypes;
    }

    /**
     * get value of <b>permissions</b>.
     * 
     * @return the permissions
     */
    public List<PermissionDto> getPermissions() {
        return permissions;
    }

    /**
     * Set value to <b>permissions</b>.
     * 
     * @param permissions
     *            the permissions to set
     */
    public void setPermissions(List<PermissionDto> permissions) {
        this.permissions = permissions;
    }

    /**
     * get value of <b>staffCount</b>.
     * 
     * @return the staffCount
     */
    public int getStaffCount() {
        return staffCount;
    }

    /**
     * Set value to <b>staffCount</b>.
     * 
     * @param staffCount
     *            the staffCount to set
     */
    public void setStaffCount(int staffCount) {
        this.staffCount = staffCount;
    }

    @Override
    public String toString() {
        return "RoleDto [roleName=" + roleName + ", description=" + description + ", roleType=" + roleType + ", status="
                + status + ", accountTypes=" + accountTypes + ", staffCount=" + staffCount + ", permissions="
                + permissions + "]";
    }

}
