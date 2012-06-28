package com.quigley.filesystem;

import java.util.ArrayList;
import java.util.List;

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
		if(includeTokens.size() > 0) {
			include = false;
			for(String includeToken : includeTokens) {
				if(path.asString().indexOf(includeToken) != -1) {
					include = true;
					break;
				}
			}
		}
		boolean exclude = false;
		if(excludeTokens.size() > 0) {
			for(String excludeToken : excludeTokens) {
				if(path.asString().indexOf(excludeToken) != -1) {
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
					paths.add(FilesystemPath.removeCommonParent(path, rootTrim));
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