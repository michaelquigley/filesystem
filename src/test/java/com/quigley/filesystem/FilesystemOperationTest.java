package com.quigley.filesystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quigley.filesystem.visitor.FilesystemInventoryVisitor;

public class FilesystemOperationTest {
	@Test
	public void testCopyFile() throws IOException {
		FilesystemPath testRoot = createTempFolder();
		log.info("testRoot = " + testRoot);
		
		FilesystemPath src = new FilesystemPath("pom.xml");
		FilesystemPath dest = testRoot.add(new FilesystemPath("pom.xml"));
		FilesystemOperation.copy(src, dest);
		
		assertTrue(dest.asFile().exists() && dest.asFile().isFile());
		
		assertTrue(FilesystemOperation.recursiveDelete(testRoot));
		assertFalse(testRoot.asFile().exists());
	}
	
	@Test
	public void testCopyAndDeleteTree() throws IOException {
		FilesystemPath dest = createTempFolder();
		log.info("dest = " + dest);

		FilesystemPath src = new FilesystemPath("src/test/java");
		List<FilesystemPath> copies = FilesystemOperation.copyTree(src, dest);
		
		for(FilesystemPath f : copies) {
			log.info("copied: " + f);
		}
		
		FilesystemInventoryVisitor v1 = new FilesystemInventoryVisitor();
		v1.setRootTrim(src);
		FilesystemIterator i1 = new FilesystemIterator();
		i1.addVisitor(v1);
		i1.iterate(src);
		List<String> s1 = new ArrayList<String>();
		for(FilesystemPath path : v1.getPaths()) {
			s1.add(path.toString());
		}
		Collections.sort(s1);
		
		for(String s : s1) {
			log.info("s1 = " + s);
		}
		
		FilesystemInventoryVisitor v2 = new FilesystemInventoryVisitor();
		v2.setRootTrim(dest);
		FilesystemIterator i2 = new FilesystemIterator();
		i2.addVisitor(v2);
		i2.iterate(dest);
		List<String> s2 = new ArrayList<String>();
		for(FilesystemPath path : v2.getPaths()) {
			s2.add(path.toString());
		}
		Collections.sort(s2);

		for(String s : s2) {
			log.info("s2 = " + s);
		}
		
		assertTrue(s1.equals(s2));
		
		assertTrue(FilesystemOperation.recursiveDelete(dest));
		assertFalse(dest.asFile().exists());		
	}
	
	private FilesystemPath createTempFolder() throws IOException {
		File tempF = File.createTempFile("temp", ".dir");
		tempF.delete();
		tempF.mkdir();
		return new FilesystemPath(tempF.getAbsolutePath());
	}
	
	private static Logger log = LoggerFactory.getLogger(FilesystemOperationTest.class);
}