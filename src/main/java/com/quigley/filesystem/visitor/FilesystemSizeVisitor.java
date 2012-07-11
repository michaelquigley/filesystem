package com.quigley.filesystem.visitor;

import com.quigley.filesystem.FilesystemPath;

public class FilesystemSizeVisitor implements FilesystemVisitor {
	public void visit(FilesystemPath path) {
		System.out.println("visiting: " + path);
		size += path.asFile().length();
	}
	
	public long getSize() {
		return size;
	}

	private long size;
}