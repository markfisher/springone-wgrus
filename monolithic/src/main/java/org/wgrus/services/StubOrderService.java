package org.wgrus.services;

import org.wgrus.Order;

public class StubOrderService implements OrderService {

	@Override
	public void placeOrder(Order order) {
		System.out.println("placing order: " + order);
	}
	
}
