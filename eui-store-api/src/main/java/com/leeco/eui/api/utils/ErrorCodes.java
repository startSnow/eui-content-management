package com.leeco.eui.api.utils;

/**
 * @ copyright 2016, LeEco Technologies.
 */
public enum ErrorCodes {
	INTERNAL_SERVER_ERROR("ERROR_001", "Internal Server error"), 
	JDBC_ERROR("ERROR_002","Database connection error"),
	ENTITY_NOT_FOUND("ERROR_003","Entity not found");
	ErrorCodes(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	};

	private String errorCode;
	private String errorMessage;

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}
}
