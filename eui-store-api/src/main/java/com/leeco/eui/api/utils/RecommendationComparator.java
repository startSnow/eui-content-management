package com.leeco.eui.api.utils;

import java.util.Comparator;

import com.leeco.eui.api.entity.Recommendation;

public class RecommendationComparator implements Comparator<Recommendation> {

	@Override
	public int compare(Recommendation o1, Recommendation o2) {
		return Long.compare(o1.getSequenceNumber(), o2.getSequenceNumber());
	}

}
