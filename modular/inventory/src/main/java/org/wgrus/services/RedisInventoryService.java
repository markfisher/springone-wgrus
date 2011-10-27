package org.wgrus.services;

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
		long remaining = this.count.addAndGet(-quantity);
		if (remaining < 0) {
			this.count.addAndGet(quantity);
			return false;
		}
		return true;
	}

}
