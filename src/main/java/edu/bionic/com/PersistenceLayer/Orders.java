package edu.bionic.com.PersistenceLayer;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;
/**
 * Entity for orders that customers submit.
 * @author andrew
 *
 */
@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int customerId;
	private double total;
	private int status;
	private Timestamp orderTime;
	private Timestamp statusTime;
	@OneToMany(mappedBy="order")
	private Collection<OrderContent> contents;
	
		public Orders(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public Collection<OrderContent> getContents() {
		return contents;
	}

	public void setContents(Collection<OrderContent> contents) {
		this.contents = contents;
	}

	public Timestamp getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Timestamp statusTime) {
		this.statusTime = statusTime;
	}
	
}
