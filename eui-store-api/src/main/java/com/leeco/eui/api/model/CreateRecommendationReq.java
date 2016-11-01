package com.leeco.eui.api.model;

public class CreateRecommendationReq {
	private Long priorityId;
	private Long sequenceId;
	private Long categoryId;
	private Long applicationId;
	private Long recommendationId;
	public Long getPriorityId() {
		return priorityId;
	}
	public void setPriorityId(Long priorityId) {
		this.priorityId = priorityId;
	}
	public Long getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public Long getRecommendationId() {
		return recommendationId;
	}
	public void setRecommendationId(Long recommendationId) {
		this.recommendationId = recommendationId;
	}
	@Override
	public String toString() {
		return "CreateRecommendationReq [priorityId=" + priorityId + ", sequenceId=" + sequenceId + ", categoryId="
				+ categoryId + ", applicationId=" + applicationId + ", recommendationId=" + recommendationId + "]";
	}
	
	
}
