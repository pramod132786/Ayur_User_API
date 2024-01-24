package com.anarghya.aayurvedaappuser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
@RestControllerAdvice
public class AccountLockedException   extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public AccountLockedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountLockedException(String message) {
        super(message);
    }
	
	@ExceptionHandler(AccountLockedException.class)
	public ResponseEntity<String> handleResourceNotFoundException(AccountLockedException ex) {
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
	}
}
