/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leeco.eui.api.dao.CategoryRepository;
import com.leeco.eui.api.entity.Category;
import com.leeco.eui.api.exception.ServiceException;
import com.leeco.eui.api.utils.ErrorCodes;

/**
 * @author Bin Gong
 *
 */
@Service
public class CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Transactional(readOnly = true)  	 
	public Category getCategoryById(Long id) throws ServiceException{
		LOGGER.debug("CategoryService.getCategoryById : Load category details by Id "+id);
		try{
			Category result = categoryRepo.findOne(id);
			return result;
		}catch(Exception e){
			LOGGER.error("CategoryService.getCategoryById Error while loading category from repository by Id " + id,e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	@Transactional(readOnly = true)     
	public List<Category> getAllCategories() throws ServiceException {
		LOGGER.debug("CategoryService.getAllCategories : Load all Categories");
		try{
			List<Category> list = new ArrayList<Category>();
			categoryRepo.findAll().forEach(list::add);
			return list;
		}catch(Exception e){
			LOGGER.error("CategoryService.getAllCategories Error while loading all Categories",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	 
	@Transactional(readOnly = false)  	 
	public Category saveCategory(Category category) throws ServiceException{
		LOGGER.debug("CategoryService.saveCategory : Save category"+category);
		try{
			Category result = categoryRepo.save(category);
			return result;
		}catch(Exception e){
			LOGGER.error("CategoryService.saveCategory Error while creating new  category to  repository  ",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	
	@Transactional(readOnly = false)  	 
	public void deleteCategory(Category category) throws ServiceException{
		LOGGER.debug("CategoryService.deleteCategory : Delete category"+category);
		try{
			categoryRepo.delete(category);
		}catch(Exception e){
			LOGGER.error("CategoryService.deleteCategory Error while deleting  category to  repository  ",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	
	@Transactional(readOnly = true)  	 
	public Long nextAvailableId() throws ServiceException{
		LOGGER.debug("CategoryService.nextAvailableId : Next available id ");
		try{
			if(categoryRepo.nextAvailableId() == null){
				return 1l; // When there is no data avaialble in database
			}
			return categoryRepo.nextAvailableId() + 1;
		}catch(Exception e){
			LOGGER.error("CategoryService.nextAvailableId Error while getting next available id  ",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	
	public byte[] getBanner(@PathParam("packageName") String packageName) {
		LOGGER.debug("CategoryService.getBanner : Load banner by package name "+packageName);
		return null;
	}
	
}
