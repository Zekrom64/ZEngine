package com.zekrom_64.ze.base.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.zekrom_64.ze.base.vfs.ZEVirtualFS.ZEVirtualFSDirectory;
import com.zekrom_64.ze.base.vfs.ZEVirtualFS.ZEVirtualFSEntry;
import com.zekrom_64.ze.base.vfs.ZEVirtualFS.ZEVirtualFSFile;
import com.zekrom_64.ze.base.vfs.ZEVirtualFS.ZEVirtualFSFlag;

public class ZEVirtualCombinedEntry implements ZEVirtualFSDirectory, ZEVirtualFSFile {

	private ZEVirtualFSDirectory dir;
	private ZEVirtualFSFile file;
	private ZEVirtualFSDirectory parent;
	private String name;
	
	public ZEVirtualCombinedEntry(ZEVirtualFSDirectory dir, ZEVirtualFSFile file) {
		if (dir==null) throw new NullPointerException();
		if (file==null) throw new NullPointerException();
		this.dir = dir;
		this.file = file;
		if (!dir.getName().equals(file.getName()))
			throw new IllegalArgumentException("Directory and file with different names cannot be combined");
		if (dir.getParent()==null||file.getParent()==null)
			throw new IllegalArgumentException("Cannot create an orphaned combined entry");
		if (dir.getParent()!=file.getParent()) 
			throw new IllegalArgumentException("Directory and file with different parents cannot be combined");
		parent = dir.getParent();
		name = dir.getName();
	}
	
	private void checkValid() {
		if (dir.getParent()!=parent) dir = null;
		if (file.getParent()!=parent) file = null;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean canHaveChildren() {
		checkValid();
		return dir != null;
	}

	@Override
	public ZEVirtualFSEntry derive(String subpath) {
		checkValid();
		return parent.derive(name + '/' + subpath);
	}

	@Override
	public ZEVirtualFSFile deriveFile(String subpath) {
		checkValid();
		return parent.deriveFile(name + '/' + subpath);
	}

	@Override
	public ZEVirtualFSDirectory deriveDirectory(String subpath) {
		checkValid();
		return parent.deriveDirectory(name + '/' + subpath);
	}

	@Override
	public boolean exists() {
		checkValid();
		return (dir != null ? dir.exists() : false) || (file != null ? file.exists() : false);
	}

	@Override
	public boolean isDirectory() {
		checkValid();
		return dir != null;
	}

	@Override
	public boolean isFile() {
		checkValid();
		return file != null;
	}

	@Override
	public boolean getFlag(ZEVirtualFSFlag flag) {
		checkValid();
		return (dir != null ? dir.getFlag(flag) : false) || (dir != null ? file.getFlag(flag) : false);
	}

	@Override
	public boolean canSetFlag(ZEVirtualFSFlag flag) {
		checkValid();
		return (dir != null ? dir.canSetFlag(flag) : false) || (file != null ? file.canSetFlag(flag) : false);
	}

	@Override
	public boolean setFlag(ZEVirtualFSFlag flag, boolean value) {
		checkValid();
		return dir.setFlag(flag, value) && file.setFlag(flag, value);
	}

	@Override
	public ZEVirtualFSFile asFile() {
		checkValid();
		return file;
	}

	@Override
	public ZEVirtualFSDirectory asDirectory() {
		checkValid();
		return dir;
	}

	@Override
	public ZEVirtualFSDirectory getParent() {
		checkValid();
		return parent;
	}

	@Override
	public void setParent(ZEVirtualFSDirectory dir) {
		checkValid();
		if (dir!=null) dir.setParent(dir);
		if (file!=null) file.setParent(dir);
		ZEVirtualFSDirectory oldparent = parent;
		parent = dir;
		if (oldparent!=null) oldparent.removeEntry(this);
	}

	@Override
	public InputStream read() throws IOException {
		checkValid();
		return file != null ? file.read() : null;
	}

	@Override
	public OutputStream write(boolean append, boolean create) throws IOException {
		checkValid();
		return file != null ? file.write(append, create) : null;
	}

	@Override
	public String getType() {
		checkValid();
		return file != null ? file.getType() : null;
	}

	@Override
	public void mkfile() throws IOException {
		checkValid();
		if (file!=null) file.mkfile();
	}

	@Override
	public ZEVirtualFSEntry[] getChildren() {
		checkValid();
		if (dir!=null) return dir.getChildren();
		return new ZEVirtualFSEntry[0];
	}

	@Override
	public ZEVirtualFSEntry getChild(String name) {
		checkValid();
		if (dir!=null) return dir.getChild(name);
		return null;
	}

	@Override
	public ZEVirtualFSFile[] getChildFiles() {
		checkValid();
		if (dir!=null) dir.getChildFiles();
		return new ZEVirtualFSFile[0];
	}

	@Override
	public ZEVirtualFSFile getChildFile(String name) {
		checkValid();
		if (dir!=null) return dir.getChildFile(name);
		return null;
	}

	@Override
	public ZEVirtualFSDirectory[] getChildDirectories() {
		checkValid();
		if (dir!=null) return dir.getChildDirectories();
		return new ZEVirtualFSDirectory[0];
	}

	@Override
	public ZEVirtualFSDirectory getChildDirectory(String name) {
		checkValid();
		if (dir!=null) return dir.getChildDirectory(name);
		return null;
	}

	@Override
	public void mkdirs() throws IOException {
		checkValid();
		if (dir!=null) dir.mkdirs();
	}

	@Override
	public void removeEntry(ZEVirtualFSEntry entry) {
		checkValid();
		if (dir!=null) dir.removeEntry(entry);
	}

	@Override
	public InputStream openInput() throws IOException {
		checkValid();
		if (file!=null) return file.openInput();
		return null;
	}

	@Override
	public OutputStream openOutput() throws IOException {
		checkValid();
		if (file!=null) return file.openOutput();
		return null;
	}

}
