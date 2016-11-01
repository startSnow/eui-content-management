/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leeco.eui.api.model.GameApplication;
import com.leeco.eui.api.utils.DeviceTypeEnum;

/**
 * @author Bin Gong
 *
 */
@Entity
@Table(name = "category")
public class Category {
	@Id
	@Column(name = "category_id")
	private Long id;
	
	@Column(name = "category_name", nullable = false)
	private String name;
	
	@Column(name = "icon_url")
	private String iconUrl;
	
	@Column(name = "region_id", nullable = false)
	private Integer regionId;
	
	@Column(name = "device_type")
	@Enumerated(value=EnumType.STRING)
	private DeviceTypeEnum deviceType;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "create_timestamp")
	private Date createTimestamp;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "update_timestamp")
	private Date updateTimestamp;
	
	@JsonIgnore
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Recommendation> recommendations;
	
	 
	
	@Transient
	private List<GameApplication> gameApplications;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public DeviceTypeEnum getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceType(DeviceTypeEnum deviceType) {
		this.deviceType = deviceType;
	}
	
	public List<Recommendation> getRecommendations() {
		return recommendations;
	}
	
	public void setRecommendations(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}
	
	public Integer getRegionId() {
		return regionId;
	}
	
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
 

	public List<GameApplication> getGameApplications() {
		return gameApplications;
	}

	public void setGameApplications(List<GameApplication> gameApplications) {
		this.gameApplications = gameApplications;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getCreateTimestamp() {
		return createTimestamp;
	}
	
	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}
	
	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
}
