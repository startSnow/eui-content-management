package com.leeco.eui.api.exception;

public class NoEntityFoundException extends Exception{

	private static final long serialVersionUID = -1650432229903861127L;
	
	public String errorCode;
	
	public NoEntityFoundException(Throwable e){
		super(e);
	}
	
	public NoEntityFoundException(String errorMessage){
		super(errorMessage);
	}
	
	public NoEntityFoundException(String errorCode, String errorMessage,Throwable e){
		super(errorMessage,e);
		this.errorCode = errorCode;
	}
	
	public NoEntityFoundException(String errorMessage,Throwable e){
		super(errorMessage,e);
	}
}
