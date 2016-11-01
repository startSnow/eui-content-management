package com.leeco.eui.api.exception;

public class RestException extends Exception{

	private static final long serialVersionUID = -1650432229903861127L;
	
	public String errorCode;
	
	public RestException(Throwable e){
		super(e);
	}
	
	public RestException(String errorCode, String errorMessage,Throwable e){
		super(errorMessage,e);
		this.errorCode = errorCode;
	}
	
	public RestException(String errorMessage,Throwable e){
		super(errorMessage,e);
	}
}
