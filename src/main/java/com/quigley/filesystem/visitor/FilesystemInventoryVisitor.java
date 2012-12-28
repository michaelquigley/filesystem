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

package com.quigley.filesystem.visitor;

import java.util.ArrayList;
import java.util.List;

import com.quigley.filesystem.FilesystemPath;

public class FilesystemInventoryVisitor implements FilesystemVisitor {
	public FilesystemInventoryVisitor() {
		paths = new ArrayList<FilesystemPath>();
		includeFiles = true;
		includeDirectories = true;
		excludeTokens = new ArrayList<String>();
		includeTokens = new ArrayList<String>();
	}
	
	public void visit(FilesystemPath path) {
		boolean include = true;
		if(includeTokens != null && includeTokens.size() > 0) {
			include = false;
			for(String includeToken : includeTokens) {
				if(path.toString().indexOf(includeToken) != -1) {
					include = true;
					break;
				}
			}
		}
		boolean exclude = false;
		if(excludeTokens != null && excludeTokens.size() > 0) {
			for(String excludeToken : excludeTokens) {
				if(path.toString().indexOf(excludeToken) != -1) {
					exclude = true;
					break;
				}
			}
		}
		if(include && !exclude) {
			if((path.asFile().isDirectory() && includeDirectories) || (path.asFile().isFile() && includeFiles)) {
				if(rootTrim == null) {
					paths.add(path);
				} else {
					paths.add(path.removeCommonParent(rootTrim));
				}
			}
		}
	}
	
	public boolean isIncludeDirectories() {
		return includeDirectories;
	}
	public void setIncludeDirectories(boolean includeDirectories) {
		this.includeDirectories = includeDirectories;
	}
	
	public boolean isIncludeFiles() {
		return includeFiles;
	}
	public void setIncludeFiles(boolean includeFiles) {
		this.includeFiles = includeFiles;
	}

	public List<String> getExcludeTokens() {
		return excludeTokens;
	}
	public void setExcludeTokens(List<String> excludeTokens) {
		if(excludeTokens != null) {
			this.excludeTokens = excludeTokens;
		}
	}
	public void addExcludeToken(String excludeToken) {
		excludeTokens.add(excludeToken);
	}

	public List<String> getIncludeTokens() {
		return includeTokens;
	}
	public void setIncludeTokens(List<String> includeTokens) {
		if(includeTokens != null) {
			this.includeTokens = includeTokens;
		}
	}
	public void addIncludeToken(String includeToken) {
		includeTokens.add(includeToken);
	}

	public FilesystemPath getRootTrim() {
		return rootTrim;
	}
	public void setRootTrim(FilesystemPath rootTrim) {
		this.rootTrim = rootTrim;
	}

	public List<FilesystemPath> getPaths() {
		return paths;
	}

	protected boolean includeDirectories;
	protected boolean includeFiles;
	protected List<String> excludeTokens;
	protected List<String> includeTokens;
	protected FilesystemPath rootTrim;
	
	protected List<FilesystemPath> paths;
}