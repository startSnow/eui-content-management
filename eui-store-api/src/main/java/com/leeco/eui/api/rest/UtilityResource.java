/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leeco.eui.api.model.UtilityResponse;
import com.leeco.eui.api.service.ApplicationService;
import com.leeco.eui.api.service.CategoryService;
import com.leeco.eui.api.service.RecommendationService;
import com.leeco.eui.api.utils.CommonUtils;

/**
 * @author Hardikkumar Patel
 *
 */
@RestController
@RequestMapping("/v1/utility")
public class UtilityResource {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private RecommendationService recommendationService;
 
	@Autowired
	private CommonUtils commonUtils;
	
	@RequestMapping( value= "/token",method = RequestMethod.GET)
	public UtilityResponse generateGamingAPIParameters( ) throws IOException {
		UtilityResponse response = new UtilityResponse();
		StringBuilder result = new StringBuilder("?");
		long currentTime = new java.util.Date().getTime();
		String sb = commonUtils.generateMD5Hash(String.valueOf(currentTime)); 
		result.append("ts=");
		result.append(currentTime);
		result.append("&sign=");
		result.append(sb.toString());
		response.setQuery(result.toString());
		response.setSign(sb.toString());
		response.setTs(String.valueOf(currentTime));
		return response;
	}
 
	@RequestMapping( value= "/id/{type}",method = RequestMethod.GET)
	public Long generateNextAvailableId(@PathVariable("type") String type) throws Exception {
		Long nextAvailId = null;
		switch(type){
		case "CATEGORY" :
				nextAvailId = categoryService.nextAvailableId();
				break;
		case "APPLICATION":
				nextAvailId = applicationService.nextAvailableId();
				break;
		case "RECOMMENDATION" :
				nextAvailId = recommendationService.nextAvailableId();
				break;
		}
		return nextAvailId;
	}
}
