package org.wgrus.services;

import javax.annotation.PostConstruct;

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

	@PostConstruct
	public void initialize() {
		if (!this.mongoTemplate.collectionExists("orders")) {
			this.mongoTemplate.createCollection("orders");
		}
	}

	@Override
	public void ship(Order order) {
		System.out.println("shipping order: " + order);
		this.mongoTemplate.save(order, "orders");
	}

}
