/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.ui.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Hardikkumar Patel
 *
 */
@Controller
public class HomePageController {
  
	@RequestMapping( value= "/",method = RequestMethod.GET)
	public String homePage(Model model) throws IOException {
		return "home";
	}
	 

}
