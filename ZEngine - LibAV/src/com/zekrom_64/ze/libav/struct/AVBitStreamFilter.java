package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVBitStreamFilter extends ZEStruct<AVBitStreamFilter> {
	
	static {
		BridJ.register();
	}
	
	public AVBitStreamFilter() {}
	
	public AVBitStreamFilter(Pointer<AVBitStreamFilter> p) { super(p); }

	@Field(0)
	public Pointer<Byte> name() {
		return io.getPointerField(this, 0);
	}
	
	@Field(0)
	public void name(Pointer<Byte> name) {
		io.setPointerField(this, 0, name);
	}
	
	@Field(1)
	public int priv_data_size() {
		return io.getIntField(this, 1);
	}
	
	@Field(1)
	public void priv_data_size(int priv_data_size) {
		io.setIntField(this, 1, priv_data_size);
	}
	
	public static abstract class AVBitStreamFilterCallback extends Callback<AVBitStreamFilterCallback> {
		
		public abstract int invoke(Pointer<AVBitStreamFilterContext> bsfc, Pointer<AVCodecContext> avctx, Pointer<Byte> args, Pointer<Pointer<Byte>> poutbuf, Pointer<Integer> poutbuf_size, Pointer<Byte> buff, int buf_size, int keyframe);
		
	}
	
	@Field(2)
	public Pointer<AVBitStreamFilterCallback> filter() {
		return io.getPointerField(this, 2);
	}
	
	@Field(2)
	public void filter(Pointer<AVBitStreamFilterCallback> filter) {
		io.setPointerField(this, 2, filter);
	}
	
	public static abstract class AVBitStreamFilterCloseCallback extends Callback<AVBitStreamFilterCloseCallback> {
		
		public abstract void invoke(Pointer<AVBitStreamFilterContext> bsfc);
		
	}
	
	@Field(3)
	public Pointer<AVBitStreamFilterCloseCallback> close() {
		return io.getPointerField(this, 3);
	}
	
	@Field(3)
	public void close(Pointer<AVBitStreamFilterCloseCallback> close) {
		io.setPointerField(this, 3, close);
	}
	
	@Field(4)
	public Pointer<AVBitStreamFilter> next() {
		return io.getPointerField(this, 4);
	}
	
	@Field(4)
	public void next(Pointer<AVBitStreamFilter> next) {
		io.setPointerField(this, 4, next);
	}
	
}
