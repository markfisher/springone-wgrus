package org.wgrus;

public class Order {

	private final long id;
	private int quantity;
	private String productId;
	private String customerId;

	public Order(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String toString() {
		return "Order #" + this.id + ": " + this.quantity + " " + this.productId + "s for " + this.customerId;
	}

}
