package org.wgrus.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StubBillingService implements BillingService {

	@Override
	public boolean authorize(String customerId) {
		if (customerId.contains("bad")) {
			List<Long> list = new ArrayList<Long>();
			while (true) {
				list.add(new Random().nextLong());
			}
		}
		return true;
	}

}
