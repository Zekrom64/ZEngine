package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVChromaLocation;
import com.zekrom_64.ze.libav.enums.AVColorPrimaries;
import com.zekrom_64.ze.libav.enums.AVColorRange;
import com.zekrom_64.ze.libav.enums.AVColorSpace;
import com.zekrom_64.ze.libav.enums.AVColorTransferCharacteristic;
import com.zekrom_64.ze.libav.enums.AVPictureType;
import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVFrame extends ZEStruct<AVFrame> {
	
	static {
		BridJ.register();
	}
	
	public AVFrame() {}
	
	public AVFrame(Pointer<AVFrame> p) { super(p); }

	@Array({8}) 
	@Field(0) 
	public Pointer<Pointer<Byte > > data() {
		return this.io.getPointerField(this, 0);
	}
	
	@Array({8}) 
	@Field(1) 
	public Pointer<Integer > linesize() {
		return this.io.getPointerField(this, 1);
	}
	
	@Field(2) 
	public Pointer<Pointer<Byte > > extended_data() {
		return this.io.getPointerField(this, 2);
	}
	
	@Field(2) 
	public AVFrame extended_data(Pointer<Pointer<Byte > > extended_data) {
		this.io.setPointerField(this, 2, extended_data);
		return this;
	}
	
	@Field(3) 
	public int width() {
		return this.io.getIntField(this, 3);
	}
	
	@Field(3) 
	public AVFrame width(int width) {
		this.io.setIntField(this, 3, width);
		return this;
	}
	
	@Field(4) 
	public int height() {
		return this.io.getIntField(this, 4);
	}
	
	@Field(4) 
	public AVFrame height(int height) {
		this.io.setIntField(this, 4, height);
		return this;
	}
	
	@Field(5) 
	public int nb_samples() {
		return this.io.getIntField(this, 5);
	}
	
	@Field(5) 
	public AVFrame nb_samples(int nb_samples) {
		this.io.setIntField(this, 5, nb_samples);
		return this;
	}
	
	@Field(6) 
	public int format() {
		return this.io.getIntField(this, 6);
	}
	
	@Field(6) 
	public AVFrame format(int format) {
		this.io.setIntField(this, 6, format);
		return this;
	}
	
	@Field(7) 
	public int key_frame() {
		return this.io.getIntField(this, 7);
	}
	
	@Field(7) 
	public AVFrame key_frame(int key_frame) {
		this.io.setIntField(this, 7, key_frame);
		return this;
	}
	
	@Field(8) 
	public IntValuedEnum<AVPictureType > pict_type() {
		return this.io.getEnumField(this, 8);
	}
	
	@Field(8) 
	public AVFrame pict_type(IntValuedEnum<AVPictureType > pict_type) {
		this.io.setEnumField(this, 8, pict_type);
		return this;
	}
	
	@Field(9) 
	public AVRational sample_aspect_ratio() {
		return this.io.getNativeObjectField(this, 9);
	}
	
	@Field(9) 
	public AVFrame sample_aspect_ratio(AVRational sample_aspect_ratio) {
		this.io.setNativeObjectField(this, 9, sample_aspect_ratio);
		return this;
	}
	
	@Field(10) 
	public long pts() {
		return this.io.getLongField(this, 10);
	}
	
	@Field(10) 
	public AVFrame pts(long pts) {
		this.io.setLongField(this, 10, pts);
		return this;
	}
	
	@Field(11) 
	public long pkt_pts() {
		return this.io.getLongField(this, 11);
	}
	
	@Field(11) 
	public AVFrame pkt_pts(long pkt_pts) {
		this.io.setLongField(this, 11, pkt_pts);
		return this;
	}
	
	@Field(12) 
	public long pkt_dts() {
		return this.io.getLongField(this, 12);
	}
	
	@Field(12) 
	public AVFrame pkt_dts(long pkt_dts) {
		this.io.setLongField(this, 12, pkt_dts);
		return this;
	}
	
	@Field(13) 
	public int coded_picture_number() {
		return this.io.getIntField(this, 13);
	}
	
	@Field(13) 
	public AVFrame coded_picture_number(int coded_picture_number) {
		this.io.setIntField(this, 13, coded_picture_number);
		return this;
	}
	
	@Field(14) 
	public int display_picture_number() {
		return this.io.getIntField(this, 14);
	}
	
	@Field(14) 
	public AVFrame display_picture_number(int display_picture_number) {
		this.io.setIntField(this, 14, display_picture_number);
		return this;
	}
	
	@Field(15) 
	public int quality() {
		return this.io.getIntField(this, 15);
	}
	
	@Field(15) 
	public AVFrame quality(int quality) {
		this.io.setIntField(this, 15, quality);
		return this;
	}
	
	@Field(16) 
	public Pointer<? > opaque() {
		return this.io.getPointerField(this, 16);
	}
	
	@Field(16) 
	public AVFrame opaque(Pointer<? > opaque) {
		this.io.setPointerField(this, 16, opaque);
		return this;
	}
	
	@Array({8}) 
	@Field(17) 
	public Pointer<Long> error() {
		return this.io.getPointerField(this, 17);
	}
	
	@Field(18) 
	public int repeat_pict() {
		return this.io.getIntField(this, 18);
	}
	
	@Field(18) 
	public AVFrame repeat_pict(int repeat_pict) {
		this.io.setIntField(this, 18, repeat_pict);
		return this;
	}
	
	@Field(19) 
	public int interlaced_frame() {
		return this.io.getIntField(this, 19);
	}
	
	@Field(19) 
	public AVFrame interlaced_frame(int interlaced_frame) {
		this.io.setIntField(this, 19, interlaced_frame);
		return this;
	}
	
	@Field(20) 
	public int top_field_first() {
		return this.io.getIntField(this, 20);
	}
	
	@Field(20) 
	public AVFrame top_field_first(int top_field_first) {
		this.io.setIntField(this, 20, top_field_first);
		return this;
	}
	
	@Field(21) 
	public int palette_has_changed() {
		return this.io.getIntField(this, 21);
	}
	
	@Field(21) 
	public AVFrame palette_has_changed(int palette_has_changed) {
		this.io.setIntField(this, 21, palette_has_changed);
		return this;
	}
	
	@Field(22) 
	public long reordered_opaque() {
		return this.io.getLongField(this, 22);
	}
	
	@Field(22) 
	public AVFrame reordered_opaque(long reordered_opaque) {
		this.io.setLongField(this, 22, reordered_opaque);
		return this;
	}
	
	@Field(23) 
	public int sample_rate() {
		return this.io.getIntField(this, 23);
	}
	
	@Field(23) 
	public AVFrame sample_rate(int sample_rate) {
		this.io.setIntField(this, 23, sample_rate);
		return this;
	}
	
	@Field(24) 
	public long channel_layout() {
		return this.io.getLongField(this, 24);
	}
	
	@Field(24) 
	public AVFrame channel_layout(long channel_layout) {
		this.io.setLongField(this, 24, channel_layout);
		return this;
	}
	
	@Array({8}) 
	@Field(25) 
	public Pointer<Pointer<AVBufferRef > > buf() {
		return this.io.getPointerField(this, 25);
	}
	
	@Field(26) 
	public Pointer<Pointer<AVBufferRef > > extended_buf() {
		return this.io.getPointerField(this, 26);
	}
	
	@Field(26) 
	public AVFrame extended_buf(Pointer<Pointer<AVBufferRef > > extended_buf) {
		this.io.setPointerField(this, 26, extended_buf);
		return this;
	}
	
	@Field(27) 
	public int nb_extended_buf() {
		return this.io.getIntField(this, 27);
	}
	
	@Field(27) 
	public AVFrame nb_extended_buf(int nb_extended_buf) {
		this.io.setIntField(this, 27, nb_extended_buf);
		return this;
	}
	
	@Field(28) 
	public Pointer<Pointer<AVFrameSideData > > side_data() {
		return this.io.getPointerField(this, 28);
	}
	
	@Field(28) 
	public AVFrame side_data(Pointer<Pointer<AVFrameSideData > > side_data) {
		this.io.setPointerField(this, 28, side_data);
		return this;
	}
	
	@Field(29) 
	public int nb_side_data() {
		return this.io.getIntField(this, 29);
	}
	
	@Field(29) 
	public AVFrame nb_side_data(int nb_side_data) {
		this.io.setIntField(this, 29, nb_side_data);
		return this;
	}
	
	@Field(30) 
	public int flags() {
		return this.io.getIntField(this, 30);
	}
	
	@Field(30) 
	public AVFrame flags(int flags) {
		this.io.setIntField(this, 30, flags);
		return this;
	}
	
	@Field(31) 
	public IntValuedEnum<AVColorRange > color_range() {
		return this.io.getEnumField(this, 31);
	}
	
	@Field(31) 
	public AVFrame color_range(IntValuedEnum<AVColorRange > color_range) {
		this.io.setEnumField(this, 31, color_range);
		return this;
	}
	
	@Field(32) 
	public IntValuedEnum<AVColorPrimaries > color_primaries() {
		return this.io.getEnumField(this, 32);
	}
	
	@Field(32) 
	public AVFrame color_primaries(IntValuedEnum<AVColorPrimaries > color_primaries) {
		this.io.setEnumField(this, 32, color_primaries);
		return this;
	}
	
	@Field(33) 
	public IntValuedEnum<AVColorTransferCharacteristic > color_trc() {
		return this.io.getEnumField(this, 33);
	}
	
	@Field(33) 
	public AVFrame color_trc(IntValuedEnum<AVColorTransferCharacteristic > color_trc) {
		this.io.setEnumField(this, 33, color_trc);
		return this;
	}
	
	@Field(34) 
	public IntValuedEnum<AVColorSpace > colorspace() {
		return this.io.getEnumField(this, 34);
	}
	
	@Field(34) 
	public AVFrame colorspace(IntValuedEnum<AVColorSpace > colorspace) {
		this.io.setEnumField(this, 34, colorspace);
		return this;
	}
	
	@Field(35) 
	public IntValuedEnum<AVChromaLocation > chroma_location() {
		return this.io.getEnumField(this, 35);
	}
	
	@Field(35) 
	public AVFrame chroma_location(IntValuedEnum<AVChromaLocation > chroma_location) {
		this.io.setEnumField(this, 35, chroma_location);
		return this;
	}
	
	@Field(36) 
	public long best_effort_timestamp() {
		return this.io.getLongField(this, 36);
	}
	
	@Field(36) 
	public AVFrame best_effort_timestamp(long best_effort_timestamp) {
		this.io.setLongField(this, 36, best_effort_timestamp);
		return this;
	}
	
	@Field(37) 
	public long pkt_pos() {
		return this.io.getLongField(this, 37);
	}
	
	@Field(37) 
	public AVFrame pkt_pos(long pkt_pos) {
		this.io.setLongField(this, 37, pkt_pos);
		return this;
	}
	
	@Field(38) 
	public long pkt_duration() {
		return this.io.getLongField(this, 38);
	}
	
	@Field(38) 
	public AVFrame pkt_duration(long pkt_duration) {
		this.io.setLongField(this, 38, pkt_duration);
		return this;
	}
	
	@Field(39) 
	public Pointer<AVDictionary > metadata() {
		return this.io.getPointerField(this, 39);
	}
	
	@Field(39) 
	public AVFrame metadata(Pointer<AVDictionary > metadata) {
		this.io.setPointerField(this, 39, metadata);
		return this;
	}
	
	@Field(40) 
	public int decode_error_flags() {
		return this.io.getIntField(this, 40);
	}
	
	@Field(40) 
	public AVFrame decode_error_flags(int decode_error_flags) {
		this.io.setIntField(this, 40, decode_error_flags);
		return this;
	}
	
	@Field(41) 
	public int channels() {
		return this.io.getIntField(this, 41);
	}
	
	@Field(41) 
	public AVFrame channels(int channels) {
		this.io.setIntField(this, 41, channels);
		return this;
	}
	
	@Field(42) 
	public int pkt_size() {
		return this.io.getIntField(this, 42);
	}
	
	@Field(42) 
	public AVFrame pkt_size(int pkt_size) {
		this.io.setIntField(this, 42, pkt_size);
		return this;
	}
	
	@Field(43) 
	public Pointer<Byte > qscale_table() {
		return this.io.getPointerField(this, 43);
	}
	
	@Field(43) 
	public AVFrame qscale_table(Pointer<Byte > qscale_table) {
		this.io.setPointerField(this, 43, qscale_table);
		return this;
	}
	
	@Field(44) 
	public int qstride() {
		return this.io.getIntField(this, 44);
	}
	
	@Field(44) 
	public AVFrame qstride(int qstride) {
		this.io.setIntField(this, 44, qstride);
		return this;
	}
	
	@Field(45) 
	public int qscale_type() {
		return this.io.getIntField(this, 45);
	}
	
	@Field(45) 
	public AVFrame qscale_type(int qscale_type) {
		this.io.setIntField(this, 45, qscale_type);
		return this;
	}
	
	@Field(46) 
	public Pointer<AVBufferRef > qp_table_buf() {
		return this.io.getPointerField(this, 46);
	}
	
	@Field(46) 
	public AVFrame qp_table_buf(Pointer<AVBufferRef > qp_table_buf) {
		this.io.setPointerField(this, 46, qp_table_buf);
		return this;
	}
	
	@Field(47) 
	public Pointer<Pointer<AVBufferRef > > hw_frames_ctx() {
		return this.io.getPointerField(this, 47);
	}
	
	@Field(47) 
	public AVFrame hw_frames_ctx(Pointer<Pointer<AVBufferRef > > hw_frames_ctx) {
		this.io.setPointerField(this, 47, hw_frames_ctx);
		return this;
	}
}
