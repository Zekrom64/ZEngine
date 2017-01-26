package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVPacketList extends Struct<AVPacketList> {
	
	static {
		BridJ.register();
	}
	
	/** C type : AVPacket* */
	@Field(0) 
	public Pointer<AVPacket > pkt() {
		return this.io.getPointerField(this, 0);
	}
	/** C type : AVPacket* */
	@Field(0) 
	public AVPacketList pkt(Pointer<AVPacket > pkt) {
		this.io.setPointerField(this, 0, pkt);
		return this;
	}
	/** C type : AVPacketList* */
	@Field(1) 
	public Pointer<AVPacketList > next() {
		return this.io.getPointerField(this, 1);
	}
	/** C type : AVPacketList* */
	@Field(1) 
	public AVPacketList next(Pointer<AVPacketList > next) {
		this.io.setPointerField(this, 1, next);
		return this;
	}
	public AVPacketList() {
		super();
	}
	public AVPacketList(Pointer<AVPacketList> pointer) {
		super(pointer);
	}

}
