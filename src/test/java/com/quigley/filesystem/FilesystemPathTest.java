package com.quigley.filesystem;

import static org.junit.Assert.*;

import org.junit.Test;

public class FilesystemPathTest {
	@Test
	public void testRoot() throws Exception {
		FilesystemPath path = new FilesystemPath("/");
		assertTrue(path.toString().equals("/"));
		assertTrue(path.size() == 0);
		assertTrue(path.isAbsolute());
	}
	
    @Test
    public void testZero() throws Exception {
        String pathString = "the/quick/brown/fox";
        FilesystemPath fsp = new FilesystemPath(pathString);
        assertTrue(fsp.toString().equals(pathString));
        assertTrue(fsp.size() == 4);
        assertTrue(!fsp.isAbsolute());
    }

    @Test
    public void testOne() throws Exception {
        String pathString = "\\the/quick\\brown/////fox\\";
        FilesystemPath fsp = new FilesystemPath(pathString);
        assertTrue(fsp.toString().equals("/the/quick/brown/fox"));
        assertTrue(fsp.size() == 4);
        assertTrue(fsp.isAbsolute());
    }

    @Test
    public void testSetExtension() throws Exception {
    	FilesystemPath path = new FilesystemPath("a/b/c.txt");
    	path = path.setExtension("html");
    	assertEquals("html", path.getExtension());
    	assertEquals("a/b/c.html", path.toString());
    }

    @Test
    public void testAddExtension() throws Exception {
    	FilesystemPath path = new FilesystemPath("a/b/c.txt");
    	path = path.addExtension("xml");
    	assertEquals("a/b/c.txt.xml", path.toString());
    }
    
    @Test
    public void testAddElement() throws Exception {
        FilesystemPath p = new FilesystemPath("a/b");
        FilesystemPath p1 = p.add("c");
        assertTrue(p.toString().equals("a/b"));
        assertTrue(p1.toString().equals("a/b/c"));
    }
    
    @Test
    public void testAddElementAbsolute() throws Exception {
    	FilesystemPath p = new FilesystemPath("/a/b");
    	FilesystemPath p1 = p.add("c");
    	assertTrue(p.toString().equals("/a/b"));
    	assertTrue(p1.toString().equals("/a/b/c"));
    	assertTrue(p1.isAbsolute());
    }

    @Test
    public void testAddFilesystemPath() throws Exception {
        FilesystemPath p = new FilesystemPath("a/b");
        FilesystemPath q = new FilesystemPath("c/d");
        FilesystemPath p1 = p.add(q);
        assertTrue(p1.toString().equals("a/b/c/d"));
    }
    
    @Test
    public void testAddFilesystemPathAbsolute() throws Exception {
        FilesystemPath p = new FilesystemPath("/a/b");
        FilesystemPath q = new FilesystemPath("c/d");
        FilesystemPath p1 = p.add(q);
        assertTrue(p.toString().equals("/a/b"));
        assertTrue(p1.toString().equals("/a/b/c/d"));
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
            throw new IllegalArgumentException("Should have thrown IndexOutOfBoundsException!");
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
        assertTrue(p1.toString().equals("a/bee/c"));

        try {
            p1.set(5, "eff");
            throw new IllegalArgumentException("Should have thrown IndexOutOfBoundsException!");
        } catch(IndexOutOfBoundsException iobe) {
            //
        }
    }

    @Test
    public void testAdd() throws Exception {
        FilesystemPath p = new FilesystemPath("a/b/c").add("d");
        assertTrue(p.toString().equals("a/b/c/d"));
    }
    
    @Test
    public void testAddAbsolute() throws Exception {
    	FilesystemPath p = new FilesystemPath("/a/b/c").add("d");
    	assertTrue(p.toString().equals("/a/b/c/d"));
    }

    @Test
    public void testCopyConstructor() {
        FilesystemPath source = new FilesystemPath("a/b/c");
        FilesystemPath dest = new FilesystemPath(source);
        assertTrue(dest.toString().equals("a/b/c"));
    }
    
    @Test
    public void testCopyConstructorAbsolute() {
    	FilesystemPath source = new FilesystemPath("/a/b/c");
    	FilesystemPath dest = new FilesystemPath(source);
    	assertTrue(dest.toString().equals("/a/b/c"));
    	assertTrue(dest.isAbsolute());
    }
    
    @Test
    public void testRemove() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.remove(1).toString().equals("a/c"));
    	assertTrue(source.toString().equals("a/b/c"));
    }
    
    @Test
    public void testRemoveFirst() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.removeFirst().toString().equals("b/c"));
    	assertTrue(source.toString().equals("a/b/c"));
    }
    
    @Test
    public void testRemoveFirst1() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.removeFirst().toString().equals(source.removeFirst(1).toString()));
    	assertTrue(source.toString().equals("a/b/c"));
    }
    
    @Test
    public void testRemoveFirst2() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.removeFirst(2).toString().equals("c"));
    	assertTrue(source.toString().equals("a/b/c"));
    }    
    
    @Test
    public void testRemoveFirst3() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.removeFirst(3).toString().equals(""));
    	
    	source = new FilesystemPath("/a/b/c");
    	assertTrue(source.removeFirst(3).toString().equals(""));
    }
    
    @Test
    public void testRemoveLast() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.removeLast().toString().equals("a/b"));
    	assertTrue(source.toString().equals("a/b/c"));
    }
    
    @Test
    public void testParent() {
    	FilesystemPath source = new FilesystemPath("a/b/c");
    	assertTrue(source.parent().toString().equals("a/b"));
    	assertTrue(source.toString().equals("a/b/c"));
    }
    
    @Test
    public void testRemoveCommonParent() {
    	FilesystemPath path = new FilesystemPath("a/b/c/e1");
    	FilesystemPath anotherPath = new FilesystemPath("a/b/c/f");
    	
    	FilesystemPath relativePath = path.removeCommonParent(anotherPath);
    	assertTrue(relativePath.toString().equals("e1"));
    	assertTrue(path.toString().equals("a/b/c/e1"));
    	assertTrue(anotherPath.toString().equals("a/b/c/f"));
    }
    
    @Test
    public void testRemoveCommonParentWithNoCommonality() {
    	FilesystemPath path = new FilesystemPath("a/b/c/d");
    	FilesystemPath anotherPath = new FilesystemPath("b/c/d/e");
    	
    	FilesystemPath relativePath = path.removeCommonParent(anotherPath);
    	assertTrue(relativePath.toString().equals("a/b/c/d"));
    }
    
    @Test
    public void testRemoveCommonParentWithOnlyCommonality() {
    	FilesystemPath path = new FilesystemPath("a/b/c/d");
    	FilesystemPath relativePath = path.removeCommonParent(path);
    	assertTrue(relativePath.size() == 0);
    }
    
    @Test
    public void testRemoveCommonParentWithShorterTarget() {
    	FilesystemPath path = new FilesystemPath("a/b");
    	FilesystemPath anotherPath = new FilesystemPath("a/b/c/d");
    	FilesystemPath relativePath = path.removeCommonParent(anotherPath);
    	assertTrue(relativePath.size() == 0);
    }
    
    @Test
    public void testRemoveCommonParentWithShorterComparison() {
    	FilesystemPath path = new FilesystemPath("a/b/c/d");
    	FilesystemPath anotherPath = new FilesystemPath("a/b");
    	FilesystemPath relativePath = path.removeCommonParent(anotherPath);
    	assertTrue(relativePath.toString().equals("c/d"));
    }
    
    @Test
    public void testToAbsolute1() {
    	FilesystemPath path = new FilesystemPath(".").toAbsolute();
    	System.out.println("Absolute: " + path);
    	path = new FilesystemPath("src/java").toAbsolute();
    	System.out.println("Absolute: " + path);
    }
    
    @Test
    public void testGetLast() {
    	FilesystemPath path = new FilesystemPath("a/b/c");
    	assertEquals("c", path.getLast());
    	path = new FilesystemPath("a");
    	assertEquals("a", path.getLast());
    	path = new FilesystemPath("/");
    	assertEquals(null, path.getLast());
    }
    
    @Test
    public void testSetLast() {
    	FilesystemPath path = new FilesystemPath("a/b/c");
    	path = path.setLast("e");
    	assertEquals(3, path.size());
    	assertEquals("e", path.getLast());
    	
    	path = new FilesystemPath("a");
    	path = path.setLast("b");
    	assertEquals(1, path.size());
    	assertEquals("b", path.getLast());
    	
    	path = new FilesystemPath("/");
    	path = path.setLast("a");
    	assertEquals(1, path.size());
    	assertEquals("a", path.getLast());
    	assertEquals("/a", path.toString());
    }
    
    @Test
    public void testSimplify() {
    	FilesystemPath path = new FilesystemPath("a/b/../c");
    	FilesystemPath simplified = path.simplify();
    	assertNotNull(simplified);
    	assertEquals("a/c", simplified.toString());
    	
    	path = new FilesystemPath("../../a");
    	simplified = path.simplify();
    	assertEquals(path.toString(), simplified.toString());
    	
    	path = new FilesystemPath("a/..");
    	simplified = path.simplify();
    	assertEquals("", simplified.toString());
    	
    	path = new FilesystemPath(".");
    	simplified = path.simplify();
    	assertEquals("", simplified.toString());
    	
    	path = new FilesystemPath("../a/b/../c/d/../e/f/../../g");
    	simplified = path.simplify();
    	assertEquals("../a/c/g", simplified.toString());
    }
}
