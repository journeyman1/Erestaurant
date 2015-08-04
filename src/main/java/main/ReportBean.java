package main;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import edu.bionic.com.PersistenceLayer.Orders;
import edu.bionic.com.PersistenceLayer.Services.CategoryService;
import edu.bionic.com.PersistenceLayer.Services.DishService;
import edu.bionic.com.PersistenceLayer.Services.OrderService;

@Named
@Scope("request")
public class ReportBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject private OrderService orderService;
	@Inject private CategoryService categoryService;
	@Inject private DishService dishService;
	
	private java.util.Date startDate;
	private java.util.Date endDate;
	
	private List<Orders> orders = null;
	private List<DailyReport> reportData = new ArrayList<>();
	
	private boolean showReport = false;	
	
	
	public ReportBean() {}
	
	
	public String createReport() {
		if (startDate == null) {startDate = new Date();}
		if (endDate == null) {endDate = new Date();}
		Instant instant = Instant.ofEpochMilli(startDate.getTime()); 
		LocalDate start = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		instant = Instant.ofEpochMilli(endDate.getTime());
		LocalDate end = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		
		while (start.isBefore(end.plusDays(1))) {
			instant = start.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			java.util.Date t1 = Date.from(instant);
			instant = start.plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			java.util.Date t2 = Date.from(instant);
			
			orders = orderService.returnOrderList4Report(t1, t2);
			DailyReport daily = new DailyReport();
			daily.setDate(t1);
			daily.setOrderCount(orders.size());
			for (Orders o : orders) {
				daily.setSums(daily.getSums()+o.getTotal());
			}	
			reportData.add(daily);
			start = start.plusDays(1);
		}		
		showReport = true;
		return "report";
	}	  
	
	public String formatDate(java.util.Date date) {
		SimpleDateFormat dtformat = new SimpleDateFormat("dd.MM.yy");
		return dtformat.format(date);
	}

		
	//** Getters & Setters */
	
	
	public OrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public CategoryService getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public DishService getDishService() {
		return dishService;
	}
	public void setDishService(DishService dishService) {
		this.dishService = dishService;
	}
	public java.util.Date getStartDate() {
		return startDate;
	}
	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}
	public java.util.Date getEndDate() {
		return endDate;
	}
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}
	public List<Orders> getOrders() {
		return orders;
	}
	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
	public boolean isShowReport() {
		return showReport;
	}
	public void setShowReport(boolean showReport) {
		this.showReport = showReport;
	}


	public List<DailyReport> getReportData() {
		return reportData;
	}


	public void setReportData(List<DailyReport> reportData) {
		this.reportData = reportData;
	}

}
