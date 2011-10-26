package org.wgrus.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WidgetInventoryServiceTests {

	@Test
	public void test() {
		WidgetInventoryService service = new WidgetInventoryService();
		assertTrue(service.reserve(50));
		assertTrue(service.reserve(48));
		assertFalse(service.reserve(3));
		assertTrue(service.reserve(1));
		assertTrue(service.reserve(1));
		assertFalse(service.reserve(1));
	}

}
