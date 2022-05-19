package grass.micro.apps.service.auth.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import grass.micro.apps.dao.auth.DistrictDao;
import grass.micro.apps.model.auth.District;
import grass.micro.apps.model.dto.SearchCondition;
import grass.micro.apps.service.auth.DistrictService;
import grass.micro.apps.service.exception.ServiceException;
import grass.micro.apps.service.impl.GenericServiceImpl;

@Service
public class DistrictServiceImpl extends GenericServiceImpl<District, Integer> implements DistrictService {

	@Autowired
	private DistrictDao districtDao;
	
	@Override
	public CrudRepository<District, Integer> getDao() {
		return this.districtDao;
	}

	@Override
	public List<District> search(SearchCondition condition) throws ServiceException {
		return null;
	}

	@Override
	public List<District> getByCityCode(String code) {
		return this.districtDao.getByCityCode(code);
	}

}
