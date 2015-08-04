package edu.bionic.com.PersistenceLayer.DAO;

import java.util.List;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

import edu.bionic.com.PersistenceLayer.Dish;


@Repository
public class DishDAO implements CRUD<Dish>{
	@PersistenceContext
  	private EntityManager em; 
  	
  	public DishDAO(){}
  	
  	public int create(Dish m) {
  		em.persist(m);
  		return m.getId(); 
  	}
  	
  	public Dish find(int id) {
  		return em.find(Dish.class, id); 		
  	}
  	
  	public void update(Dish dish) {
  		em.merge(dish);
  	}
  	 	
  	public void remove(Dish dish) {
  		em.remove(dish);
  	}
  	
  	public List<Dish> returnDishes() {
  		String q = "SELECT d FROM Dish d WHERE d.active=1";
  		TypedQuery<Dish> query = em.createQuery(q, Dish.class);
  		return query.getResultList(); 		  		
  	}
  	  		
}
