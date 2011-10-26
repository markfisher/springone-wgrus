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
		long pending = mongoTemplate.getCollection("pending").count();
		long processed = mongoTemplate.getCollection("processed").count();
		System.out.println("****** " + pending + "/" + processed);
		try {
			ds = new DefaultCategoryDataset();
			ds.addValue(pending, "Orders", "Pending");
			ds.addValue(processed, "Orders", "Processed");
			return ds;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;


	}

}
