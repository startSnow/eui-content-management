/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leeco.eui.api.dao.RegionRepository;
import com.leeco.eui.api.entity.Region;
import com.leeco.eui.api.exception.ServiceException;
import com.leeco.eui.api.utils.ErrorCodes;

/**
 * @author Hardikkumar Patel
 *
 */
@Service
public class RegionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegionService.class);

	@Autowired
	private RegionRepository regionRepository;
	
	@Transactional(readOnly = true)  	 
	public List<Region> listAllRegion() throws ServiceException{
		LOGGER.debug("RegionService.listAllRegion : Load regions");
		try{
			return regionRepository.findAll();
		}catch(Exception e){
			LOGGER.error("RegionService.listAllRegion Error while loading category from repository by Id " ,e);
			throw new ServiceException(ErrorCodes.JDBC_ERROR.getErrorCode(),ErrorCodes.JDBC_ERROR.getErrorMessage(),e);
		}
	}
	 
	
}
