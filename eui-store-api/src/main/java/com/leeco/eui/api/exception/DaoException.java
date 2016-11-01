package com.leeco.eui.api.exception;

public class DaoException extends Throwable{

	private static final long serialVersionUID = -1650432229903861127L;
	
	public String errorCode;
	
	public DaoException(Throwable e){
		super(e);
	}
	
	public DaoException(String errorCode, String errorMessage,Throwable e){
		super(errorMessage,e);
		this.errorCode = errorCode;
	}
	
	public DaoException(String errorMessage,Throwable e){
		super(errorMessage,e);
	}
}
