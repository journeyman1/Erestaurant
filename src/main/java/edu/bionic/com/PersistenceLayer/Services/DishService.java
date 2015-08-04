package edu.bionic.com.PersistenceLayer.Services;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.bionic.com.PersistenceLayer.Dish;
import edu.bionic.com.PersistenceLayer.DAO.DishDAO;

/**
 * @author andrew
 *
 */
@Service
public class DishService implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private DishDAO dishDAO;
	@Transactional
	public int create(Dish dish) {
		dish.setActive(1);
		return dishDAO.create(dish);
	}
	
	public Dish find(int id) {
		return dishDAO.find(id);		
	}
	@Transactional
	public void update(Dish dish) {
		dishDAO.update(dish);		
	}
	@Transactional
	public void remove(Dish dish) {
		dish.setActive(0);
		dishDAO.update(dish);		
	}
	
	public List<Dish> returnDishes() {
		return dishDAO.returnDishes();
	}
	
}
