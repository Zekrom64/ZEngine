package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Callback;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Field;
import org.bridj.ann.Ptr;

import com.zekrom_64.ze.libav.enums.AVCodecID;
import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVOutputFormat extends ZEStruct<AVOutputFormat> {

	static {
		BridJ.register();
	}
	
	public AVOutputFormat() {}
	
	public AVOutputFormat(Pointer<AVOutputFormat> p) { super(p); }

	/** C type : const char* */
	@Field(0) 
	public Pointer<Byte > name() {
		return this.io.getPointerField(this, 0);
	}
	/** C type : const char* */
	@Field(0) 
	public AVOutputFormat name(Pointer<Byte > name) {
		this.io.setPointerField(this, 0, name);
		return this;
	}
	/** C type : const char* */
	@Field(1) 
	public Pointer<Byte > long_name() {
		return this.io.getPointerField(this, 1);
	}
	/** C type : const char* */
	@Field(1) 
	public AVOutputFormat long_name(Pointer<Byte > long_name) {
		this.io.setPointerField(this, 1, long_name);
		return this;
	}
	/** C type : const char* */
	@Field(2) 
	public Pointer<Byte > mime_type() {
		return this.io.getPointerField(this, 2);
	}
	/** C type : const char* */
	@Field(2) 
	public AVOutputFormat mime_type(Pointer<Byte > mime_type) {
		this.io.setPointerField(this, 2, mime_type);
		return this;
	}
	/** C type : const char* */
	@Field(3) 
	public Pointer<Byte > extensions() {
		return this.io.getPointerField(this, 3);
	}
	/** C type : const char* */
	@Field(3) 
	public AVOutputFormat extensions(Pointer<Byte > extensions) {
		this.io.setPointerField(this, 3, extensions);
		return this;
	}
	/** C type : AVCodecID */
	@Field(4) 
	public IntValuedEnum<AVCodecID > audio_codec() {
		return this.io.getEnumField(this, 4);
	}
	/** C type : AVCodecID */
	@Field(4) 
	public AVOutputFormat audio_codec(IntValuedEnum<AVCodecID > audio_codec) {
		this.io.setEnumField(this, 4, audio_codec);
		return this;
	}
	/** C type : AVCodecID */
	@Field(5) 
	public IntValuedEnum<AVCodecID > video_codec() {
		return this.io.getEnumField(this, 5);
	}
	/** C type : AVCodecID */
	@Field(5) 
	public AVOutputFormat video_codec(IntValuedEnum<AVCodecID > video_codec) {
		this.io.setEnumField(this, 5, video_codec);
		return this;
	}
	/** C type : AVCodecID */
	@Field(6) 
	public IntValuedEnum<AVCodecID > subtitle_codec() {
		return this.io.getEnumField(this, 6);
	}
	/** C type : AVCodecID */
	@Field(6) 
	public AVOutputFormat subtitle_codec(IntValuedEnum<AVCodecID > subtitle_codec) {
		this.io.setEnumField(this, 6, subtitle_codec);
		return this;
	}
	@Field(7) 
	public int flags() {
		return this.io.getIntField(this, 7);
	}
	@Field(7) 
	public AVOutputFormat flags(int flags) {
		this.io.setIntField(this, 7, flags);
		return this;
	}
	/** C type : const AVCodecTag** */
	@Field(8) 
	public Pointer<Pointer<AVCodecTag > > codec_tag() {
		return this.io.getPointerField(this, 8);
	}
	/** C type : const AVCodecTag** */
	@Field(8) 
	public AVOutputFormat codec_tag(Pointer<Pointer<AVCodecTag > > codec_tag) {
		this.io.setPointerField(this, 8, codec_tag);
		return this;
	}
	/** C type : const AVClass* */
	@Field(9) 
	public Pointer<AVClass > priv_class() {
		return this.io.getPointerField(this, 9);
	}
	/** C type : const AVClass* */
	@Field(9) 
	public AVOutputFormat priv_class(Pointer<AVClass > priv_class) {
		this.io.setPointerField(this, 9, priv_class);
		return this;
	}
	/** C type : AVOutputFormat* */
	@Field(10) 
	public Pointer<AVOutputFormat > next() {
		return this.io.getPointerField(this, 10);
	}
	/** C type : AVOutputFormat* */
	@Field(10) 
	public AVOutputFormat next(Pointer<AVOutputFormat > next) {
		this.io.setPointerField(this, 10, next);
		return this;
	}
	@Field(11) 
	public int priv_data_size() {
		return this.io.getIntField(this, 11);
	}
	@Field(11) 
	public AVOutputFormat priv_data_size(int priv_data_size) {
		this.io.setIntField(this, 11, priv_data_size);
		return this;
	}
	/** C type : write_header_callback* */
	@Field(12) 
	public Pointer<AVOutputFormat.write_header_callback > write_header() {
		return this.io.getPointerField(this, 12);
	}
	/** C type : write_header_callback* */
	@Field(12) 
	public AVOutputFormat write_header(Pointer<AVOutputFormat.write_header_callback > write_header) {
		this.io.setPointerField(this, 12, write_header);
		return this;
	}
	/** C type : write_packet_callback* */
	@Field(13) 
	public Pointer<AVOutputFormat.write_packet_callback > write_packet() {
		return this.io.getPointerField(this, 13);
	}
	/** C type : write_packet_callback* */
	@Field(13) 
	public AVOutputFormat write_packet(Pointer<AVOutputFormat.write_packet_callback > write_packet) {
		this.io.setPointerField(this, 13, write_packet);
		return this;
	}
	/** C type : write_trailer_callback** */
	@Field(14) 
	public Pointer<Pointer<AVOutputFormat.write_trailer_callback > > write_trailer() {
		return this.io.getPointerField(this, 14);
	}
	/** C type : write_trailer_callback** */
	@Field(14) 
	public AVOutputFormat write_trailer(Pointer<Pointer<AVOutputFormat.write_trailer_callback > > write_trailer) {
		this.io.setPointerField(this, 14, write_trailer);
		return this;
	}
	/** C type : interleave_packet_callback* */
	@Field(15) 
	public Pointer<AVOutputFormat.interleave_packet_callback > interleave_packet() {
		return this.io.getPointerField(this, 15);
	}
	/** C type : interleave_packet_callback* */
	@Field(15) 
	public AVOutputFormat interleave_packet(Pointer<AVOutputFormat.interleave_packet_callback > interleave_packet) {
		this.io.setPointerField(this, 15, interleave_packet);
		return this;
	}
	/** C type : query_codec_callback* */
	@Field(16) 
	public Pointer<AVOutputFormat.query_codec_callback > query_codec() {
		return this.io.getPointerField(this, 16);
	}
	/** C type : query_codec_callback* */
	@Field(16) 
	public AVOutputFormat query_codec(Pointer<AVOutputFormat.query_codec_callback > query_codec) {
		this.io.setPointerField(this, 16, query_codec);
		return this;
	}
	/** C type : get_output_timestamp_callback* */
	@Field(17) 
	public Pointer<AVOutputFormat.get_output_timestamp_callback > get_output_timestamp() {
		return this.io.getPointerField(this, 17);
	}
	/** C type : get_output_timestamp_callback* */
	@Field(17) 
	public AVOutputFormat get_output_timestamp(Pointer<AVOutputFormat.get_output_timestamp_callback > get_output_timestamp) {
		this.io.setPointerField(this, 17, get_output_timestamp);
		return this;
	}
	/** C type : control_message_callback* */
	@Field(18) 
	public Pointer<AVOutputFormat.control_message_callback > control_message() {
		return this.io.getPointerField(this, 18);
	}
	/** C type : control_message_callback* */
	@Field(18) 
	public AVOutputFormat control_message(Pointer<AVOutputFormat.control_message_callback > control_message) {
		this.io.setPointerField(this, 18, control_message);
		return this;
	}
	/** C type : write_uncoded_frame_callback* */
	@Field(19) 
	public Pointer<AVOutputFormat.write_uncoded_frame_callback > write_uncoded_frame() {
		return this.io.getPointerField(this, 19);
	}
	/** C type : write_uncoded_frame_callback* */
	@Field(19) 
	public AVOutputFormat write_uncoded_frame(Pointer<AVOutputFormat.write_uncoded_frame_callback > write_uncoded_frame) {
		this.io.setPointerField(this, 19, write_uncoded_frame);
		return this;
	}
	public static abstract class write_header_callback extends Callback<write_header_callback > {
		public abstract int apply(Pointer<AVFormatContext > AVFormatContextPtr1);
	};
	public static abstract class write_packet_callback extends Callback<write_packet_callback > {
		public abstract int apply(Pointer<AVFormatContext > AVFormatContextPtr1, Pointer<AVPacket > AVPacketPtr1);
	};
	public static abstract class write_trailer_callback extends Callback<write_trailer_callback > {
		public abstract int apply(Pointer<AVFormatContext > AVFormatContextPtr1);
	};
	public static abstract class interleave_packet_callback extends Callback<interleave_packet_callback > {
		public abstract int apply(Pointer<AVFormatContext > AVFormatContextPtr1, Pointer<AVPacket > out, Pointer<AVPacket > in, int flush);
	};
	public static abstract class query_codec_callback extends Callback<query_codec_callback > {
		public abstract int apply(IntValuedEnum<AVCodecID > id, int std_compliance);
	};
	public static abstract class get_output_timestamp_callback extends Callback<get_output_timestamp_callback > {
		public abstract void apply(Pointer<AVFormatContext > s, int stream, Pointer<Long > dst, Pointer<Long > wall);
	};
	public static abstract class control_message_callback extends Callback<control_message_callback > {
		public abstract int apply(Pointer<AVFormatContext> s, int type, Pointer<? > data, @Ptr long data_size);
	};
	public static abstract class write_uncoded_frame_callback extends Callback<write_uncoded_frame_callback > {
		public abstract int apply(Pointer<AVFormatContext > AVFOrmatContextPtr1, int stream_index, Pointer<Pointer<AVFrame > > frame, int flags);
	};
	
}
