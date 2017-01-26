package com.zekrom_64.ze.base.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/** A dynamic data map is a utility object for storing changing game data in key-value pairs.
 * 
 * @author Zekrom_64
 *
 */
public class DynamicDataMap implements ZEByteable, ZERawSaveable {

	private class DynamicDataEntry {
		
		public ZEByteable objectValue;
		public byte[] bytesValue;
		public Class<?> valueClass;
		
	}
	
	private Map<String,DynamicDataEntry> data = new HashMap<>();
	
	/** Creates a new empty data map.
	 * 
	 */
	public DynamicDataMap() {}
	
	/** Creates a new data map from a byte source. This is the same as creating a new
	 * instance and calling {@link #rawRead(DataInput) rawRead()} using the byte array. 
	 * 
	 * @param bytes Byte source
	 * @throws IOException If an IOException occurs
	 */
	public DynamicDataMap(byte[] bytes) throws IOException {
		rawRead(new DataInputStream(new ByteArrayInputStream(bytes)));
	}
	
	/** Removes an entry from the data map.
	 * 
	 * @param key Key of entry to remove
	 */
	public void remove(String key) {
		data.remove(data);
	}
	
	/** Sets a byte array value for an entry.
	 * 
	 * @param key Key
	 * @param bytes Byte value of entry
	 */
	public void put(String key, byte[] bytes) {
		if (bytes != null && key != null) {
			DynamicDataEntry e = data.get(key);
			if (e == null) {
				e = new DynamicDataEntry();
				data.put(key, e);
			}
			e.bytesValue = bytes;
			e.objectValue = null;
			e.valueClass = byte[].class;
		}
	}
	
	/** Sets a byte value for an entry.
	 * 
	 * @param key Key
	 * @param byteable Byte value of entry
	 */
	public void put(String key, ZEByteable byteable) {
		if (key != null && byteable != null) {
			DynamicDataEntry e = data.get(key);
			if (e == null) {
				e = new DynamicDataEntry();
				data.put(key, e);
			}
			e.bytesValue = byteable.toBytes();
			e.objectValue = byteable;
			e.valueClass = byteable.getClass();
		}
	}
	
	/** Sets a byte value for an entry.
	 * 
	 * @param key Key
	 * @param b Byte value of entry
	 */
	public void put(String key, byte b) {
		if (key != null) {
			DynamicDataEntry e = data.get(key);
			if (e == null) {
				e = new DynamicDataEntry();
				data.put(key, e);
			}
			e.bytesValue = new byte[] {b};
			e.objectValue = null;
			e.valueClass = byte.class;
		}
	}
	
	/** Sets a short value for an entry.
	 * 
	 * @param key Key
	 * @param s Short value of entry
	 */
	public void put(String key, short s) {
		if (key != null) {
			DynamicDataEntry e = data.get(key);
			if (e == null) {
				e = new DynamicDataEntry();
				data.put(key, e);
			}
			e.bytesValue = Bytes.get(s);
			e.objectValue = null;
			e.valueClass = short.class;
		}
	}
	
	/** Sets an int value for an entry.
	 * 
	 * @param key Key
	 * @param i Int value of entry
	 */
	public void put(String key, int i) {
		if (key != null) {
			DynamicDataEntry e = data.get(key);
			if (e == null) {
				e = new DynamicDataEntry();
				data.put(key, e);
			}
			e.bytesValue = Bytes.get(i);
			e.objectValue = null;
			e.valueClass = int.class;
		}
	}
	
	/** Sets a long value for an entry.
	 * 
	 * @param key Key
	 * @param l Long value of entry
	 */
	public void put(String key, long l) {
		if (key != null) {
			DynamicDataEntry e = data.get(key);
			if (e == null) {
				e = new DynamicDataEntry();
				data.put(key, e);
			}
			e.bytesValue = Bytes.get(l);
			e.objectValue = null;
			e.valueClass = long.class;
		}
	}
	
	/** Sets a float value for an entry.
	 * 
	 * @param key Key
	 * @param f Float value of entry
	 */
	public void put(String key, float f) {
		if (key != null) {
			DynamicDataEntry e = data.get(key);
			if (e == null) {
				e = new DynamicDataEntry();
				data.put(key, e);
			}
			e.bytesValue = Bytes.get(f);
			e.objectValue = null;
			e.valueClass = float.class;
		}
	}
	
	/** Sets a double value for an entry.
	 * 
	 * @param key Key
	 * @param d Double value of entry
	 */
	public void put(String key, double d) {
		if (key != null) {
			DynamicDataEntry e = data.get(key);
			if (e == null) {
				e = new DynamicDataEntry();
				data.put(key, e);
			}
			e.bytesValue = Bytes.get(d);
			e.objectValue = null;
			e.valueClass = double.class;
		}
	}
	
	/** Gets the raw byte value of an entry. If the entry does not exist, a zero-length
	 * array is returned.
	 * 
	 * @param key Key
	 * @return Raw byte value of entry
	 */
	public byte[] getBytes(String key) {
		DynamicDataEntry e = data.get(key);
		if (e == null) return new byte[0];
		return e.bytesValue;
	}
	
	/** Attempts to get the {@link ZEByteable} value of an entry. If the ZEByteable value cannot
	 * be constructed or the entry is not a ZEByteable value, null is returned.
	 * 
	 * @param key Key
	 * @return ZEByteable value
	 */
	public ZEByteable get(String key) {
		DynamicDataEntry e = data.get(key);
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
	 * @param key Key
	 * @param clazz Class of value to get
	 * @return Value, or null
	 * @throws Exception If an exception occurs in conversion
	 */
	public <T extends ZEByteable> T get(String key, Class<T> clazz) throws Exception {
		DynamicDataEntry e = data.get(key);
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
					throw new Exception("Failed to convert bytes to class \"" + clazz.getName() + '\"', ex);
				}
			}
		} return null;
	}
	
	/** Gets a byte value from the map. If no entry is found, 0 is returned.
	 * 
	 * @param key Key
	 * @return Byte value
	 */
	public byte getByte(String key) {
		DynamicDataEntry e = data.get(key);
		if (e != null) return e.bytesValue[0];
		return 0;
	}
	
	/** Gets a short value from the map. If no entry is found, 0 is returned.
	 * 
	 * @param key Key
	 * @return Short value
	 */
	public short getShort(String key) {
		DynamicDataEntry e = data.get(key);
		if (e != null) return Bytes.toShort(e.bytesValue);
		return 0;
	}
	
	/** Gets an int value from the map. If no entry is found, 0 is returned.
	 * 
	 * @param key Key
	 * @return Int value
	 */
	public int getInt(String key) {
		DynamicDataEntry e = data.get(key);
		if (e != null) return Bytes.toInt(e.bytesValue);
		return 0;
	}
	
	/** Gets a long value from the map. If no entry is found, 0 is returned.
	 * 
	 * @param key Key
	 * @return Long value
	 */
	public long getLong(String key) {
		DynamicDataEntry e = data.get(key);
		if (e != null) return Bytes.toLong(e.bytesValue);
		return 0;
	}
	
	/** Gets a float value from the map. If no entry is found, 0 is returned.
	 * 
	 * @param key Key
	 * @return Float value
	 */
	public float getFloat(String key) {
		return Float.intBitsToFloat(getInt(key));
	}
	
	/** Gets a double value from the map. If no entry is found, 0 is returned.
	 * 
	 * @param key Key
	 * @return Double value
	 */
	public double getDouble(String key) {
		return Double.longBitsToDouble(getLong(key));
	}

	@Override
	public void rawRead(DataInput input) throws IOException {
		synchronized(data) {
			int length = input.readInt();
			Map<String,DynamicDataEntry> tempMap = new HashMap<>();
			for(int i = 0; i < length; i++) {
				String key = input.readUTF();
				String className = input.readUTF();
				int bytesLength = input.readInt();
				byte[] bytes = new byte[bytesLength];
				input.readFully(bytes);
				
				DynamicDataEntry entry = new DynamicDataEntry();
				entry.bytesValue = bytes;
				try {
					entry.valueClass = Class.forName(className);
				} catch (ClassNotFoundException e) {
					entry.valueClass = byte[].class;
				}
				tempMap.put(key, entry);
			}
			data.clear();
			data.putAll(tempMap);
		}
	}

	@Override
	public void rawWrite(DataOutput output) throws IOException {
		synchronized(data) {
			output.writeInt(data.size());
			for(Entry<String,DynamicDataEntry> e : data.entrySet()) {
				output.writeUTF(e.getKey());
				DynamicDataEntry de = e.getValue();
				output.writeUTF(de.valueClass.getName());
				output.writeInt(de.bytesValue.length);
				output.write(de.bytesValue);
			}
		}
	}

	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// There should not be an IOException...
		try { rawWrite(new DataOutputStream(baos)); } catch (IOException e) {}
		return baos.toByteArray();
	}
	
	/** Gets the set of keys for this data map.
	 * 
	 * @return Key set
	 */
	public Set<String> keySet() {
		return data.keySet();
	}
	
	/** Tests if the data map contains the given key.
	 * 
	 * @param key Key
	 * @return If the map contains the key
	 */
	public boolean containsKey(String key) {
		return data.containsKey(key);
	}
	
	/** Clears the data map.
	 * 
	 */
	public void clear() {
		data.clear();
	}
	
	/** Tests if the data map is empty.
	 * 
	 * @return If the map is empty
	 */
	public boolean isEmpty() {
		return data.isEmpty();
	}
	
	/** Gets the size of the data map
	 * 
	 * @return Map size
	 */
	public int size() {
		return data.size();
	}
	
}
