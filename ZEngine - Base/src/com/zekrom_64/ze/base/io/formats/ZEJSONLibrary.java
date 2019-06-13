package com.zekrom_64.ze.base.io.formats;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/** Interface for a library implementing JSON object and serialization. ZEngine's JSON implementation
 * acts as a wrapper around the library-specific code, providing a generic way of using the JSON
 * library. Each library-specific implementation is compatible with this generic form, its own
 * format can be converted to the generic form and if generic objects are not already in the library's
 * own format they are automatically converted via a deep copy.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEJSONLibrary {
	
	public static interface ZEJSONValue {
		
		public ZEJSONLibrary getLibrary();
		
		public boolean isNull();
		
		public boolean isNumber();
		
		public boolean isString();
		
		public boolean isBoolean();
		
		public boolean isArray();
		
		public boolean isObject();
		
		public Number asNumber();
		
		public String asString();
		
		public boolean asBoolean();
		
		public ZEJSONArray asArray();
		
		public ZEJSONObject asObject();
		
	}
	
	public static interface ZEJSONArray extends List<ZEJSONValue>, ZEJSONValue {
		
		public Number getNumber(int index);
		
		public String getString(int index);
		
		public boolean getBoolean(int index);
		
		public ZEJSONArray getArray(int index);
		
		public ZEJSONObject getObject(int index);
		
		public default Number getNumberOrDefault(int index, Number num) {
			ZEJSONValue val = get(index);
			if (val.isNumber()) return val.asNumber();
			return num;
		}
		
		public default String getStringOrDefault(int index, String str) {
			ZEJSONValue val = get(index);
			if (val.isString()) return val.asString();
			return str;
		}
		
		public default boolean getBooleanOrDefault(int index, boolean bool) {
			ZEJSONValue val = get(index);
			if (val.isBoolean()) return val.asBoolean();
			return bool;
		}
		
		public default ZEJSONArray getAndConsume(int index, Consumer<ZEJSONValue> consumer) {
			consumer.accept(get(index));
			return this;
		}
		
		public default ZEJSONArray getNumberAndConsume(int index, Consumer<Number> consumer, Runnable onDefault) {
			ZEJSONValue val = get(index);
			if (val.isNumber()) consumer.accept(val.asNumber());
			else onDefault.run();
			return this;
		}
		
		public default ZEJSONArray getNumberAndConsume(int index, Consumer<Number> consumer) {
			return getNumberAndConsume(index, consumer, () -> {});
		}
		
		public default ZEJSONArray getStringAndConsume(int index, Consumer<String> consumer, Runnable onDefault) {
			ZEJSONValue val = get(index);
			if (val.isString()) consumer.accept(val.asString());
			else onDefault.run();
			return this;
		}
		
		public default ZEJSONArray getStringAndConsume(int index, Consumer<String> consumer) {
			return getStringAndConsume(index, consumer, () -> {});
		}
		
		public default ZEJSONArray getBooleanAndConsume(int index, Consumer<Boolean> consumer, Runnable onDefault) {
			ZEJSONValue val = get(index);
			if (val.isBoolean()) consumer.accept(Boolean.valueOf(val.asBoolean()));
			else onDefault.run();
			return this;
		}
		
		public default ZEJSONArray getBooleanAndConsume(int index, Consumer<Boolean> consumer) {
			return getBooleanAndConsume(index, consumer, () -> {});
		}
		
		public default ZEJSONArray getArrayAndConsume(int index, Consumer<ZEJSONArray> consumer, Runnable onDefault) {
			ZEJSONValue val = get(index);
			if (val.isArray()) consumer.accept(val.asArray());
			else onDefault.run();
			return this;
		}
		
		public default ZEJSONArray getArrayAndConsume(int index, Consumer<ZEJSONArray> consumer) {
			return getArrayAndConsume(index, consumer, () -> {});
		}
		
		public default ZEJSONArray getObjectAndConsume(int index, Consumer<ZEJSONObject> consumer, Runnable onDefault) {
			ZEJSONValue val = get(index);
			if (val.isObject()) consumer.accept(val.asObject());
			else onDefault.run();
			return this;
		}
		
		public default ZEJSONArray getObjectAndConsume(int index, Consumer<ZEJSONObject> consumer) {
			return getObjectAndConsume(index, consumer, () -> {});
		}
		
	}
	
	public static interface ZEJSONObject extends Map<String,ZEJSONValue>, ZEJSONValue {
		
		public Number getNumber(String field);
		
		public String getString(String field);
		
		public boolean getBoolean(String field);
		
		public ZEJSONArray getArray(String field);
		
		public ZEJSONObject getObject(String field);
		
		public default Number getNumberOrDefault(String index, Number num) {
			ZEJSONValue val = get(index);
			if (val.isNumber()) return val.asNumber();
			return num;
		}
		
		public default String getStringOrDefault(String index, String str) {
			ZEJSONValue val = get(index);
			if (val.isString()) return val.asString();
			return str;
		}
		
		public default boolean getBooleanOrDefault(String index, boolean bool) {
			ZEJSONValue val = get(index);
			if (val.isBoolean()) return val.asBoolean();
			return bool;
		}
		
		public default ZEJSONObject getAndConsume(String index, Consumer<ZEJSONValue> consumer) {
			consumer.accept(get(index));
			return this;
		}
		
		public default ZEJSONObject getNumberAndConsume(String index, Consumer<Number> consumer, Runnable onDefault) {
			ZEJSONValue val = get(index);
			if (val.isNumber()) consumer.accept(val.asNumber());
			else onDefault.run();
			return this;
		}
		
		public default ZEJSONObject getNumberAndConsume(String index, Consumer<Number> consumer) {
			return getNumberAndConsume(index, consumer, () -> {});
		}
		
		public default ZEJSONObject getStringAndConsume(String index, Consumer<String> consumer, Runnable onDefault) {
			ZEJSONValue val = get(index);
			if (val.isString()) consumer.accept(val.asString());
			else onDefault.run();
			return this;
		}
		
		public default ZEJSONObject getStringAndConsume(String index, Consumer<String> consumer) {
			return getStringAndConsume(index, consumer, () -> {});
		}
		
		public default ZEJSONObject getBooleanAndConsume(String index, Consumer<Boolean> consumer, Runnable onDefault) {
			ZEJSONValue val = get(index);
			if (val.isBoolean()) consumer.accept(Boolean.valueOf(val.asBoolean()));
			else onDefault.run();
			return this;
		}
		
		public default ZEJSONObject getBooleanAndConsume(String index, Consumer<Boolean> consumer) {
			return getBooleanAndConsume(index, consumer, () -> {});
		}
		
		public default ZEJSONObject getArrayAndConsume(String index, Consumer<ZEJSONArray> consumer, Runnable onDefault) {
			ZEJSONValue val = get(index);
			if (val.isArray()) consumer.accept(val.asArray());
			else onDefault.run();
			return this;
		}
		
		public default ZEJSONObject getArrayAndConsume(String index, Consumer<ZEJSONArray> consumer) {
			return getArrayAndConsume(index, consumer, () -> {});
		}
		
		public default ZEJSONObject getObjectAndConsume(String index, Consumer<ZEJSONObject> consumer, Runnable onDefault) {
			ZEJSONValue val = get(index);
			if (val.isObject()) consumer.accept(val.asObject());
			else onDefault.run();
			return this;
		}
		
		public default ZEJSONObject getObjectAndConsume(String index, Consumer<ZEJSONObject> consumer) {
			return getObjectAndConsume(index, consumer, () -> {});
		}
		
	}
	
	/** Flag indicating the JSON library should "pretty print" its output, ie.
	 * output a more readable format such as including new lines and tabs.
	 */
	public static final String FLAG_PRETTY_SERIALIZE = "ze.flag.json.pretty_serialize";
	
	/** Sets a flag in the JSON library.
	 * 
	 * @param flag Flag to set
	 * @param value Flag value
	 */
	public void setFlag(String flag, boolean value);
	
	/** Reads a JSON object from the given reader.
	 * 
	 * @param read Reader to read JSON from
	 * @return JSON object
	 * @throws IOException If an IOException occurs
	 */
	public ZEJSONObject read(Reader read) throws IOException;
	
	/** Writes a JSON object to the given writer.
	 * 
	 * @param json JSON to write
	 * @param write Writer to write JSON to
	 * @throws IOException If an IOException occurs
	 */
	public void write(ZEJSONObject json, Writer write) throws IOException;
	
	/** Returns the
	 * 
	 * @return
	 */
	public ZEJSONValue nullValue();
	
	public ZEJSONValue valueOf(Number num);
	
	public ZEJSONValue valueOf(String str);
	
	public ZEJSONValue valueOf(boolean bool);
	
	public ZEJSONArray newArray();
	
	public ZEJSONArray valueOf(Collection<ZEJSONValue> values);
	
	public ZEJSONObject newObject();
	
	public ZEJSONObject valueOf(Map<String,ZEJSONValue> map);
	
}
