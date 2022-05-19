package grass.micro.apps.dao.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import grass.micro.apps.dao.auth.AccountDaoCustomMethods;

@Repository
@Transactional
public class AccountDaoImpl implements AccountDaoCustomMethods {
    @Autowired
    @Qualifier("authEntityManager")
    private EntityManager entityManager;
}
