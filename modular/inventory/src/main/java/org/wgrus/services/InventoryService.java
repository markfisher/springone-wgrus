package org.wgrus.services;

public interface InventoryService {

	long available();

	boolean reserve(long quantity);

}
