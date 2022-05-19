package grass.micro.apps.service.auth.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import grass.micro.apps.dao.auth.CustomerDao;
import grass.micro.apps.model.auth.Customer;
import grass.micro.apps.model.dto.SearchCondition;
import grass.micro.apps.service.auth.CustomerService;
import grass.micro.apps.service.exception.ServiceException;
import grass.micro.apps.service.impl.GenericServiceImpl;

@Service
public class CustomerServiceImpl extends GenericServiceImpl<Customer, Integer> implements CustomerService {
    
    @SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerDao customerDao;

	@Override
	public CrudRepository<Customer, Integer> getDao() {
		return this.customerDao;
	}

	@Override
	public List<Customer> search(SearchCondition condition) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
}
