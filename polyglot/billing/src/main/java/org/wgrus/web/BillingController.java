package org.wgrus.web;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Handles order requests.
 */
@Controller
@RequestMapping(value="/")
public class BillingController {

	JdbcTemplate jdbcTenmplate;
	
	@Autowired
	void setDataSource(DataSource dataSource) {
		this.jdbcTenmplate = new JdbcTemplate(dataSource);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String root(Model model) {
		model.addAttribute("msg", "Use /authorize/{customer_id}");
		return "bad";
	}

	@RequestMapping(value={"/authorize/{id}"}, method=RequestMethod.GET)
	public String populate(@PathVariable("id") String id, Model model) {
		List<Map<String, Object>> results = 
				this.jdbcTenmplate.queryForList(
						"select creditcard_no, credit_score from customers where customer_id = ?", 
						new Object[] {id});
		boolean ok = false;
		String message = "UNKNOWN";
		if (results.size() == 1) {
			if (results.get(0).get("creditcard_no") == null || ((String)results.get(0).get("creditcard_no")).length() < 16) {
				message = "NO_CREDIT";
			}
			else if (results.get(0).get("credit_score") != null && ((Number)results.get(0).get("credit_score")).intValue() < 650) {
				message = "BAD_CREDIT";
			}
			else {
				ok = true;
				message = id;
			}
		}
		else {
			message = "NOT_FOUND";
		}
		model.addAttribute("msg", message);
		if (ok) {
			return "good";
		}
		else {
			return "bad";
		}
	}

}
