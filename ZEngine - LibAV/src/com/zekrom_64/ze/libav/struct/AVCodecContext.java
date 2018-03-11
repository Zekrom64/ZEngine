package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVCodecID;
import com.zekrom_64.ze.libav.enums.AVMediaType;
import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVCodecContext extends ZEStruct<AVCodecContext> {

	static {
		BridJ.register();
	}
	
	public AVCodecContext() {}
	
	public AVCodecContext(Pointer<AVCodecContext> p) { super(p); }
	
	@Field(0)
	public Pointer<AVClass> av_class() {
		return io.getPointerField(this, 0);
	}
	
	@Field(1)
	public int log_level_offset() {
		return io.getIntField(this, 1);
	}
	
	@Field(1)
	public void log_level_offset(int log_level_offset) {
		io.setIntField(this, 1, log_level_offset);
	}
	
	@Field(2)
	public AVMediaType codec_type() {
		IntValuedEnum<AVMediaType> e = io.getEnumField(this, 2);
		return (AVMediaType)e;
	}
	
	@Field(2)
	public void codec_type(AVMediaType codec_type) {
		io.setEnumField(this, 2, codec_type);
	}
	
	@Field(3)
	public Pointer<AVCodec> codec() {
		return io.getPointerField(this, 3);
	}
	
	@Field(3)
	public void codec(Pointer<AVCodec> codec) {
		io.setPointerField(this, 3, codec);
	}
	
	@Deprecated
	@Array(32)
	@Field(4)
	public Pointer<Byte> codec_name() {
		return io.getPointerField(this, 4);
	}
	
	@Field(5)
	public AVCodecID codec_id() {
		IntValuedEnum<AVCodecID> e = io.getEnumField(this, 5);
		return (AVCodecID)e;
	}
	
	@Field(5)
	public void codec_id(AVCodecID codec_id) {
		io.setEnumField(this, 5, codec_id);
	}
	
	@Field(6)
	public int codec_tag() {
		return io.getIntField(this, 6);
	}
	
	@Field(6)
	public void codec_tag(int codec_tag) {
		io.setIntField(this, 6, codec_tag);
	}
	
	@Deprecated
	@Field(7)
	public int stream_codec_tag() {
		return io.getIntField(this, 7);
	}
	
	@Deprecated
	@Field(7)
	public void stream_codec_tag(int stream_codec_tag) {
		io.setIntField(this, 7, stream_codec_tag);
	}
	
	@Field(8)
	public Pointer<Void> priv_data() {
		return io.getPointerField(this, 8);
	}
	
	@Field(8)
	public void priv_data(Pointer<Void> priv_data) {
		io.setPointerField(this, 8, priv_data);
	}
	
	@SuppressWarnings("rawtypes")
	@Field(9)
	public Pointer internal() {
		return io.getPointerField(this, 9);
	}
	
	@Field(10)
	public Pointer<Void> opaque() {
		return io.getPointerField(this, 10);
	}
	
	@Field(11)
	public int bit_rate() {
		return io.getIntField(this, 11);
	}
	
	@Field(11)
	public void bit_rate(int bit_rate) {
		io.setIntField(this, 11, bit_rate);
	}
	
	@Field(12)
	public int bit_rate_tolerance() {
		return io.getIntField(this, 12);
	}
	
	@Field(12)
	public void bit_rate_tolerance(int bit_rate_tolerance) {
		io.setIntField(this, 12, bit_rate_tolerance);
	}
	
}
