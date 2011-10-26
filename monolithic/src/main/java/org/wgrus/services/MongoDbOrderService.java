package org.wgrus.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.wgrus.Order;

@Service
public class MongoDbOrderService implements OrderService {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public MongoDbOrderService(MongoDbFactory mongoDbFactory) {
		this.mongoTemplate = new MongoTemplate(mongoDbFactory);
	}

	@PostConstruct
	public void initialize() {
		if (!this.mongoTemplate.collectionExists("orders")) {
			this.mongoTemplate.createCollection("orders");
		}
	}

	@Override
	public void placeOrder(Order order) {
		System.out.println("placing order: " + order);
		this.mongoTemplate.save(order, "orders");
	}

}
