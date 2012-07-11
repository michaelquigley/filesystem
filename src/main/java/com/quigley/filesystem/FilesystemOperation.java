package com.quigley.filesystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import com.quigley.filesystem.utils.StreamCopier;
import com.quigley.filesystem.visitor.FilesystemInventoryVisitor;

public class FilesystemOperation {
	public static void copy(FilesystemPath source, FilesystemPath dest) throws FilesystemException {
		copy(source.asFile(), dest.asFile());
	}

	public static void copy(File source, File dest) throws FilesystemException {
		FileChannel sourceChannel = null;
		FileChannel destChannel = null;
		try {
			sourceChannel = new FileInputStream(source).getChannel();
			destChannel = new FileOutputStream(dest).getChannel();

			sourceChannel.transferTo(0, sourceChannel.size(), destChannel);

		} catch (Exception e) {
			throw new FilesystemException("Unable to copy", e);
		} finally {
			if(sourceChannel != null) {
				try {
					sourceChannel.close();
				} catch(IOException ioe) {
				}
			}
			if(destChannel != null) {
				try {
					destChannel.close();
				} catch(IOException ioe) {
				}
			}
		}
	}
	
	public static List<FilesystemPath> copyTree(FilesystemPath sourceTree, FilesystemPath destTree) {
		return copyTree(sourceTree, destTree, null, null);
	}
	
	public static List<FilesystemPath> copyTree(FilesystemPath sourceTree, FilesystemPath destTree, List<String> includeTokens, List<String> excludeTokens) throws FilesystemException {
		FilesystemInventoryVisitor visitor = new FilesystemInventoryVisitor();
		visitor.setExcludeTokens(excludeTokens);
		visitor.setIncludeTokens(includeTokens);
		visitor.setRootTrim(sourceTree);
		visitor.setIncludeDirectories(false);
		FilesystemIterator iterator = new FilesystemIterator(visitor);
		iterator.iterate(sourceTree);
		
		List<FilesystemPath> copiedFiles = new ArrayList<FilesystemPath>();
		for(FilesystemPath sourcePath : visitor.getPaths()) {
			copiedFiles.add(sourcePath);
			FilesystemPath destPath = destTree.add(sourcePath);
			destPath.parent().asFile().mkdirs();
			copy(sourceTree.add(sourcePath), destPath);
		}
		
		return copiedFiles;
	}

	public static String readFileAsString(File path) throws IOException {
		FileInputStream in = new FileInputStream(path);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StreamCopier.copy(in, out);
		in.close();
		out.close();
		return new String(out.toByteArray());
	}
	
	public static void writeStringToFile(String data, File path) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
		FileOutputStream out = new FileOutputStream(path);
		StreamCopier.copy(in, out);
		in.close();
		out.close();
	}
	
	public static boolean recursiveDelete(FilesystemPath path) {
		return recursiveDelete(path.asFile());
	}
	
	public static boolean recursiveDelete(File path) {
		if(path.exists()) {
			if(path.isDirectory()) {
				for(String child : path.list()) {
					if(!recursiveDelete(new File(path, child))) {
						return false;
					}
				}
			}
			return path.delete();
		}
		return false;
	}
}
