package org.wgrus.services;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

public class RedisInventoryService implements InventoryService {

	private volatile RedisAtomicLong count;

	public RedisInventoryService(String productId, RedisConnectionFactory factory) {
		this.count = new RedisAtomicLong(productId, factory);
		this.count.compareAndSet(0, 100);
	}

	@Override
	public boolean reserve(int quantity) {
		long remaining = count.addAndGet(-quantity);
		if (remaining < 0) {
			count.addAndGet(quantity);
			return false;
		}
		return true;
	}

}
