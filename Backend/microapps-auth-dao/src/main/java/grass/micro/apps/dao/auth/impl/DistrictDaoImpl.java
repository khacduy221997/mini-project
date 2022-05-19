package grass.micro.apps.dao.auth.impl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import grass.micro.apps.dao.auth.DistrictDaoCustomMethods;

@Service
@Repository
@Transactional
public class DistrictDaoImpl implements DistrictDaoCustomMethods {
	
	@Autowired
    @Qualifier("authEntityManager")
    private EntityManager entityManager;

}
