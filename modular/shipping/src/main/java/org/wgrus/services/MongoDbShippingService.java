package org.wgrus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.wgrus.Order;

@Service("shippingService")
public class MongoDbShippingService implements ShippingService {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public MongoDbShippingService(MongoDbFactory mongoDbFactory) {
		this.mongoTemplate = new MongoTemplate(mongoDbFactory);
	}

	@Override
	public void ship(Order order) {
		if (order.getApproved() && order.getReserved()) {
			System.out.println("shipping order: " + order);
			this.mongoTemplate.save(order, "orders");
		}
		else {
			System.out.println("cannot ship order (out of stock or not authorized): " + order);
			this.mongoTemplate.save(order, "rejects");
		}
	}

}
