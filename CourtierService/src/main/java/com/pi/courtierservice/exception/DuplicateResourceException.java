package com.pi.courtierservice.exception;

public class DuplicateResourceException extends RuntimeException {

	public DuplicateResourceException(String message) {
		super(message);
	}
}
