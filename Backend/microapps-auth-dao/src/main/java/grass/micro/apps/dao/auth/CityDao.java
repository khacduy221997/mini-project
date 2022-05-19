package grass.micro.apps.dao.auth;

import org.springframework.data.repository.CrudRepository;

import grass.micro.apps.model.auth.City;

public interface CityDao extends CrudRepository<City, Integer>, CityDaoCustomMethods {

}
