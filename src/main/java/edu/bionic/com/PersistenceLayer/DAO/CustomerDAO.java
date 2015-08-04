package edu.bionic.com.PersistenceLayer.DAO;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

import edu.bionic.com.PersistenceLayer.Customer;
@Repository
public class CustomerDAO implements CRUD<Customer> {
	@PersistenceContext
	private EntityManager em;
  	
	public CustomerDAO(){}
	
	public int create(Customer customer) {
		em.persist(customer);
		return customer.getId();
	}
	
	public Customer find(String email) {
		if (email == null) return null;
		String q = "SELECT c FROM Customer c WHERE c.email='"+email.trim()+"'";
		TypedQuery<Customer> query = em.createQuery(q, Customer.class);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	public Customer find(int id) {
		return em.find(Customer.class, id);
	}
	
	public void update(Customer customer) {
		em.merge(customer);
	}
	
	public void remove(Customer customer) {
//			em.merge(customer);
			em.remove(customer);
	}
	
	
	
	
}
