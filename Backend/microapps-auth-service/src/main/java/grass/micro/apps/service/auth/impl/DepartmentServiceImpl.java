package grass.micro.apps.service.auth.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import grass.micro.apps.dao.auth.DepartmentDao;
import grass.micro.apps.model.auth.Department;
import grass.micro.apps.model.dto.SearchCondition;
import grass.micro.apps.service.auth.DepartmentService;
import grass.micro.apps.service.exception.ServiceException;
import grass.micro.apps.service.impl.GenericServiceImpl;

@Service
public class DepartmentServiceImpl extends GenericServiceImpl<Department, Integer> implements DepartmentService {
    
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public CrudRepository<Department, Integer> getDao() {
        return this.departmentDao;
    }

    @Override
    public List<Department> search(SearchCondition condition) throws ServiceException {
        return null;
    }

    @Override
    public List<Department> getByCompanyId(int companyId) {
        return this.departmentDao.findByCompanyId(companyId);
    }
    
}    
