package com.bksmart.smarttalk.db;

public class OutOfStorageSpaceException extends StorageUnavailableException {
	public OutOfStorageSpaceException(String description) {
		super(description, "");
	}
}
