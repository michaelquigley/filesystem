package com.quigley.filesystem;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesystemIteratorTest {
    @Test
    public void testBasicIteration() throws Exception {
        FilesystemPath root = new FilesystemPath("src");
        TestVisitor visitor = new TestVisitor();
        FilesystemIterator iter = new FilesystemIterator(visitor);
        iter.iterate(root);

        List<FilesystemPath> pathList = visitor.getPathList();
        assertTrue(pathList.get(1).asString().equals("src/main"));
    }

    @Test
    public void testIterationStartingWithFile() throws Exception {
    	FilesystemPath root = new FilesystemPath("pom.xml");
    	TestVisitor visitor = new TestVisitor();
    	FilesystemIterator iter = new FilesystemIterator(visitor);
    	iter.iterate(root);
    	
    	List<FilesystemPath> pathList = visitor.getPathList();
    	assertTrue(pathList.size() == 1);
    	assertTrue(pathList.get(0).asString().equals("pom.xml"));
    }
    
    private class TestVisitor implements FilesystemVisitor {
        private List<FilesystemPath> pathList;

        public TestVisitor() {
            pathList = new LinkedList<FilesystemPath>();
        }

        public List<FilesystemPath> getPathList() {
            return pathList;
        }

        public void visit(FilesystemPath path) {
            log.info("Visited: " + path.asString());
            pathList.add(path);
        }
    }
    
    private static Logger log = LoggerFactory.getLogger(FilesystemIteratorTest.class);
}