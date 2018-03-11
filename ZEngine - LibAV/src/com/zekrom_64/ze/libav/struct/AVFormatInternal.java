package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVFormatInternal extends ZEStruct<AVFormatInternal> {
	
	static {
		BridJ.register();
	}
	
	public AVFormatInternal() {}
	
	public AVFormatInternal(Pointer<AVFormatInternal> p) { super(p); }
	
	@Field(0) 
	public int nb_interleaved_streams() {
		return this.io.getIntField(this, 0);
	}
	
	@Field(0) 
	public void nb_interleaved_streams(int nb_interleaved_streams) {
		this.io.setIntField(this, 0, nb_interleaved_streams);
		
	}
	
	@Field(1) 
	public Pointer<AVPacketList > packet_buffer() {
		return this.io.getPointerField(this, 1);
	}
	
	@Field(1) 
	public void packet_buffer(Pointer<AVPacketList > packet_buffer) {
		this.io.setPointerField(this, 1, packet_buffer);
		
	}
	
	@Field(2) 
	public Pointer<AVPacketList > packet_buffer_end() {
		return this.io.getPointerField(this, 2);
	}
	
	@Field(2) 
	public void packet_buffer_end(Pointer<AVPacketList > packet_buffer_end) {
		this.io.setPointerField(this, 2, packet_buffer_end);
		
	}
	
	@Field(3) 
	public long data_offset() {
		return this.io.getLongField(this, 3);
	}
	
	@Field(3) 
	public void data_offset(long data_offset) {
		this.io.setLongField(this, 3, data_offset);
		
	}
	
	@Field(4) 
	public Pointer<AVPacketList > raw_packet_buffer() {
		return this.io.getPointerField(this, 4);
	}
	
	@Field(4) 
	public void raw_packet_buffer(Pointer<AVPacketList > raw_packet_buffer) {
		this.io.setPointerField(this, 4, raw_packet_buffer);
		
	}
	
	@Field(5) 
	public Pointer<AVPacketList > raw_packet_buffer_end() {
		return this.io.getPointerField(this, 5);
	}
	
	@Field(5) 
	public void raw_packet_buffer_end(Pointer<AVPacketList > raw_packet_buffer_end) {
		this.io.setPointerField(this, 5, raw_packet_buffer_end);
		
	}
	
	@Field(6) 
	public Pointer<AVPacketList > parse_queue() {
		return this.io.getPointerField(this, 6);
	}
	
	@Field(6) 
	public void parse_queue(Pointer<AVPacketList > parse_queue) {
		this.io.setPointerField(this, 6, parse_queue);
		
	}
	
	@Field(7) 
	public Pointer<AVPacketList > parse_queue_end() {
		return this.io.getPointerField(this, 7);
	}
	
	@Field(7) 
	public void parse_queue_end(Pointer<AVPacketList > parse_queue_end) {
		this.io.setPointerField(this, 7, parse_queue_end);
		
	}
	
	@Field(8) 
	public int raw_packet_buffer_remaining_size() {
		return this.io.getIntField(this, 8);
	}
	
	@Field(8) 
	public void raw_packet_buffer_remaining_size(int raw_packet_buffer_remaining_size) {
		this.io.setIntField(this, 8, raw_packet_buffer_remaining_size);
		
	}
	
	@Field(9) 
	public long offset() {
		return this.io.getLongField(this, 9);
	}
	
	@Field(9) 
	public void offset(long offset) {
		this.io.setLongField(this, 9, offset);
		
	}
	
	@Field(10) 
	public AVRational offset_timebase() {
		return this.io.getNativeObjectField(this, 10);
	}
	
	@Field(10) 
	public void offset_timebase(AVRational offset_timebase) {
		this.io.setNativeObjectField(this, 10, offset_timebase);
		
	}
	@Field(11) 
	public int inject_global_side_data() {
		return this.io.getIntField(this, 11);
	}
	
	@Field(11) 
	public void inject_global_side_data(int inject_global_side_data) {
		this.io.setIntField(this, 11, inject_global_side_data);
		
	}
	
	@Field(12) 
	public int avoid_negative_ts_use_pts() {
		return this.io.getIntField(this, 12);
	}
	
	@Field(12) 
	public void avoid_negative_ts_use_pts(int avoid_negative_ts_use_pts) {
		this.io.setIntField(this, 12, avoid_negative_ts_use_pts);
		
	}

}
