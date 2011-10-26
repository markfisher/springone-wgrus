package org.wgrus.web;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wgrus.Order;
import org.wgrus.services.BillingService;
import org.wgrus.services.InventoryService;
import org.wgrus.services.OrderService;

/**
 * Handles order requests.
 */
@Controller
@RequestMapping(value="/")
public class StoreFront {

	private final AtomicLong orderIdCounter = new AtomicLong(1);

	private volatile BillingService billingService;

	private volatile Map<String, InventoryService> inventoryServices;

	private volatile OrderService orderService;

	@RequestMapping(method=RequestMethod.GET)
	public String displayForm() {
		return "order";
	}

	@Autowired
	public void setBillingService(BillingService billingService) {
		this.billingService = billingService;
	}

	@Autowired
	public void setInventoryServices(Map<String, InventoryService> inventoryServices) {
		this.inventoryServices = inventoryServices;
	}

	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@RequestMapping(method=RequestMethod.POST)
	public String placeOrder(@RequestParam String customerId, @RequestParam int quantity, @RequestParam String productId, Map<String, Object> model) {
		long orderId = orderIdCounter.getAndIncrement();
		Order order = new Order(orderId);
		order.setCustomerId(customerId);
		order.setQuantity(quantity);
		order.setProductId(productId);
		Assert.state(this.billingService.authorize(customerId), "failed to authorize: " + customerId);
		InventoryService inventoryService = this.inventoryServices.get(productId + "InventoryService");
		Assert.notNull(inventoryService, "cannot find InventoryService for: " + productId);
		Assert.state(inventoryService.reserve(quantity), "not enough " + productId + "s in stock");
		this.orderService.placeOrder(order);
		model.put("orderId", orderId);
		return "order";
	}

}
