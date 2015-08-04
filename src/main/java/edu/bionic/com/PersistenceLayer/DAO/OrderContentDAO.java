package edu.bionic.com.PersistenceLayer.DAO;

import java.util.List;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

import edu.bionic.com.PersistenceLayer.OrderContent;
@Repository
public class OrderContentDAO implements CRUD<OrderContent> {
	@PersistenceContext
  	private EntityManager em;
  	
  	public OrderContentDAO(){}
  	
  	public int create(OrderContent oc) {
  		em.persist(oc);
  		return oc.getId();
  	}
  	
  	public OrderContent find(int id) {
  		return em.find(OrderContent.class, id);
  	}
  	
  	public void update(OrderContent oc) {
  		em.merge(oc);
  	}
  	
  	public void remove(OrderContent oc) {
  		em.remove(oc);
  	}
  	
  	public List<OrderContent> returnKitchenList() {
  		String q = "SELECT oc FROM OrderContent oc WHERE oc.state=0";
  		TypedQuery<OrderContent> query = em.createQuery(q, OrderContent.class);
  		return query.getResultList();
  	}
  	 	
}
