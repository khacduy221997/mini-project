package grass.micro.apps.auth.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import grass.micro.apps.auth.web.dto.AccountDto;
import grass.micro.apps.auth.web.dto.CityDto;
import grass.micro.apps.auth.web.dto.CompanyDto;
import grass.micro.apps.auth.web.dto.DepartmentDto;
import grass.micro.apps.auth.web.dto.DistrictDto;
import grass.micro.apps.auth.web.dto.PermissionDto;
import grass.micro.apps.auth.web.dto.PermissionGroupDto;
import grass.micro.apps.auth.web.dto.RoleDto;
import grass.micro.apps.auth.web.dto.UserDto;
import grass.micro.apps.model.auth.Account;
import grass.micro.apps.model.auth.City;
import grass.micro.apps.model.auth.Company;
import grass.micro.apps.model.auth.Department;
import grass.micro.apps.model.auth.District;
import grass.micro.apps.model.auth.Permission;
import grass.micro.apps.model.auth.Role;
import grass.micro.apps.model.auth.User;
import grass.micro.apps.web.util.CommonFetchingUtils;

public class DtoFetchingUtils {
    private DtoFetchingUtils() {}
    
    /**
     * Fetch List of User to List of UserDto.
     * @param users
     * @return
     */
    public static List<UserDto> fetchUsers(List<User> users) {
        if (users == null) {
            return new ArrayList<>();
        }
        
        List<UserDto> result = new LinkedList<>();
        for (User user : users) {
            result.add(fetchUser(user));
        }
        
        return result;
    }

    public static UserDto fetchUser(User user) {
        UserDto result = new UserDto();
        CommonFetchingUtils.fetchStatusTimestamp(result, user);
        result.setAddress(user.getAddress());
        Company company = user.getCompany();
        result.setCompanyId(company != null ? company.getId() : null);
        result.setCompanyName(company != null ? company.getShortName() : null);
        Department department = user.getDepartment();
        result.setDepartmentId(department != null ? department.getId() : null);
        result.setDepartmentName(department != null ? department.getFullname() : "");
        result.setFullname(user.getFullname());
        result.setMobile(user.getMobile());
        result.setTelephone(user.getTelephone());
        result.setTelephoneExt(user.getTelephoneExt());
        result.setAccount(fetchAccount(user.getAccount()));
        result.setRoles(fetchRoles(user.getRoles()));
        return result;
    }
    
    /**
     * Fetch one Account.
     * @param account
     * @return
     */
    public static AccountDto fetchAccount(Account account) {
        AccountDto result = new AccountDto();
        CommonFetchingUtils.fetchStatusTimestamp(result, account);
        result.setId(account.getId());
        result.setEmail(account.getEmail());
        result.setLoginName(account.getLoginName());
        result.setAccountType(account.getAccountType());
        return result;
    }
    
    /**
     * Fetch List of Role to List of RoleDto.
     * @param roles list of {@link Role}
     * @return list of {@link RoleDto}
     */
    public static List<RoleDto> fetchRoles(List<Role> roles) {
        if (roles == null) {
            return new ArrayList<>();
        }
        
        List<RoleDto> result = new LinkedList<>();
        for (Role role : roles) {
            result.add(fetchRole(role));
        }
        
        return result;
    }
    
    /**
     * Fetch Role to RoleDto.
     * @param role {@link Role}
     * @return {@link RoleDto}
     */
    public static RoleDto fetchRole(Role role) {
        RoleDto result = new RoleDto();
        CommonFetchingUtils.fetchStatusTimestamp(result, role);
        result.setRoleName(role.getRoleName());
        result.setDescription(role.getDescription());
        result.setRoleType(role.getRoleType());
        result.setStatus(role.getStatus());
        result.setAccountTypes(role.getAccountsTypes());
        result.setStaffCount(role.getStaffCount());
        result.setPermissions(fetchPemissionsToDtos(role.getPermissions()));
        return result;
    }

    /**
     * Fetch Role to RoleDto.
     * @param role {@link Role}
     * @return {@link RoleDto}
     */
    public static List<PermissionDto> fetchPemissionsToDtos(List<Permission> permissions) {
        if (permissions == null) {
            return new ArrayList<>();
        }
        
        return permissions.stream().map(p -> fetchPemission(p)).collect(Collectors.toList());
    }
    
    public static List<PermissionGroupDto> fetchPermissions(List<Permission> permissions) {
        List<PermissionGroupDto> groups = new LinkedList<>();
        Map<Integer, PermissionGroupDto> map = new HashMap<>();
        for (Permission permission : permissions) {
            PermissionGroupDto group = map.get(permission.getPermissionGroup());
            if (group == null) {
                group = new PermissionGroupDto();
                group.setGroupId(permission.getPermissionGroup());
                group.setGroupName(permission.getPermissionGroupName());
                map.put(permission.getPermissionGroup(), group);
            }
            
            group.addPermission(fetchPemission(permission));
        }
        
        groups.addAll(map.values());
        return groups;
    }
    
    public static PermissionDto fetchPemission(Permission permission) {
        PermissionDto result = new PermissionDto();
        result.setId(permission.getId());
        result.setDescription(permission.getDescription());
        result.setPermissionName(permission.getPermissionName());
        return result;
    }

    /**
     * Fetch List of Company to List of CompanyDto.
     * @param roles list of {@link Company}
     * @return list of {@link CompanyDto}
     */
    public static List<CompanyDto> fetchCompanies(List<Company> companies) {
        if (companies == null) {
            return new ArrayList<>();
        }
        
        List<CompanyDto> result = new LinkedList<>();
        for (Company company : companies) {
            result.add(fetchCompany(company));
        }
        
        return result;
    }

    /**
     * Fetch Company to CompanyDto.
     * @param role {@link Company}
     * @return {@link CompanyDto}
     */
    private static CompanyDto fetchCompany(Company company) {
        CompanyDto result = new CompanyDto();
        CommonFetchingUtils.fetchStatusTimestamp(result, company);
        result.setShortName(company.getShortName());
        result.setFullname(company.getFullname());
        result.setAddress(company.getAddress());
        result.setFax(company.getFax());
        result.setTax(company.getTax());
        result.setTelephone(company.getTelephone());
        result.setWebsite(company.getWebsite());
        DistrictDto district = fetchDistrict(company.getDistrict());
        result.setDistrict(district);
        result.setCity(district.getCity());
        district.setCity(null);
        return result;
    }
    
    /**
     * Fetch List of Department to List of DepartmentDto.
     * @param roles list of {@link Department}
     * @param isFetchCompany true when you want to fetch Company.
     * @return list of {@link DepartmentDto}
     */
    public static List<DepartmentDto> fetchDepartments(List<Department> departments, boolean isFetchCompany) {
        if (departments == null) {
            return new ArrayList<>();
        }
        
        List<DepartmentDto> result = new LinkedList<>();
        for (Department department : departments) {
            result.add(fetchDepartment(department, isFetchCompany));
        }
        
        return result;
    }
    
    /**
     * Fetch List of Department to List of DepartmentDto.
     * @param roles list of {@link Department}
     * @return list of {@link DepartmentDto}
     */
    public static List<DepartmentDto> fetchDepartments(List<Department> departments) {
        return fetchDepartments(departments, false);
    }

    /**
     * Fetch Department to DepartmentDto.
     * @param role {@link Department}
     * @return {@link DepartmentDto}
     */
    public static DepartmentDto fetchDepartment(Department department) {
        return fetchDepartment(department, false);
    }
    
    /**
     * Fetch Department to DepartmentDto.
     * @param role {@link Department}
     * @return {@link DepartmentDto}
     */
    public static DepartmentDto fetchDepartment(Department department, boolean isFetchCompany) {
        DepartmentDto result = new DepartmentDto();
        CommonFetchingUtils.fetchStatusTimestamp(result, department);
        result.setShortName(department.getShortName());
        result.setFullname(department.getFullname());
        if (isFetchCompany) {
            result.setCompany(fetchCompany(department.getCompany()));
        }
        return result;
    }
    
    /**
     * Fetch List of District to List of DistrictDto.
     * @param roles list of {@link District}
     * @return list of {@link DistrictDto}
     */
    public static List<DistrictDto> fetchDistricts(List<District> districts) {
        if (districts == null) {
            return new ArrayList<>();
        }
        
        List<DistrictDto> result = new LinkedList<>();
        for (District district : districts) {
            result.add(fetchDistrict(district));
        }
        
        return result;
    }

    /**
     * Fetch District to DistrictDto.
     * @param role {@link District}
     * @return {@link DistrictDto}
     */
    public static DistrictDto fetchDistrict(District district) {
        DistrictDto result = new DistrictDto();
        result.setCode(district.getCode());
        result.setName(district.getName());
        result.setType(district.getType());
        result.setCity(fetchCity(district.getCity()));
        return result;
    }
    
    /**
     * Fetch List of City to List of CityDto.
     * @param roles list of {@link City}
     * @return list of {@link CityDto}
     */
    public static List<CityDto> fetchCities(List<City> cities) {
        if (cities == null) {
            return new ArrayList<>();
        }
        
        List<CityDto> result = new LinkedList<>();
        for (City city : cities) {
            result.add(fetchCity(city));
        }
        
        return result;
    }
    
    /**
     * Fetch City to CityDto.
     * @param role {@link City}
     * @return {@link CityDto}
     */
    public static CityDto fetchCity(City city) {
        CityDto result = new CityDto();
        result.setCode(city.getCode());
        result.setName(city.getName());
        result.setType(city.getType());
        return result;
    }

}
