package grass.micro.apps.auth.web.form;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RoleForm implements Serializable {
    private static final long serialVersionUID = 261372685428971298L;
    
    public static Map<String, String> fieldMap = new LinkedHashMap<String, String>();

    static {
        fieldMap.put("role_name", "A207.field.role_name");
        fieldMap.put("description", "A207.field.description");
        fieldMap.put("permissions", "A207.field.permissions");
    }
    
    @JsonProperty("role_name")
    private String roleName;
    
    private String description;
    
    private List<Integer> permissions = new ArrayList<>();

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
     * get value of <b>permissions</b>.
     * 
     * @return the permissions
     */
    public List<Integer> getPermissions() {
        return permissions;
    }

    /**
     * Set value to <b>permissions</b>.
     * 
     * @param permissions
     *            the permissions to set
     */
    public void setPermissions(List<Integer> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "RoleForm [roleName=" + roleName + ", description=" + description + ", permissions=" + permissions + "]";
    }

}
