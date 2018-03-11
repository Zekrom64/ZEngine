package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVCodecID;
import com.zekrom_64.ze.libav.enums.AVPixelFormat;
import com.zekrom_64.ze.libav.enums.AVSampleFormat;
import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVDeviceCapabilitiesQuery extends ZEStruct<AVDeviceCapabilitiesQuery> {
	
	static {
		BridJ.register();
	}
	
	public AVDeviceCapabilitiesQuery() {}
	
	public AVDeviceCapabilitiesQuery(Pointer<AVDeviceCapabilitiesQuery> p) { super(p); }
	
	/** C type : const AVClass* */
	@Field(0) 
	public Pointer<AVClass > av_class() {
		return this.io.getPointerField(this, 0);
	}
	/** C type : const AVClass* */
	@Field(0) 
	public void av_class(Pointer<AVClass > av_class) {
		this.io.setPointerField(this, 0, av_class);
		
	}
	/** C type : AVFormatContext* */
	@Field(1) 
	public Pointer<AVFormatContext > device_context() {
		return this.io.getPointerField(this, 1);
	}
	/** C type : AVFormatContext* */
	@Field(1) 
	public void device_context(Pointer<AVFormatContext > device_context) {
		this.io.setPointerField(this, 1, device_context);
		
	}
	/** C type : AVCodecID */
	@Field(2) 
	public IntValuedEnum<AVCodecID > codec() {
		return this.io.getEnumField(this, 2);
	}
	/** C type : AVCodecID */
	@Field(2) 
	public void codec(IntValuedEnum<AVCodecID > codec) {
		this.io.setEnumField(this, 2, codec);
		
	}
	/** C type : AVSampleFormat */
	@Field(3) 
	public IntValuedEnum<AVSampleFormat > sample_format() {
		return this.io.getEnumField(this, 3);
	}
	/** C type : AVSampleFormat */
	@Field(3) 
	public void sample_format(IntValuedEnum<AVSampleFormat > sample_format) {
		this.io.setEnumField(this, 3, sample_format);
		
	}
	/** C type : AVPixelFormat */
	@Field(4) 
	public IntValuedEnum<AVPixelFormat > pixel_format() {
		return this.io.getEnumField(this, 4);
	}
	/** C type : AVPixelFormat */
	@Field(4) 
	public void pixel_format(IntValuedEnum<AVPixelFormat > pixel_format) {
		this.io.setEnumField(this, 4, pixel_format);
		
	}
	@Field(5) 
	public int sample_rate() {
		return this.io.getIntField(this, 5);
	}
	@Field(5) 
	public void sample_rate(int sample_rate) {
		this.io.setIntField(this, 5, sample_rate);
		
	}
	@Field(6) 
	public int channels() {
		return this.io.getIntField(this, 6);
	}
	@Field(6) 
	public void channels(int channels) {
		this.io.setIntField(this, 6, channels);
		
	}
	@CLong 
	@Field(7) 
	public long channel_layout() {
		return this.io.getCLongField(this, 7);
	}
	@CLong 
	@Field(7) 
	public void channel_layout(long channel_layout) {
		this.io.setCLongField(this, 7, channel_layout);
		
	}
	@Field(8) 
	public int window_width() {
		return this.io.getIntField(this, 8);
	}
	@Field(8) 
	public void window_width(int window_width) {
		this.io.setIntField(this, 8, window_width);
		
	}
	@Field(9) 
	public int window_height() {
		return this.io.getIntField(this, 9);
	}
	@Field(9) 
	public void window_height(int window_height) {
		this.io.setIntField(this, 9, window_height);
		
	}
	@Field(10) 
	public int frame_width() {
		return this.io.getIntField(this, 10);
	}
	@Field(10) 
	public void frame_width(int frame_width) {
		this.io.setIntField(this, 10, frame_width);
		
	}
	@Field(11) 
	public int frame_height() {
		return this.io.getIntField(this, 11);
	}
	@Field(11) 
	public void frame_height(int frame_height) {
		this.io.setIntField(this, 11, frame_height);
		
	}
	/** C type : AVRational */
	@Field(12) 
	public AVRational fps() {
		return this.io.getNativeObjectField(this, 12);
	}
	/** C type : AVRational */
	@Field(12) 
	public void fps(AVRational fps) {
		this.io.setNativeObjectField(this, 12, fps);
		
	}

}
