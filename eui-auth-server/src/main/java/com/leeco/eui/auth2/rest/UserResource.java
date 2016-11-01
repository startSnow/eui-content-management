/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.auth2.rest;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.leeco.eui.auth2.model.UserProfile;
 

/**
 * @author Hardikkumar Patel
 *
 */
@RestController
@RequestMapping("/v1/user")
@EnableResourceServer
public class UserResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
	
	 
	@ResponseBody
	public Principal user(Principal user) {
		return user;
	} 
	
	@RequestMapping(value="/locale" , method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public @ResponseBody UserProfile getUserLocale(HttpServletRequest request,Principal principal) throws Exception {
		UserProfile profile = new UserProfile();
		String defaultLocale = "en_US";
		if(request.getSession().getAttribute("defaultLocale") != null){
			defaultLocale = (String)request.getSession().getAttribute("defaultLocale"); 
		}
		profile.setUserName(principal.getName());
		profile.setLocale(defaultLocale);
		LOGGER.debug("UserResource.getUserLocal : "+ defaultLocale);
		return profile;
	} 
  
}
