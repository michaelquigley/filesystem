package com.quigley.testing;

@SuppressWarnings("serial")
public class TestFailureException extends RuntimeException {
	public TestFailureException() {
		super();
	}
	public TestFailureException(String message, Throwable cause) {
		super(message, cause);
	}
	public TestFailureException(String message) {
		super(message);
	}
	public TestFailureException(Throwable cause) {
		super(cause);
	}
}