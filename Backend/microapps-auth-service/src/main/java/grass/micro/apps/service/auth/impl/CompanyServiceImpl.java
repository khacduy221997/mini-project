package grass.micro.apps.service.auth.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import grass.micro.apps.dao.auth.CompanyDao;
import grass.micro.apps.model.auth.Company;
import grass.micro.apps.model.dto.SearchCondition;
import grass.micro.apps.service.auth.CompanyService;
import grass.micro.apps.service.exception.ServiceException;
import grass.micro.apps.service.impl.GenericServiceImpl;

@Service
public class CompanyServiceImpl extends GenericServiceImpl<Company, Integer> implements CompanyService {
    
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyDao companyDao;

    @Override
    public CrudRepository<Company, Integer> getDao() {
        return this.companyDao;
    }

    @Override
    public List<Company> search(SearchCondition condition) throws ServiceException {
        return null;
    }
    
}    
