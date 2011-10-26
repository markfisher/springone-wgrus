package org.wgrus.web;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wgrus.web.StoreFront;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class StoreFrontIntegrationTests {

	@Autowired
	private StoreFront store;

	@Test
	public void test() {
		Map<String, Object> model = new HashMap<String, Object>(); 
		store.placeOrder("joe", 25, "widget", model);
		store.placeOrder("joe", 25, "widget", model);
		store.placeOrder("joe", 25, "widget", model);
		//store.placeOrder("bad", 25, "widget", model);
	}

}
