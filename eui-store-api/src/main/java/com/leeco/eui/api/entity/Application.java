/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.leeco.eui.api.utils.DeviceTypeEnum;

/**
 * @author Bin Gong
 *
 */
@Entity
@Table(name = "application")
public class Application {
	@Id
	@Column(name = "application_id")
	private Long id;
	
	@Column(name = "package_name", nullable = false)
	private String packageName;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "banner_image_url")
	private String bannerImageUrl;
	
	@Column(name = "icon_url")
	private String iconUrl;

	@Column(name = "device_type")
	@Enumerated(EnumType.STRING)
	private DeviceTypeEnum deviceType;
	
	@Column(name = "big_image_url")
	private String bigImageUrl;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "create_timestamp")
	private Date createTimestamp;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "update_timestamp")
	private Date updateTimestamp;
	
	@Transient
	private String icon;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBannerImageUrl() {
		return bannerImageUrl;
	}
	
	public void setBannerImageUrl(String bannerImageUrl) {
		this.bannerImageUrl = bannerImageUrl;
	}
	
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	public String getBigImageUrl() {
		return bigImageUrl;
	}

	public void setBigImageUrl(String bigImageUrl) {
		this.bigImageUrl = bigImageUrl;
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

	public DeviceTypeEnum getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceTypeEnum deviceType) {
		this.deviceType = deviceType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
