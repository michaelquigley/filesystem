package com.quigley.filesystem;

import java.text.DecimalFormat;

public class FilesystemSizeFormatter {
	public static String format(long sizeInBytes) {
		if(sizeInBytes < KILOBYTE) {
			return integerFormatter.format(sizeInBytes);
		}
		
		if(sizeInBytes < MEGABYTE) {
			float kilobytes = ((float) sizeInBytes) / ((float) KILOBYTE);
			float remainder = ((float) sizeInBytes) % ((float) KILOBYTE);
			if(remainder == 0.0F) {
				return integerFormatter.format(kilobytes) + " KiB";
			} else {
				return formatter.format(kilobytes) + " KiB";
			}
		}

		if(sizeInBytes < GIGABYTE) {
			float megabytes = ((float) sizeInBytes) / ((float) MEGABYTE);
			float remainder = ((float) sizeInBytes) % ((float) MEGABYTE);
			if(remainder == 0.0F) {
				return integerFormatter.format(megabytes) + " MiB";
			} else {
				return formatter.format(megabytes) + " MiB";
			}
		}
		
		if(sizeInBytes < TERABYTE) {
			float gigabytes = ((float) sizeInBytes) / ((float) GIGABYTE);
			float remainder = ((float) sizeInBytes) % ((float) GIGABYTE);
			if(remainder == 0.0F) {
				return integerFormatter.format(gigabytes) + " GiB";
			} else {
				return formatter.format(gigabytes) + " GiB";
			}
		}
		
		if(sizeInBytes < PETABYTE) {
			float terabytes = sizeInBytes / TERABYTE;
			float remainder = sizeInBytes % TERABYTE;
			if(remainder == 0.0F) {
				return integerFormatter.format(terabytes) + " TiB";
			} else {
				return formatter.format(terabytes) + " TiB";
			}
		}
		
		float petabytes = sizeInBytes / PETABYTE;
		float remainder = sizeInBytes % PETABYTE;
		if(remainder == 0.0F) {
			return integerFormatter.format(petabytes) + " PiB";
		} else {
			return formatter.format(petabytes) + " PiB";
		}
	}
	
	public static final long KILOBYTE = 1024L;
	public static final long MEGABYTE = KILOBYTE * 1024L;
	public static final long GIGABYTE = MEGABYTE * 1024L;
	public static final long TERABYTE = GIGABYTE * 1024L;
	public static final long PETABYTE = TERABYTE * 1024L;
	
	private static DecimalFormat formatter = new DecimalFormat("#####.00");
	private static DecimalFormat integerFormatter = new DecimalFormat("#####");
}