/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.rest;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.leeco.eui.api.entity.Application;
import com.leeco.eui.api.entity.Category;
import com.leeco.eui.api.entity.Recommendation;
import com.leeco.eui.api.exception.NoEntityFoundException;
import com.leeco.eui.api.exception.ServiceException;
import com.leeco.eui.api.model.ApiResponseBody;
import com.leeco.eui.api.model.CreateRecommendationReq;
import com.leeco.eui.api.service.ApplicationService;
import com.leeco.eui.api.service.CategoryService;
import com.leeco.eui.api.service.RecommendationService;
import com.leeco.eui.api.service.UpdateInfoService;
import com.leeco.eui.api.utils.ApplicationEvents;
import com.leeco.eui.api.utils.RecommendationHelper;
 
/**
 * The Class RecommendationResource.
 *
 * @author Hardikkumar Patel
 */
@RestController
@RequestMapping("/v1/store/category/{categoryId}/recommendation")
public class RecommendationResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationResource.class);
	
	@Autowired
	private RecommendationService recommendationService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ApplicationService applicationService;
	
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
	public @ResponseBody ApiResponseBody<List<Recommendation>>  listRecommendations(@PathVariable("categoryId") Long categoryId , @RequestParam(value="available", defaultValue = "false") boolean available) throws ServiceException,NoEntityFoundException{
		LOGGER.debug("RecommendationResource.listRecommendations : Retrieving all recommendations ? "+available);
		long lastUpdatedDt = new java.util.Date().getTime();
		List<Recommendation> recommendations = null;
		if(available){
			List<Recommendation> allRecommendations = recommendationService.loadRecommendationsByCategoryId(categoryId);
			Category category = categoryService.getCategoryById(categoryId);
			List<Application> totalApps = applicationService.getApplicationsByDeviceType(category.getDeviceType()); 
			recommendations = RecommendationHelper.availableRecommondation(totalApps, allRecommendations,category);
		}else{
			recommendations = recommendationService.loadRecommendationsByCategoryId(categoryId);
		}
		if(recommendations != null && !recommendations.isEmpty()){
			return  ApiResponseBody.ok(HttpStatus.OK.value(), recommendations, lastUpdatedDt);
		}else{
			return  ApiResponseBody.ok(HttpStatus.OK.value(), null, lastUpdatedDt);
		}
	}
	 
	@RequestMapping(value = "/apps" , method = RequestMethod.GET
			, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public @ResponseBody ApiResponseBody<List<Application>>  loadRecommendationdedApps(@PathVariable("categoryId") Long categoryId ) throws ServiceException,NoEntityFoundException{
		LOGGER.debug("RecommendationResource.loadRecommendationdedApps : Retrieving all recommended applications");
		long lastUpdatedDt = new java.util.Date().getTime(); 
		List<Recommendation> recommendations = recommendationService.loadRecommendationsByCategoryId(categoryId);
		Category category = categoryService.getCategoryById(categoryId);
		List<Application> totalApps = applicationService.getApplicationsByDeviceType(category.getDeviceType()); 
		List<Application> result = RecommendationHelper.excludeApplications(totalApps, recommendations);
		if(result != null && !result.isEmpty()){
			return  ApiResponseBody.ok(HttpStatus.OK.value(), result, lastUpdatedDt);
		}else{
			return  ApiResponseBody.ok(HttpStatus.OK.value(), null, lastUpdatedDt);
		}
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST
			, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},  consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE} )
	public  Recommendation saveRecommendation(@PathVariable("categoryId") Long categoryId ,@RequestBody CreateRecommendationReq  req) throws ServiceException,NoEntityFoundException{
		LOGGER.debug("RecommendationResource.saveRecommendation : Creating new recommendation :"+req + " Category Id "+categoryId);
		try{
			Recommendation recommendation = recommendationService.loadRecommendationsById(req.getRecommendationId());
			if(recommendation != null){
				throw new IllegalArgumentException("Recommendation Id already exist"); 
			}
			Recommendation createdData=  recommendationService.saveRecommendation(req);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			updateInfoService.publishChanges(authentication.getName(),ApplicationEvents.RECOMMENDATION_ADDED);
			return createdData;
			
		}catch(Exception e){
			LOGGER.error("Error while saving Recommendation",e);
			throw e;
		}
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.PATCH
			, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},  consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE} )
	public  Recommendation  updateRecommendation(@PathVariable("categoryId") Long categoryId ,@RequestBody CreateRecommendationReq  req) throws ServiceException,NoEntityFoundException{
		LOGGER.debug("RecommendationResource.updateRecommendation : Updating recommendation :"+req);
		try{
			Recommendation recommendation = recommendationService.loadRecommendationsById(req.getRecommendationId());
			if(recommendation == null){
				throw new IllegalArgumentException("Recommendation Id does not exist"); 
			}
			Recommendation updatedData =  recommendationService.saveRecommendation(req);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			updateInfoService.publishChanges(authentication.getName(),ApplicationEvents.RECOMMENDATION_ORDER_CHANGED);
			return updatedData; 
			
		}catch(Exception e){
			LOGGER.error("Error while updating Recommendation",e);
			throw e;
		}
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/batch",method = RequestMethod.PATCH
			, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},  consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE} )
	public  List<Recommendation>  updateAllRecommendationsSeq(@PathVariable("categoryId") Long categoryId ,@RequestBody List<Recommendation>  recommendations) throws ServiceException,NoEntityFoundException{
		LOGGER.debug("RecommendationResource.updateAllRecommendationsSeq : Updating recommendations :"+recommendations);
		try{
			recommendationService.batchUpdateRecommendations(recommendations);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			updateInfoService.publishChanges(authentication.getName(),ApplicationEvents.RECOMMENDATION_ORDER_CHANGED);
			return recommendationService.loadRecommendationsByCategoryId(categoryId);
		}catch(Exception e){
			LOGGER.error("Error while updating batch Recommendations",e);
			throw e;
		}
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{recommendationId}" ,method = RequestMethod.DELETE)
	public  void deleteRecommendation(@PathVariable("categoryId") Long categoryId , @PathVariable("recommendationId") Long recommendationId) throws ServiceException,NoEntityFoundException{
		LOGGER.debug("RecommendationResource.deleteRecommendation : Creating new  recommendation :"+recommendationId);
		try{
			Recommendation recommendation = recommendationService.loadRecommendationsById(recommendationId);
			if(recommendation == null){
				throw new IllegalArgumentException("Recommendation Id does not exist"); 
			}
			recommendationService.deleteRecommendation(recommendationId);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			updateInfoService.publishChanges(authentication.getName(),ApplicationEvents.RECOMMENDATION_DELETED);
			
		}catch(Exception e){
			LOGGER.error("Error while deleting Recommendation",e);
			throw e;
		}
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/batch" ,method = RequestMethod.DELETE)
	public void deleteRecommendations(@PathVariable("categoryId") Long categoryId , @RequestParam( value ="recommendationIds" , required = true) String recommendationIds) throws ServiceException,NoEntityFoundException{
		LOGGER.debug("RecommendationResource.deleteRecommendation : Creating new  recommendation :"+recommendationIds);
		try{
			String[] recommondIds = recommendationIds.split(",");
			for(String recommendationId : recommondIds){
				Recommendation recommendation = recommendationService.loadRecommendationsById(Long.valueOf(recommendationId));
				if(recommendation == null){
					throw new IllegalArgumentException("Recommendation Id does not exist"); 
				}
				recommendationService.deleteRecommendation(Long.valueOf(recommendationId));
			}
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			updateInfoService.publishChanges(authentication.getName(),ApplicationEvents.RECOMMENDATION_DELETED);
			
		}catch(Exception e){
			LOGGER.error("Error while deleting Recommendation",e);
			throw e;
		}
		
	}
}
