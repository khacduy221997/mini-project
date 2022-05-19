package grass.micro.apps.dao.auth;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import grass.micro.apps.model.auth.District;

public interface DistrictDao extends CrudRepository<District, Integer>, DistrictDaoCustomMethods {
	
	@Query("SELECT d FROM District d JOIN d.city dc WHERE dc.code = :code")
	List<District> getByCityCode(@Param("code")String code);
}
