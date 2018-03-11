package com.zekrom_64.ze.nat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.lwjgl.BufferUtils;

public class ZEStructUtils {
	
	@FunctionalInterface
	public static interface StructBufferFactory<T> {
		
		public T createStructBuffer(int length);
		
	}
	
	/** Map containing supplier shortcuts for creating structs */
	public static final Map<Class<?>,Supplier<?>> structFactories = new HashMap<>();
	public static final Map<Class<?>,StructBufferFactory<?>> structBufferFactories = new HashMap<>();
	
	/** Attempts to instantiate a LWJGL or BridJ struct. If the instantiation fails, null is returned.
	 * 
	 * @param structClass
	 * @return The instantiated struct, or null
	 */
	@SuppressWarnings("unchecked")
	public static <STRUCT> STRUCT createStruct(Class<? extends org.lwjgl.system.Struct> structClass) {
		Supplier<?> supplier = structFactories.get(structClass);
		if (supplier==null) {
			try {
				final Constructor<? extends org.lwjgl.system.Struct> ctor = structClass.getConstructor();
				supplier = new Supplier<Object>() {
	
					@Override
					public Object get() {
						try {
							return ctor.newInstance();
						} catch (
								InstantiationException | 
								IllegalAccessException | 
								IllegalArgumentException | 
								InvocationTargetException e) {
							return null;
						}
					}
					
				};
				structFactories.put(structClass, supplier);
			} catch (NoSuchMethodException | SecurityException e) {
				return null;
			}
		}
		return (STRUCT) supplier.get();
	}
	
	/** Attempts to instantiate a LWJGL struct buffer. If the instantiation fails, null is returned.
	 * 
	 * @param length Length of the buffer, in structs
	 * @param structClass LWJGL struct class
	 * @return The instantiated struct buffer, or null
	 */
	@SuppressWarnings("unchecked")
	public static <BUF> BUF createStructBuffer(int length, Class<? extends org.lwjgl.system.Struct> structClass) {
		StructBufferFactory<?> factory = structBufferFactories.get(structClass);
		if (factory==null) {
			try {
				Field sizeofField = structClass.getField("SIZEOF");
				final int sizeof = sizeofField.getInt(null);
				Class<?>[] classes = structClass.getClasses();
				Class<?> structBufferClass = null;
				for(Class<?> c : classes)
					if (c.getName().equals("Buffer")&&c.getEnclosingClass()==structClass) structBufferClass = c;
				if (structBufferClass==null) throw new ClassNotFoundException("Failed to find struct Buffer class");
				final Constructor<?> ctor = structBufferClass.getConstructor(ByteBuffer.class);
				factory = new StructBufferFactory<Object>() {

					@Override
					public Object createStructBuffer(int length) {
						ByteBuffer buf = BufferUtils.createByteBuffer(sizeof * length);
						try {
							return ctor.newInstance(buf);
						} catch (
								InstantiationException |
								IllegalAccessException |
								IllegalArgumentException |
								InvocationTargetException e) {
							return null;
						}
					}
					
				};
				structBufferFactories.put(structClass, factory);
			} catch (
					ClassNotFoundException |
					NoSuchMethodException |
					NoSuchFieldException |
					IllegalAccessException e) {
				return null;
			}
		}
		return (BUF)factory.createStructBuffer(length);
	}
	
}
