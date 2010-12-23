package com.quigley.filesystem;

import org.junit.Test;
import static org.junit.Assert.*;

public class FilesystemSizeFormatterTest {
	@Test
	public void testGigabytes() {
		String output = FilesystemSizeFormatter.format(1696612761L);
		assertEquals("1.58 GiB", output);
	}
}