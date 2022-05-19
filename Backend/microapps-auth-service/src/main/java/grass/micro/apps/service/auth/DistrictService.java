package grass.micro.apps.service.auth;

import java.util.List;

import grass.micro.apps.model.auth.District;
import grass.micro.apps.service.GenericService;

public interface DistrictService extends GenericService<District, Integer>{

	List<District> getByCityCode(String code);
}
