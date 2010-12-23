package com.quigley.filesystem;

import static org.junit.Assert.*;

import org.junit.Test;

import com.quigley.testing.TestFailureException;

public class FilesystemPathTest {
	@Test
	public void testRoot() throws Exception {
		FilesystemPath path = new FilesystemPath("/");
		assertTrue(path.asString().equals("/"));
		assertTrue(path.size() == 0);
		assertTrue(path.isAbsolute());
	}
	
    @Test
    public void testZero() throws Exception {
        String pathString = "the/quick/brown/fox";
        FilesystemPath fsp = new FilesystemPath(pathString);
        assertTrue(fsp.asString().equals(pathString));
        assertTrue(fsp.size() == 4);
        assertTrue(!fsp.isAbsolute());
    }

    @Test
    public void testOne() throws Exception {
        String pathString = "\\the/quick\\brown/////fox\\";
        FilesystemPath fsp = new FilesystemPath(pathString);
        assertTrue(fsp.asString().equals("/the/quick/brown/fox"));
        assertTrue(fsp.size() == 4);
        assertTrue(fsp.isAbsolute());
    }

    @Test
    public void testRemoveExtension() throws Exception {
        String pathString = "/the/quick/brown/fox.txt";
        assertTrue(FilesystemPath.removeExtension(pathString).equals("/the/quick/brown/fox"));

        pathString = "/the/quick/brown/fox.txt.txt";
        assertTrue(FilesystemPath.removeExtension(pathString).equals("/the/quick/brown/fox.txt"));

        pathString = "fox";
        assertTrue(FilesystemPath.removeExtension(pathString).equals(pathString));
    }

    @Test
    public void testAddElement() throws Exception {
        FilesystemPath p = new FilesystemPath("a/b");
        FilesystemPath p1 = p.add("c");
        assertTrue(p.asString().equals("a/b"));
        assertTrue(p1.asString().equals("a/b/c"));
    }
    
    @Test
    public void testAddElementAbsolute() throws Exception {
    	FilesystemPath p = new FilesystemPath("/a/b");
    	FilesystemPath p1 = p.add("c");
    	assertTrue(p.asString().equals("/a/b"));
    	assertTrue(p1.asString().equals("/a/b/c"));
    	assertTrue(p1.isAbsolute());
    }

    @Test
    public void testAddFilesystemPath() throws Exception {
        FilesystemPath p = new FilesystemPath("a/b");
        FilesystemPath q = new FilesystemPath("c/d");
        FilesystemPath p1 = p.add(q);
        assertTrue(p1.asString().equals("a/b/c/d"));
    }
    
    @Test
    public void testAddFilesystemPathAbsolute() throws Exception {
        FilesystemPath p = new FilesystemPath("/a/b");
        FilesystemPath q = new FilesystemPath("c/d");
        FilesystemPath p1 = p.add(q);
        assertTrue(p.asString().equals("/a/b"));
        assertTrue(p1.asString().equals("/a/b/c/d"));
        assertTrue(p1.isAbsolute());
    }

    @Test
    public void testGetOutOfBounds() throws Exception {
        FilesystemPath p = new FilesystemPath("a/b/c");
        assertTrue(p.get(0).equals("a"));
        assertTrue(p.get(1).equals("b"));
        assertTrue(p.get(2).equals("c"));

        try {
            p.get(3);
            throw new TestFailureException("Should have thrown IndexOutOfBoundsException!");
        } catch(IndexOutOfBoundsException iobe) {
            //
        }
    }

    @Test
    public void testGetExtension() throws Exception {
        FilesystemPath p0 = new FilesystemPath("a.xml");
        String p0Extension = p0.getExtension();
        assertTrue(p0Extension != null);
        assertTrue(p0Extension.equals("xml"));

        FilesystemPath p1 = new FilesystemPath("a/b/c.xml");
        String p1Extension = p1.getExtension();
        assertTrue(p1Extension != null);
        assertTrue(p1Extension.equals("xml"));

        FilesystemPath p2 = new FilesystemPath("a/b/c");
        assertTrue(p2.getExtension() == null);

        FilesystemPath p3 = new FilesystemPath("a.");
        assertTrue(p3.getExtension().equals(""));
    }

    @Test
    public void testSet() throws Exception {
        FilesystemPath p = new FilesystemPath("a/b/c");
        FilesystemPath p1 = p.set(1, "bee");
        assertTrue(p1.asString().equals("a/bee/c"));

        try {
            p1.set(5, "eff");
            throw new TestFailureException("Should have thrown IndexOutOfBoundsException!");
        } catch(IndexOutOfBoundsException iobe) {
            //
        }
    }

    @Test
    public void testAdd() throws Exception {
        FilesystemPath p = new FilesystemPath("a/b/c").add("d");
        assertTrue(p.asString().equals("a/b/c/d"));
    }
    
    @Test
    public void testAddAbsolute() throws Exception {
    	FilesystemPath p = new FilesystemPath("/a/b/c").add("d");
    	assertTrue(p.asString().equals("/a/b/c/d"));
    }

    @Test
    public void testCopyConstructor() {
        FilesystemPath source = new FilesystemPath("a/b/c");
        FilesystemPath dest = new FilesystemPath(source);
        assertTrue(dest.asString().equals("a/b/c"));
    }
    
    @Test
    public void testCopyConstructorAbsolute() {
    	FilesystemPath source = new FilesystemPath("/a/b/c");
    	FilesystemPath dest = new FilesystemPath(source);
    	assertTrue(dest.asString().equals("/a/b/c"));
    	assertTrue(dest.isAbsolute());
    }
    
    @Test
    public void testRemove() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.remove(1).asString().equals("a/c"));
    	assertTrue(source.asString().equals("a/b/c"));
    }
    
    @Test
    public void testRemoveFirst() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.removeFirst().asString().equals("b/c"));
    	assertTrue(source.asString().equals("a/b/c"));
    }
    
    @Test
    public void testRemoveFirst1() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.removeFirst().asString().equals(source.removeFirst(1).asString()));
    	assertTrue(source.asString().equals("a/b/c"));
    }
    
    @Test
    public void testRemoveFirst2() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.removeFirst(2).asString().equals("c"));
    	assertTrue(source.asString().equals("a/b/c"));
    }    
    
    @Test
    public void testRemoveFirst3() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.removeFirst(3).asString().equals(""));
    	
    	source = new FilesystemPath("/a/b/c");
    	assertTrue(source.removeFirst(3).asString().equals(""));
    }
    
    @Test
    public void testRemoveLast() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.removeLast().asString().equals("a/b"));
    	assertTrue(source.asString().equals("a/b/c"));
    }
    
    @Test
    public void testParent() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.parent().asString().equals("a/b"));
    	assertTrue(source.asString().equals("a/b/c"));
    }
    
    @Test
    public void testRemoveCommonParent() {
    	FilesystemPath path = new FilesystemPath("a/b/c/e1");
    	FilesystemPath anotherPath = new FilesystemPath("a/b/c/f");
    	
    	FilesystemPath relativePath = FilesystemPath.removeCommonParent(path, anotherPath);
    	assertTrue(relativePath.asString().equals("e1"));
    	assertTrue(path.asString().equals("a/b/c/e1"));
    	assertTrue(anotherPath.asString().equals("a/b/c/f"));
    }
    
    @Test
    public void testRemoveCommonParentWithNoCommonality() {
    	FilesystemPath path = new FilesystemPath("a/b/c/d");
    	FilesystemPath anotherPath = new FilesystemPath("b/c/d/e");
    	
    	FilesystemPath relativePath = FilesystemPath.removeCommonParent(path, anotherPath);
    	assertTrue(relativePath.asString().equals("a/b/c/d"));
    }
    
    @Test
    public void testRemoveCommonParentWithOnlyCommonality() {
    	FilesystemPath path = new FilesystemPath("a/b/c/d");
    	FilesystemPath relativePath = FilesystemPath.removeCommonParent(path, path);
    	assertTrue(relativePath.size() == 0);
    }
    
    @Test
    public void testRemoveCommonParentWithShorterTarget() {
    	FilesystemPath path = new FilesystemPath("a/b");
    	FilesystemPath anotherPath = new FilesystemPath("a/b/c/d");
    	FilesystemPath relativePath = FilesystemPath.removeCommonParent(path, anotherPath);
    	assertTrue(relativePath.size() == 0);
    }
    
    @Test
    public void testRemoveCommonParentWithShorterComparison() {
    	FilesystemPath path = new FilesystemPath("a/b/c/d");
    	FilesystemPath anotherPath = new FilesystemPath("a/b");
    	FilesystemPath relativePath = FilesystemPath.removeCommonParent(path, anotherPath);
    	assertTrue(relativePath.asString().equals("c/d"));
    }
    
    @Test
    public void testNavigate0() {
    	FilesystemPath path = new FilesystemPath("a");
    	String linkPath = path.navigate(new FilesystemPath("a"));
    	assertEquals("a", linkPath);
    }
    
    @Test
    public void testNavigate1() {
    	FilesystemPath path = new FilesystemPath("a");
    	String linkPath = path.navigate(new FilesystemPath("b"));
    	assertEquals("b", linkPath);
    }
    
    @Test
    public void testNavigate2() {
    	FilesystemPath path = new FilesystemPath("a/b");
    	String linkPath = path.navigate(new FilesystemPath("a/c"));
    	assertEquals("c", linkPath);
    }
    
    @Test
    public void testNavigate3() {
    	FilesystemPath path = new FilesystemPath("a");
    	String linkPath = path.navigate(new FilesystemPath("b/c"));
    	assertEquals("b/c", linkPath);
    }
    
    @Test
    public void testNavigate4() {
    	FilesystemPath path = new FilesystemPath("b/c");
    	String linkPath = path.navigate(new FilesystemPath("a"));
    	assertEquals("../a", linkPath);
    }
    
    @Test
    public void testNavigate5() {
    	FilesystemPath path = new FilesystemPath("a/b/c");
    	String linkPath = path.navigate(new FilesystemPath("a/d/e"));
    	assertEquals("../d/e", linkPath);
    }
}
