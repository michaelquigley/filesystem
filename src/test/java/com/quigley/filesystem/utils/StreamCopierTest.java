package com.quigley.filesystem.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import static org.junit.Assert.*;

import org.junit.Test;

public class StreamCopierTest {
	@Test
	public void testStreamCopier() throws IOException {
		String inString = "Hello World!";
		ByteArrayInputStream in = new ByteArrayInputStream(inString.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StreamCopier.copy(in, out);
		String outString = new String(out.toByteArray());
		assertTrue(inString.equals(outString));
	}

	@Test
	public void testStreamCopierBigData() throws IOException {
		String blockString = "FFFFFFFFFFFFFFFF";
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < 1024; i++) {
			builder.append(blockString);
		}
		String inString = builder.toString();
		ByteArrayInputStream in = new ByteArrayInputStream(inString.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StreamCopier.copy(in, out);
		String outString = new String(out.toByteArray());
		assertTrue(inString.equals(outString));
	}
}
