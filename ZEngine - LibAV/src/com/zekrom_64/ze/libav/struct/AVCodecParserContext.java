package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVFieldOrder;
import com.zekrom_64.ze.libav.enums.AVPictureStructure;
import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVCodecParserContext extends Struct<AVCodecParserContext> {
	
	static {
		BridJ.register();
	}
	
	public AVCodecParserContext() {}
	
	public AVCodecParserContext(Pointer<AVCodecParserContext> p) { super(p); }
	
	@Field(0) 
	public Pointer<? > priv_data() {
		return this.io.getPointerField(this, 0);
	}
	
	@Field(0) 
	public void priv_data(Pointer<? > priv_data) {
		this.io.setPointerField(this, 0, priv_data);
	}
	
	@Field(1) 
	public Pointer<AVCodecParser > parser() {
		return this.io.getPointerField(this, 1);
	}
	
	@Field(1) 
	public void parser(Pointer<AVCodecParser > parser) {
		this.io.setPointerField(this, 1, parser);
	}
	
	@Field(2) 
	public long frame_offset() {
		return this.io.getLongField(this, 2);
	}
	
	@Field(2) 
	public void frame_offset(long frame_offset) {
		this.io.setLongField(this, 2, frame_offset);
	}
	
	@Field(3) 
	public long cur_offset() {
		return this.io.getLongField(this, 3);
	}
	
	@Field(3) 
	public void cur_offset(long cur_offset) {
		this.io.setLongField(this, 3, cur_offset);
	}
	
	@Field(4) 
	public long next_frame_offset() {
		return this.io.getLongField(this, 4);
	}
	
	@Field(4) 
	public void next_frame_offset(long next_frame_offset) {
		this.io.setLongField(this, 4, next_frame_offset);
	}
	
	@Field(5) 
	public int pict_type() {
		return this.io.getIntField(this, 5);
	}
	
	@Field(5) 
	public void pict_type(int pict_type) {
		this.io.setIntField(this, 5, pict_type);
	}
	
	@Field(6) 
	public int repeat_pict() {
		return this.io.getIntField(this, 6);
	}
	
	@Field(6) 
	public void repeat_pict(int repeat_pict) {
		this.io.setIntField(this, 6, repeat_pict);
	}
	
	@Field(7) 
	public long pts() {
		return this.io.getLongField(this, 7);
	}
	
	@Field(7) 
	public void pts(long pts) {
		this.io.setLongField(this, 7, pts);
	}
	
	@Field(8) 
	public long dts() {
		return this.io.getLongField(this, 8);
	}
	
	@Field(8) 
	public void dts(long dts) {
		this.io.setLongField(this, 8, dts);
	}
	
	@Field(9) 
	public long last_pts() {
		return this.io.getLongField(this, 9);
	}
	
	@Field(9) 
	public void last_pts(long last_pts) {
		this.io.setLongField(this, 9, last_pts);
	}
	
	@Field(10) 
	public long last_dts() {
		return this.io.getLongField(this, 10);
	}
	
	@Field(10) 
	public void last_dts(long last_dts) {
		this.io.setLongField(this, 10, last_dts);
	}
	
	@Field(11) 
	public int fetch_timestamp() {
		return this.io.getIntField(this, 11);
	}
	
	@Field(11) 
	public void fetch_timestamp(int fetch_timestamp) {
		this.io.setIntField(this, 11, fetch_timestamp);
	}
	
	@Field(12) 
	public int cur_frame_start_index() {
		return this.io.getIntField(this, 12);
	}
	
	@Field(12) 
	public void cur_frame_start_index(int cur_frame_start_index) {
		this.io.setIntField(this, 12, cur_frame_start_index);
	}
	
	@Array({4}) 
	@Field(13) 
	public Pointer<Long > cur_frame_offset() {
		return this.io.getPointerField(this, 13);
	}
	
	@Array({4}) 
	@Field(14) 
	public Pointer<Long > cur_frame_pts() {
		return this.io.getPointerField(this, 14);
	}
	
	@Array({4}) 
	@Field(15) 
	public Pointer<Long > cur_frame_dts() {
		return this.io.getPointerField(this, 15);
	}
	
	@Field(16) 
	public int flags() {
		return this.io.getIntField(this, 16);
	}
	
	@Field(16) 
	public void flags(int flags) {
		this.io.setIntField(this, 16, flags);
	}
	
	@Field(17) 
	public long offset() {
		return this.io.getLongField(this, 17);
	}
	
	@Field(17) 
	public void offset(long offset) {
		this.io.setLongField(this, 17, offset);
	}
	
	@Array({4}) 
	@Field(18) 
	public Pointer<Long > cur_frame_end() {
		return this.io.getPointerField(this, 18);
	}
	
	@Field(19) 
	public int key_frame() {
		return this.io.getIntField(this, 19);
	}
	
	@Field(19) 
	public void key_frame(int key_frame) {
		this.io.setIntField(this, 19, key_frame);
	}
	
	@Field(20) 
	public long convergence_duration() {
		return this.io.getLongField(this, 20);
	}
	
	@Field(20) 
	public void convergence_duration(long convergence_duration) {
		this.io.setLongField(this, 20, convergence_duration);
	}
	
	@Field(21) 
	public int dts_sync_point() {
		return this.io.getIntField(this, 21);
	}
	
	@Field(21) 
	public void dts_sync_point(int dts_sync_point) {
		this.io.setIntField(this, 21, dts_sync_point);
	}
	
	@Field(22) 
	public int dts_ref_dts_delta() {
		return this.io.getIntField(this, 22);
	}
	
	@Field(22) 
	public void dts_ref_dts_delta(int dts_ref_dts_delta) {
		this.io.setIntField(this, 22, dts_ref_dts_delta);
		
	}
	
	@Field(23) 
	public int pts_dts_delta() {
		return this.io.getIntField(this, 23);
	}
	
	@Field(23) 
	public void pts_dts_delta(int pts_dts_delta) {
		this.io.setIntField(this, 23, pts_dts_delta);
		
	}
	
	@Array({4}) 
	@Field(24) 
	public Pointer<Long > cur_frame_pos() {
		return this.io.getPointerField(this, 24);
	}
	
	@Field(25) 
	public long pos() {
		return this.io.getLongField(this, 25);
	}
	
	@Field(25) 
	public void pos(long pos) {
		this.io.setLongField(this, 25, pos);
	}
	
	@Field(26) 
	public long last_pos() {
		return this.io.getLongField(this, 26);
	}
	
	@Field(26) 
	public void last_pos(long last_pos) {
		this.io.setLongField(this, 26, last_pos);
	}
	
	@Field(27) 
	public int duration() {
		return this.io.getIntField(this, 27);
	}
	
	@Field(27) 
	public void duration(int duration) {
		this.io.setIntField(this, 27, duration);
	}
	
	@Field(28) 
	public IntValuedEnum<AVFieldOrder > field_order() {
		return this.io.getEnumField(this, 28);
	}
	
	@Field(28) 
	public void field_order(IntValuedEnum<AVFieldOrder > field_order) {
		this.io.setEnumField(this, 28, field_order);
	}
	
	@Field(29) 
	public IntValuedEnum<AVPictureStructure > picture_structure() {
		return this.io.getEnumField(this, 29);
	}
	
	@Field(29) 
	public void picture_structure(IntValuedEnum<AVPictureStructure > picture_structure) {
		this.io.setEnumField(this, 29, picture_structure);
	}
	
	@Field(30) 
	public int output_picture_number() {
		return this.io.getIntField(this, 30);
	}
	
	@Field(30) 
	public void output_picture_number(int output_picture_number) {
		this.io.setIntField(this, 30, output_picture_number);
	}

}
