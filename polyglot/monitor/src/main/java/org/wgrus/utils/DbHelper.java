package org.wgrus.utils;

import java.util.Date;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.wgrus.domain.OrderInfo;

public class DbHelper {

	MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void populate() {
		if (mongoTemplate.collectionExists("pending")) {
			mongoTemplate.dropCollection("pending");
		}
		if (mongoTemplate.collectionExists("processed")) {
			mongoTemplate.dropCollection("processed");
		}
		mongoTemplate.insert(new OrderInfo("12345", new Date(), 123.45D), "pending");
		mongoTemplate.insert(new OrderInfo("67545", new Date(), 123.45D), "pending");
		mongoTemplate.insert(new OrderInfo("98978", new Date(), 123.45D), "processed");
		mongoTemplate.insert(new OrderInfo("26572", new Date(), 123.45D), "processed");
		mongoTemplate.insert(new OrderInfo("09027", new Date(), 123.45D), "processed");
		mongoTemplate.insert(new OrderInfo("17682", new Date(), 123.45D), "processed");
	}
}
