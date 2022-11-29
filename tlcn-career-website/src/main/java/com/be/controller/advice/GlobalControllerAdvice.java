package com.be.controller.advice;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import javax.validation.ConstraintViolationException;

import org.hibernate.StaleStateException;
import org.hibernate.TransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.be.controller.exception.CommonRuntimeException;
import com.be.payload.BaseResponse;
import com.be.payload.DataResponse;

@RestControllerAdvice
public class GlobalControllerAdvice {
	@ExceptionHandler(CommonRuntimeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse ssuserInfoExceptionHandler(CommonRuntimeException ex) {
		return new BaseResponse(false, ex.getMessage());
	}

	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse badCredentialsExceptionHandler(BadCredentialsException ex) {
		return new BaseResponse(false, "Username or password is invalid");
	}

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse entityNotFoundExceptionHandler(EntityNotFoundException ex) {
		return new BaseResponse(false, ex.getMessage());

	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse illegalArgumentExceptionnHandler(IllegalArgumentException ex) {
		return new BaseResponse(false, ex.getMessage());
	}

	@ExceptionHandler(ParseException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse parserExceptionHandler(ParseException ex) {
		return new BaseResponse(false, ex.getMessage());
	}

	@ExceptionHandler(IOException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseResponse IOExceptionHandler(IOException ex) {
		return new BaseResponse(false, ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public DataResponse<Map<String, String>> invalidDataFormat(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		List<FieldError> fieldErrors = ex.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return new DataResponse<>(false, "Data format invalid and violate constrains", errors);
	}

	@ExceptionHandler(OptimisticLockException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse OptimisticLockExceptionExceptionHandler(OptimisticLockException ex) {
		return new BaseResponse(false, "Something went wrong please try again");
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse SQLIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException ex) {
		return new BaseResponse(false, ex.getMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse ConstraintViolationExceptionHandler(ConstraintViolationException ex) {
		return new BaseResponse(false, ex.getMessage());
	}

	@ExceptionHandler(SQLException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse SQLExceptionHandler(SQLException ex) {
		return new BaseResponse(false, ex.getMessage());
	}

	@ExceptionHandler(StaleStateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse StaleStateExceptionHandler(StaleStateException ex) {
		return new BaseResponse(false, "Something went wrong. Please try again !");
	}
	
	@ExceptionHandler(TransactionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse TransactionExceptionHandler(TransactionException ex) {
		return new BaseResponse(false, ex.getMessage());
	}
}
