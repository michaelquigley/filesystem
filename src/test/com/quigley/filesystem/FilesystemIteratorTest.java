package com.quigley.filesystem;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class FilesystemIteratorTest {
    private static Log log = LogFactory.getLog(FilesystemIteratorTest.class);

    @Test
    public void testBasicIteration() throws Exception {
        FilesystemPath root = new FilesystemPath("bin/");
        TestVisitor visitor = new TestVisitor();
        FilesystemIterator iter = new FilesystemIterator(root, visitor);
        iter.iterate();

        List<FilesystemPath> pathList = visitor.getPathList();
        assertTrue(pathList.get(1).asString().equals("bin/webc"));
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
}
