/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Hardikkumar patel
 *
 */
@Entity
@Table(name = "update_info")
public class UpdateInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "update_info_id")
	private Long id;
	
	@Column(name = "updated_data")
	private String updatedData;
	
	@Column(name = "updated_by", nullable = false)
	private String updatedBy;
	
	@Column(name = "last_updated")
	private Date lastUpdated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUpdatedData() {
		return updatedData;
	}

	public void setUpdatedData(String updatedData) {
		this.updatedData = updatedData;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
