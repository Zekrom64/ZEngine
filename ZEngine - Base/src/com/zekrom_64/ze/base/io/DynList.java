package com.zekrom_64.ze.base.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DynList implements ZEByteable, ZERawSaveable, Cloneable {

	private static class DynamicDataEntry {
		
		public ZEByteable objectValue;
		public byte[] bytesValue;
		public Class<?> valueClass;
		
	}
	
	private ArrayList<DynamicDataEntry> data = new ArrayList<>();
	
	/** Creates a new empty data list.
	 * 
	 */
	public DynList() {}
	
	/** Creates a new data list from a byte source. This is the same as creating a new
	 * instance and calling {@link #rawRead(DataInput) rawRead()} using the byte array. 
	 * 
	 * @param bytes Byte source
	 * @throws IOException If an IOException occurs
	 */
	public DynList(byte[] bytes) throws IOException {
		rawRead(new DataInputStream(new ByteArrayInputStream(bytes)));
	}
	
	/** Removes an entry from the data list.
	 * 
	 * @param index Index of entry to remove
	 */
	public void remove(int index) {
		data.remove(index);
	}
	
	/** Sets a byte array value for an entry.
	 * 
	 * @param index Index
	 * @param bytes Byte value of entry
	 */
	public void put(int index, byte[] bytes) {
		if (bytes != null) {
			DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
			if (e == null) {
				e = new DynamicDataEntry();
				data.set(index, e);
			}
			e.bytesValue = bytes;
			e.objectValue = null;
			e.valueClass = byte[].class;
		}
	}
	
	/** Sets a byte value for an entry.
	 * 
	 * @param index Index
	 * @param byteable Byte value of entry
	 */
	public void put(int index, ZEByteable byteable) {
		if (byteable != null) {
			DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
			if (e == null) {
				e = new DynamicDataEntry();
				data.set(index, e);
			}
			e.bytesValue = byteable.toBytes();
			e.objectValue = byteable;
			e.valueClass = byteable.getClass();
		}
	}
	
	/** Sets a byte value for an entry.
	 * 
	 * @param index Index
	 * @param b Byte value of entry
	 */
	public void put(int index, byte b) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e == null) {
			e = new DynamicDataEntry();
			data.set(index, e);
		}
		e.bytesValue = new byte[] {b};
		e.objectValue = null;
		e.valueClass = byte.class;
	}
	
	/** Sets a short value for an entry.
	 * 
	 * @param index Index
	 * @param s Short value of entry
	 */
	public void put(int index, short s) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e == null) {
			e = new DynamicDataEntry();
			data.set(index, e);
		}
		e.bytesValue = Bytes.get(s);
		e.objectValue = null;
		e.valueClass = short.class;
	}
	
	/** Sets an int value for an entry.
	 * 
	 * @param index Index
	 * @param i Int value of entry
	 */
	public void put(int index, int i) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e == null) {
			e = new DynamicDataEntry();
			data.set(index, e);
		}
		e.bytesValue = Bytes.get(i);
		e.objectValue = null;
		e.valueClass = int.class;
	}
	
	/** Sets a long value for an entry.
	 * 
	 * @param index Index
	 * @param l Long value of entry
	 */
	public void put(int index, long l) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e == null) {
			e = new DynamicDataEntry();
			data.set(index, e);
		}
		e.bytesValue = Bytes.get(l);
		e.objectValue = null;
		e.valueClass = long.class;
	}
	
	/** Sets a float value for an entry.
	 * 
	 * @param index Index
	 * @param f Float value of entry
	 */
	public void put(int index, float f) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e == null) {
			e = new DynamicDataEntry();
			data.set(index, e);
		}
		e.bytesValue = Bytes.get(f);
		e.objectValue = null;
		e.valueClass = float.class;
	}
	
	/** Sets a double value for an entry.
	 * 
	 * @param index Index
	 * @param d Double value of entry
	 */
	public void put(int index, double d) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e == null) {
			e = new DynamicDataEntry();
			data.set(index, e);
		}
		e.bytesValue = Bytes.get(d);
		e.objectValue = null;
		e.valueClass = double.class;
	}
	
	/** Sets a string value for an entry.
	 * 
	 * @param index Index
	 * @param str String value of entry
	 */
	public void put(int index, String str) {
		if (str != null) {
			DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
			if (e == null) {
				e = new DynamicDataEntry();
				data.set(index, e);
			}
			e.bytesValue = str.getBytes(StandardCharsets.UTF_8);
			e.objectValue = null;
			e.valueClass = String.class;
		}
	}
	
	/** Gets the raw byte value of an entry. If the entry does not exist, a zero-length
	 * array is returned.
	 * 
	 * @param index Index
	 * @return Raw byte value of entry
	 */
	public byte[] getBytes(int index) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e == null) return new byte[0];
		return e.bytesValue;
	}
	
	/** Attempts to get the {@link ZEByteable} value of an entry. If the ZEByteable value cannot
	 * be constructed or the entry is not a ZEByteable value, null is returned.
	 * 
	 * @param index Index
	 * @return ZEByteable value
	 */
	public ZEByteable get(int index) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e != null) {
			if (e.objectValue == null) {
				if (!ZEByteable.class.isAssignableFrom(e.valueClass)) return null;
				try {
					@SuppressWarnings("unchecked")
					Constructor<ZEByteable> ctor = (Constructor<ZEByteable>) e.valueClass.getConstructor(byte[].class);
					e.objectValue = ctor.newInstance(e.bytesValue);
				} catch (Exception ex) {}
			}
			return e.objectValue;
		}
		return null;
	}
	
	/** Attempts to get a ZEByteable value of an entry, converting it to a byte array, then to the given type
	 * if it cannot be directly assigned to the given type.
	 * 
	 * @param index Index
	 * @param clazz Class of value to get
	 * @return Value, or null
	 * @throws RuntimeException If an exception occurs in conversion
	 */
	public <T extends ZEByteable> T get(int index, Class<T> clazz) throws RuntimeException {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e != null) {
			if (clazz.isAssignableFrom(e.valueClass) && e.objectValue != null) {
				@SuppressWarnings("unchecked")
				T t = (T)e.objectValue;
				return t;
			} else {
				try {
					Constructor<T> c = clazz.getConstructor(byte[].class);
					T t = c.newInstance(e.bytesValue);
					e.objectValue = t;
					e.valueClass = clazz;
					return t;
				} catch (Exception ex) {
					throw new RuntimeException("Failed to convert bytes to class \"" + clazz.getName() + '\"', ex);
				}
			}
		} return null;
	}
	
	/** Gets a byte value from the list. If no entry is found, 0 is returned.
	 * 
	 * @param index Index
	 * @return Byte value
	 */
	public byte getByte(int index) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e != null) return e.bytesValue[0];
		return 0;
	}
	
	/** Gets a short value from the list. If no entry is found, 0 is returned.
	 * 
	 * @param index Index
	 * @return Short value
	 */
	public short getShort(int index) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e != null) return Bytes.toShort(e.bytesValue);
		return 0;
	}
	
	/** Gets an int value from the list. If no entry is found, 0 is returned.
	 * 
	 * @param index Index
	 * @return Int value
	 */
	public int getInt(int index) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e != null) return Bytes.toInt(e.bytesValue);
		return 0;
	}
	
	/** Gets a long value from the list. If no entry is found, 0 is returned.
	 * 
	 * @param index Index
	 * @return Long value
	 */
	public long getLong(int index) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e != null) return Bytes.toLong(e.bytesValue);
		return 0;
	}
	
	/** Gets a float value from the list. If no entry is found, 0 is returned.
	 * 
	 * @param index Index
	 * @return Float value
	 */
	public float getFloat(int index) {
		return Float.intBitsToFloat(getInt(index));
	}
	
	/** Gets a double value from the list. If no entry is found, 0 is returned.
	 * 
	 * @param index Index
	 * @return Double value
	 */
	public double getDouble(int index) {
		return Double.longBitsToDouble(getLong(index));
	}
	
	/** Gets a string value from the list. If no entry is found, <b>null</b> is returned.
	 * 
	 * @param index Index
	 * @return String value
	 */
	public String getString(int index) {
		DynamicDataEntry e = (index < 0 || index >= data.size()) ? null : data.get(index);
		if (e != null) return new String(e.bytesValue, StandardCharsets.UTF_8);
		return null;
	}
	
	/** Gets the data type of a value in the list. If no entry is found, <b>null</b> is returned.
	 * 
	 * @param index Index of value
	 * @return Value class
	 */
	public Class<?> getDataType(int index) {
		if (index < 0 || index >= data.size()) return null;
		DynamicDataEntry e = data.get(index);
		if (e != null) return e.valueClass;
		return null;
	}

	@Override
	public void rawRead(DataInput input) throws IOException {
		int length = input.readInt();
		data.clear();
		data.ensureCapacity(length);
		for(int i = 0; i < length; i++) {
			String className = input.readUTF();
			int bytesLength = input.readInt();
			if (bytesLength <= 0) {
				data.set(i, null);
			} else {
				byte[] bytes = new byte[bytesLength];
				input.readFully(bytes);
				
				DynamicDataEntry entry = new DynamicDataEntry();
				entry.bytesValue = bytes;
				try {
					entry.valueClass = Class.forName(className);
				} catch (ClassNotFoundException e) {
					entry.valueClass = byte[].class;
				}
				data.set(i, entry);
			}
		}
	}

	@Override
	public void rawWrite(DataOutput output) throws IOException {
		int length = data.size();
		output.writeInt(length);
		for(int i = 0; i < length; i++) {
			DynamicDataEntry de = data.get(i);
			if (de != null) {
				output.writeInt(de.bytesValue.length);
				output.write(de.bytesValue);
				output.writeUTF(de.valueClass.getName());
			} else output.writeInt(0);
		}
	}

	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// There should not be an IOException...
		try { rawWrite(new DataOutputStream(baos)); } catch (IOException e) {}
		return baos.toByteArray();
	}
	
	/** Clears the data map.
	 * 
	 */
	public void clear() {
		data.clear();
	}
	
	/** Tests if the data list is empty.
	 * 
	 * @return If the list is empty
	 */
	public boolean isEmpty() {
		return data.isEmpty();
	}
	
	/** Gets the size of the data list.
	 * 
	 * @return List size
	 */
	public int size() {
		return data.size();
	}
	
	/** Tests if this dynamic data is equal to another set of dynamic data.
	 * 
	 * @param data Other data
	 * @return If the data is equal to this
	 */
	public boolean equals(DynList data) {
		if (data == null) return false;
		if (data.data.size() != this.data.size()) return false;
		return true;
	}
	
	public boolean equals(Object o) {
		if (o instanceof DynList) {
			return equals((DynList)o);
		} else return false;
	}
	
	public DynList clone() {
		DynList newdata = new DynList();
		data.forEach((DynamicDataEntry entry) ->{
			DynamicDataEntry newEntry = new DynamicDataEntry();
			newEntry.bytesValue = entry.bytesValue;
			newEntry.objectValue = entry.objectValue;
			newEntry.valueClass = entry.valueClass;
			newdata.data.add(newEntry);
		});
		return newdata;
	}

}
