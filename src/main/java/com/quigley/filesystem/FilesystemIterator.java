package com.quigley.filesystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesystemIterator {
	public FilesystemIterator() {
		visitors = new ArrayList<FilesystemVisitor>();
	}
	
    public FilesystemIterator(FilesystemVisitor visitor) {
    	visitors = new ArrayList<FilesystemVisitor>();
        visitors.add(visitor);
    }

    public void iterate(FilesystemPath path) throws FilesystemException {
    	File pathF = path.asFile();
    	if(pathF.exists() && pathF.canRead()) {
    		visit(path);
    		if(pathF.isDirectory()) {
    			File[] contents = pathF.listFiles();
    			for(File f : contents) {
    				FilesystemPath fPath = new FilesystemPath(path).add(f.getName());
    				iterate(fPath);
    			}
    		}
    		
    	} else {
    		throw new FilesystemException("Unable to read '" + path.asString() + "'!");
    	}
    }

	private void visit(FilesystemPath path) {
    	for(FilesystemVisitor visitor : visitors) {
    		visitor.visit(path);
    	}
    }

    public List<FilesystemVisitor> getVisitors() {
		return visitors;
	}
	public void setVisitors(List<FilesystemVisitor> visitors) {
		this.visitors = visitors;
	}
	public void addVisitor(FilesystemVisitor visitor) {
		visitors.add(visitor);
	}
	
    private List<FilesystemVisitor> visitors;
}
