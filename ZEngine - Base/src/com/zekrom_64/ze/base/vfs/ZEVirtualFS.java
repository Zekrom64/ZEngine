package com.zekrom_64.ze.base.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import com.zekrom_64.ze.base.io.ZEInput;
import com.zekrom_64.ze.base.io.ZEOutput;

/** Represents one instance of a virtual file system.
 * 
 * @author Zekrom_64
 *
 */
public class ZEVirtualFS {
	
	/** Flags used by the virtual file system. More flags may be available depending on the
	 * implementation of an entry, but these will always be available. */
	public enum ZEVirtualFSFlag {
		/** The entry is readable */
		READABLE,
		/** The entry can be written to */
		WRITABLE
	}
	
	/** Describes a single entry in the virtual file system. This abstracts the actual implementation of the
	 * entry and is the base for all entries, even if orphaned or non-existent in the file system.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public interface ZEVirtualFSEntry {
		
		/** Get the name of the entry */
		public String getName();
		
		/** If the entry can have children, This does not necessarily mean it is a directory. */
		public boolean canHaveChildren();
		
		/** Derives an entry from this entry's position in the file system.
		 * 
		 * @param subpath Path relative to current entry
		 * @return
		 */
		public ZEVirtualFSEntry derive(String subpath);
		
		/**Explicit form of {@link #derive(String)}. Because some files may not have an extension on their name,
		 * there is ambiguity to if a path is a file or a folder. This explicitly derives a file.
		 * 
		 * @param subpath Path relative to current entry
		 * @return
		 */
		public ZEVirtualFSFile deriveFile(String subpath);

		/**Explicit form of {@link #derive(String)}. Because some files may not have an extension on their name,
		 * there is ambiguity to if a path is a file or a folder. This explicitly derives a directory.
		 * 
		 * @param subpath Path relative to current entry
		 * @return
		 */
		public ZEVirtualFSDirectory deriveDirectory(String subpath);
		
		/** If the entry actually exists. 
		 * 
		 * @return If thies entry exists
		 */
		public boolean exists();
		
		/** If the entry is a directory.
		 * 
		 * @return If this is a directory
		 */
		public boolean isDirectory();
		
		/** If the entry is a file.
		 * 
		 * @return If this is a file.
		 */
		public boolean isFile();
		
		/** Gets a file system flag for this entry.
		 * 
		 * @param flag File system flag
		 * @return Flag value
		 */
		public boolean getFlag(ZEVirtualFSFlag flag);
		
		/** Tests if a file system flag can be modified.
		 * 
		 * @param flag Flag to test for
		 * @return If the flag can be changed
		 */
		public boolean canSetFlag(ZEVirtualFSFlag flag);
		
		/** Sets the value of a file system flag. The return value determines if the flag was successfully written
		 * to.
		 * 
		 * @param flag Flag to modify
		 * @param value Value to set flag to
		 * @return If the value was modified
		 */
		public boolean setFlag(ZEVirtualFSFlag flag, boolean value);
		
		/** Casts this entry to a file. If this entry cannot be a file, null is returned.
		 * 
		 * @return This entry as a file, or null
		 */
		public ZEVirtualFSFile asFile();
		
		/** Casts this entry to a directory. If this entry cannot be a directory, null is returned.
		 * 
		 * @return This entry as a directory, or null
		 */
		public ZEVirtualFSDirectory asDirectory();
		
		/** Gets the parent directory of this entry. If this is a root directory or an orphaned entry, this 
		 * returns null.
		 * 
		 * @return The parent directory, or null
		 */
		public ZEVirtualFSDirectory getParent();
		
		/** Sets the parent directory of this entry. This can be used to mount or unmount directories like
		 * file systems, quickly move entries, create linked folders, or orphan files.
		 * 
		 * @param dir The directory to set as a parent
		 */
		public void setParent(ZEVirtualFSDirectory dir);
	}
	
	/** Type of virtual file system entry that represents a directory.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public interface ZEVirtualFSDirectory extends ZEVirtualFSEntry {
		
		/** Gets all the child entries contained in this directory. If this directory contains no entries, a
		 * zero-length array is returned.
		 * 
		 * @return All this directories children
		 */
		public ZEVirtualFSEntry[] getChildren();
		
		/** Gets a child entry with the given name.
		 * 
		 * @param name Name of the entry
		 * @return The entry with the given name
		 */
		public ZEVirtualFSEntry getChild(String name);
		
		/** Gets all the files contained in this directory that are known to exist. If this directory contains
		 * no files, a zero-length array is returned.
		 * 
		 * @return All the extant files in this directory
		 */
		public ZEVirtualFSFile[] getChildFiles();
		
		/** Gets a file with the given name.
		 * 
		 * @param name Name of the file
		 * @return The file with the given name
		 */
		public ZEVirtualFSFile getChildFile(String name);
		
		/** Gets all the directories contain in this directory that are known to exist. If this directory contains
		 * no directories, a zero-length array is returned.
		 * 
		 * @return All the extant directories in this directory
		 */
		public ZEVirtualFSDirectory[] getChildDirectories();
		
		/** Gets a directory with the given name.
		 * 
		 * @param name Name of the directory
		 * @return The directory with the given name
		 */
		public ZEVirtualFSDirectory getChildDirectory(String name);
		
		/** Attempts to create this directory and all of its parent directories.
		 * 
		 * @throws IOException If the directory creation failed
		 */
		public void mkdirs() throws IOException;
		
		/** Removes an entry from this directory and sets its parent to null.
		 * 
		 * @param entry Entry to remove
		 */
		public void removeEntry(ZEVirtualFSEntry entry);
	}
	
	/** Type of virtual file system entry that represents a file.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public interface ZEVirtualFSFile extends ZEVirtualFSEntry, ZEInput, ZEOutput {
		
		/** Opens an InputStream for this virtual file. If the file does not exist, null is returned.
		 * 
		 * @return The input stream, or null
		 * @throws IOException If the file failed to be opened
		 */
		public InputStream read() throws IOException;
		
		/** Opens an OutputStream for this virtual file. How the file is opened depends on the flags
		 * given to the method. The append flag determines if the stream is opened at the beginning or
		 * the end of the file. The create flag determines if the file should attempt to be created
		 * if it does not exist. If the file does not exist and the create flag is false, null is returned.
		 * 
		 * @param append If the stream should be opened at the end of the file
		 * @param create If the file should be created if it doesn't exist
		 * @return The output stream, or null
		 * @throws IOException If the file failed to be opened
		 */
		public OutputStream write(boolean append, boolean create) throws IOException;
		
		/** Gets this file's type or extension.
		 * 
		 * @return File type
		 */
		public String getType();

		/** If this file is not created, this method attempts to create a new file.
		 * 
		 * @throws IOException If the creation failed
		 */
		public void mkfile() throws IOException;
	}
	
	/** The root directory in this virtual file system */
	public final ZEVirtualDirectory root = new ZEVirtualDirectory();
	
	/** Resolves a path by simplifying the path and converting all backslashes to forward slashes.
	 * 
	 * @param path Path to resolve
	 * @return Resolved path
	 */
	public static String resolvePath(String path) {
		LinkedList<String> listpath = new LinkedList<>();
		String[] splitpath = path.replace('\\', '/').split("\\/");
		for(String s : splitpath) listpath.add(s);
		boolean hasFoundPair = false;
		do {
			hasFoundPair = false;
			for(int i = 0; i < listpath.size(); i++) {
				if (i==listpath.size()-1) continue;
				if (listpath.get(i+1).startsWith("..") && !listpath.get(i).startsWith("..")) {
					listpath.remove(i);
					listpath.remove(i);
					hasFoundPair = true;
				}
			}
		} while(hasFoundPair);
		StringBuilder sb = new StringBuilder();
		for(String s : listpath) sb.append(s).append('/');
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
