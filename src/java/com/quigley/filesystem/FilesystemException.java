package com.quigley.filesystem;

@SuppressWarnings("serial")
public class FilesystemException extends RuntimeException {
	public FilesystemException() {
		super();
	}
	public FilesystemException(String message, Throwable cause) {
		super(message, cause);
	}
	public FilesystemException(String message) {
		super(message);
	}
	public FilesystemException(Throwable cause) {
		super(cause);
	}
}