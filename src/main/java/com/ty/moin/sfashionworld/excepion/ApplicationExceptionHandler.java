package com.ty.moin.sfashionworld.excepion;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ty.moin.sfashionworld.dto.ResponseStructure;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleIdNotValid(IdNotFoundException exception){
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage("ID NOT FOUND");
		responseStructure.setData(exception.getMessage());
		return new ResponseEntity<>(responseStructure, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EmailIdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleEmailNotValid(EmailIdNotFoundException exception){
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage("EMAIL NOT FOUND");
		responseStructure.setData(exception.getMessage());
		
		return new ResponseEntity<>(responseStructure, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PhoneNumberNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handlePhoneNumberNotValid(PhoneNumberNotFoundException exception){
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage("PHONE NUMBER NOT FOUND");
		responseStructure.setData(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidCredentialException.class)
	public ResponseEntity<ResponseStructure<String>> handleInvalidCredential(InvalidCredentialException exception){
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		responseStructure.setStatus(HttpStatus.UNAUTHORIZED.value());
		responseStructure.setMessage("Invalid Credential");
		responseStructure.setData(exception.getMessage());
		
		return new ResponseEntity<>(responseStructure, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(DateNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleDateNotFound(DateNotFoundException exception){
		ResponseStructure<String> responseStructure=new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage("DATE NOT FOUND");
		responseStructure.setData(exception.getMessage());
		
		return new ResponseEntity<>(responseStructure,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DressNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleDressNotFound(DressNotFoundException exception){
		ResponseStructure<String> responseStructure=new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage("DRESS NOT FOUND");
		responseStructure.setData(exception.getMessage());
		
		return new ResponseEntity<>(responseStructure,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleDtaNotFound(DataNotFoundException exception){
		ResponseStructure<String> responseStructure=new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage("DATA NOT PRESENT");
		responseStructure.setData(exception.getMessage());
		
		return new ResponseEntity<>(responseStructure,HttpStatus.NOT_FOUND);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		HashMap<String, String> info=new LinkedHashMap<String, String>();
		List<FieldError> errors=ex.getFieldErrors();
		for(FieldError error:errors) {
			String fieldName=error.getField();
			String fieldMessage=error.getDefaultMessage();
			info.put(fieldName, fieldMessage);
		}
		
		ResponseStructure<Map<String, String>> responseStructure = new ResponseStructure<Map<String, String>>();
		responseStructure.setStatus(HttpStatus.BAD_REQUEST.value());
		responseStructure.setMessage("BAD DATA");
		responseStructure.setData(info);

		return new ResponseEntity<Object>(responseStructure, HttpStatus.BAD_REQUEST);
	}
}
