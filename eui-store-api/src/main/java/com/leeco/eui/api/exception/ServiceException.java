package com.leeco.eui.api.exception;

public class ServiceException extends Exception{

	private static final long serialVersionUID = -1650432229903861127L;
	
	public String errorCode;
	
	public ServiceException(Throwable e){
		super(e);
	}
	
	public ServiceException(String errorCode, String errorMessage,Throwable e){
		super(errorMessage,e);
		this.errorCode = errorCode;
	}
	
	public ServiceException(String errorMessage,Throwable e){
		super(errorMessage,e);
	}
}
