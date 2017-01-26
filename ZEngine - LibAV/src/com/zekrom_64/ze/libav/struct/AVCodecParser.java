package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.DynamicFunction;
import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVCodecParser extends Struct<AVCodecParser> {

	static {
		BridJ.register();
	}
	
	public AVCodecParser() {}
	
	public AVCodecParser(Pointer<AVCodecParser> p) { super(p); }
	
	@Array(5)
	@Field(0)
	public Pointer<Integer> codec_ids() {
		return io.getPointerField(this, 0);
	}
	
	@Field(1)
	public int priv_data_size() {
		return io.getIntField(this, 1);
	}
	
	@Field(1)
	public void priv_data_size(int priv_data_size) {
		io.setIntField(this, 1, priv_data_size);
	}
	
	@Field(2)
	public DynamicFunction<Integer> parser_init() {
		return io.getNativeObjectField(this, 2);
	}
	
	@Field(2)
	public void parser_init(DynamicFunction<Integer> parser_init) {
		io.setNativeObjectField(this, 2, parser_init);
	}
	
	@Field(3)
	public DynamicFunction<Integer> parser_parse() {
		return io.getNativeObjectField(this, 3);
	}
	
	@Field(3)
	public void parser_parse(DynamicFunction<Integer> parser_parse) {
		io.setNativeObjectField(this, 3, parser_parse);
	}
	
	@Field(4)
	public DynamicFunction<Void> parser_close() {
		return io.getNativeObjectField(this, 4);
	}
	
	@Field(4)
	public void parser_close(DynamicFunction<Void> parser_close) {
		io.setNativeObjectField(this, 4, parser_close);
	}
	
	@Field(5)
	public DynamicFunction<Integer> split() {
		return io.getNativeObjectField(this, 5);
	}
	
	@Field(5)
	public void split(DynamicFunction<Integer> split) {
		io.setNativeObjectField(this, 5, split);
	}
	
	@Field(6)
	public Pointer<AVCodecParser> next() {
		return io.getPointerField(this, 6);
	}
	
	@Field(6)
	public void next(Pointer<AVCodecParser> next) {
		io.setPointerField(this, 6, next);
	}
	
}
