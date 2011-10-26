package org.wgrus.domain;

import java.util.Date;

public class OrderInfo {

	String orderId;
	
	Date orderDate;
	
	Double orderTotal;


	public OrderInfo(String orderId, Date orderDate, Double orderTotal) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.orderTotal = orderTotal;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}

}
