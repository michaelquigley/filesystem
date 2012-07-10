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