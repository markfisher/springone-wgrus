package org.wgrus.web;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.wgrus.services.GadgetInventoryService;
import org.wgrus.services.InventoryService;
import org.wgrus.services.StubBillingService;
import org.wgrus.services.WidgetInventoryService;

public class StoreFrontUnitTests {

	@Test
	public void test() {
		StoreFront store = new StoreFront();
		store.setBillingService(new StubBillingService());
		store.setOrderService(new StubOrderService());
		Map<String, InventoryService> inventoryServices = new HashMap<String, InventoryService>();
		inventoryServices.put("gadgetInventoryService", new GadgetInventoryService());
		inventoryServices.put("widgetInventoryService", new WidgetInventoryService());
		store.setInventoryServices(inventoryServices);
		Map<String, Object> model = new HashMap<String, Object>(); 
		store.placeOrder("joe", 80, "widget", model);
		try {
			store.placeOrder("joe", 21, "widget", model);
		}
		catch (IllegalStateException e) {
			// not enough in stock
		}
		store.placeOrder("joe", 15, "widget", model);
	}

}
