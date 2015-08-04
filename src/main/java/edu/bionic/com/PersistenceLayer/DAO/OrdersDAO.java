package edu.bionic.com.PersistenceLayer.DAO;

import java.util.List;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

import edu.bionic.com.PersistenceLayer.Orders;
@Repository
public class OrdersDAO implements CRUD<Orders>{
	@PersistenceContext
  	private EntityManager em;
  	
  	public OrdersDAO(){}
  	
  	public int create(Orders order){
  		em.persist(order);
  		return order.getId();  		
  	}
  	
  	public Orders find(int id) {
		return em.find(Orders.class, id);
  	}
  	 
  	public void update(Orders order) {
  		em.merge(order);
  	}
  	
  	public void remove(Orders order) {
  			em.remove(order);
  	}
  	
  	public List<Orders> returnOrderList() {
  		String q = "SELECT o FROM Orders o";
  		TypedQuery<Orders> query = em.createQuery(q, Orders.class);
		return query.getResultList();
  	}  	
  	
  	public List<Orders> returnOrderList4Report(java.sql.Timestamp startDate, java.sql.Timestamp endDate) {
  		String q = "SELECT o FROM Orders o WHERE o.orderTime BETWEEN :start and :end";
  		TypedQuery<Orders> query = em.createQuery(q, Orders.class);
  		query.setParameter("start", startDate);
  		query.setParameter("end", endDate);
  		return query.getResultList();
  	}
 	 	
}
