package grass.micro.apps.dao.auth.impl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import grass.micro.apps.dao.auth.UserDaoCustomMethods;

@Repository
@Transactional
public class UserDaoImpl implements UserDaoCustomMethods {
	
	@Autowired
    @Qualifier("authEntityManager")
    private EntityManager entityManager;
}
