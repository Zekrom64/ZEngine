package com.zekrom_64.ze.jackson;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.zekrom_64.ze.base.io.formats.ZEJSONException;
import com.zekrom_64.ze.base.io.formats.ZEJSONLibrary;

/** JSON library implemented by the Jackson JSON library.
 * 
 * @author Zekrom_64
 *
 */
public class JacksonJSONLibrary implements ZEJSONLibrary {

	public final JacksonJSONValue NULL = new JacksonJSONValue();
	public final JacksonJSONValue TRUE = new JacksonJSONValue(Boolean.TRUE);
	public final JacksonJSONValue FALSE = new JacksonJSONValue(Boolean.FALSE);
	
	public class JacksonJSONValue implements ZEJSONValue {

		public final Number number;
		public final String string;
		public final Boolean bool;
		
		private JacksonJSONValue() {
			number = null;
			string = null;
			bool = null;
		}
		
		public JacksonJSONValue(Number num) {
			number = num;
			string = null;
			bool = null;
		}
		
		public JacksonJSONValue(String str) {
			number = null;
			string = str;
			bool = null;
		}
		
		public JacksonJSONValue(Boolean b) {
			number = null;
			string = null;
			bool = b;
		}
		
		@Override
		public ZEJSONLibrary getLibrary() {
			return JacksonJSONLibrary.this;
		}

		@Override
		public boolean isNull() {
			return this == NULL;
		}

		@Override
		public boolean isNumber() {
			return number != null;
		}

		@Override
		public boolean isString() {
			return string != null;
		}

		@Override
		public boolean isBoolean() {
			return bool != null;
		}

		@Override
		public Number asNumber() {
			if (number == null) throw new ZEJSONException("JSON value is not a number");
			return number;
		}

		@Override
		public String asString() {
			if (string == null) throw new ZEJSONException("JSON value is not a string");
			return string;
		}

		@Override
		public boolean asBoolean() {
			if (bool == null) throw new ZEJSONException("JSON value is not a boolean");
			return bool;
		}

		@Override
		public ZEJSONArray asArray() {
			throw new ZEJSONException("JSON value is not an array");
		}

		@Override
		public ZEJSONObject asObject() {
			throw new ZEJSONException("JSON value is not an object");
		}

		@Override
		public boolean isArray() {
			return false;
		}

		@Override
		public boolean isObject() {
			return false;
		}
		
	}
	
	@SuppressWarnings("serial")
	public class JacksonJSONArray extends ArrayList<ZEJSONValue> implements ZEJSONArray {

		public JacksonJSONArray() {}
		
		public JacksonJSONArray(Collection<ZEJSONValue> vals) {
			super(vals);
		}
		
		@Override
		public ZEJSONLibrary getLibrary() {
			return JacksonJSONLibrary.this;
		}

		@Override
		public boolean isNull() {
			return false;
		}

		@Override
		public boolean isNumber() {
			return false;
		}

		@Override
		public boolean isString() {
			return false;
		}

		@Override
		public boolean isBoolean() {
			return false;
		}

		@Override
		public boolean isArray() {
			return true;
		}

		@Override
		public boolean isObject() {
			return false;
		}

		@Override
		public Number asNumber() {
			throw new ZEJSONException("JSON value is not a number");
		}

		@Override
		public String asString() {
			throw new ZEJSONException("JSON value is not a string");
		}

		@Override
		public boolean asBoolean() {
			throw new ZEJSONException("JSON value is not a boolean");
		}

		@Override
		public ZEJSONArray asArray() {
			return this;
		}

		@Override
		public ZEJSONObject asObject() {
			throw new ZEJSONException("JSON value is not an object");
		}

		@Override
		public Number getNumber(int index) {
			return get(index).asNumber();
		}

		@Override
		public String getString(int index) {
			return get(index).asString();
		}

		@Override
		public boolean getBoolean(int index) {
			return get(index).asBoolean();
		}

		@Override
		public ZEJSONArray getArray(int index) {
			return get(index).asArray();
		}

		@Override
		public ZEJSONObject getObject(int index) {
			return get(index).asObject();
		}
		
	}
	
	@SuppressWarnings("serial")
	public class JacksonJSONObject extends HashMap<String,ZEJSONValue> implements ZEJSONObject {

		public JacksonJSONObject() {}
		
		public JacksonJSONObject(Map<String,ZEJSONValue> map) {
			super(map);
		}
		
		@Override
		public ZEJSONLibrary getLibrary() {
			return JacksonJSONLibrary.this;
		}

		@Override
		public boolean isNull() {
			return false;
		}

		@Override
		public boolean isNumber() {
			return false;
		}

		@Override
		public boolean isString() {
			return false;
		}

		@Override
		public boolean isBoolean() {
			return false;
		}

		@Override
		public boolean isArray() {
			return false;
		}

		@Override
		public boolean isObject() {
			return true;
		}

		@Override
		public Number asNumber() {
			throw new ZEJSONException("JSON value is not a number");
		}

		@Override
		public String asString() {
			throw new ZEJSONException("JSON value is not a string");
		}

		@Override
		public boolean asBoolean() {
			throw new ZEJSONException("JSON value is not a boolean");
		}

		@Override
		public ZEJSONArray asArray() {
			throw new ZEJSONException("JSON value is not an array");
		}

		@Override
		public ZEJSONObject asObject() {
			return this;
		}

		@Override
		public Number getNumber(String field) {
			return get(field).asNumber();
		}

		@Override
		public String getString(String field) {
			return get(field).asString();
		}

		@Override
		public boolean getBoolean(String field) {
			return get(field).asBoolean();
		}

		@Override
		public ZEJSONArray getArray(String field) {
			return get(field).asArray();
		}

		@Override
		public ZEJSONObject getObject(String field) {
			return get(field).asObject();
		}
		
	}
	
	private boolean flagPrettyPrint = false;
	
	@Override
	public void setFlag(String flag, boolean value) {
		switch(flag) {
		case ZEJSONLibrary.FLAG_PRETTY_SERIALIZE: flagPrettyPrint = true; break;
		}
	}
	
	private JacksonJSONArray parseArray(JsonParser jp) throws IOException {
		JsonToken jt = null;
		JacksonJSONArray jja = new JacksonJSONArray();
		while((jt = jp.nextToken()) != null) {
			switch(jt) {
			case START_OBJECT:
				jja.add(parseObject(jp));
				break;
			case END_ARRAY:
				return jja;
			case END_OBJECT: throw new ZEJSONException("Unexpected end of object parsing array");
			case FIELD_NAME: throw new ZEJSONException("Unexpected field name parsing array");
			case NOT_AVAILABLE: break;
			case START_ARRAY:
				jja.add(parseArray(jp));
				break;
			case VALUE_EMBEDDED_OBJECT: throw new ZEJSONException("Parser does not support embedded objects");
			case VALUE_FALSE:
				jja.add(valueOf(false));
				break;
			case VALUE_NULL:
				jja.add(nullValue());
				break;
			case VALUE_NUMBER_FLOAT:
			case VALUE_NUMBER_INT:
				jja.add(valueOf(jp.getNumberValue()));
				break;
			case VALUE_STRING:
				jja.add(valueOf(jp.getValueAsString()));
				break;
			case VALUE_TRUE:
				jja.add(valueOf(true));
				break;
			}
		}
		throw new ZEJSONException("Unexpected end of input");
	}
	
	private JacksonJSONObject parseObject(JsonParser jp) throws IOException {
		JsonToken jt = null;
		String field = null;
		JacksonJSONObject jjo = new JacksonJSONObject();
		while((jt = jp.nextToken()) != null) {
			switch(jt) {
			case START_OBJECT:
				if (field == null) throw new ZEJSONException("Unexpected object value parsing object");
				jjo.put(field, parseObject(jp));
				field = null;
				break;
			case END_ARRAY: throw new ZEJSONException("Unexpected end of array parsing object");
			case END_OBJECT:
				return jjo;
			case FIELD_NAME:
				field = jp.getCurrentName();
				break;
			case NOT_AVAILABLE: break;
			case START_ARRAY:
				if (field == null) throw new ZEJSONException("Unexpected array value parsing object");
				jjo.put(field, parseArray(jp));
				field = null;
				break;
			case VALUE_EMBEDDED_OBJECT: throw new ZEJSONException("Parser does not support embedded objects");
			case VALUE_FALSE:
				if (field == null) throw new ZEJSONException("Unexpected boolean value parsing object");
				jjo.put(field, valueOf(false));
				field = null;
				break;
			case VALUE_NULL:
				if (field == null) throw new ZEJSONException("Unexpected null value parsing object");
				jjo.put(field, nullValue());
				field = null;
				break;
			case VALUE_NUMBER_FLOAT:
			case VALUE_NUMBER_INT:
				if (field == null) throw new ZEJSONException("Unexpected array value parsing object");
				jjo.put(field, valueOf(jp.getNumberValue()));
				field = null;
				break;
			case VALUE_STRING:
				if (field == null) throw new ZEJSONException("Unexpected string value parsing object");
				jjo.put(field, valueOf(jp.getValueAsString()));
				field = null;
				break;
			case VALUE_TRUE:
				if (field == null) throw new ZEJSONException("Unexpected boolean value parsing object");
				jjo.put(field, valueOf(true));
				field = null;
				break;
			}
		}
		throw new ZEJSONException("Unexpected end of input");
	}

	@Override
	public ZEJSONObject read(Reader read) throws IOException {
		JsonFactory jf = new JsonFactory();
		JsonParser jp = jf.createParser(read);
		return parseObject(jp);
	}

	private void generateValue(JsonGenerator jg, ZEJSONValue v) throws IOException {
		if (v.isNull()) jg.writeNull();
		else if (v.isBoolean()) jg.writeBoolean(v.asBoolean());
		else if (v.isNumber()) {
			Number n = v.asNumber();
			if (n instanceof BigDecimal) jg.writeNumber((BigDecimal)n);
			else if (n instanceof BigInteger) jg.writeNumber((BigInteger)n);
			else jg.writeNumber(n.doubleValue());
		} else if (v.isString()) jg.writeString(v.asString());
		else if (v.isArray()) generateArray(jg, v.asArray());
		else if (v.isObject()) generateObject(jg, v.asObject());
		else throw new ZEJSONException("Attempted to generate JSON for JSON value of unknown type");
	}
	
	private void generateArray(JsonGenerator jg, ZEJSONArray a) throws IOException {
		jg.writeStartArray();
		for(ZEJSONValue val : a) {
			generateValue(jg, val);
		}
		jg.writeEndArray();
	}
	
	private void generateObject(JsonGenerator jg, ZEJSONObject o) throws IOException {
		jg.writeStartObject();
		for(Entry<String,ZEJSONValue> entry : o.entrySet()) {
			jg.writeFieldName(entry.getKey());
			generateValue(jg, entry.getValue());
		}
		jg.writeEndObject();
	}
	
	@Override
	public void write(ZEJSONObject json, Writer write) throws IOException {
		JsonFactory jf = new JsonFactory();
		JsonGenerator jg = jf.createGenerator(write);
		if (flagPrettyPrint) jg.setPrettyPrinter(new DefaultPrettyPrinter());
		generateObject(jg, json);
	}

	@Override
	public ZEJSONValue nullValue() {
		return NULL;
	}

	@Override
	public ZEJSONValue valueOf(Number num) {
		return new JacksonJSONValue(num);
	}

	@Override
	public ZEJSONValue valueOf(String str) {
		return new JacksonJSONValue(str);
	}

	@Override
	public ZEJSONValue valueOf(boolean bool) {
		return bool ? TRUE : FALSE;
	}

	@Override
	public ZEJSONArray newArray() {
		return new JacksonJSONArray();
	}

	@Override
	public ZEJSONArray valueOf(Collection<ZEJSONValue> values) {
		return new JacksonJSONArray(values);
	}

	@Override
	public ZEJSONObject newObject() {
		return new JacksonJSONObject();
	}

	@Override
	public ZEJSONObject valueOf(Map<String, ZEJSONValue> map) {
		return new JacksonJSONObject(map);
	}

}
