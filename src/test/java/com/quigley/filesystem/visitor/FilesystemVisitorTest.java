package com.quigley.filesystem.visitor;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quigley.filesystem.FilesystemIterator;
import com.quigley.filesystem.FilesystemPath;
import com.quigley.filesystem.utils.FilesystemSizeFormatter;

public class FilesystemVisitorTest {
	@Test
	public void testFilesystemSizeVisitorOnTree() {
		FilesystemSizeVisitor visitor = new FilesystemSizeVisitor();
		FilesystemIterator iterator = new FilesystemIterator(visitor);
		iterator.iterate(new FilesystemPath("src/main"));
		log.info("src/main = " + FilesystemSizeFormatter.format(visitor.getSize()));
	}
	
	private static Logger log = LoggerFactory.getLogger(FilesystemVisitorTest.class);
}