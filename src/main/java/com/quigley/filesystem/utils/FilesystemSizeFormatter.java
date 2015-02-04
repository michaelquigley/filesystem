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

import java.text.DecimalFormat;

public class FilesystemSizeFormatter {
	public static String format(long sizeInBytes) {
		if(sizeInBytes < KILOBYTE) {
			return integerFormatter.format(sizeInBytes);
		}
		
		if(sizeInBytes < MEGABYTE) {
			double kilobytes = ((double) sizeInBytes) / ((double) KILOBYTE);
			double remainder = ((double) sizeInBytes) % ((double) KILOBYTE);
			if(remainder == 0.0) {
				return integerFormatter.format(kilobytes) + " KiB";
			} else {
				return formatter.format(kilobytes) + " KiB";
			}
		}

		if(sizeInBytes < GIGABYTE) {
			double megabytes = ((double) sizeInBytes) / ((double) MEGABYTE);
			double remainder = ((double) sizeInBytes) % ((double) MEGABYTE);
			if(remainder == 0.0) {
				return integerFormatter.format(megabytes) + " MiB";
			} else {
				return formatter.format(megabytes) + " MiB";
			}
		}
		
		if(sizeInBytes < TERABYTE) {
			double gigabytes = ((double) sizeInBytes) / ((double) GIGABYTE);
			double remainder = ((double) sizeInBytes) % ((double) GIGABYTE);
			if(remainder == 0.0) {
				return integerFormatter.format(gigabytes) + " GiB";
			} else {
				return formatter.format(gigabytes) + " GiB";
			}
		}
		
		if(sizeInBytes < PETABYTE) {
			double terabytes = ((double) sizeInBytes) / ((double) TERABYTE);
			double remainder = ((double) sizeInBytes) % ((double) TERABYTE);
			if(remainder == 0.0) {
				return integerFormatter.format(terabytes) + " TiB";
			} else {
				return formatter.format(terabytes) + " TiB";
			}
		}
		
		double petabytes = ((double) sizeInBytes) / ((double) PETABYTE);
		double remainder = ((double) sizeInBytes) % ((double) PETABYTE);
		if(remainder == 0.0) {
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