package com.meatigo.campaign.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(value = CampaignException.class)
	@ResponseBody
	public ErrorDetails handleDatabaseException(CampaignException e) {
		log.error("EXCEPTION:", e);
		return new ErrorDetails(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
	}
    
    
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDetails handleValidationException(MethodArgumentNotValidException e) {
		BindingResult validationResult = e.getBindingResult();
		String object = validationResult.getObjectName();
		ErrorDetails response = new ErrorDetails("RESOURCE_NOT_VALID",
				"Validation error: " + object + " is not valid. ");
		validationResult.getFieldErrors().stream().forEach(error -> response.addCause("Invalid " + error.getField(),
				StringUtils.capitalize(error.getField() + " " + error.getDefaultMessage())));

		return response;
	}


    @ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorDetails handleException(Exception e) {
		return new ErrorDetails(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
	}

}
