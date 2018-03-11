package com.zekrom_64.ze.nat;

import org.bridj.BridJRuntime.TypeInfo;
import org.bridj.Pointer;
import org.bridj.StructIO;
import org.bridj.StructObject;

public class ZEStruct<T extends ZEStruct<?>> extends StructObject implements ZENativeMemory {
	
	public ZEStruct() {}
	
	public ZEStruct(Pointer<T> ptr) {
		super(ptr);
	}

	/** Gets the structure's IO information, giving access to specific fields.
	 * 
	 * @return Structure IO
	 */
	public StructIO getIO() {
		return io;
	}
	
	/** Gets a pointer to the structure's native peer.
	 * 
	 * @return Structure peer
	 */
	@SuppressWarnings("unchecked")
	public Pointer<T> getPointer() {
		return (Pointer<T>)peer;
	}
	
	/** Returns the size of the structure, in bytes.
	 * 
	 * @return Size of structure
	 */
	public long sizeof() {
		return typeInfo.sizeOf();
	}
	
	/** Returns the type information for this structure's type.
	 * 
	 * @return Structure type information
	 */
	@SuppressWarnings("unchecked")
	public TypeInfo<T> getTypeInfo() {
		return typeInfo;
	}

	@Override
	public long getAddress() {
		return peer.getPeer();
	}
	
}
