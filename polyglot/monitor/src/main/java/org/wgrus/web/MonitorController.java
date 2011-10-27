package org.wgrus.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles order requests.
 */
@Controller
@RequestMapping(value="/")
public class MonitorController {

	@RequestMapping(method=RequestMethod.GET)
	public String displayForm() {
		return "monitor";
	}
}
