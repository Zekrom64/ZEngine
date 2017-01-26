package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Callback;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVCodecID;
import com.zekrom_64.ze.libav.enums.AVMediaType;
import com.zekrom_64.ze.libav.enums.AVPixelFormat;
import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVHWAccel extends Struct<AVHWAccel> {
	
	static {
		BridJ.register();
	}
	
	public AVHWAccel() {}
	
	public AVHWAccel(Pointer<AVHWAccel> p) { super(p); }

	@Field(0)
	public Pointer<Byte> name() {
		return io.getPointerField(this, 0);
	}
	
	@Field(0)
	public void name(Pointer<Byte> name) {
		io.setPointerField(this, 0, name);
	}
	
	@Field(1)
	public AVMediaType type() {
		IntValuedEnum<AVMediaType> type = io.getEnumField(this, 1);
		return (AVMediaType) type;
	}
	
	@Field(1)
	public void type(AVMediaType type) {
		io.setEnumField(this, 1, type);
	}
	
	@Field(2)
	public AVCodecID id() {
		IntValuedEnum<AVCodecID> id = io.getEnumField(this, 2);
		return (AVCodecID)id;
	}
	
	@Field(2)
	public void id(AVCodecID id) {
		io.setEnumField(this, 2, id);
	}
	
	@Field(3)
	public AVPixelFormat pix_fmt() {
		IntValuedEnum<AVPixelFormat> pix_fmt = io.getEnumField(this, 3);
		return (AVPixelFormat)pix_fmt;
	}
	
	@Field(3)
	public void pix_fmt(AVPixelFormat pix_fmt) {
		io.setEnumField(this, 3, pix_fmt);
	}
	
	@Field(4)
	public int capabilities() {
		return io.getIntField(this, 4);
	}
	
	@Field(4)
	public void capabilities(int capabilities) {
		io.setIntField(this, 4, capabilities);
	}
	
	@Field(5)
	public Pointer<AVHWAccel> next() {
		return io.getPointerField(this, 5);
	}
	
	@Field(5)
	public void next(Pointer<AVHWAccel> next) {
		io.setPointerField(this, 5, next);
	}
	
	public static abstract class AVHWAccelAllocCallback extends Callback<AVHWAccelAllocCallback> {
		
		public abstract int invoke(Pointer<AVCodecContext> avctx, Pointer<AVFrame> frame);
		
	}
	
	@Field(6)
	public Pointer<AVHWAccelAllocCallback> alloc_frame() {
		return io.getPointerField(this, 6);
	}
	
	@Field(6)
	public void alloc_frame(Pointer<AVHWAccelAllocCallback> alloc_frame) {
		io.setPointerField(this, 6, alloc_frame);
	}
	
	public static abstract class AVHWAccelStartCallback extends Callback<AVHWAccelStartCallback> {
		
		public abstract int invoke(Pointer<AVCodecContext> avctx, Pointer<Byte> buf, int buf_size);
		
	}
	
	@Field(7)
	public Pointer<AVHWAccelStartCallback> start_frame() {
		return io.getPointerField(this, 7);
	}
	
	@Field(7)
	public void start_frame(Pointer<AVHWAccelStartCallback> start_frame) {
		io.setPointerField(this, 7, start_frame);
	}
	
	public static abstract class AVHWAccelSliceCallback extends Callback<AVHWAccelSliceCallback> {
		
		public abstract int invoke(Pointer<AVCodecContext> avctx, Pointer<Byte> buf, int buf_size);
		
	}
	
	@Field(8)
	public Pointer<AVHWAccelSliceCallback> decode_slice() {
		return io.getPointerField(this, 8);
	}
	
	@Field(8)
	public void decode_slice(Pointer<AVHWAccelSliceCallback> decode_slice) {
		io.setPointerField(this, 8, decode_slice);
	}
	
	public static abstract class AVHWAccelEndCallback extends Callback<AVHWAccelEndCallback> {
		
		public abstract int invoke(Pointer<AVCodecContext> avctx);
		
	}
	
	@Field(9)
	public Pointer<AVHWAccelEndCallback> end_frame() {
		return io.getPointerField(this, 9);
	}
	
	@Field(9)
	public void end_frame(Pointer<AVHWAccelEndCallback> end_frame) {
		io.setPointerField(this, 9, end_frame);
	}
	
	@Field(10)
	public int frame_priv_data_size() {
		return io.getIntField(this, 10);
	}
	
	@Field(10)
	public void frame_priv_data_size(int frame_priv_data_size) {
		io.setIntField(this, 10, frame_priv_data_size);
	}
	
	public static abstract class AVHWAccelDecodeCallback extends Callback<AVHWAccelDecodeCallback> {
		
		// TODO: Implement MpegEncContext struct fully
		public abstract void invoke(Pointer<?> s);
		
	}
	
	@Field(11)
	public Pointer<AVHWAccelDecodeCallback> decode_mb() {
		return io.getPointerField(this, 11);
	}
	
	@Field(11)
	public void decode_mb(Pointer<AVHWAccelDecodeCallback> decode_mb) {
		io.setPointerField(this, 11, decode_mb);
	}
	
}
