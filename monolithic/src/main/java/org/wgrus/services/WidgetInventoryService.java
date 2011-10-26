package org.wgrus.services;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class WidgetInventoryService implements InventoryService {

	private final AtomicInteger count = new AtomicInteger(100);

	@Override
	public boolean reserve(int quantity) {
		int remaining = count.addAndGet(-quantity);
		if (remaining < 0) {
			count.addAndGet(quantity);
			return false;
		}
		return true;
	}

}
