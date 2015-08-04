package edu.bionic.com.PersistenceLayer;

import javax.persistence.*;
/**
 * Entity for every menu item
 * @author andrew
 *
 */


@Entity
public class Dish {
	@Id
 	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String dishName;
	private double price;
	private String description;
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;
	private String dishType;
	private int active;
	
	public Dish(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dish) {
		this.dishName = dish;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDishType() {
		return dishType;
	}

	public void setDishType(String dishType) {
		this.dishType = dishType;
	};
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

}
