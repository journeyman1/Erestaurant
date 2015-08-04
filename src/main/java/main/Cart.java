package main;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import edu.bionic.com.PersistenceLayer.Dish;
import edu.bionic.com.PersistenceLayer.OrderContent;
import edu.bionic.com.PersistenceLayer.Orders;
import edu.bionic.com.PersistenceLayer.Services.OrderContentService;
import edu.bionic.com.PersistenceLayer.Services.OrderService;

@Named
@Scope("session")
public class Cart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private OrderService orderService;
	@Inject
	private OrderContentService contentService;
	private ArrayList<Dish> cart = new ArrayList<>();
	private Map<Dish, Integer> cartContents = new HashMap<>();
	private double cartTotal; 
	private boolean submit = false;

	
	public Cart(){}
	
		
	public String submitOrder(int customer) {
		Orders order = new Orders();
  		Timestamp dt = Timestamp.from(Instant.now());
  		order.setOrderTime(dt);
  		order.setStatusTime(dt);
  		order.setCustomerId(customer);
  		order.setTotal(cartTotal);
  		orderService.create(order);
		for (Dish d : cart) {
			OrderContent oc = new OrderContent();
			oc.setDishId(d.getId());
			oc.setPrice(d.getPrice());
			oc.setOrder(order);
			oc.setStateTime(dt);
			if (d.getDishType().equals(MenuBean.KITCHENMADE)) {
				oc.setState(MenuBean.UNCOOKED);
			} else {oc.setState(MenuBean.NONKITCHENREADY);
			}
			contentService.create(oc);
		}
		cart = new ArrayList<>();
		cartContents = new HashMap<>();
		submit = false;
		return "submitted";
	}
	
	public String getTotal() {
		double total = 0;
		if (cart!= null) {
			for (Dish dish : cart) {
				total += dish.getPrice();
			}
		}
		cartTotal = total;
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(total);
	}
	
	public void addDish(Dish dish) {
		getCart().add(dish);
	}
	
	public void removeDish(Dish dish) {
		getCart().remove(dish);
	}
	
	public int getDishCount() {
		return getCart().size();
	}

	public void setCartContents(Map<Dish, Integer> cartContents) {
		this.cartContents = cartContents;
	}
	
	public Map<Dish, Integer> getCartContents() {
		Map<Dish, Integer> cartContents = new HashMap<>();
		for (Dish d : cart) {
			if(cartContents.containsKey(d)) {
				cartContents.put(d, cartContents.get(d)+1);
			} else { cartContents.put(d, 1); }
		}
		return cartContents;
	}

	public String enableSubmitCart() {
		this.submit = true;
		return "cart";
	}
	
	
	//** Getters&Setters */

	
	public ArrayList<Dish> getCart() {
		return cart;
	}

	public void setCart(ArrayList<Dish> cart) {
		this.cart = cart;
	}
	
	public boolean isSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = false;
	}


	public OrderService getOrderService() {
		return orderService;
	}


	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}


	public OrderContentService getContentService() {
		return contentService;
	}


	public void setContentService(OrderContentService contentService) {
		this.contentService = contentService;
	}


	public double getCartTotal() {
		return cartTotal;
	}


	public void setCartTotal(double cartTotal) {
		this.cartTotal = cartTotal;
	}
	
}
