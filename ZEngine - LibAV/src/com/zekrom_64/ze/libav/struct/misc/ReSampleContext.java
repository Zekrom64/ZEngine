package com.zekrom_64.ze.libav.struct.misc;

import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVSampleFormat;
import com.zekrom_64.ze.libav.struct.AVAudioConvert;
import com.zekrom_64.ze.libav.struct.AVResampleContext;
import com.zekrom_64.ze.nat.Struct;

public class ReSampleContext extends Struct<ReSampleContext> {
	
	@Field(0) 
	public Pointer<AVResampleContext > resample_context() {
		return this.io.getPointerField(this, 0);
	}
	
	@Field(0) 
	public void resample_context(Pointer<AVResampleContext > resample_context) {
		this.io.setPointerField(this, 0, resample_context);
	}
	
	@Array({8}) 
	@Field(1) 
	public Pointer<Pointer<Short > > temp() {
		return this.io.getPointerField(this, 1);
	}
	
	@Field(2) 
	public int temp_len() {
		return this.io.getIntField(this, 2);
	}
	
	@Field(2) 
	public void temp_len(int temp_len) {
		this.io.setIntField(this, 2, temp_len);
	}
	
	@Field(3) 
	public float ratio() {
		return this.io.getFloatField(this, 3);
	}
	
	@Field(3) 
	public void ratio(float ratio) {
		this.io.setFloatField(this, 3, ratio);
	}
	
	@Field(4) 
	public int input_channels() {
		return this.io.getIntField(this, 4);
	}
	
	@Field(4) 
	public void input_channels(int input_channels) {
		this.io.setIntField(this, 4, input_channels);
	}
	
	@Field(5) 
	public int output_channels() {
		return this.io.getIntField(this, 5);
	}
	
	@Field(5) 
	public void output_channels(int output_channels) {
		this.io.setIntField(this, 5, output_channels);
	}
	
	@Field(6) 
	public int filter_channels() {
		return this.io.getIntField(this, 6);
	}
	
	@Field(6) 
	public void filter_channels(int filter_channels) {
		this.io.setIntField(this, 6, filter_channels);
	}
	
	@Array({2}) 
	@Field(7) 
	public Pointer<Pointer<AVAudioConvert > > convert_ctx() {
		return this.io.getPointerField(this, 7);
	}
	
	@Array({2}) 
	@Field(8) 
	public Pointer<IntValuedEnum<AVSampleFormat > > sample_fmt() {
		return this.io.getPointerField(this, 8);
	}
	
	@Array({2}) 
	@Field(9) 
	public Pointer<Integer > sample_size() {
		return this.io.getPointerField(this, 9);
	}
	
	@Array({2}) 
	@Field(10) 
	public Pointer<Pointer<Short > > buffer() {
		return this.io.getPointerField(this, 10);
	}
	
	@Array({2}) 
	@Field(11) 
	public Pointer<Integer > buffer_size() {
		return this.io.getPointerField(this, 11);
	}

}
