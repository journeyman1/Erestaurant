package edu.bionic.com.PersistenceLayer.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.bionic.com.PersistenceLayer.Customer;
import edu.bionic.com.PersistenceLayer.DAO.CustomerDAO;

@Service
public class CustomerService {
	@Autowired
	private CustomerDAO customerDAO;
	@Transactional
	public int create(Customer customer) {
		if (find(customer.getEmail()) != null) { 
			return -1;
		} else return customerDAO.create(customer);
	}

	public Customer findById(int id) {
		return customerDAO.find(id);		
	}
	
	public Customer find(String email) {
		if (email == null) return null;
		return customerDAO.find(email);
	}
	@Transactional
	public void update(Customer customer) {
		customerDAO.update(customer);		
	}
	@Transactional
	public void remove(Customer customer) {
		customerDAO.remove(customer);		
	}

}
