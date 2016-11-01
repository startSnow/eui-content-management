/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Bin Gong
 *
 */
@Entity
@Table(name = "recommendation")
public class Recommendation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "recommendation_id")
	private Long recommendationId;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "application_id")
	private Application applilcation;
	
	@Column(name = "sequence_number", nullable = false)
	private Long sequenceNumber;
	
	@Column(name = "priority")
	private Integer priority;

	public Long getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(Long recommendationId) {
		this.recommendationId = recommendationId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Application getApplilcation() {
		return applilcation;
	}

	public void setApplilcation(Application applilcation) {
		this.applilcation = applilcation;
	}

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}
