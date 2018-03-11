package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.DynamicFunction;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVCodecID;
import com.zekrom_64.ze.libav.enums.AVMediaType;
import com.zekrom_64.ze.libav.enums.AVPixelFormat;
import com.zekrom_64.ze.libav.enums.AVSampleFormat;
import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVCodec extends ZEStruct<AVCodec> {

	static {
		BridJ.register();
	}
	
	public AVCodec() {}
	
	public AVCodec(Pointer<AVCodec> p) { super(p); }
	
	@Field(0)
	public Pointer<Byte> name() {
		return io.getPointerField(this, 0);
	}
	
	@Field(1)
	public Pointer<Byte> long_name() {
		return io.getPointerField(this, 1);
	}
	
	@Field(2)
	public AVMediaType type() {
		IntValuedEnum<AVMediaType> e = io.getEnumField(this, 2);
		return (AVMediaType)e;
	}
	
	@Field(3)
	public AVCodecID id() {
		IntValuedEnum<AVCodecID> e = io.getEnumField(this, 3);
		return (AVCodecID) e;
	}
	
	@Field(4)
	public Pointer<AVRational> supported_framerates() {
		return io.getPointerField(this, 4);
	}
	
	@Field(5)
	public Pointer<AVPixelFormat> pix_fmts() {
		return io.getPointerField(this, 5);
	}
	
	@Field(6)
	public Pointer<Integer> supported_samplerates() {
		return io.getPointerField(this, 6);
	}
	
	@Field(7)
	public Pointer<AVSampleFormat> sample_fmts() {
		return io.getPointerField(this, 7);
	}
	
	@Field(8)
	public Pointer<Long> channel_layouts() {
		return io.getPointerField(this, 8);
	}
	
	@Deprecated
	@Field(9)
	public Pointer<Byte> max_lowres() {
		return io.getPointerField(this, 9);
	}
	
	@Field(10)
	public Pointer<AVClass> priv_class() {
		return io.getPointerField(this, 10);
	}
	
	@Field(11)
	public Pointer<AVProfile> profiles() {
		return io.getPointerField(this, 11);
	}
	
	@Field(12)
	public int priv_data_size() {
		return io.getIntField(this, 12);
	}
	
	@Field(13)
	public Pointer<AVCodec> next() {
		return io.getPointerField(this, 13);
	}
	
	@Field(14)
	public Pointer<AVCodecDefault> defaults() {
		return io.getPointerField(this, 14);
	}
	
	@Field(15)
	public Pointer<DynamicFunction<Void>> init_static_data() {
		return io.getPointerField(this, 15);
	}
	
	@Field(16)
	public Pointer<DynamicFunction<Integer>> init() {
		return io.getPointerField(this, 16);
	}
	
	@Field(17)
	public Pointer<DynamicFunction<Integer>> encode_sub() {
		return io.getPointerField(this, 17);
	}
	
	@Field(18)
	public Pointer<DynamicFunction<Integer>> encode2() {
		return io.getPointerField(this, 18);
	}
	
	@Field(19)
	public Pointer<DynamicFunction<Integer>> decode() {
		return io.getPointerField(this, 19);
	}
	
	@Field(20)
	public Pointer<DynamicFunction<Integer>> close() {
		return io.getPointerField(this, 20);
	}
	
	@Field(21)
	public Pointer<DynamicFunction<Void>> flush() {
		return io.getPointerField(this, 21);
	}
	
	@Field(22)
	public int caps_internal() {
		return io.getIntField(this, 22);
	}
	
}
