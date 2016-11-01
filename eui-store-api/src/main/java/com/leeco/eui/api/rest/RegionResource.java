/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leeco.eui.api.entity.Region;
import com.leeco.eui.api.exception.NoEntityFoundException;
import com.leeco.eui.api.exception.ServiceException;
import com.leeco.eui.api.model.ApiResponseBody;
import com.leeco.eui.api.service.RegionService;
 
/**
 * The Class CategoryResource.
 *
 * @author Hardikkumar Patel
 */
@RestController
@RequestMapping("/v1/store/regions")
public class RegionResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegionResource.class);

	@Autowired
	private RegionService regionService;
	
	/**
	 * This method list all categories present into database. 
	 * This method is also used to find category by category id and device type 
	 *
	 * @param id the id
	 * @param type the type
	 * @return the categories
	 * @throws NoEntityFoundException the no entity found exception
	 * @throws ServiceException the service exception
	 */
	@RequestMapping(method = RequestMethod.GET
			, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public ApiResponseBody<List<Region>> listAllRegion() throws NoEntityFoundException , ServiceException{
		LOGGER.debug("RegionResource.listAllRegion : List all regions");
		long lastUpdatedDt = new java.util.Date().getTime();
		List<Region> result = new ArrayList<>();
		result = regionService.listAllRegion();
		return ApiResponseBody.ok(HttpStatus.OK.value(), result, lastUpdatedDt);
	}
	
	 
	
}
