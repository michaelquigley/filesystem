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

package com.quigley.filesystem.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamCopier {
	public static void copy(InputStream in, OutputStream out) throws IOException {
		synchronized(in) {
			synchronized(out) {
				byte[] buffer = new byte[4096];
				while(true) {
					int length = in.read(buffer);
					if(length == -1) {
						break;
					}
					out.write(buffer, 0, length);
				}
			}
		}
	}
}