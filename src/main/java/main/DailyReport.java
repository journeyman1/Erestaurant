package main;

public class DailyReport {
	private java.util.Date date;
	private int orderCount;
	private double sums;
	
	
	public DailyReport(){}
	
	
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public double getSums() {
		return sums;
	}
	public void setSums(double sums) {
		this.sums = sums;
	}
	
}