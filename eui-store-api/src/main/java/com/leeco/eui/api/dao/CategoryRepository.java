/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.leeco.eui.api.entity.Category;
import com.leeco.eui.api.utils.DeviceTypeEnum;

/**
 * @author Bin Gong
 *
 */
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
	
	@Query("select c from Category c where c.deviceType = ?1")
	public List<Category> findByDeviceType(DeviceTypeEnum deviceType);
	
	@Query("select c from Category c where c.regionId = ?1 and c.deviceType = ?2")
	public List<Category> findByRegionAndDeviceType(Long regionId, DeviceTypeEnum deviceType);
	
	@Query("select c from Category c where c.name = ?1")
	public List<Category> findByName(String categoryName);
	
	@Query("select max(id) from Category")
	public Long nextAvailableId();

}
