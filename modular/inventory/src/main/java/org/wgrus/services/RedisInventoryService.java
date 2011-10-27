package org.wgrus.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

public class RedisInventoryService implements InventoryService {

	private volatile RedisAtomicLong count;

	public RedisInventoryService(String productId, RedisConnectionFactory factory) {
		this.count = new RedisAtomicLong(productId, factory);
		this.count.compareAndSet(0, 100);
	}

	public long available() {
		return this.count.get();
	}

	public boolean reserve(long quantity) {
		if (quantity == 50) { crash(); }
		if (this.count.get() < quantity) {
			return false;
		}
		for (int i = 0; i < quantity; i++) {
			this.count.decrementAndGet();
		}
		return true;
	}

	private void crash() {
		List<Long> list = new ArrayList<Long>();
		while (true) {
			list.add(new Random().nextLong());
		}
	}
}
