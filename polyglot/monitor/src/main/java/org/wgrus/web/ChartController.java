package org.wgrus.web;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
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

		JFreeChart chart = ChartFactory.createBarChart("WGRUS Order Status", // title
				"Status", // x-axis label
				"Count", // y-axis label
				dataset, rotate ? PlotOrientation.HORIZONTAL
				: PlotOrientation.VERTICAL, true, // legend displayed
				true, // tooltips displayed
				false); // no URLs*/
		
		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		GradientPaint paintPending = new GradientPaint(
				0.0f, 0.0f, Color.yellow, 
				0.0f, 0.0f, new Color(0, 64, 64));
		GradientPaint paintProcessed = new GradientPaint(
				0.0f, 0.0f, Color.green, 
				0.0f, 0.0f, new Color(0, 64, 0));
		GradientPaint paintReject = new GradientPaint(
				0.0f, 0.0f, Color.red, 
				0.0f, 0.0f, new Color(64, 0, 0));
		
		renderer.setSeriesPaint(0, paintPending);
		renderer.setSeriesPaint(1, paintProcessed);
		renderer.setSeriesPaint(2, paintReject);
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
		return chart;
	}
	
	private DefaultCategoryDataset getOrderData() {
		DefaultCategoryDataset ds = null;
		long messages = mongoTemplate.getCollection("messages").count();
		long orders = mongoTemplate.getCollection("orders").count();
		long rejects = mongoTemplate.getCollection("rejects").count();
		try {
			ds = new DefaultCategoryDataset();
			ds.addValue(messages, "Pending", "Orders");
			ds.addValue(orders, "Processed", "Orders");
			ds.addValue(rejects, "Rejects", "Orders");
			return ds;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;


	}

}
