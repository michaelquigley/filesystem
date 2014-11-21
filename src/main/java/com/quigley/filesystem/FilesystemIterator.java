/*
    This file is part of Filesystem.

    Filesystem is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as 
    published by the Free Software Foundation, either version 3 of 
    the License, or (at your option) any later version.

    Filesystem is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public 
    License along with Moose.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.quigley.filesystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.quigley.filesystem.visitor.FilesystemVisitor;

public class FilesystemIterator {
	public FilesystemIterator() {
		visitors = new ArrayList<FilesystemVisitor>();
	}
	
    public FilesystemIterator(FilesystemVisitor visitor) {
    	visitors = new ArrayList<FilesystemVisitor>();
        visitors.add(visitor);
    }

    public void iterate(FilesystemPath path) throws FilesystemException {
    	iterate(path, 0, -1);
    }
    
    public void iterate(FilesystemPath path, int maxDepth) {
    	iterate(path, 0, maxDepth);
    }
    
    private void iterate(FilesystemPath path, int currentDepth, int maxDepth) throws FilesystemException {
    	File pathF = path.asFile();
    	if(pathF.exists() && pathF.canRead()) {
    		visit(path);
    		if(pathF.isDirectory()) {
    			File[] contents = pathF.listFiles();
    			for(File f : contents) {
    				FilesystemPath fPath = new FilesystemPath(path).add(f.getName());
    				if(currentDepth < maxDepth || maxDepth == -1) {
    					iterate(fPath, currentDepth + 1, maxDepth);
    				} else {
    					visit(fPath);
    				}
    			}
    		}
    		
    	} else {
    		throw new FilesystemException("Unable to read '" + path.toString() + "'!");
    	}
    }

	private void visit(FilesystemPath path) {
		if(visitors != null) {
	    	for(FilesystemVisitor visitor : visitors) {
	    		visitor.visit(path);
	    	}
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
