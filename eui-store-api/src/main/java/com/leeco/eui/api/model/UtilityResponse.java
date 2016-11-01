
package com.leeco.eui.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Hardikkumar patel
 */

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UtilityResponse {

	private String query;
	private String ts;
	private String sign;

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String toString() {
		return "UtilityResponse [query=" + query + ", ts=" + ts + ", sign=" + sign + "]";
	}
	
}
