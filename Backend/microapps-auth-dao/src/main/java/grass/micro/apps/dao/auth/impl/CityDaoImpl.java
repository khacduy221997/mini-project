package grass.micro.apps.dao.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import grass.micro.apps.dao.auth.CityDaoCustomMethods;

@Repository
@Transactional
public class CityDaoImpl implements CityDaoCustomMethods {
    
    @Autowired
    @Qualifier("authEntityManager")
    private EntityManager entityManager;
}
