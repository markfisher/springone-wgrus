package org.wgrus.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wgrus.utils.DbHelper;

/**
 * Handles order requests.
 */
@Controller
@RequestMapping(value="/")
public class MonitorController {

	@Autowired
	DbHelper dbHelper;

	@RequestMapping(method=RequestMethod.GET)
	public String displayForm() {
		return "monitor";
	}

	@RequestMapping(value={"/populate"}, method=RequestMethod.GET)
	public String populate(Model model) {
		dbHelper.populate();
		return "redirect:/";
	}

}
