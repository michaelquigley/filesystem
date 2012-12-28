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

import com.quigley.filesystem.FilesystemPath;

public class FilesystemModificationVisitor implements FilesystemVisitor {
	public void visit(FilesystemPath path) {
		long fileModified = path.asFile().lastModified();
		if(lastModified < fileModified) {
			lastModified = fileModified;
		}
	}
	
	public long getLastModified() {
		return lastModified;
	}

	private long lastModified;
}