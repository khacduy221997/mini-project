package grass.micro.apps.service.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import grass.micro.apps.dao.auth.PermissionDao;
import grass.micro.apps.model.auth.Permission;
import grass.micro.apps.model.dto.SearchCondition;
import grass.micro.apps.service.auth.PermissionService;
import grass.micro.apps.service.exception.ServiceException;
import grass.micro.apps.service.impl.GenericServiceImpl;

@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Integer> implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public CrudRepository<Permission, Integer> getDao() {
        return this.permissionDao;
    }

    @Override
    public List<Permission> search(SearchCondition condition) throws ServiceException {
        return null;
    }

}
