package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVBitStreamFilterContext extends Struct<AVBitStreamFilterContext> {

	static {
		BridJ.register();
	}
	
	public AVBitStreamFilterContext() {}
	public AVBitStreamFilterContext(Pointer<AVBitStreamFilterContext> p) { super(p); }
	
	@Field(0)
	public Pointer<?> priv_data() {
		return io.getPointerField(this, 0);
	}
	
	@Field(0)
	public void priv_data(Pointer<?> priv_data) {
		io.setPointerField(this, 0, priv_data);
	}
	
	@Field(1)
	public Pointer<AVBitStreamFilter> filter() {
		return io.getPointerField(this, 1);
	}
	
	@Field(1)
	public void filter(Pointer<AVBitStreamFilter> filter) {
		io.setPointerField(this, 1, filter);
	}
	
	@Field(2)
	public Pointer<AVCodecParserContext> parser() {
		return io.getPointerField(this, 2);
	}
	
	@Field(2)
	public void parser(Pointer<AVCodecParserContext> parser) {
		io.setPointerField(this, 2, parser);
	}
	
	@Field(3)
	public Pointer<AVBitStreamFilterContext> next() {
		return io.getPointerField(this, 3);
	}
	
	@Field(3)
	public void next(Pointer<AVBitStreamFilterContext> next) {
		io.setPointerField(this, 3, next);
	}
	
}
