/**
 * 
 */
package org.dtodo1paco.samples.urlshortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author pac
 *
 */
@Controller
public class IndexController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

}
