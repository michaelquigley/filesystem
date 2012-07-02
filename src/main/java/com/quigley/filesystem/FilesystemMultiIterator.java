package com.quigley.filesystem;

import java.util.ArrayList;
import java.util.List;

public class FilesystemMultiIterator {
	public FilesystemMultiIterator(FilesystemPath root) {
		this.root = root;
		visitors = new ArrayList<FilesystemVisitor>();
	}

	private FilesystemPath root;
	private List<FilesystemVisitor> visitors;
}