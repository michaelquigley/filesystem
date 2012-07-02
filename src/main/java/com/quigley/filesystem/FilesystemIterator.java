package com.quigley.filesystem;

import java.io.File;

public class FilesystemIterator {
    public FilesystemIterator(FilesystemPath root, FilesystemVisitor visitor) {
        this.root = root;
        this.visitor = visitor;
    }

    public void iterate() throws FilesystemException {
        File rootF = root.asFile();
        if(rootF.exists() && rootF.canRead() && rootF.isDirectory()) {
            visitor.visit(root);

            File[] contents = rootF.listFiles();
            for(File f : contents) {
                FilesystemPath fPath = new FilesystemPath(root).add(f.getName());

                if(f.isDirectory()) {
                    FilesystemIterator subIter = new FilesystemIterator(fPath, visitor);
                    subIter.iterate();

                } else {
                    visitor.visit(fPath);
                }
            }

        } else {
            throw new FilesystemException(root.asString() + " is not a valid directory!");
        }
    }
    
    private FilesystemPath root;
    private FilesystemVisitor visitor;
}
