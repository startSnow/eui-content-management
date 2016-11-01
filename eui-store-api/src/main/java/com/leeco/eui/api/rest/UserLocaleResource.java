/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.rest;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leeco.eui.api.model.UserProfile;

/**
 * @author Hardikkumar Patel
 *
 */
@RestController
@RequestMapping("/v1/user/locale")
public class UserLocaleResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserLocaleResource.class);
	 
	@RequestMapping(method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public UserProfile getUserLocale(HttpServletRequest request,Principal principal) throws Exception {
		UserProfile profile = new UserProfile();
		String defaultLocale = "en_US";
		if(request.getSession().getAttribute("defaultLocale") != null){
			defaultLocale = (String)request.getSession().getAttribute("defaultLocale"); 
		}
		profile.setUserName(principal.getName());
		profile.setLocale(defaultLocale);
		LOGGER.debug("UserLocaleResource.getUserLocal : "+ defaultLocale);
		return profile;
	}
  
}
