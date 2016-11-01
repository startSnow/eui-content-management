/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Bin Gong
 *
 */
@Entity
@Table(name = "region")
public class Region {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "region_id")
	private Integer regionId;
	
	@Column(name = "region_name", nullable = false)
	private String regionName;
	
	@Column(name = "region_description", nullable = false)
	private String regionDescription;

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionDescription() {
		return regionDescription;
	}

	public void setRegionDescription(String regionDescription) {
		this.regionDescription = regionDescription;
	}

}
