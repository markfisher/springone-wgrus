package org.wgrus;

public class Order {

	private long id;
	private int quantity;
	private String productId;
	private String customerId;
	private boolean reserved;

	public void setId(long id) {
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

	public boolean getReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public String toString() {
		return "Order #" + this.id + ": " + this.quantity + " " + this.productId + "s for " + this.customerId;
	}

}
