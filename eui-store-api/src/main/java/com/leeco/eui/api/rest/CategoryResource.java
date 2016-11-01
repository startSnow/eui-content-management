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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leeco.eui.api.entity.Category;
import com.leeco.eui.api.entity.Recommendation;
import com.leeco.eui.api.exception.NoEntityFoundException;
import com.leeco.eui.api.exception.ServiceException;
import com.leeco.eui.api.model.ApiResponseBody;
import com.leeco.eui.api.service.CategoryService;
import com.leeco.eui.api.service.RecommendationService;
import com.leeco.eui.api.service.UpdateInfoService;
import com.leeco.eui.api.utils.ApplicationEvents;
 
/**
 * The Class CategoryResource.
 *
 * @author Hardikkumar Patel
 */
@RestController
@RequestMapping("/v1/store/categories")
public class CategoryResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryResource.class);

	@Autowired
	private CategoryService categoryRepo;
	
	@Autowired
	private RecommendationService recommendationService;
	
	@Autowired
	private UpdateInfoService updateInfoService;

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
	public ApiResponseBody<List<Category>> listCategories() throws NoEntityFoundException , ServiceException{
		System.out.println("********"+SecurityContextHolder.getContext().getAuthentication());
		LOGGER.debug("CategoryResource.listCategories : List all categories");
		long lastUpdatedDt = new java.util.Date().getTime();
		List<Category> result = new ArrayList<>();
		result = categoryRepo.getAllCategories();
		return ApiResponseBody.ok(HttpStatus.OK.value(), result, lastUpdatedDt);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}
			, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public Category saveCategory(@Valid @RequestBody(required=true)  Category category) throws NoEntityFoundException , ServiceException{
		LOGGER.debug("CategoryResource.saveCategory : Create new category"+category);
		try{
			Category dbCategory = categoryRepo.getCategoryById(category.getId());
			if(dbCategory != null){
				throw new IllegalArgumentException("Category Id already exist"); 
			}
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			category.setCreatedBy(authentication.getName());
			category.setCreateTimestamp(new java.util.Date());
			category = categoryRepo.saveCategory(category);
			
			updateInfoService.publishChanges(authentication.getName(),ApplicationEvents.CATEGORY_ADDED);
			
		}catch(Exception e){
			LOGGER.error("Error while saving category ",e);
			throw e;
		}
		return category;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteCategory(@PathVariable("id") Long id ) throws NoEntityFoundException , ServiceException{
		LOGGER.debug("CategoryResource.deleteCategory : Deleting  category"+id);
		try{
			Category dbCategory = categoryRepo.getCategoryById(id);
			if(dbCategory == null){
				throw new IllegalArgumentException("Category Id does not  exist"); 
			}
			List<Recommendation> recommendations = recommendationService.loadRecommendationsByCategoryId(id);
			if(recommendations != null && !recommendations.isEmpty()){
				throw new IllegalArgumentException("Can't remove category as it is being used as recommendation"); 
			}
			categoryRepo.deleteCategory(dbCategory);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			updateInfoService.publishChanges(authentication.getName(),ApplicationEvents.CATEGORY_DELETED);
			
		}catch(Exception e){
			LOGGER.error("Error while deleting category ",e);
			throw e;
		}
	}
 
	
}
