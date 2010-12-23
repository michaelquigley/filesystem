package com.quigley.filesystem;

import org.exolab.castor.mapping.GeneralizedFieldHandler;


public class FilesystemPathHandler extends GeneralizedFieldHandler {
	@Override
	public Object convertUponGet(Object value) {
		if(value == null) return null;
		FilesystemPath path = (FilesystemPath) value;
		return path.asString();
	}

	@Override
	public Object convertUponSet(Object value) {
		String pathString = (String) value;
		return new FilesystemPath(pathString);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class getFieldType() {
		return FilesystemPath.class;
	}
}