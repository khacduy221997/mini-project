package grass.micro.apps.auth.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PermissionGroupDto implements Serializable {

    private static final long serialVersionUID = 4344403459854507824L;

    @JsonProperty("group_id")
    private int groupId;

    @JsonProperty("group_name")
    private String groupName;

    private List<PermissionDto> permissions = new ArrayList<>();

    /**
     * get value of <b>groupId</b>.
     * 
     * @return the groupId
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Set value to <b>groupId</b>.
     * 
     * @param groupId
     *            the groupId to set
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * get value of <b>groupName</b>.
     * 
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Set value to <b>groupName</b>.
     * 
     * @param groupName
     *            the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * get value of <b>permissions</b>.
     * 
     * @return the permissions
     */
    public List<PermissionDto> getPermissions() {
        return permissions;
    }

    public void addPermission(PermissionDto permission) {
        this.permissions.add(permission);
    }

}
