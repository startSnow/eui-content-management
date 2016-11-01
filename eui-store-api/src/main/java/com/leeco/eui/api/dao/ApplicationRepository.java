/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.leeco.eui.api.entity.Application;
import com.leeco.eui.api.utils.DeviceTypeEnum;

/**
 * @author Hardikkumar Patel
 *
 */
public interface ApplicationRepository extends CrudRepository<Application, Long> {
	@Query("select max(id) from Application")
	public Long nextAvailableId();
	
	@Query("from Application a where  a.deviceType = ?1")
	public List<Application> findApplicationByDeviceType(DeviceTypeEnum deviceType);
	
}
