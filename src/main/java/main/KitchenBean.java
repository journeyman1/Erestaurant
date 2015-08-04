package main;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import edu.bionic.com.PersistenceLayer.OrderContent;
import edu.bionic.com.PersistenceLayer.Services.DishService;
import edu.bionic.com.PersistenceLayer.Services.OrderContentService;


@Named
@Scope("request")
public class KitchenBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject	private OrderContentService contentService;
	@Inject	private DishService dishService;
	
	private List<OrderContent> list = new ArrayList<>();
	private OrderContent oc = null;
	
	
	public KitchenBean(){}

	
	public String markAsDone() {
		oc.setState(MenuBean.KITCHENREADY);
		oc.setStateTime(Timestamp.from(Instant.now()));
		contentService.update(oc);
		updateKitchenList();
		return "kitchen";
	}
	@PostConstruct
	public void updateKitchenList() {
		setList(contentService.returnKitchenList());
	}
	
	
	//** Getters&Setters */
	
	
	public List<OrderContent> getList() {
		return list;
	}

	public void setList(List<OrderContent> list) {
		this.list = list;
	}
	
	public OrderContent getOc() {
		return oc;
	}

	public void setOc(OrderContent oc) {
		this.oc = oc;
	}

	public OrderContentService getContentService() {
		return contentService;
	}

	public void setContentService(OrderContentService ocService) {
		this.contentService = ocService;
	}


	public DishService getDishService() {
		return dishService;
	}


	public void setDishService(DishService dishService) {
		this.dishService = dishService;
	}
	
}
