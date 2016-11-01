/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leeco.eui.api.dao.RecommendationRepository;
import com.leeco.eui.api.entity.Application;
import com.leeco.eui.api.entity.Category;
import com.leeco.eui.api.entity.Recommendation;
import com.leeco.eui.api.exception.ServiceException;
import com.leeco.eui.api.model.CreateRecommendationReq;
import com.leeco.eui.api.utils.ErrorCodes;

/**
 * @author Hardikkumar Patel
 *
 */
@Service
public class RecommendationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationService.class);

	@Autowired
	private RecommendationRepository recommendationRepository;
	
	@Transactional(readOnly = true)    
	public Recommendation loadRecommendationsById(Long id) throws ServiceException {
		LOGGER.debug("RecommendationService.loadRecommendationsById : Load all Recommendations by" + id);
		try{
			Recommendation result = recommendationRepository.findOne(id);
			return result;	
		}catch(Exception e){
			LOGGER.error("RecommendationService.loadRecommendationsById Error while loading all application from repository",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	
	@Transactional(readOnly = true)    
	public List<Recommendation> loadRecommendationsByCategoryId(Long categoryId) throws ServiceException {
		LOGGER.debug("RecommendationService.loadRecommendationsByCategoryId : Load all Recommendations by" + categoryId);
		try{
			List<Recommendation> result = new ArrayList<Recommendation>();
			recommendationRepository.findByCategoryId(categoryId).forEach(result::add);
			return result;	
		}catch(Exception e){
			LOGGER.error("RecommendationService.loadRecommendationsByCategoryId Error while loading all application from repository",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	
	@Transactional(readOnly = true)    
	public List<Recommendation> loadRecommendationsByAppId(Long appId) throws ServiceException {
		LOGGER.debug("RecommendationService.loadRecommendationsByAppId : Load all Recommendations by" + appId);
		try{
			List<Recommendation> result = new ArrayList<Recommendation>();
			recommendationRepository.findByAppId(appId).forEach(result::add);
			return result;	
		}catch(Exception e){
			LOGGER.error("RecommendationService.loadRecommendationsByAppId Error while loading all application from repository",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	
	@Transactional(readOnly = false)    
	public Recommendation saveRecommendation(CreateRecommendationReq req) throws ServiceException {
		LOGGER.debug("RecommendationService.loadRecommendations : Load all Recommendations by" + req);
		try{
			Recommendation newRecommendation = new Recommendation();
			if(req.getPriorityId() == null){
				newRecommendation.setPriority(0);
			}else{
				newRecommendation.setPriority(req.getPriorityId().intValue());
			}
			newRecommendation.setSequenceNumber(req.getSequenceId());
			Application application = new Application();
			application.setId(req.getApplicationId());
			Category category = new Category();
			category.setId(req.getCategoryId());
			newRecommendation.setApplilcation(application);
			newRecommendation.setCategory(category);
			newRecommendation.setRecommendationId(req.getRecommendationId());
			newRecommendation = recommendationRepository.save(newRecommendation);

			return newRecommendation;
			 
		}catch(Exception e){
			LOGGER.error("RecommendationService.loadRecommendations Error while loading all application from repository",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	
	@Transactional(readOnly = false, rollbackFor = {Exception.class})    
	public void batchUpdateRecommendations(List<Recommendation>  recommendations) throws ServiceException {
		LOGGER.debug("RecommendationService.batchUpdateRecommendations : Updating all recommendations");
		try{
			AtomicInteger index = new AtomicInteger();
			recommendations.forEach(item -> {
				item.setSequenceNumber(Long.valueOf(index.incrementAndGet()));
				if(item.getRecommendationId().longValue() == -99999L)
				{
					item.setRecommendationId(null);
				}
				recommendationRepository.save(item);
			});
  		}catch(Exception e){
			LOGGER.error("RecommendationService.batchUpdateRecommendations Error while updating recommendation repository",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	} 


	@Transactional(readOnly = true)  	 
	public Long nextAvailableId() throws ServiceException{
		LOGGER.debug("RecommendationService.nextAvailableId : Next available id ");
		try{
			if(recommendationRepository.nextAvailableId() == null){
				return 1l; // When there is no data avaialble in database
			}
			return recommendationRepository.nextAvailableId() + 1;
		}catch(Exception e){
			LOGGER.error("RecommendationService.nextAvailableId Error while getting next available id  ",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	
	@Transactional(readOnly = false)    
	public void deleteRecommendation(Long id) throws ServiceException {
		LOGGER.debug("RecommendationService.loadRecommendations : Load all Recommendations by" + id);
		try{ 
			recommendationRepository.delete(id); 
		}catch(Exception e){
			LOGGER.error("RecommendationService.loadRecommendations Error while loading all application from repository",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
}

