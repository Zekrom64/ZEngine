package com.zekrom_64.ze.libav.struct.misc;

import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.LibAVCodec;
import com.zekrom_64.ze.libav.struct.AVBufferRef;
import com.zekrom_64.ze.libav.struct.AVFrame;
import com.zekrom_64.ze.nat.Struct;

public class Picture extends Struct<Picture> {
	
	@Field(0) 
	public Pointer<AVFrame > f() {
		return this.io.getPointerField(this, 0);
	}
	
	@Field(0) 
	public Picture f(Pointer<AVFrame > f) {
		this.io.setPointerField(this, 0, f);
		return this;
	}
	
	@Field(1) 
	public ThreadFrame tf() {
		return this.io.getNativeObjectField(this, 1);
	}
	
	@Field(1) 
	public Picture tf(ThreadFrame tf) {
		this.io.setNativeObjectField(this, 1, tf);
		return this;
	}
	
	@Field(2) 
	public Pointer<AVBufferRef > qscale_table_buf() {
		return this.io.getPointerField(this, 2);
	}
	
	@Field(2) 
	public Picture qscale_table_buf(Pointer<AVBufferRef > qscale_table_buf) {
		this.io.setPointerField(this, 2, qscale_table_buf);
		return this;
	}
	
	@Field(3) 
	public Pointer<Byte > qscale_table() {
		return this.io.getPointerField(this, 3);
	}
	
	@Field(3) 
	public Picture qscale_table(Pointer<Byte > qscale_table) {
		this.io.setPointerField(this, 3, qscale_table);
		return this;
	}
	
	@Array({2}) 
	@Field(4) 
	public Pointer<Pointer<AVBufferRef > > motion_val_buf() {
		return this.io.getPointerField(this, 4);
	}
	
	@Array({2}) 
	@Field(5) 
	public Pointer<Pointer<Pointer<Short > > > motion_val() {
		return this.io.getPointerField(this, 5);
	}
	
	@Field(6) 
	public Pointer<AVBufferRef > mb_type_buf() {
		return this.io.getPointerField(this, 6);
	}
	
	@Field(6) 
	public Picture mb_type_buf(Pointer<AVBufferRef > mb_type_buf) {
		this.io.setPointerField(this, 6, mb_type_buf);
		return this;
	}
	
	@Field(7) 
	public Pointer<Integer > mb_type() {
		return this.io.getPointerField(this, 7);
	}
	
	@Field(7) 
	public Picture mb_type(Pointer<Integer > mb_type) {
		this.io.setPointerField(this, 7, mb_type);
		return this;
	}
	
	@Field(8) 
	public Pointer<AVBufferRef > mbskip_table_buf() {
		return this.io.getPointerField(this, 8);
	}
	
	@Field(8) 
	public Picture mbskip_table_buf(Pointer<AVBufferRef > mbskip_table_buf) {
		this.io.setPointerField(this, 8, mbskip_table_buf);
		return this;
	}
	
	@Field(9) 
	public Pointer<Byte > mbskip_table() {
		return this.io.getPointerField(this, 9);
	}
	
	@Field(9) 
	public Picture mbskip_table(Pointer<Byte > mbskip_table) {
		this.io.setPointerField(this, 9, mbskip_table);
		return this;
	}
	
	@Array({2}) 
	@Field(10) 
	public Pointer<Pointer<AVBufferRef > > ref_index_buf() {
		return this.io.getPointerField(this, 10);
	}
	
	@Array({2}) 
	@Field(11) 
	public Pointer<Pointer<Byte > > ref_index() {
		return this.io.getPointerField(this, 11);
	}
	
	@Field(12) 
	public Pointer<AVBufferRef > mb_var_buf() {
		return this.io.getPointerField(this, 12);
	}
	
	@Field(12) 
	public Picture mb_var_buf(Pointer<AVBufferRef > mb_var_buf) {
		this.io.setPointerField(this, 12, mb_var_buf);
		return this;
	}
	
	@Field(13) 
	public Pointer<Short > mb_var() {
		return this.io.getPointerField(this, 13);
	}
	
	@Field(13) 
	public Picture mb_var(Pointer<Short > mb_var) {
		this.io.setPointerField(this, 13, mb_var);
		return this;
	}
	
	@Field(14) 
	public Pointer<AVBufferRef > mc_mb_var_buf() {
		return this.io.getPointerField(this, 14);
	}
	
	@Field(14) 
	public Picture mc_mb_var_buf(Pointer<AVBufferRef > mc_mb_var_buf) {
		this.io.setPointerField(this, 14, mc_mb_var_buf);
		return this;
	}
	
	@Field(15) 
	public Pointer<Short > mc_mb_var() {
		return this.io.getPointerField(this, 15);
	}
	
	@Field(15) 
	public Picture mc_mb_var(Pointer<Short > mc_mb_var) {
		this.io.setPointerField(this, 15, mc_mb_var);
		return this;
	}
	
	@Field(16) 
	public Pointer<AVBufferRef > mb_mean_buf() {
		return this.io.getPointerField(this, 16);
	}
	
	@Field(16) 
	public Picture mb_mean_buf(Pointer<AVBufferRef > mb_mean_buf) {
		this.io.setPointerField(this, 16, mb_mean_buf);
		return this;
	}
	
	@Field(17) 
	public Pointer<Byte > mb_mean() {
		return this.io.getPointerField(this, 17);
	}
	
	@Field(17) 
	public Picture mb_mean(Pointer<Byte > mb_mean) {
		this.io.setPointerField(this, 17, mb_mean);
		return this;
	}
	
	@Field(18) 
	public Pointer<AVBufferRef > hwaccel_priv_buf() {
		return this.io.getPointerField(this, 18);
	}
	
	@Field(18) 
	public Picture hwaccel_priv_buf(Pointer<AVBufferRef > hwaccel_priv_buf) {
		this.io.setPointerField(this, 18, hwaccel_priv_buf);
		return this;
	}

	@Field(19) 
	public Pointer<? > hwaccel_picture_private() {
		return this.io.getPointerField(this, 19);
	}

	@Field(19) 
	public Picture hwaccel_picture_private(Pointer<? > hwaccel_picture_private) {
		this.io.setPointerField(this, 19, hwaccel_picture_private);
		return this;
	}
	
	@Field(20) 
	public int field_picture() {
		return this.io.getIntField(this, 20);
	}
	
	@Field(20) 
	public Picture field_picture(int field_picture) {
		this.io.setIntField(this, 20, field_picture);
		return this;
	}
	
	@Field(21) 
	public int mb_var_sum() {
		return this.io.getIntField(this, 21);
	}
	
	@Field(21) 
	public Picture mb_var_sum(int mb_var_sum) {
		this.io.setIntField(this, 21, mb_var_sum);
		return this;
	}
	
	@Field(22) 
	public int mc_mb_var_sum() {
		return this.io.getIntField(this, 22);
	}
	
	@Field(22) 
	public Picture mc_mb_var_sum(int mc_mb_var_sum) {
		this.io.setIntField(this, 22, mc_mb_var_sum);
		return this;
	}
	
	@Field(23) 
	public int b_frame_score() {
		return this.io.getIntField(this, 23);
	}
	
	@Field(23) 
	public Picture b_frame_score(int b_frame_score) {
		this.io.setIntField(this, 23, b_frame_score);
		return this;
	}
	
	@Field(24) 
	public int needs_realloc() {
		return this.io.getIntField(this, 24);
	}
	
	@Field(24) 
	public Picture needs_realloc(int needs_realloc) {
		this.io.setIntField(this, 24, needs_realloc);
		return this;
	}
	
	@Field(25) 
	public int reference() {
		return this.io.getIntField(this, 25);
	}
	
	@Field(25) 
	public Picture reference(int reference) {
		this.io.setIntField(this, 25, reference);
		return this;
	}
	
	@Field(26) 
	public int shared() {
		return this.io.getIntField(this, 26);
	}
	
	@Field(26) 
	public Picture shared(int shared) {
		this.io.setIntField(this, 26, shared);
		return this;
	}
	
	@Array(LibAVCodec.AV_NUM_DATA_POINTERS)
	@Field(27)
	public Pointer<Long> error() {
		return io.getPointerField(this, 27);
	}
	
}
