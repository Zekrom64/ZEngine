package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVPacket extends ZEStruct<AVPacket> {

	static {
		BridJ.register();
	}
	
	public AVPacket() {}
	
	public AVPacket(Pointer<AVPacket> p) { super(p); }
	
	@Field(0)
	public Pointer<AVBufferRef> buf() {
		return io.getPointerField(this, 0);
	}
	
	@Field(0)
	public void buf(Pointer<AVBufferRef> buf) {
		io.setPointerField(this, 0, buf);
	}
	
	@Field(1)
	public long pts() {
		return io.getLongField(this, 1);
	}
	
	@Field(1)
	public void pts(long pts) {
		io.setLongField(this, 1, pts);
	}
	
	@Field(2)
	public long dts() {
		return io.getLongField(this, 2);
	}
	
	@Field(2)
	public void dts(long dts) {
		io.setLongField(this, 2, dts);
	}
	
	@Field(3)
	public Pointer<Byte> data() {
		return io.getPointerField(this, 3);
	}
	
	@Field(3)
	public void data(Pointer<Byte> data) {
		io.setPointerField(this, 3, data);
	}
	
	@Field(4)
	public int size() {
		return io.getIntField(this, 4);
	}
	
	@Field(4)
	public void size(int size) {
		io.setIntField(this, 4, size);
	}
	
	@Field(5)
	public int stream_index() {
		return io.getIntField(this, 5);
	}
	
	@Field(5)
	public void stream_index(int stream_index) {
		io.setIntField(this, 5, stream_index);
	}
	
	@Field(6)
	public int flags() {
		return io.getIntField(this, 6);
	}
	
	@Field(6)
	public void flags(int flags) {
		io.setIntField(this, 6, flags);
	}
	
	@Field(7)
	public Pointer<AVPacketSideData> side_data() {
		return io.getPointerField(this, 7);
	}
	
	@Field(7)
	public void side_data(Pointer<AVPacketSideData> side_data) {
		io.setPointerField(this, 7, side_data);
	}
	
	@Field(8)
	public int side_data_elems() {
		return io.getIntField(this, 8);
	}
	
	@Field(8)
	public void side_data_elems(int side_data_elems) {
		io.setIntField(this, 8, side_data_elems);
	}
	
	@Field(9)
	public long duration() {
		return io.getLongField(this, 9);
	}
	
	@Field(9)
	public void duration(long duration) {
		io.setLongField(this, 9, duration);
	}
	
	@Field(10)
	public long pos() {
		return io.getLongField(this, 10);
	}
	
	@Field(10)
	public void pos(long pos) {
		io.setLongField(this, 10, pos);
	}
	
	@Deprecated
	@Field(11)
	public long convergence_duration() {
		return io.getLongField(this, 11);
	}
	
	@Deprecated
	@Field(11)
	public void convergence_duration(long convergence_duration) {
		io.setLongField(this, 11, convergence_duration);
	}
	
}
