package grass.micro.apps.service.auth.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import grass.micro.apps.dao.auth.CityDao;
import grass.micro.apps.model.auth.City;
import grass.micro.apps.model.dto.SearchCondition;
import grass.micro.apps.service.auth.CityService;
import grass.micro.apps.service.exception.ServiceException;
import grass.micro.apps.service.impl.GenericServiceImpl;

@Service
public class CityServiceImpl extends GenericServiceImpl<City, Integer> implements CityService {
    
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Autowired
    private CityDao companyDao;

    @Override
    public CrudRepository<City, Integer> getDao() {
        return this.companyDao;
    }

    @Override
    public List<City> search(SearchCondition condition) throws ServiceException {
        return null;
    }
    
}    
