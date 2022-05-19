package grass.micro.apps.service.auth;

import java.util.List;

import grass.micro.apps.model.auth.Company;
import grass.micro.apps.model.auth.Department;
import grass.micro.apps.service.GenericService;

public interface DepartmentService extends GenericService<Department, Integer> {

    /**
     * Get List of {@link Department} by given companyId.
     * @param companyId id of {@link Company}
     * @return List of {@link Department}.
     */
    List<Department> getByCompanyId(int companyId);

}
