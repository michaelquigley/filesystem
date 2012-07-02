package com.quigley.filesystem;

public class FilesystemModificationVisitor implements FilesystemVisitor {
	public void visit(FilesystemPath path) {
		long fileModified = path.asFile().lastModified();
		if(lastModified < fileModified) {
			lastModified = fileModified;
		}
	}
	
	public long getLastModified() {
		return lastModified;
	}

	private long lastModified;
}