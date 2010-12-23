package com.quigley.filesystem;

public interface FilesystemVisitor {
    public void visit(FilesystemPath path);
}
