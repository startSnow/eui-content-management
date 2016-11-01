
package com.leeco.eui.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Hardikkumar patel
 */

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResponseBody<T> {

	private boolean success = false;

	private String message;

	private int code = -1;

	private long last_updated;

	private T data;

	public static <T> ApiResponseBody<T> error(int code, String message) {
		ApiResponseBody<T> response = new ApiResponseBody<>();
		response.code = code;
		response.message = message;
		return response;
	}

	public static <T> ApiResponseBody<T> ok(int code, T data, long date) {
		ApiResponseBody<T> response = new ApiResponseBody<>();
		response.code = code;
		response.success = true;
		response.last_updated = date;
		response.data = data;
		return response;
	}

	public static <T> ApiResponseBody<T> ok(int code) {
		ApiResponseBody<T> response = new ApiResponseBody<>();
		response.code = code;
		response.success = true;
		return response;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public long getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(long last_updated) {
		this.last_updated = last_updated;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
