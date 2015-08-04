package edu.bionic.com.PersistenceLayer;

import java.sql.Timestamp;

import javax.persistence.*;

/**
 * Entity for order contents. This item contains dish ID, state of item - is it ready for shipment?
 * and the time when the state was changed.
 * @author andrew
 *
 */
@Entity
public class OrderContent {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int dishId;
	private int state;
	private double price;
	private Timestamp stateTime;
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Orders order;	
	
	public OrderContent(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDishId() {
		return dishId;
	}

	public void setDishId(int dishId) {
		this.dishId = dishId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Timestamp getStateTime() {
		return stateTime;
	}

	public void setStateTime(Timestamp stateTime) {
		this.stateTime = stateTime;
	}

}
