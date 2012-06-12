package com.quigley.filesystem;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FilesystemPath {
    private boolean isAbsolute;
    private List<String> elements;

    public FilesystemPath(String pathString) {
        pathString = FilesystemPath.normalize(pathString);
        if(pathString.length() == 0) {
        	isAbsolute = true;
        	elements = new ArrayList<String>();
        } else
        if(pathString.substring(0, 1).equals("/")) {
            isAbsolute = true;
            pathString = pathString.substring(1, pathString.length());
        }
        if(pathString.length() > 0) {
	        String[] componentArray = pathString.split("/");
	        elements = new ArrayList<String>(componentArray.length);
	        for(String component : componentArray) {
	            elements.add(component);
	        }
        }
    }

    public FilesystemPath(FilesystemPath source) {
        elements = new LinkedList<String>();
        for(int i = 0; i < source.size(); i++) {
            elements.add(source.get(i));
        }
        isAbsolute = source.isAbsolute();
    }
    
    public FilesystemPath(List<String> elements) {
    	this.elements = elements;
    }

    public FilesystemPath toAbsolute() {
    	FilesystemPath absolutePath = new FilesystemPath(this.asFile().getAbsolutePath());
    	if(absolutePath.getLast().equals(".")) {
    		absolutePath = absolutePath.removeLast();
    	}
    	return absolutePath;
    }
    
    public boolean isAbsolute() {
        return isAbsolute;
    }
    public void setAbsolute(boolean absolute) {
        isAbsolute = absolute;
    }

    public FilesystemPath add(String element) {
    	List<String> elementsCopy = new ArrayList<String>(elements);
    	
        elementsCopy.add(element);
        
        FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
        pathCopy.setAbsolute(isAbsolute);
        
        return pathCopy;
    }
    
    public FilesystemPath add(int element) {
    	return add("" + element);
    }
    
    public FilesystemPath add(long element) {
    	return add("" + element);
    }

    public FilesystemPath add(FilesystemPath p) {
    	List<String> elementsCopy = new ArrayList<String>(elements);
    	
        for(int i = 0; i < p.size(); i++) {
            elementsCopy.add(p.get(i));
        }

        FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
        pathCopy.setAbsolute(isAbsolute);
        
        return pathCopy;
    }

    public FilesystemPath set(int idx, String component) {
    	List<String> elementsCopy = new ArrayList<String>(elements);
    	
        elementsCopy.set(idx, component);

        FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
        pathCopy.setAbsolute(isAbsolute);
        
        return pathCopy;
    }    
    
    public FilesystemPath remove(int idx) {
    	List<String> elementsCopy = new ArrayList<String>(elements);
    	
        elementsCopy.remove(idx);
        
        FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
        pathCopy.setAbsolute(isAbsolute);
        
        return pathCopy;
    }
    
    public FilesystemPath removeFirst() {
    	List<String> elementsCopy = new ArrayList<String>(elements);
    	
    	elementsCopy.remove(0);
    	
    	FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
    	
    	return pathCopy;
    }
    
    public FilesystemPath removeFirst(int count) {
    	List<String> elementsCopy = new ArrayList<String>(elements);
    	
    	elementsCopy = elementsCopy.subList(count, elementsCopy.size());
    	
    	FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
    	
    	return pathCopy;
    }
    
    public FilesystemPath removeLast() {
    	List<String> elementsCopy = new ArrayList<String>(elements);
    	
    	elementsCopy.remove(elements.size() - 1);
    	
    	FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
    	pathCopy.setAbsolute(isAbsolute);
    	
    	return pathCopy;
    }
    
    public FilesystemPath removeExtension() {
    	return new FilesystemPath(removeExtension(this.asString()));
    }
    
    public FilesystemPath parent() {
    	return removeLast();
    }

    public String get(int index) {
        return elements.get(index);
    }
    
    public String getLast() {
    	if(size() > 0) {
    		return elements.get(elements.size() - 1);
    		
    	} else {
    		return null;
    	}
    }

    public String getExtension() {
        if(elements.size() < 1) {
            return null;
        }
        String element = elements.get(elements.size() - 1);
        int extStart = element.lastIndexOf(".");
        if(extStart != -1) {
            return element.substring(extStart + 1);
        } else {
            return null;
        }
    }    
    
    public boolean contains(String match) {
    	for(String element : elements) {
    		if(element.equals(match)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public String navigate(FilesystemPath toPath) {
    	if(toPath.asString().equals(this.asString())) {
    		return this.getLast();
    	}
    	
		StringBuilder p = new StringBuilder();

		FilesystemPath shortenedCurrentPath = FilesystemPath.removeCommonParent(this, toPath);
		for(int i = 0; i < shortenedCurrentPath.size() - 1; i++) {
			p.append("../");
		}
		
		FilesystemPath shortenedTargetPath = FilesystemPath.removeCommonParent(toPath, this);
		p.append(shortenedTargetPath.asString());
		
		return p.toString();    	
    }
    
    public String asString() {
    	return toString();
    }

    public int size() {
        return elements.size();
    }    
    
    public File asFile() {
        return new File(this.asString());
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((elements == null) ? 0 : elements.hashCode());
		result = prime * result + (isAbsolute ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FilesystemPath other = (FilesystemPath) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		if (isAbsolute != other.isAbsolute)
			return false;
		return true;
	}

	public String toString() {
        StringBuilder sb = new StringBuilder();
        if(isAbsolute && elements.size() == 0) {
        	sb.append("/");
        } else {
	        for(int i = 0; i < elements.size(); i++) {
	            if(isAbsolute || i > 0) {
	                sb.append("/");
	            }
	            sb.append(elements.get(i));
	        }
        }
        return sb.toString();
    }

    /*
     * Static filesystem path operations.
     */
    
    public static String normalize(String pathString) {
        while(pathString.indexOf("\\") != -1) {
            pathString = pathString.replace('\\', '/');
        }
        while(pathString.indexOf("//") != -1) {
            pathString = pathString.replace("//", "/");
        }
        if(pathString.lastIndexOf("/") == (pathString.length() - 1)) {
            pathString = pathString.substring(0, pathString.length() - 1);
        }
        return pathString;
    }

    public static String removeExtension(String pathString) {
        int extensionStartIndex = pathString.lastIndexOf(".");
        if(extensionStartIndex != -1) {
            return pathString.substring(0, extensionStartIndex);
        } else {
            return pathString;
        }
    }
    
    public static FilesystemPath removeCommonParent(FilesystemPath path, FilesystemPath pathComparedTo) {
    	FilesystemPath outputPath = path;
    	FilesystemPath parallelPath = pathComparedTo;
    	
    	while(outputPath.size() > 0 && parallelPath.size() > 0 && outputPath.get(0).equals(parallelPath.get(0))) {
    		outputPath = outputPath.removeFirst();
    		parallelPath = parallelPath.removeFirst();
    	}
    	
    	return outputPath;
    }
}