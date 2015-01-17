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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.quigley.filesystem.json.FilesystemPathDeserializer;
import com.quigley.filesystem.json.FilesystemPathSerializer;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@JsonSerialize(using = FilesystemPathSerializer.class)
@JsonDeserialize(using = FilesystemPathDeserializer.class)
public class FilesystemPath implements Comparable<FilesystemPath> {

	/*
	 * Constructors
	 */

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
        if(elements.size() > 0 && elements.get(0).matches("[a-zA-Z]\\:")) {
        	isAbsolute = true;
        }
    }

    public FilesystemPath(FilesystemPath source) {
        elements = new LinkedList<String>();
        for(int i = 0; i < source.size(); i++) {
            elements.add(source.get(i));
        }
        isAbsolute = source.isAbsolute;
    }

    public FilesystemPath(List<String> elements) {
    	this.elements = elements;
    }

    /*
     * Absolute
     */

    public FilesystemPath makeAbsolute() {
    	FilesystemPath absolutePath = new FilesystemPath(this.asFile().getAbsolutePath()).setAbsolute(true);
    	return absolutePath;
    }

    public boolean isAbsolute() {
        return isAbsolute;
    }
    public FilesystemPath setAbsolute(boolean absolute) {
        FilesystemPath absolutePath = new FilesystemPath(elements);
        absolutePath.isAbsolute = true;
        return absolutePath;
    }

    /*
     * Modifiers
     */

    public FilesystemPath add(String element) {
    	List<String> elementsCopy = new ArrayList<String>(elements);

        elementsCopy.add(element);

        FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
        pathCopy.isAbsolute = isAbsolute;

        return pathCopy;
    }

    public FilesystemPath add(FilesystemPath p) {
    	List<String> elementsCopy = new ArrayList<String>(elements);

        for(int i = 0; i < p.size(); i++) {
            elementsCopy.add(p.get(i));
        }

        FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
        pathCopy.isAbsolute = isAbsolute;

        return pathCopy;
    }

    public FilesystemPath set(int idx, String component) {
    	List<String> elementsCopy = new ArrayList<String>(elements);

        elementsCopy.set(idx, component);

        FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
        pathCopy.isAbsolute = isAbsolute;

        return pathCopy;
    }

    public FilesystemPath remove(int idx) {
    	List<String> elementsCopy = new ArrayList<String>(elements);

        elementsCopy.remove(idx);

        FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
        pathCopy.isAbsolute = isAbsolute;

        return pathCopy;
    }

    public FilesystemPath removeFirst() {
    	List<String> elementsCopy = new ArrayList<String>(elements);

    	elementsCopy.remove(0);

    	FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
    	pathCopy.isAbsolute = isAbsolute;

    	return pathCopy;
    }

    public FilesystemPath removeFirst(int count) {
    	List<String> elementsCopy = new ArrayList<String>(elements);

    	elementsCopy = elementsCopy.subList(count, elementsCopy.size());

    	FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
    	pathCopy.isAbsolute = isAbsolute;

    	return pathCopy;
    }

    public FilesystemPath removeLast() {
    	List<String> elementsCopy = new ArrayList<String>(elements);

    	elementsCopy.remove(elements.size() - 1);

    	FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
    	pathCopy.isAbsolute = isAbsolute;

    	return pathCopy;
    }

    public FilesystemPath setLast(String last) {
    	List<String> elementsCopy = new ArrayList<String>(elements);

    	if(elementsCopy.size() > 0) {
    		elementsCopy.set(elementsCopy.size() - 1, last);

    	} else {
    		elementsCopy.add(last);
    	}

    	FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
    	pathCopy.isAbsolute = isAbsolute;

    	return pathCopy;
    }

    public FilesystemPath removeCommonParent(FilesystemPath otherPath) {
    	FilesystemPath outputPath = this;
    	FilesystemPath parallelPath = otherPath;

    	while(outputPath.size() > 0 && parallelPath.size() > 0 && outputPath.get(0).equals(parallelPath.get(0))) {
    		outputPath = outputPath.removeFirst();
    		parallelPath = parallelPath.removeFirst();
    	}

    	if(!outputPath.equals(otherPath)) {
    		outputPath.isAbsolute = false;
    	}

    	return outputPath;
    }

    public FilesystemPath simplify() {
    	List<String> elementsCopy = new ArrayList<String>(elements);
    	ListIterator<String> i = elementsCopy.listIterator();
    	boolean saw = false;
    	while(i.hasNext()) {
    		String value = i.next();
    		if(value.equals(".")) {
    			i.remove();
    		} else
    		if(value.equals("..")) {
    			if(saw) {
    				if(i.hasPrevious()) {
    					i.remove();
    					i.previous();
    					i.remove();
    				}
    			}
    		} else {
    			saw = true;
    		}
    	}
    	FilesystemPath pathCopy = new FilesystemPath(elementsCopy);
    	pathCopy.isAbsolute = isAbsolute;

    	return pathCopy;
    }

    /*
     * Component Accessors
     */

    public FilesystemPath parent() {
    	return removeLast();
    }

    public int size() {
        return elements.size();
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

    /*
     * Extension
     */

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

    public FilesystemPath removeExtension() {
    	FilesystemPath pathCopy = new FilesystemPath(removeExtension(this.toString()));
    	pathCopy.isAbsolute = isAbsolute;
    	return pathCopy;
    }

    public FilesystemPath setExtension(String extension) {
    	FilesystemPath pathCopy = new FilesystemPath(removeExtension(this.toString()));
    	pathCopy = new FilesystemPath(pathCopy.toString() + "." + extension);
    	pathCopy.isAbsolute = isAbsolute;
    	return pathCopy;
    }

    public FilesystemPath addExtension(String extension) {
    	FilesystemPath pathCopy = new FilesystemPath(this.toString() + "." + extension);
    	pathCopy.isAbsolute = isAbsolute;
    	return pathCopy;
    }

    /*
     * Matching
     */

    public boolean contains(String match) {
    	for(String element : elements) {
    		if(element.equals(match)) {
    			return true;
    		}
    	}
    	return false;
    }

    public File asFile() {
        return new File(this.toString());
    }

    public Path asPath() {
        return asFile().toPath();
    }

    @Override
	public int compareTo(FilesystemPath arg0) {
		return toString().compareTo(arg0.toString());
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
        boolean leadingSlash = isAbsolute;
       	if(isAbsolute && elements.size() > 0 && elements.get(0).matches("[a-zA-Z]\\:")) {
       		leadingSlash = false;
        }

       	boolean trailingSlash = false;
       	if(elements.size() == 1 && elements.get(0).matches("[a-zA-Z]\\:")) {
       		trailingSlash = true;
       	}

        StringBuilder sb = new StringBuilder();
        if(leadingSlash) {
        	sb.append("/");
        }
        for(int i = 0; i < elements.size(); i++) {
        	if(i > 0) {
        		sb.append("/");
        	}
        	sb.append(elements.get(i));
        }
        if(trailingSlash) {
        	sb.append("/");
        }

        return sb.toString();
    }

    private boolean isAbsolute;
    private List<String> elements;

    /*
     * Static Operations
     */

    public static FilesystemPath currentWorkingDirectory() {
    	File cwd = new File(".");
    	return new FilesystemPath(cwd.getAbsolutePath()).setAbsolute(true).simplify();
    }

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

    private static String removeExtension(String pathString) {
        int extensionStartIndex = pathString.lastIndexOf(".");
        if(extensionStartIndex != -1) {
            return pathString.substring(0, extensionStartIndex);
        } else {
            return pathString;
        }
    }

    public static List<FilesystemPath> removeCommonParent(List<FilesystemPath> paths, FilesystemPath otherPath) {
    	List<FilesystemPath> trimmedPaths = new ArrayList<FilesystemPath>();
    	for(FilesystemPath path : paths) {
    		trimmedPaths.add(path.removeCommonParent(otherPath));
    	}
    	return trimmedPaths;
    }
}