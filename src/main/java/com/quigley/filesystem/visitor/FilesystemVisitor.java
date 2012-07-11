package com.quigley.filesystem.visitor;

import com.quigley.filesystem.FilesystemPath;

public interface FilesystemVisitor {
    public void visit(FilesystemPath path);
}
