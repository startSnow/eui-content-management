package com.leeco.eui.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.leeco.eui.api.exception.AuthenticationException;
import com.leeco.eui.api.exception.NoEntityFoundException;
import com.leeco.eui.api.exception.ServiceException;
import com.leeco.eui.api.model.ApiResponseBody;
/**
 * The Class EuiExceptionHandler.
 */
@ControllerAdvice
public class EuiExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Bad request.
	 *
	 * @param exception the exception
	 * @return the response body
	 */
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({IllegalArgumentException.class })
	public ApiResponseBody<Object> badRequest(Exception exception) {
		return ApiResponseBody.error(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
	}

	/**
	 * Internal server error request.
	 *
	 * @param exception the exception
	 * @return the response body
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ ServiceException.class ,Exception.class})
	public ApiResponseBody<Object> internalServerErrorRequest(Exception exception) {
		return ApiResponseBody.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler({ NoEntityFoundException.class})
	public ApiResponseBody<Object> resourceNotFoundRequest(NoEntityFoundException exception) {
		return ApiResponseBody.error(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
	
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler({ AuthenticationException.class})
	public ApiResponseBody<Object> authenticationExceptionRequest(AuthenticationException exception) {
		return ApiResponseBody.error(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
	}
	
	
	
}