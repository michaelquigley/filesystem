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

@SuppressWarnings("serial")
public class FilesystemException extends RuntimeException {
	public FilesystemException() {
		super();
	}
	public FilesystemException(String message, Throwable cause) {
		super(message, cause);
	}
	public FilesystemException(String message) {
		super(message);
	}
	public FilesystemException(Throwable cause) {
		super(cause);
	}
}