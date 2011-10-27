package org.wgrus.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wgrus.services.InventoryService;

@Controller
@RequestMapping(value="/")
public class InventoryController {

	private final Map<String, InventoryService> inventoryServices;

	@Autowired
	public InventoryController(Map<String, InventoryService> inventoryServices) {
		this.inventoryServices = inventoryServices;
	}

	@ResponseBody
	@RequestMapping(value="/available/{productId}/", method=RequestMethod.GET)
	public String quantity(@PathVariable String productId, Map<String, Object> model) {
		return "" + this.inventoryServices.get(productId).available();
	}

	@ResponseBody
	@RequestMapping(value="/reserve/{productId}/{quantity}", method=RequestMethod.POST)
	public String reserve(@PathVariable String productId, @PathVariable int quantity) {
		return "" + (this.inventoryServices.get(productId).reserve(quantity) ? quantity : 0);
	}

}
