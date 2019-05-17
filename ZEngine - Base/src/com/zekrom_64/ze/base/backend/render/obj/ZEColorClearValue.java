package com.zekrom_64.ze.base.backend.render.obj;

import java.nio.ByteBuffer;
import java.util.function.LongConsumer;

import com.zekrom_64.mathlib.tuple.impl.Vector2F;
import com.zekrom_64.mathlib.tuple.impl.Vector2I;
import com.zekrom_64.mathlib.tuple.impl.Vector3F;
import com.zekrom_64.mathlib.tuple.impl.Vector3I;
import com.zekrom_64.mathlib.tuple.impl.Vector4F;
import com.zekrom_64.mathlib.tuple.impl.Vector4I;
import com.zekrom_64.ze.base.image.ZEPixelFormat;

/** A color clear value stores the clear value for the color aspect of a texture.
 * 
 * @author Zekrom_64
 *
 */
public class ZEColorClearValue {
	
	private int r,g,b,a;
	
	public int getIntR() {
		return r;
	}
	
	public int getIntG() {
		return g;
	}
	
	public int getIntB() {
		return b;
	}
	
	public int getIntA() {
		return a;
	}
	
	public float getFloatR() {
		return Float.intBitsToFloat(r);
	}
	
	public float getFloatG() {
		return Float.intBitsToFloat(g);
	}
	
	public float getFloatB() {
		return Float.intBitsToFloat(b);
	}
	
	public float getFloatA() {
		return Float.intBitsToFloat(a);
	}
	
	public ZEColorClearValue setInt(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		return this;
	}
	
	public ZEColorClearValue setInt(Vector4I v) {
		return setInt(v.x,v.y,v.z,v.w);
	}
	
	public ZEColorClearValue getInt(Vector4I v) {
		v.x = r;
		v.y = g;
		v.z = b;
		v.w = a;
		return this;
	}
	
	public ZEColorClearValue setInt(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		return this;
	}
	
	public ZEColorClearValue setInt(Vector3I v) {
		return setInt(v.x,v.y,v.z);
	}
	
	public ZEColorClearValue getInt(Vector3I v) {
		v.x = r;
		v.y = g;
		v.z = b;
		return this;
	}
	
	public ZEColorClearValue setInt(int r, int g) {
		this.r = r;
		this.g = g;
		return this;
	}
	
	public ZEColorClearValue setInt(Vector2I v) {
		return setInt(v.x,v.y);
	}
	
	public ZEColorClearValue getInt(Vector2I v) {
		v.x = r;
		v.y = g;
		return this;
	}
	
	public ZEColorClearValue setInt(int r) {
		this.r = r;
		return this;
	}
	
	public ZEColorClearValue setFloat(float r, float g, float b, float a) {
		this.r = Float.floatToRawIntBits(r);
		this.g = Float.floatToRawIntBits(g);
		this.b = Float.floatToRawIntBits(b);
		this.a = Float.floatToRawIntBits(a);
		return this;
	}
	
	public ZEColorClearValue setFloat(Vector4F v) {
		return setFloat(v.x,v.y,v.z,v.w);
	}
	
	public ZEColorClearValue getFloat(Vector4F v) {
		v.x = Float.intBitsToFloat(r);
		v.y = Float.intBitsToFloat(g);
		v.z = Float.intBitsToFloat(b);
		v.w = Float.intBitsToFloat(a);
		return this;
	}
	
	public ZEColorClearValue setFloat(float r, float g, float b) {
		this.r = Float.floatToRawIntBits(r);
		this.g = Float.floatToRawIntBits(g);
		this.b = Float.floatToRawIntBits(b);
		return this;
	}
	
	public ZEColorClearValue setFloat(Vector3F v) {
		return setFloat(v.x,v.y,v.z);
	}
	
	public ZEColorClearValue getFloat(Vector3F v) {
		v.x = Float.intBitsToFloat(r);
		v.y = Float.intBitsToFloat(g);
		v.z = Float.intBitsToFloat(b);
		return this;
	}
	
	public ZEColorClearValue setFloat(float r, float g) {
		this.r = Float.floatToRawIntBits(r);
		this.g = Float.floatToRawIntBits(g);
		return this;
	}
	
	public ZEColorClearValue setFloat(Vector2F v) {
		return setFloat(v.x,v.y);
	}
	
	public ZEColorClearValue getFloat(Vector2F v) {
		v.x = Float.intBitsToFloat(r);
		v.y = Float.intBitsToFloat(g);
		return this;
	}
	
	public ZEColorClearValue setFloat(float r) {
		this.r = Float.floatToRawIntBits(r);
		return this;
	}
	
	public ZEColorClearValue write(ZEPixelFormat format, ByteBuffer buf) {
		long bitsR, bitsG, bitsB, bitsA;
		LongConsumer elementWriter;
		switch(format.elementType) {
		case BYTE:
		case UBYTE:
			bitsR = r & 0xFF;
			bitsG = g & 0xFF;
			bitsB = b & 0xFF;
			bitsA = a & 0xFF;
			elementWriter = (long l) -> buf.put((byte)l);
			break;
		case SHORT:
		case USHORT:
			bitsR = r & 0xFFFF;
			bitsG = g & 0xFFFF;
			bitsB = b & 0xFFFF;
			bitsA = a & 0xFFFF;
			elementWriter = (long l) -> buf.putShort((short)l);
			break;
		case INT:
		case UINT:
		case ULONG:
		case FLOAT:
			bitsR = r & 0xFFFFFFFFL;
			bitsG = g & 0xFFFFFFFFL;
			bitsB = b & 0xFFFFFFFFL;
			bitsA = a & 0xFFFFFFFFL;
			elementWriter = (long l) -> buf.putInt((int)l);
			break;
		case LONG:
			bitsR = r;
			bitsG = g;
			bitsB = b;
			bitsA = a;
			elementWriter = buf::putLong;
			break;
		case DOUBLE:
			bitsR = Double.doubleToRawLongBits(Float.intBitsToFloat(r));
			bitsG = Double.doubleToRawLongBits(Float.intBitsToFloat(g));
			bitsB = Double.doubleToRawLongBits(Float.intBitsToFloat(b));
			bitsA = Double.doubleToRawLongBits(Float.intBitsToFloat(a));
			elementWriter = buf::putLong;
			break;
		default: throw new IllegalArgumentException("Pixel format defines unsupported element type");
		}
		
		int pos = buf.position();
		if (format.redOffset >= 0) {
			buf.position(format.redOffset / 8);
			elementWriter.accept(bitsR);
		}
		if (format.greenOffset >= 0) {
			buf.position(format.greenOffset / 8);
			elementWriter.accept(bitsG);
		}
		if (format.blueOffset >= 0) {
			buf.position(format.blueOffset / 8);
			elementWriter.accept(bitsB);
		}
		if (format.alphaOffset >= 0) {
			buf.position(format.alphaOffset / 8);
			elementWriter.accept(bitsA);
		}
		buf.position(pos);
		
		return this;
	}
	
}