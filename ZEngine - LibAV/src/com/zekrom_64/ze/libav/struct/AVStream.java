package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVDiscard;
import com.zekrom_64.ze.libav.enums.AVStreamParseType;
import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVStream extends ZEStruct<AVStream> {

	static {
		BridJ.register();
	}
	/** < stream index in AVFormatContext */
	@Field(0) 
	public int index() {
		return this.io.getIntField(this, 0);
	}
	/** < stream index in AVFormatContext */
	@Field(0) 
	public AVStream index(int index) {
		this.io.setIntField(this, 0, index);
		return this;
	}
	/**
	 * Format-specific stream ID.<br>
	 * decoding: set by libavformat<br>
	 * encoding: set by the user, replaced by libavformat if left unset
	 */
	@Field(1) 
	public int id() {
		return this.io.getIntField(this, 1);
	}
	/**
	 * Format-specific stream ID.<br>
	 * decoding: set by libavformat<br>
	 * encoding: set by the user, replaced by libavformat if left unset
	 */
	@Field(1) 
	public AVStream id(int id) {
		this.io.setIntField(this, 1, id);
		return this;
	}
	/**
	 * Codec context associated with this stream. Allocated and freed by<br>
	 * libavformat.<br>
	 * * - decoding: The demuxer exports codec information stored in the headers<br>
	 *             here.<br>
	 * - encoding: The user sets codec information, the muxer writes it to the<br>
	 *             output. Mandatory fields as specified in AVCodecContext<br>
	 *             documentation must be set even if this AVCodecContext is<br>
	 *             not actually used for encoding.<br>
	 * C type : AVCodecContext*
	 */
	@Field(2) 
	public Pointer<AVCodecContext > codec() {
		return this.io.getPointerField(this, 2);
	}
	/**
	 * Codec context associated with this stream. Allocated and freed by<br>
	 * libavformat.<br>
	 * * - decoding: The demuxer exports codec information stored in the headers<br>
	 *             here.<br>
	 * - encoding: The user sets codec information, the muxer writes it to the<br>
	 *             output. Mandatory fields as specified in AVCodecContext<br>
	 *             documentation must be set even if this AVCodecContext is<br>
	 *             not actually used for encoding.<br>
	 * C type : AVCodecContext*
	 */
	@Field(2) 
	public AVStream codec(Pointer<AVCodecContext > codec) {
		this.io.setPointerField(this, 2, codec);
		return this;
	}
	/** C type : void* */
	@Field(3) 
	public Pointer<? > priv_data() {
		return this.io.getPointerField(this, 3);
	}
	/** C type : void* */
	@Field(3) 
	public AVStream priv_data(Pointer<? > priv_data) {
		this.io.setPointerField(this, 3, priv_data);
		return this;
	}
	/**
	 * This is the fundamental unit of time (in seconds) in terms<br>
	 * of which frame timestamps are represented.<br>
	 * * decoding: set by libavformat<br>
	 * encoding: May be set by the caller before avformat_write_header() to<br>
	 *           provide a hint to the muxer about the desired timebase. In<br>
	 *           avformat_write_header(), the muxer will overwrite this field<br>
	 *           with the timebase that will actually be used for the timestamps<br>
	 *           written into the file (which may or may not be related to the<br>
	 *           user-provided one, depending on the format).<br>
	 * C type : AVRational
	 */
	@Field(4) 
	public AVRational time_base() {
		return this.io.getNativeObjectField(this, 4);
	}
	/**
	 * This is the fundamental unit of time (in seconds) in terms<br>
	 * of which frame timestamps are represented.<br>
	 * * decoding: set by libavformat<br>
	 * encoding: May be set by the caller before avformat_write_header() to<br>
	 *           provide a hint to the muxer about the desired timebase. In<br>
	 *           avformat_write_header(), the muxer will overwrite this field<br>
	 *           with the timebase that will actually be used for the timestamps<br>
	 *           written into the file (which may or may not be related to the<br>
	 *           user-provided one, depending on the format).<br>
	 * C type : AVRational
	 */
	@Field(4) 
	public AVStream time_base(AVRational time_base) {
		this.io.setNativeObjectField(this, 4, time_base);
		return this;
	}
	/**
	 * Decoding: pts of the first frame of the stream, in stream time base.<br>
	 * Only set this if you are absolutely 100% sure that the value you set<br>
	 * it to really is the pts of the first frame.<br>
	 * This may be undefined (AV_NOPTS_VALUE).
	 */
	@Field(5) 
	public long start_time() {
		return this.io.getLongField(this, 5);
	}
	/**
	 * Decoding: pts of the first frame of the stream, in stream time base.<br>
	 * Only set this if you are absolutely 100% sure that the value you set<br>
	 * it to really is the pts of the first frame.<br>
	 * This may be undefined (AV_NOPTS_VALUE).
	 */
	@Field(5) 
	public AVStream start_time(long start_time) {
		this.io.setLongField(this, 5, start_time);
		return this;
	}
	/**
	 * Decoding: duration of the stream, in stream time base.<br>
	 * If a source file does not specify a duration, but does specify<br>
	 * a bitrate, this value will be estimated from bitrate and file size.
	 */
	@Field(6) 
	public long duration() {
		return this.io.getLongField(this, 6);
	}
	/**
	 * Decoding: duration of the stream, in stream time base.<br>
	 * If a source file does not specify a duration, but does specify<br>
	 * a bitrate, this value will be estimated from bitrate and file size.
	 */
	@Field(6) 
	public AVStream duration(long duration) {
		this.io.setLongField(this, 6, duration);
		return this;
	}
	/** < number of frames in this stream if known or 0 */
	@Field(7) 
	public long nb_frames() {
		return this.io.getLongField(this, 7);
	}
	/** < number of frames in this stream if known or 0 */
	@Field(7) 
	public AVStream nb_frames(long nb_frames) {
		this.io.setLongField(this, 7, nb_frames);
		return this;
	}
	/** < AV_DISPOSITION_* bit field */
	@Field(8) 
	public int disposition() {
		return this.io.getIntField(this, 8);
	}
	/** < AV_DISPOSITION_* bit field */
	@Field(8) 
	public AVStream disposition(int disposition) {
		this.io.setIntField(this, 8, disposition);
		return this;
	}
	/**
	 * < Selects which packets can be discarded at will and do not need to be demuxed.<br>
	 * C type : AVDiscard
	 */
	@Field(9) 
	public IntValuedEnum<AVDiscard > discard() {
		return this.io.getEnumField(this, 9);
	}
	/**
	 * < Selects which packets can be discarded at will and do not need to be demuxed.<br>
	 * C type : AVDiscard
	 */
	@Field(9) 
	public AVStream discard(IntValuedEnum<AVDiscard > discard) {
		this.io.setEnumField(this, 9, discard);
		return this;
	}
	/**
	 * sample aspect ratio (0 if unknown)<br>
	 * - encoding: Set by user.<br>
	 * - decoding: Set by libavformat.<br>
	 * C type : AVRational
	 */
	@Field(10) 
	public AVRational sample_aspect_ratio() {
		return this.io.getNativeObjectField(this, 10);
	}
	/**
	 * sample aspect ratio (0 if unknown)<br>
	 * - encoding: Set by user.<br>
	 * - decoding: Set by libavformat.<br>
	 * C type : AVRational
	 */
	@Field(10) 
	public AVStream sample_aspect_ratio(AVRational sample_aspect_ratio) {
		this.io.setNativeObjectField(this, 10, sample_aspect_ratio);
		return this;
	}
	/** C type : AVDictionary* */
	@Field(11) 
	public Pointer<AVDictionary > metadata() {
		return this.io.getPointerField(this, 11);
	}
	/** C type : AVDictionary* */
	@Field(11) 
	public AVStream metadata(Pointer<AVDictionary > metadata) {
		this.io.setPointerField(this, 11, metadata);
		return this;
	}
	/**
	 * Average framerate<br>
	 * * - demuxing: May be set by libavformat when creating the stream or in<br>
	 *             avformat_find_stream_info().<br>
	 * - muxing: May be set by the caller before avformat_write_header().<br>
	 * C type : AVRational
	 */
	@Field(12) 
	public AVRational avg_frame_rate() {
		return this.io.getNativeObjectField(this, 12);
	}
	/**
	 * Average framerate<br>
	 * * - demuxing: May be set by libavformat when creating the stream or in<br>
	 *             avformat_find_stream_info().<br>
	 * - muxing: May be set by the caller before avformat_write_header().<br>
	 * C type : AVRational
	 */
	@Field(12) 
	public AVStream avg_frame_rate(AVRational avg_frame_rate) {
		this.io.setNativeObjectField(this, 12, avg_frame_rate);
		return this;
	}
	/**
	 * For streams with AV_DISPOSITION_ATTACHED_PIC disposition, this packet<br>
	 * will contain the attached picture.<br>
	 * * decoding: set by libavformat, must not be modified by the caller.<br>
	 * encoding: unused<br>
	 * C type : AVPacket
	 */
	@Field(13) 
	public AVPacket attached_pic() {
		return this.io.getNativeObjectField(this, 13);
	}
	/**
	 * For streams with AV_DISPOSITION_ATTACHED_PIC disposition, this packet<br>
	 * will contain the attached picture.<br>
	 * * decoding: set by libavformat, must not be modified by the caller.<br>
	 * encoding: unused<br>
	 * C type : AVPacket
	 */
	@Field(13) 
	public AVStream attached_pic(AVPacket attached_pic) {
		this.io.setNativeObjectField(this, 13, attached_pic);
		return this;
	}
	/**
	 * An array of side data that applies to the whole stream (i.e. the<br>
	 * container does not allow it to change between packets).<br>
	 * * There may be no overlap between the side data in this array and side data<br>
	 * in the packets. I.e. a given side data is either exported by the muxer<br>
	 * (demuxing) / set by the caller (muxing) in this array, then it never<br>
	 * appears in the packets, or the side data is exported / sent through<br>
	 * the packets (always in the first packet where the value becomes known or<br>
	 * changes), then it does not appear in this array.<br>
	 * * - demuxing: Set by libavformat when the stream is created.<br>
	 * - muxing: May be set by the caller before avformat_write_header().<br>
	 * * Freed by libavformat in avformat_free_context().<br>
	 * C type : AVPacketSideData*
	 */
	@Field(14) 
	public Pointer<AVPacketSideData > side_data() {
		return this.io.getPointerField(this, 14);
	}
	/**
	 * An array of side data that applies to the whole stream (i.e. the<br>
	 * container does not allow it to change between packets).<br>
	 * * There may be no overlap between the side data in this array and side data<br>
	 * in the packets. I.e. a given side data is either exported by the muxer<br>
	 * (demuxing) / set by the caller (muxing) in this array, then it never<br>
	 * appears in the packets, or the side data is exported / sent through<br>
	 * the packets (always in the first packet where the value becomes known or<br>
	 * changes), then it does not appear in this array.<br>
	 * * - demuxing: Set by libavformat when the stream is created.<br>
	 * - muxing: May be set by the caller before avformat_write_header().<br>
	 * * Freed by libavformat in avformat_free_context().<br>
	 * C type : AVPacketSideData*
	 */
	@Field(14) 
	public AVStream side_data(Pointer<AVPacketSideData > side_data) {
		this.io.setPointerField(this, 14, side_data);
		return this;
	}
	/** The number of elements in the AVStream.side_data array. */
	@Field(15) 
	public int nb_side_data() {
		return this.io.getIntField(this, 15);
	}
	/** The number of elements in the AVStream.side_data array. */
	@Field(15) 
	public AVStream nb_side_data(int nb_side_data) {
		this.io.setIntField(this, 15, nb_side_data);
		return this;
	}
	/**
	 * Flags for the user to detect events happening on the stream. Flags must<br>
	 * be cleared by the user once the event has been handled.<br>
	 * A combination of AVSTREAM_EVENT_FLAG_*.
	 */
	@Field(16) 
	public int event_flags() {
		return this.io.getIntField(this, 16);
	}
	/**
	 * Flags for the user to detect events happening on the stream. Flags must<br>
	 * be cleared by the user once the event has been handled.<br>
	 * A combination of AVSTREAM_EVENT_FLAG_*.
	 */
	@Field(16) 
	public AVStream event_flags(int event_flags) {
		this.io.setIntField(this, 16, event_flags);
		return this;
	}
	/** C type : info_struct* */
	@Field(17) 
	public Pointer<AVStream.info_struct > info() {
		return this.io.getPointerField(this, 17);
	}
	/** C type : info_struct* */
	@Field(17) 
	public AVStream info(Pointer<AVStream.info_struct > info) {
		this.io.setPointerField(this, 17, info);
		return this;
	}
	/** < number of bits in pts (used for wrapping control) */
	@Field(18) 
	public int pts_wrap_bits() {
		return this.io.getIntField(this, 18);
	}
	/** < number of bits in pts (used for wrapping control) */
	@Field(18) 
	public AVStream pts_wrap_bits(int pts_wrap_bits) {
		this.io.setIntField(this, 18, pts_wrap_bits);
		return this;
	}
	/** Timestamp generation support: */
	@Field(19) 
	public long first_dts() {
		return this.io.getLongField(this, 19);
	}
	/** Timestamp generation support: */
	@Field(19) 
	public AVStream first_dts(long first_dts) {
		this.io.setLongField(this, 19, first_dts);
		return this;
	}
	@Field(20) 
	public long cur_dts() {
		return this.io.getLongField(this, 20);
	}
	@Field(20) 
	public AVStream cur_dts(long cur_dts) {
		this.io.setLongField(this, 20, cur_dts);
		return this;
	}
	@Field(21) 
	public long last_IP_pts() {
		return this.io.getLongField(this, 21);
	}
	@Field(21) 
	public AVStream last_IP_pts(long last_IP_pts) {
		this.io.setLongField(this, 21, last_IP_pts);
		return this;
	}
	@Field(22) 
	public int last_IP_duration() {
		return this.io.getIntField(this, 22);
	}
	@Field(22) 
	public AVStream last_IP_duration(int last_IP_duration) {
		this.io.setIntField(this, 22, last_IP_duration);
		return this;
	}
	@Field(23) 
	public int probe_packets() {
		return this.io.getIntField(this, 23);
	}
	@Field(23) 
	public AVStream probe_packets(int probe_packets) {
		this.io.setIntField(this, 23, probe_packets);
		return this;
	}
	/** Number of frames that have been demuxed during av_find_stream_info() */
	@Field(24) 
	public int codec_info_nb_frames() {
		return this.io.getIntField(this, 24);
	}
	/** Number of frames that have been demuxed during av_find_stream_info() */
	@Field(24) 
	public AVStream codec_info_nb_frames(int codec_info_nb_frames) {
		this.io.setIntField(this, 24, codec_info_nb_frames);
		return this;
	}
	/**
	 * av_read_frame() support<br>
	 * C type : AVStreamParseType
	 */
	@Field(25) 
	public IntValuedEnum<AVStreamParseType > need_parsing() {
		return this.io.getEnumField(this, 25);
	}
	/**
	 * av_read_frame() support<br>
	 * C type : AVStreamParseType
	 */
	@Field(25) 
	public AVStream need_parsing(IntValuedEnum<AVStreamParseType > need_parsing) {
		this.io.setEnumField(this, 25, need_parsing);
		return this;
	}
	/** C type : AVCodecParserContext* */
	@Field(26) 
	public Pointer<AVCodecParserContext > parser() {
		return this.io.getPointerField(this, 26);
	}
	/** C type : AVCodecParserContext* */
	@Field(26) 
	public AVStream parser(Pointer<AVCodecParserContext > parser) {
		this.io.setPointerField(this, 26, parser);
		return this;
	}
	/**
	 * last packet in packet_buffer for this stream when muxing.<br>
	 * C type : AVPacketList*
	 */
	@Field(27) 
	public Pointer<AVPacketList > last_in_packet_buffer() {
		return this.io.getPointerField(this, 27);
	}
	/**
	 * last packet in packet_buffer for this stream when muxing.<br>
	 * C type : AVPacketList*
	 */
	@Field(27) 
	public AVStream last_in_packet_buffer(Pointer<AVPacketList > last_in_packet_buffer) {
		this.io.setPointerField(this, 27, last_in_packet_buffer);
		return this;
	}
	/** C type : AVProbeData */
	@Field(28) 
	public AVProbeData probe_data() {
		return this.io.getNativeObjectField(this, 28);
	}
	/** C type : AVProbeData */
	@Field(28) 
	public AVStream probe_data(AVProbeData probe_data) {
		this.io.setNativeObjectField(this, 28, probe_data);
		return this;
	}
	/** C type : int64_t[16 + 1] */
	@Array({16 + 1}) 
	@Field(29) 
	public Pointer<Long > pts_buffer() {
		return this.io.getPointerField(this, 29);
	}
	/**
	 * < Only used if the format does not<br>
	 * support seeking natively.<br>
	 * C type : AVIndexEntry*
	 */
	@Field(30) 
	public Pointer<AVIndexEntry > index_entries() {
		return this.io.getPointerField(this, 30);
	}
	/**
	 * < Only used if the format does not<br>
	 * support seeking natively.<br>
	 * C type : AVIndexEntry*
	 */
	@Field(30) 
	public AVStream index_entries(Pointer<AVIndexEntry > index_entries) {
		this.io.setPointerField(this, 30, index_entries);
		return this;
	}
	@Field(31) 
	public int nb_index_entries() {
		return this.io.getIntField(this, 31);
	}
	@Field(31) 
	public AVStream nb_index_entries(int nb_index_entries) {
		this.io.setIntField(this, 31, nb_index_entries);
		return this;
	}
	@Field(32) 
	public int index_entries_allocated_size() {
		return this.io.getIntField(this, 32);
	}
	@Field(32) 
	public AVStream index_entries_allocated_size(int index_entries_allocated_size) {
		this.io.setIntField(this, 32, index_entries_allocated_size);
		return this;
	}
	public static class info_struct extends StructObject {
		static {
			BridJ.register();
		}
		@Field(0) 
		public int nb_decoded_frames() {
			return this.io.getIntField(this, 0);
		}
		@Field(0) 
		public info_struct nb_decoded_frames(int nb_decoded_frames) {
			this.io.setIntField(this, 0, nb_decoded_frames);
			return this;
		}
		@Field(1) 
		public int found_decoder() {
			return this.io.getIntField(this, 1);
		}
		@Field(1) 
		public info_struct found_decoder(int found_decoder) {
			this.io.setIntField(this, 1, found_decoder);
			return this;
		}
		/** Those are used for average framerate estimation. */
		@Field(2) 
		public long fps_first_dts() {
			return this.io.getLongField(this, 2);
		}
		/** Those are used for average framerate estimation. */
		@Field(2) 
		public info_struct fps_first_dts(long fps_first_dts) {
			this.io.setLongField(this, 2, fps_first_dts);
			return this;
		}
		@Field(3) 
		public int fps_first_dts_idx() {
			return this.io.getIntField(this, 3);
		}
		@Field(3) 
		public info_struct fps_first_dts_idx(int fps_first_dts_idx) {
			this.io.setIntField(this, 3, fps_first_dts_idx);
			return this;
		}
		@Field(4) 
		public long fps_last_dts() {
			return this.io.getLongField(this, 4);
		}
		@Field(4) 
		public info_struct fps_last_dts(long fps_last_dts) {
			this.io.setLongField(this, 4, fps_last_dts);
			return this;
		}
		@Field(5) 
		public int fps_last_dts_idx() {
			return this.io.getIntField(this, 5);
		}
		@Field(5) 
		public info_struct fps_last_dts_idx(int fps_last_dts_idx) {
			this.io.setIntField(this, 5, fps_last_dts_idx);
			return this;
		}
		public info_struct() {
			super();
		}
		public info_struct(Pointer<info_struct> pointer) {
			super(pointer);
		}
	};
	public AVStream() {
		super();
	}
	public AVStream(Pointer<AVStream> pointer) {
		super(pointer);
	}

}
