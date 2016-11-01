/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leeco.eui.api.entity.UpdateInfo;

/**
 * @author Hardikkumar p
 *
 */
 
public interface UpdateInfoRepository extends JpaRepository<UpdateInfo, Long> {
	 
	
}
