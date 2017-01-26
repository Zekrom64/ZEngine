package com.zekrom_64.ze.base.vfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zekrom_64.ze.base.vfs.ZEVirtualFS.ZEVirtualFSDirectory;
import com.zekrom_64.ze.base.vfs.ZEVirtualFS.ZEVirtualFSEntry;
import com.zekrom_64.ze.base.vfs.ZEVirtualFS.ZEVirtualFSFile;
import com.zekrom_64.ze.base.vfs.ZEVirtualFS.ZEVirtualFSFlag;

/** A virtual directory emulates the functionality of a directory in the virtual file system.
 * 
 * @author Zekrom_64
 *
 */
public class ZEVirtualDirectory implements ZEVirtualFSDirectory {

	private String name;
	private ZEVirtualFSDirectory parent;
	private boolean[] flags = new boolean[ZEVirtualFSFlag.values().length];
	private List<ZEVirtualFSEntry> entries = new ArrayList<>();
	
	ZEVirtualDirectory() { name = "/"; }
	
	public ZEVirtualDirectory(String name) {
		if (!setName(name)) throw new RuntimeException("Invalid virtual directory name \"" + name + '\"');
	}
	
	public ZEVirtualDirectory(String name, ZEVirtualFSDirectory parent) {
		this(name);
		this.parent = parent;
	}

	@Override
	public String getName() { return name; }
	
	private boolean setName(String name) {
		if (name==null||name.length()==0) return false;
		if (name.indexOf('\\')!=-1) return false;
		if (name.indexOf('/')!=-1) return false;
		if (name.equals("..")) return false;
		this.name = name;
		return true;
	}

	@Override
	public boolean canHaveChildren() { return true; }

	@Override
	public ZEVirtualFSEntry derive(String subpath) {
		if (subpath==null||subpath.length()==0) return null;
		subpath = ZEVirtualFS.resolvePath(subpath);
		String[] parts = subpath.split("/");
		if (parts.length>1) {
			subpath = subpath.substring(subpath.indexOf('/')+1);
			if (parts[0].equals("..")) {
				if (parent!=null) return parent.deriveFile(subpath);
			} else {
				for(ZEVirtualFSEntry e : entries) {
					if (e.getName().equals(parts[0]) && e.isDirectory()) return e.asDirectory().deriveFile(subpath);
				}
				ZEVirtualDirectory vd = new ZEVirtualDirectory(parts[0]);
				vd.setParent(this);
				entries.add(vd);
				return vd.deriveFile(subpath);
			}
		} else if (parts.length==1) {
			if (parts[0].equals("..")) return parent;
			ZEVirtualFSEntry ve = null;
			if (parts[0].indexOf('.')!=-1) {
				ZEVirtualFile file = new ZEVirtualFile(parts[0]);
				file.setParent(parent);
				ve = file;
			} else {
				ZEVirtualDirectory dir = new ZEVirtualDirectory(parts[0]);
				dir.setParent(parent);
				ve = dir;
			}
			entries.add(ve);
			return ve;
		}
		return null;
	}

	@Override
	public void mkdirs() throws IOException { }

	@Override
	public boolean exists() { return true; }

	@Override
	public boolean isDirectory() { return true; }

	@Override
	public boolean isFile() { return false; }

	@Override
	public ZEVirtualFSDirectory getParent() { return parent; }

	@Override
	public ZEVirtualFSEntry[] getChildren() {
		return entries.toArray(new ZEVirtualFSEntry[0]);
	}

	@Override
	public ZEVirtualFSFile deriveFile(String subpath) {
		if (subpath==null||subpath.length()==0) return null;
		subpath = ZEVirtualFS.resolvePath(subpath);
		String[] parts = subpath.split("/");
		if (parts.length>1) {
			subpath = subpath.substring(subpath.indexOf('/')+1);
			if (parts[0].equals("..")) {
				if (parent!=null) return parent.deriveFile(subpath);
			} else {
				for(ZEVirtualFSEntry e : entries) {
					if (e.getName().equals(parts[0]) && e.isDirectory()) return e.asDirectory().deriveFile(subpath);
				}
				ZEVirtualDirectory vd = new ZEVirtualDirectory(parts[0]);
				vd.setParent(this);
				entries.add(vd);
				return vd.deriveFile(subpath);
			}
		} else if (parts.length==1) {
			if (parts[0].equals("..")) return null;
			for(ZEVirtualFSEntry e : entries) {
				if (e.getName().equals(parts[0]) && e.isFile()) return e.asFile();
			}
			ZEVirtualFile vf = new ZEVirtualFile(parts[0]);
			vf.setParent(this);
			entries.add(vf);
			return vf;
		}
		return null;
	}

	@Override
	public ZEVirtualFSDirectory deriveDirectory(String subpath) {
		if (subpath==null||subpath.length()==0) return null;
		subpath = ZEVirtualFS.resolvePath(subpath);
		String[] parts = subpath.split("/");
		if (parts.length>1) {
			subpath = subpath.substring(subpath.indexOf('/')+1);
			if (parts[0].equals("..")) {
				if (parent!=null) return parent.deriveDirectory(subpath);
			} else {
				for(ZEVirtualFSEntry e : entries) {
					if (e.getName().equals(parts[0]) && e.isDirectory()) return e.asDirectory().deriveDirectory(subpath);
				}
				ZEVirtualDirectory vd = new ZEVirtualDirectory(parts[0]);
				vd.setParent(this);
				entries.add(vd);
				return vd.deriveDirectory(subpath);
			}
		} else if (parts.length==1) {
			if (parts[0].equals("..")) return parent;
			for(ZEVirtualFSEntry e : entries) {
				if (e.getName().equals(parts[0]) && e.isFile()) return e.asDirectory();
			}
			ZEVirtualDirectory vd = new ZEVirtualDirectory(parts[0]);
			vd.setParent(this);
			entries.add(vd);
			return vd;
		}
		return null;
	}

	@Override
	public boolean getFlag(ZEVirtualFSFlag flag) {
		if (flag==null) return false;
		return flags[flag.ordinal()];
	}

	@Override
	public boolean canSetFlag(ZEVirtualFSFlag flag) { return true; }

	@Override
	public boolean setFlag(ZEVirtualFSFlag flag, boolean value) {
		if (flag==null) return false;
		flags[flag.ordinal()] = value;
		return true;
	}

	@Override
	public ZEVirtualFSFile asFile() { return null; }

	@Override
	public ZEVirtualFSDirectory asDirectory() { return this; }

	@Override
	public void setParent(ZEVirtualFSDirectory dir) {
		ZEVirtualFSDirectory oldparent = parent;
		parent = dir;
		if (oldparent!=null) oldparent.removeEntry(this);
	}

	@Override
	public ZEVirtualFSEntry getChild(String name) {
		ZEVirtualFSDirectory dir = null;
		ZEVirtualFSFile file = null;
		for(ZEVirtualFSEntry e : entries) {
			if (e.getName().equals(name)) {
				if (e.isDirectory()) dir = e.asDirectory();
				if (e.isFile()) file = e.asFile();
			}
		}
		if (dir==null) return file;
		if (file==null) return dir;
		if (dir!=null&&file!=null) return new ZEVirtualCombinedEntry(dir, file);
		return null;
	}

	@Override
	public ZEVirtualFSFile[] getChildFiles() {
		List<ZEVirtualFSFile> files = new ArrayList<>();
		for(ZEVirtualFSEntry e : entries) if (e.isFile()) files.add(e.asFile());
		return files.toArray(new ZEVirtualFSFile[0]);
	}

	@Override
	public ZEVirtualFSFile getChildFile(String name) {
		for(ZEVirtualFSEntry e : entries) if (e.getName().equals(name) && e.isFile()) return e.asFile();
		return null;
	}

	@Override
	public ZEVirtualFSDirectory[] getChildDirectories() {
		List<ZEVirtualFSDirectory> dirs = new ArrayList<>();
		for(ZEVirtualFSEntry e : entries) if (e.isDirectory()) dirs.add(e.asDirectory());
		return dirs.toArray(new ZEVirtualFSDirectory[0]);
	}

	@Override
	public ZEVirtualFSDirectory getChildDirectory(String name) {
		for(ZEVirtualFSEntry e : entries) if (e.getName().equals(name) && e.isDirectory()) return e.asDirectory();
		return null;
	}

	@Override
	public void removeEntry(ZEVirtualFSEntry entry) {
		entries.remove(entry);
		entry.setParent(null);
	}

}
