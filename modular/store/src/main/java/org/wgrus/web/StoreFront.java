package org.wgrus.web;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wgrus.Order;

/**
 * Handles order requests.
 */
@Controller
@RequestMapping(value="/")
public class StoreFront {

	private final AtomicLong orderIdCounter = new AtomicLong(1);

	private volatile MessagingTemplate messagingTemplate;

	@Autowired
	public void setOrderChannel(@Qualifier("orderChannel") MessageChannel orderChannel) {
		this.messagingTemplate = new MessagingTemplate(orderChannel);
	}

	@RequestMapping(method=RequestMethod.GET)
	public String displayForm() {
		return "store";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String placeOrder(@RequestParam String customerId, @RequestParam int quantity, @RequestParam String productId, Map<String, Object> model) {
		long orderId = orderIdCounter.getAndIncrement();
		Order order = new Order();
		order.setId(orderId);
		order.setCustomerId(customerId);
		order.setQuantity(quantity);
		order.setProductId(productId);
		this.messagingTemplate.send(MessageBuilder.withPayload(order).build());
		model.put("orderId", orderId);
		return "store";
	}

}
