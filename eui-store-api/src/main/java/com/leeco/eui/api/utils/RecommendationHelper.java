/**
 * @ copyright 2016, LeEco Technologies.
 */
package com.leeco.eui.api.utils;

import java.util.ArrayList;
import java.util.List;

import com.leeco.eui.api.entity.Application;
import com.leeco.eui.api.entity.Category;
import com.leeco.eui.api.entity.Recommendation;

public final class RecommendationHelper {
 
	static public List<Application> excludeApplications(List<Application> totalApps , List<Recommendation> recommandations){
		final List<Application> result = new ArrayList<Application>();
		totalApps.forEach(application -> {
			if(!recommandations.stream().filter( x -> x.getApplilcation().getId().equals(application.getId())).findFirst().isPresent()){
				result.add(application);
			}
		});
		return result;
	}
	
	static public List<Recommendation> availableRecommondation(List<Application> totalApps , List<Recommendation> recommandations,final Category category){
		final List<Recommendation> result = new ArrayList<Recommendation>();
		totalApps.forEach(application -> {
			if(!recommandations.stream().filter( x -> x.getApplilcation().getId().equals(application.getId())).findFirst().isPresent()){
				Recommendation recommendation = new Recommendation();
				recommendation.setRecommendationId(-99999L);
				recommendation.setApplilcation(application);
				recommendation.setCategory(category);
				recommendation.setPriority(0);
				result.add(recommendation);
			}
		});
		return result;
	}
}
