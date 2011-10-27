package org.wgrus.web;

import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

@RequestMapping("/charts")
@Controller
public class ChartController {

	@Autowired
	MongoTemplate mongoTemplate;

	@RequestMapping("/orders.png")
	public void renderControllers(OutputStream stream)
			throws Exception {
		JFreeChart chart = generateChart();
		ChartUtilities.writeChartAsPNG(stream, chart, 750, 400);
	}

	private JFreeChart generateChart() {
		DefaultCategoryDataset dataset = getOrderData();
		
		boolean rotate = false;

		return ChartFactory.createBarChart("WGRUS Order Status", // title
				"Orders", // x-axis label
				"Status", // y-axis label
				dataset, rotate ? PlotOrientation.HORIZONTAL
				: PlotOrientation.VERTICAL, true, // legend displayed
				true, // tooltips displayed
				false); // no URLs*/
	}
	
	private DefaultCategoryDataset getOrderData() {
		DefaultCategoryDataset ds = null;
		long messages = mongoTemplate.getCollection("messages").count();
		long orders = mongoTemplate.getCollection("orders").count();
		long rejects = mongoTemplate.getCollection("rejects").count();
		try {
			ds = new DefaultCategoryDataset();
			ds.addValue(messages, "Orders", "Pending");
			ds.addValue(orders, "Orders", "Processed");
			ds.addValue(rejects, "Orders", "Rejects");
			return ds;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;


	}

}
