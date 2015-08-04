package main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import edu.bionic.com.PersistenceLayer.OrderContent;
import edu.bionic.com.PersistenceLayer.Orders;
import edu.bionic.com.PersistenceLayer.Services.DishService;
import edu.bionic.com.PersistenceLayer.Services.OrderContentService;
import edu.bionic.com.PersistenceLayer.Services.OrderService;

import java.io.Serializable;

@Named
@Scope("request")
public class DeliveryBean implements Serializable {
	/**
	 * 
	 */
	@Inject private OrderService orderService;
	@Inject	private OrderContentService contentService;
	@Inject	private DishService dishService;
	
	private static final long serialVersionUID = 1L;
	private List<Orders> orderList = new ArrayList<>();
	private List<Orders> filteredList = new ArrayList<>();
	private Orders currentOrder = new Orders();
	
	private int[] filter = {0, 1, 2, 3, 4, 5};
	private static Map<String, Integer> statuses;
	private static Map<Integer, String> statusesKey; 
	
	static {
		statuses = new LinkedHashMap<>();
		statuses.put("Unprepared", MenuBean.UNCOOKED);
		statuses.put("Kitchen-done", MenuBean.KITCHENREADY);
		statuses.put("Non-kitchen-done", MenuBean.NONKITCHENREADY);
		statuses.put("Ready for shipment", MenuBean.READYFORSHIPMENT);
		statuses.put("Delivering", MenuBean.DELIVERING);
		statuses.put("Delivered", MenuBean.DELIVERED);
		
		statusesKey = new LinkedHashMap<>();
		statusesKey.put(MenuBean.UNCOOKED, "Unprepared");
		statusesKey.put(MenuBean.KITCHENREADY, "Kitchen-done");
		statusesKey.put(MenuBean.NONKITCHENREADY, "Non-kitchen-done");
		statusesKey.put(MenuBean.READYFORSHIPMENT, "Ready for shipment");
		statusesKey.put(MenuBean.DELIVERING, "Delivering");
		statusesKey.put(MenuBean.DELIVERED, "Delivered");
	}
	
	
	public DeliveryBean() {}
	
	
	public boolean showMarkButton(Orders order) {
		if ( order.getStatus() > MenuBean.DELIVERING ) return false;
		for (OrderContent oc : order.getContents()) {
			if ( oc.getState() == MenuBean.UNCOOKED ) return false;
		}
		return true;
	}
	
	public String incStatus(Integer i) {
		i++;
		return statusesKey.get(i);
	}	
	
	public List<OrderContent> returnKitchenMadeDishes(Orders order) {
		List<OrderContent> list = new ArrayList<>();
		for (OrderContent oc: order.getContents()) {
			if (dishService.find(oc.getDishId()).getDishType().equals(MenuBean.KITCHENMADE))
				list.add(oc);
		}
		return list;
	}
	
	public List<OrderContent> returnNonKitchenMadeDishes(Orders order) {
		List<OrderContent> list = new ArrayList<>();
		for (OrderContent oc: order.getContents()) {
			if (dishService.find(oc.getDishId()).getDishType().equals(MenuBean.NONKITCHENMADE))
				list.add(oc);
		}
		return list;
	}	
	
	public String setNextStatus() {
		currentOrder.setStatus(currentOrder.getStatus()+1);
		orderService.update(currentOrder);
		return "delivery";
	}
	
	public boolean contains(int[] s, Integer n) {
		for (int i=0; i<s.length; i++) {
			if(Integer.valueOf(s[i]).equals(n)) {
				return true;
			}
		}		
		return false;
	}
	
	public String applyFilter() {
		ArrayList<Orders> list = new ArrayList<>();
		for (Orders o : orderList) {
			if (contains(filter, o.getStatus())) {
				list.add(o);
			};
			filteredList = list;
		}
		return "delivery"; 
	}
	@PostConstruct
	public void updateDeliveryList() {
		orderList = orderService.returnOrderList();
		filteredList = orderService.returnOrderList();
		applyFilter();
	}
	
	
	/** Getter&Setters*/
	
	
	public List<Orders> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Orders> orderList) {
		this.orderList = orderList;
	}
	public Orders getCurrentOrder() {
		return currentOrder;
	}
	public void setCurrentOrder(Orders currentOrder) {
		this.currentOrder = currentOrder;
	}
	
	public int[] getFilter() {
		return filter;
	}

	public void setFilter(int[] filter) {
		this.filter = filter;
	}

	public Map<String, Integer> getStatuses() {
		return statuses;
	}

	public void setStatuses(Map<String, Integer> statuses) {
		DeliveryBean.statuses = statuses;
	}

	public List<Orders> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Orders> filteredList) {
		this.filteredList = filteredList;
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


	public DishService getDishService() {
		return dishService;
	}


	public void setDishService(DishService dishService) {
		this.dishService = dishService;
	}


	public Map<Integer, String> getStatusesKey() {
		return statusesKey;
	}


	public void setStatusesKey(Map<Integer, String> statusesKey) {
		DeliveryBean.statusesKey = statusesKey;
	}

}
