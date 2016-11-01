package com.leeco.eui.api.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leeco.eui.api.dao.UpdateInfoRepository;
import com.leeco.eui.api.entity.UpdateInfo;
import com.leeco.eui.api.utils.ApplicationEvents;

@Service
public class UpdateInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateInfoService.class);

	@Autowired
	private UpdateInfoRepository infoRepository;
	
	@Transactional(readOnly = false)
	public void publishChanges(String publishedBy,ApplicationEvents applicationEvent){
		LOGGER.debug("UpdateInfoService.publishChanges : Publishing updated changes to all devices by :"+publishedBy + " Event "+applicationEvent);
		UpdateInfo item = new UpdateInfo();
		item.setUpdatedBy(publishedBy);
		item.setUpdatedData(applicationEvent.name());
		infoRepository.save(item);
	}
}
