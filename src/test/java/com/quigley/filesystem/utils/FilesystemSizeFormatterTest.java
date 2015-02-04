package com.quigley.filesystem.utils;

import org.junit.Test;

import com.quigley.filesystem.utils.FilesystemSizeFormatter;

import static org.junit.Assert.*;

public class FilesystemSizeFormatterTest {
	@Test
	public void testGigabytes() {
		String output = FilesystemSizeFormatter.format(1696612761L);
		assertEquals("1.58 GiB", output);
	}

    @Test
    public void testTerabytes() {
        String output = FilesystemSizeFormatter.format(2010166711827L);
        assertEquals("1.83 TiB", output);
    }

    @Test
    public void testPetabytes() {
        String output = FilesystemSizeFormatter.format(2010166711827000L);
        assertEquals("1.79 PiB", output);
    }
}