package com.quigley.filesystem;

import java.beans.PropertyEditorSupport;

public class FilesystemPathEditor extends PropertyEditorSupport {
	public void setAsText(String text) {
		FilesystemPath path = new FilesystemPath(text);
		setValue(path);
	}
}
