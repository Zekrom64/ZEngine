package com.zekrom_64.ze.base.vfs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import com.zekrom_64.ze.base.io.ZEFileUtil;
import com.zekrom_64.ze.base.vfs.ZEVirtualFS.ZEVirtualFSDirectory;
import com.zekrom_64.ze.base.vfs.ZEVirtualFS.ZEVirtualFSEntry;
import com.zekrom_64.ze.base.vfs.ZEVirtualFS.ZEVirtualFSFile;
import com.zekrom_64.ze.base.vfs.ZEVirtualFS.ZEVirtualFSFlag;

public class ZEVirtualFile implements ZEVirtualFSFile {

	private String name;
	private ZEVirtualFSDirectory parent;
	private byte[] data = new byte[0];
	private boolean[] flags = new boolean[ZEVirtualFSFlag.values().length];
	private VirtualFileOutputStream currentOutputStream = null;
	
	public ZEVirtualFile(String name) {
		if (!setName(name)) throw new RuntimeException("Invalid virtual file name \"" + name + '\"');
	}
	
	public ZEVirtualFile(String name, ZEVirtualFSDirectory parent) {
		this(name);
		this.parent = parent;
	}
	
	private boolean setName(String name) {
		if (name==null||name.length()==0) return false;
		if (name.indexOf('\\')!=-1) return false;
		if (name.indexOf('/')!=-1) return false;
		if (name.equals("..")) return false;
		this.name = name;
		return true;
	}
	
	@Override
	public String getName() { return name; }

	@Override
	public boolean canHaveChildren() { return false; }

	@Override
	public ZEVirtualFSEntry derive(String subpath) { return null; }

	@Override
	public ZEVirtualFSFile deriveFile(String subpath) { return null; }

	@Override
	public ZEVirtualFSDirectory deriveDirectory(String subpath) { return null; }

	@Override
	public boolean exists() { return true; }

	@Override
	public boolean isDirectory() { return false; }

	@Override
	public boolean isFile() { return true; }

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
	public ZEVirtualFSFile asFile() { return this; }

	@Override
	public ZEVirtualFSDirectory asDirectory() { return null; }

	@Override
	public ZEVirtualFSDirectory getParent() { return parent; }

	@Override
	public void setParent(ZEVirtualFSDirectory dir) {
		ZEVirtualFSDirectory oldparent = parent;
		parent = dir;
		if (oldparent!=null) oldparent.removeEntry(this);
	}

	@Override
	public InputStream read() throws IOException {
		return new ByteArrayInputStream(Arrays.copyOf(data, data.length));
	}
	
	private class VirtualFileOutputStream extends ByteArrayOutputStream {
		
		private final boolean append;
		
		public VirtualFileOutputStream(boolean a) {
			append = a;
		}
		
		@Override
		public void close() throws IOException {
			if (currentOutputStream==this) currentOutputStream = null;
			flush();
		}

		@Override
		public void flush() throws IOException {
			if (append) {
				synchronized(data) {
					byte[] outdata = toByteArray();
					data = Arrays.copyOf(data, data.length + outdata.length);
					System.arraycopy(outdata, 0, data, data.length - outdata.length, outdata.length);
				}
			} else data = toByteArray();
		}
		
	}

	@Override
	public OutputStream write(boolean append, boolean create) throws IOException {
		return currentOutputStream = new VirtualFileOutputStream(append);
	}

	@Override
	public String getType() {
		return ZEFileUtil.getExtension(name);
	}

	@Override
	public void mkfile() throws IOException { }

	@Override
	public InputStream openInput() throws IOException {
		return read();
	}

	@Override
	public OutputStream openOutput() throws IOException {
		return write(false, false);
	}

}
