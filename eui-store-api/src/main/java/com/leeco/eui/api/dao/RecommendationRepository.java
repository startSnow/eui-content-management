/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.leeco.eui.api.entity.Recommendation;

/**
 * @author Bin Gong
 *
 */
public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {
	
	@Query("select max(recommendationId) from Recommendation")
	public Long nextAvailableId();
	
	@Query("from Recommendation r where r.category.id = ?1 order by r.priority , r.sequenceNumber")
	public List<Recommendation> findByCategoryId(Long categoryId);
	
	@Query("from Recommendation r where r.applilcation.id = ?1")
	public List<Recommendation> findByAppId(Long appId);
	
}
