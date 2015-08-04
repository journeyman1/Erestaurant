package edu.bionic.com.PersistenceLayer;

import java.util.Collection;

import javax.persistence.*;
/**
 * Entity for the category of dish. Eg - starters, main dishes, beverages.
 * @author andrew
 * 
 */

@Entity
public class Category {
	@Id
 	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	@OneToMany(mappedBy="category")
	private Collection<Dish> dishes;
	
	public Category(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(Collection<Dish> dishes) {
		this.dishes = dishes;
	}
	
	
}
