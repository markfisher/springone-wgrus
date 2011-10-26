package org.wgrus.web;

import org.wgrus.Order;
import org.wgrus.services.OrderService;

public class StubOrderService implements OrderService {

	@Override
	public void placeOrder(Order order) {
		System.out.println("plading order: " + order);
	}

}
