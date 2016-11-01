/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leeco.eui.api.dao.ApplicationRepository;
import com.leeco.eui.api.entity.Application;
import com.leeco.eui.api.exception.ServiceException;
import com.leeco.eui.api.utils.DeviceTypeEnum;
import com.leeco.eui.api.utils.ErrorCodes;

/**
 * @author Hardikkumar Patel
 *
 */
@Service
public class ApplicationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationService.class);

	@Autowired
	private ApplicationRepository applicationRepo;

	
	@Transactional(readOnly = true)    
	public List<Application> getApplications() throws ServiceException {
		LOGGER.debug("ApplicationService.getApplications : Service to load all applications");
		try{
			List<Application> result = new ArrayList<Application>();
			applicationRepo.findAll().forEach(result::add);
			return result;	
		}catch(Exception e){
			LOGGER.error("ApplicationService.getApplications Error while loading all application from repository",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}

	@Transactional(readOnly = true)    
	public List<Application> getApplicationsByDeviceType(DeviceTypeEnum deviceType) throws ServiceException {
		LOGGER.debug("ApplicationService.getApplications : Service to load all applications");
		try{
			List<Application> result = new ArrayList<Application>();
			applicationRepo.findApplicationByDeviceType(deviceType).forEach(result::add);
			return result;	
		}catch(Exception e){
			LOGGER.error("ApplicationService.getApplications Error while loading all application from repository",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	
	@Transactional(readOnly = true)    
	public Application getApplicationById(Long applicationId) throws ServiceException {
		LOGGER.debug("ApplicationService.getApplicationById : Service to load all applications");
		try{
			return  applicationRepo.findOne(applicationId);
		}catch(Exception e){
			LOGGER.error("ApplicationService.getApplicationById Error while loading all application from repository",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	 
	
	@Transactional(readOnly = false)    
	public Application saveApplication(Application application) throws ServiceException {
		LOGGER.debug("ApplicationService.saveApplication : Service to save new applications");
		try{
			return applicationRepo.save(application);
		}catch(Exception e){
			LOGGER.error("ApplicationService.saveApplication Error while Service to saving new applications",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	
	@Transactional(readOnly = false)    
	public void deleteApplication(Application application) throws ServiceException {
		LOGGER.debug("ApplicationService.deleteApplication : Service to save new applications");
		try{
			 applicationRepo.delete(application);
		}catch(Exception e){
			LOGGER.error("ApplicationService.deleteApplication Error while Service to saving new applications",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	
	@Transactional(readOnly = true)  	 
	public Long nextAvailableId() throws ServiceException{
		LOGGER.debug("ApplicationService.nextAvailableId : Next available id ");
		try{
			if(applicationRepo.nextAvailableId() == null){
				return 1l; // When there is no data avaialble in database
			}
			return applicationRepo.nextAvailableId() + 1;
		}catch(Exception e){
			LOGGER.error("ApplicationService.nextAvailableId Error while getting next available id  ",e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	
	 
}
