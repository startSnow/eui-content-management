/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leeco.eui.api.entity.Application;
import com.leeco.eui.api.entity.Recommendation;
import com.leeco.eui.api.exception.NoEntityFoundException;
import com.leeco.eui.api.exception.ServiceException;
import com.leeco.eui.api.model.ApiResponseBody;
import com.leeco.eui.api.service.ApplicationService;
import com.leeco.eui.api.service.RecommendationService;
import com.leeco.eui.api.service.UpdateInfoService;
import com.leeco.eui.api.utils.ApplicationEvents;
import com.leeco.eui.api.utils.DeviceTypeEnum;
 
/**
 * The Class ApplicationResource.
 *
 * @author Hardikkumar Patel
 */
@RestController
@RequestMapping("/v1/store/apps")
public class ApplicationResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationResource.class);
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private RecommendationService recommendationService;
	
	@Autowired
	private UpdateInfoService updateInfoService;
	
	/**
	 * Retrieve all application from Database.This method throws Service exception if something wrong went with database.
	 * If no application found then this API will throw HTTP 400 bad request response. 
	 *
	 * @return the applications
	 * @throws ServiceException the service exception
	 */
	@RequestMapping(method = RequestMethod.GET
			, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public @ResponseBody ApiResponseBody<List<Application>>  listApplications() throws ServiceException,NoEntityFoundException{
		LOGGER.debug("ApplicationResource.listApplications : List all applications ");
		long lastUpdatedDt = new java.util.Date().getTime();
		if(applicationService.getApplications() != null && !applicationService.getApplications().isEmpty()){
			return  ApiResponseBody.ok(HttpStatus.OK.value(), applicationService.getApplications(), lastUpdatedDt);
		}else{
			LOGGER.warn("No application found.");
			return  ApiResponseBody.ok(HttpStatus.OK.value(), null, lastUpdatedDt);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public Application  saveApplication(@Valid @RequestBody(required=true) Application application) throws Exception{
		LOGGER.debug("ApplicationResource.saveApplication : Creating new application.");
		try{
			Application dbApplication = applicationService.getApplicationById(application.getId());
			if(dbApplication != null){
				throw new IllegalArgumentException("Application Id already exist"); 
			}
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			application.setCreatedBy(authentication.getName());
			application.setCreateTimestamp(new java.util.Date());
			application = applicationService.saveApplication(application);
			
			updateInfoService.publishChanges(authentication.getName(),ApplicationEvents.APPLICATION_ADDED);
			
		}catch(Exception e){
			LOGGER.error("Error while creating new  application ",e);
			throw e;
		}
		return application; 
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.PATCH, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public Application  updateApplication(@Valid @RequestBody(required=true) Application application) throws Exception{
		LOGGER.debug("ApplicationResource.updateApplication : Updating application : "+application);
		try{
			Application dbApplication = applicationService.getApplicationById(application.getId());
			if(dbApplication == null){
				throw new IllegalArgumentException("Application does not exist"); 
			}
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			application.setCreatedBy(authentication.getName());
			application.setCreateTimestamp(new java.util.Date());
			application = applicationService.saveApplication(application);
			
			updateInfoService.publishChanges(authentication.getName(),ApplicationEvents.APPLICATION_UPDATED);
			
		}catch(Exception e){
			LOGGER.error("Error while updating application ",e);
			throw e;
		}
		return application; 
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteApplication(@PathVariable("id") Long id ) throws NoEntityFoundException , ServiceException{
		LOGGER.debug("ApplicationResource.deleteApplication : Deleting application :"+id);
		try{
			Application dbApplication = applicationService.getApplicationById(id);
			if(dbApplication == null){
				throw new IllegalArgumentException("Application Id does not  exist"); 
			}
			List<Recommendation> recommendations = recommendationService.loadRecommendationsByAppId(id);
			if(recommendations != null && !recommendations.isEmpty()){
				throw new IllegalArgumentException("Can't remove application as it is being used as recommendation"); 
			}
			applicationService.deleteApplication(dbApplication);

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			updateInfoService.publishChanges(authentication.getName(),ApplicationEvents.APPLICATION_DELETED);
			
			
		}catch(Exception e){
			LOGGER.error("Error while deleting application ",e);
			throw e;
		}
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{deviceType}/batch", method = RequestMethod.POST ,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public  List<Application> uploadApplicationUsingBatchFile(@PathVariable("deviceType") String  deviceType , @RequestParam("file") MultipartFile file) {
		LOGGER.debug("ApplicationResource.uploadApplicationUsingBatchFile File : "+file + " Device Type: "+deviceType);
		if (file.isEmpty()) {
			throw new IllegalArgumentException("File in request does not  exist"); 
		}
		final List<Application> results = new ArrayList<Application>();
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Application>> mapType = new TypeReference<List<Application>>() {};
		try {
			List<Application> apps = mapper.readValue(file.getBytes(), mapType);
			apps.forEach(item ->{
				try {
					item.setId(applicationService.nextAvailableId());
					item.setIconUrl(item.getIcon());
					item.setDeviceType(DeviceTypeEnum.valueOf(deviceType));
					item.setCreateTimestamp(new java.util.Date());
					item.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
					results.add(applicationService.saveApplication(item));
				} catch (Exception e) {
					LOGGER.error("Error while saving applicaiton",e);
				}
			});
		} catch (Exception e) {
			 LOGGER.error("Error while uploading batch data",e);
			 throw new IllegalArgumentException(e.getMessage());
		}
		return results;
	} 
}
