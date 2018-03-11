package com.zekrom_64.ze.nat.lin;

import org.bridj.DynamicFunction;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

/**
 * Extensions need a way to hang private data on some structures.
 */
public class XExtData extends ZEStruct<XExtData> {

	public static class free_private extends DynamicFunction<Integer> { }
	
	/** number returned by XRegisterExtension */
	@Field(0)
	public int number() {
		return io.getIntField(this, 0);
	}
	
	/** number returned by XRegisterExtension */
	@Field(0)
	public void number(int number) {
		io.setIntField(this, 0, number);
	}
	
	/** next item on list of data for structure */
	@Field(1)
	public Pointer<XExtData> next() {
		return io.getPointerField(this, 1);
	}
	
	/** next item on list of data for structure */
	@Field(1)
	public void next(Pointer<XExtData> next) {
		io.setPointerField(this, 1, next);
	}
	
	/** called to free private storage */
	@Field(2)
	public Pointer<free_private> free_private() {
		return io.getPointerField(this, 2);
	}
	
	/** called to free private storage */
	@Field(2)
	public void free_private(Pointer<free_private> free_private) {
		io.setPointerField(this, 2, free_private);
	}
	
	/** data private to this extension. */
	@Field(3)
	public Pointer<Byte> private_data() {
		return io.getPointerField(this, 3);
	}
	
	/** data private to this extension. */
	@Field(3)
	public void private_data(Pointer<Byte> private_data) {
		io.setPointerField(this, 3, private_data);
	}
	
}
