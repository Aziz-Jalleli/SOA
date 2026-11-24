package com.pi.restaurantservice.exception;

public class DuplicateResourceException extends RuntimeException {

	public DuplicateResourceException(String message) {
		super(message);
	}
}
