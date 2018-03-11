package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Callback;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.callback.AVFormatControlMessage;
import com.zekrom_64.ze.libav.enums.AVCodecID;
import com.zekrom_64.ze.libav.enums.AVDurationEstimationMethod;
import com.zekrom_64.ze.nat.ZEStruct;

public class AVFormatContext extends ZEStruct<AVFormatContext> {

	public AVFormatContext() {}
	
	public AVFormatContext(Pointer<AVFormatContext> s) {
		super(s);
	}
	static {
		BridJ.register();
	}
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
	/** C type : AVInputFormat* */
	@Field(1) 
	public Pointer<AVInputFormat > iformat() {
		return this.io.getPointerField(this, 1);
	}
	/** C type : AVInputFormat* */
	@Field(1) 
	public void iformat(Pointer<AVInputFormat > iformat) {
		this.io.setPointerField(this, 1, iformat);
		
	}
	/** C type : AVOutputFormat* */
	@Field(2) 
	public Pointer<AVOutputFormat > oformat() {
		return this.io.getPointerField(this, 2);
	}
	/** C type : AVOutputFormat* */
	@Field(2) 
	public void oformat(Pointer<AVOutputFormat > oformat) {
		this.io.setPointerField(this, 2, oformat);
		
	}
	/** C type : void* */
	@Field(3) 
	public Pointer<? > priv_data() {
		return this.io.getPointerField(this, 3);
	}
	/** C type : void* */
	@Field(3) 
	public void priv_data(Pointer<? > priv_data) {
		this.io.setPointerField(this, 3, priv_data);
		
	}
	/** C type : AVIOContext* */
	@Field(4) 
	public Pointer<AVIOContext > pb() {
		return this.io.getPointerField(this, 4);
	}
	/** C type : AVIOContext* */
	@Field(4) 
	public void pb(Pointer<AVIOContext > pb) {
		this.io.setPointerField(this, 4, pb);
		
	}
	@Field(5) 
	public int ctx_flags() {
		return this.io.getIntField(this, 5);
	}
	@Field(5) 
	public void ctx_flags(int ctx_flags) {
		this.io.setIntField(this, 5, ctx_flags);
		
	}
	@Field(6) 
	public int nb_streams() {
		return this.io.getIntField(this, 6);
	}
	@Field(6) 
	public void nb_streams(int nb_streams) {
		this.io.setIntField(this, 6, nb_streams);
		
	}
	/** C type : AVStream** */
	@Field(7) 
	public Pointer<Pointer<AVStream > > streams() {
		return this.io.getPointerField(this, 7);
	}
	/** C type : AVStream** */
	@Field(7) 
	public void streams(Pointer<Pointer<AVStream > > streams) {
		this.io.setPointerField(this, 7, streams);
		
	}
	/** C type : char[1024] */
	@Array({1024}) 
	@Field(8) 
	public Pointer<Byte > filename() {
		return this.io.getPointerField(this, 8);
	}
	@Field(9) 
	public long start_time() {
		return this.io.getLongField(this, 9);
	}
	@Field(9) 
	public void start_time(long start_time) {
		this.io.setLongField(this, 9, start_time);
		
	}
	@Field(10) 
	public long duration() {
		return this.io.getLongField(this, 10);
	}
	@Field(10) 
	public void duration(long duration) {
		this.io.setLongField(this, 10, duration);
		
	}
	@Field(11) 
	public int bit_rate() {
		return this.io.getIntField(this, 11);
	}
	@Field(11) 
	public void bit_rate(int bit_rate) {
		this.io.setIntField(this, 11, bit_rate);
		
	}
	@Field(12) 
	public int packet_size() {
		return this.io.getIntField(this, 12);
	}
	@Field(12) 
	public void packet_size(int packet_size) {
		this.io.setIntField(this, 12, packet_size);
		
	}
	@Field(13) 
	public int max_delay() {
		return this.io.getIntField(this, 13);
	}
	@Field(13) 
	public void max_delay(int max_delay) {
		this.io.setIntField(this, 13, max_delay);
		
	}
	@Field(14) 
	public int flags() {
		return this.io.getIntField(this, 14);
	}
	@Field(14) 
	public void flags(int flags) {
		this.io.setIntField(this, 14, flags);
		
	}
	@Field(15) 
	public int probesize() {
		return this.io.getIntField(this, 15);
	}
	@Field(15) 
	public void probesize(int probesize) {
		this.io.setIntField(this, 15, probesize);
		
	}
	@Field(16) 
	public int max_analyze_duration() {
		return this.io.getIntField(this, 16);
	}
	@Field(16) 
	public void max_analyze_duration(int max_analyze_duration) {
		this.io.setIntField(this, 16, max_analyze_duration);
		
	}
	/** C type : const uint8_t* */
	@Field(17) 
	public Pointer<Byte > key() {
		return this.io.getPointerField(this, 17);
	}
	/** C type : const uint8_t* */
	@Field(17) 
	public void key(Pointer<Byte > key) {
		this.io.setPointerField(this, 17, key);
		
	}
	@Field(18) 
	public int keylen() {
		return this.io.getIntField(this, 18);
	}
	@Field(18) 
	public void keylen(int keylen) {
		this.io.setIntField(this, 18, keylen);
		
	}
	@Field(19) 
	public int nb_programs() {
		return this.io.getIntField(this, 19);
	}
	@Field(19) 
	public void nb_programs(int nb_programs) {
		this.io.setIntField(this, 19, nb_programs);
		
	}
	/** C type : AVProgram** */
	@Field(20) 
	public Pointer<Pointer<AVProgram > > programs() {
		return this.io.getPointerField(this, 20);
	}
	/** C type : AVProgram** */
	@Field(20) 
	public void programs(Pointer<Pointer<AVProgram > > programs) {
		this.io.setPointerField(this, 20, programs);
		
	}
	/** C type : AVCodecID */
	@Field(21) 
	public IntValuedEnum<AVCodecID > video_codec_id() {
		return this.io.getEnumField(this, 21);
	}
	/** C type : AVCodecID */
	@Field(21) 
	public void video_codec_id(IntValuedEnum<AVCodecID > video_codec_id) {
		this.io.setEnumField(this, 21, video_codec_id);
		
	}
	/** C type : AVCodecID */
	@Field(22) 
	public IntValuedEnum<AVCodecID > audio_codec_id() {
		return this.io.getEnumField(this, 22);
	}
	/** C type : AVCodecID */
	@Field(22) 
	public void audio_codec_id(IntValuedEnum<AVCodecID > audio_codec_id) {
		this.io.setEnumField(this, 22, audio_codec_id);
		
	}
	/** C type : AVCodecID */
	@Field(23) 
	public IntValuedEnum<AVCodecID > subtitle_codec_id() {
		return this.io.getEnumField(this, 23);
	}
	/** C type : AVCodecID */
	@Field(23) 
	public void subtitle_codec_id(IntValuedEnum<AVCodecID > subtitle_codec_id) {
		this.io.setEnumField(this, 23, subtitle_codec_id);
		
	}
	@Field(24) 
	public int max_index_size() {
		return this.io.getIntField(this, 24);
	}
	@Field(24) 
	public void max_index_size(int max_index_size) {
		this.io.setIntField(this, 24, max_index_size);
		
	}
	@Field(25) 
	public int max_picture_bfufer() {
		return this.io.getIntField(this, 25);
	}
	@Field(25) 
	public void max_picture_bfufer(int max_picture_bfufer) {
		this.io.setIntField(this, 25, max_picture_bfufer);
		
	}
	@Field(26) 
	public int nb_chapters() {
		return this.io.getIntField(this, 26);
	}
	@Field(26) 
	public void nb_chapters(int nb_chapters) {
		this.io.setIntField(this, 26, nb_chapters);
		
	}
	/** C type : AVChapter** */
	@Field(27) 
	public Pointer<Pointer<AVChapter > > chapters() {
		return this.io.getPointerField(this, 27);
	}
	/** C type : AVChapter** */
	@Field(27) 
	public void chapters(Pointer<Pointer<AVChapter > > chapters) {
		this.io.setPointerField(this, 27, chapters);
		
	}
	/** C type : AVDictionary* */
	@Field(28) 
	public Pointer<AVDictionary > metadata() {
		return this.io.getPointerField(this, 28);
	}
	/** C type : AVDictionary* */
	@Field(28) 
	public void metadata(Pointer<AVDictionary > metadata) {
		this.io.setPointerField(this, 28, metadata);
		
	}
	@Field(29) 
	public long start_time_realtime() {
		return this.io.getLongField(this, 29);
	}
	@Field(29) 
	public void start_time_realtime(long start_time_realtime) {
		this.io.setLongField(this, 29, start_time_realtime);
		
	}
	@Field(30) 
	public int fps_probe_size() {
		return this.io.getIntField(this, 30);
	}
	@Field(30) 
	public void fps_probe_size(int fps_probe_size) {
		this.io.setIntField(this, 30, fps_probe_size);
		
	}
	@Field(31) 
	public int error_recognition() {
		return this.io.getIntField(this, 31);
	}
	@Field(31) 
	public void error_recognition(int error_recognition) {
		this.io.setIntField(this, 31, error_recognition);
		
	}
	/** C type : AVIOInterruptCB */
	@Field(32) 
	public AVIOInterruptCB interrupt_callback() {
		return this.io.getNativeObjectField(this, 32);
	}
	/** C type : AVIOInterruptCB */
	@Field(32) 
	public void interrupt_callback(AVIOInterruptCB interrupt_callback) {
		this.io.setNativeObjectField(this, 32, interrupt_callback);
		
	}
	@Field(33) 
	public int debug() {
		return this.io.getIntField(this, 33);
	}
	@Field(33) 
	public void debug(int debug) {
		this.io.setIntField(this, 33, debug);
		
	}
	@Field(34) 
	public long max_interleave_delta() {
		return this.io.getLongField(this, 34);
	}
	@Field(34) 
	public void max_interleave_delta(long max_interleave_delta) {
		this.io.setLongField(this, 34, max_interleave_delta);
		
	}
	@Field(35) 
	public int strict_std_compliance() {
		return this.io.getIntField(this, 35);
	}
	@Field(35) 
	public void strict_std_compliance(int strict_std_compliance) {
		this.io.setIntField(this, 35, strict_std_compliance);
		
	}
	@Field(36) 
	public int event_flags() {
		return this.io.getIntField(this, 36);
	}
	@Field(36) 
	public void event_flags(int event_flags) {
		this.io.setIntField(this, 36, event_flags);
		
	}
	@Field(37) 
	public int max_ts_probe() {
		return this.io.getIntField(this, 37);
	}
	@Field(37) 
	public void max_ts_probe(int max_ts_probe) {
		this.io.setIntField(this, 37, max_ts_probe);
		
	}
	@Field(38) 
	public int avoid_negative_ts() {
		return this.io.getIntField(this, 38);
	}
	@Field(38) 
	public void avoid_negative_ts(int avoid_negative_ts) {
		this.io.setIntField(this, 38, avoid_negative_ts);
		
	}
	@Field(39) 
	public int ts_id() {
		return this.io.getIntField(this, 39);
	}
	@Field(39) 
	public void ts_id(int ts_id) {
		this.io.setIntField(this, 39, ts_id);
		
	}
	@Field(40) 
	public int audio_preload() {
		return this.io.getIntField(this, 40);
	}
	@Field(40) 
	public void audio_preload(int audio_preload) {
		this.io.setIntField(this, 40, audio_preload);
		
	}
	@Field(41) 
	public int max_chunk_duration() {
		return this.io.getIntField(this, 41);
	}
	@Field(41) 
	public void max_chunk_duration(int max_chunk_duration) {
		this.io.setIntField(this, 41, max_chunk_duration);
		
	}
	@Field(42) 
	public int max_chunk_size() {
		return this.io.getIntField(this, 42);
	}
	@Field(42) 
	public void max_chunk_size(int max_chunk_size) {
		this.io.setIntField(this, 42, max_chunk_size);
		
	}
	@Field(43) 
	public int use_wallclock_as_timestamps() {
		return this.io.getIntField(this, 43);
	}
	@Field(43) 
	public void use_wallclock_as_timestamps(int use_wallclock_as_timestamps) {
		this.io.setIntField(this, 43, use_wallclock_as_timestamps);
		
	}
	@Field(44) 
	public int avio_flatgs() {
		return this.io.getIntField(this, 44);
	}
	@Field(44) 
	public void avio_flatgs(int avio_flatgs) {
		this.io.setIntField(this, 44, avio_flatgs);
		
	}
	/** C type : AVDurationEstimationMethod */
	@Field(45) 
	public IntValuedEnum<AVDurationEstimationMethod > duration_estimation_method() {
		return this.io.getEnumField(this, 45);
	}
	/** C type : AVDurationEstimationMethod */
	@Field(45) 
	public void duration_estimation_method(IntValuedEnum<AVDurationEstimationMethod > duration_estimation_method) {
		this.io.setEnumField(this, 45, duration_estimation_method);
		
	}
	@Field(46) 
	public long skip_initial_bytes() {
		return this.io.getLongField(this, 46);
	}
	@Field(46) 
	public void skip_initial_bytes(long skip_initial_bytes) {
		this.io.setLongField(this, 46, skip_initial_bytes);
		
	}
	@Field(47) 
	public int correct_ts_overflow() {
		return this.io.getIntField(this, 47);
	}
	@Field(47) 
	public void correct_ts_overflow(int correct_ts_overflow) {
		this.io.setIntField(this, 47, correct_ts_overflow);
		
	}
	@Field(48) 
	public int seek2any() {
		return this.io.getIntField(this, 48);
	}
	@Field(48) 
	public void seek2any(int seek2any) {
		this.io.setIntField(this, 48, seek2any);
		
	}
	@Field(49) 
	public int flush_packets() {
		return this.io.getIntField(this, 49);
	}
	@Field(49) 
	public void flush_packets(int flush_packets) {
		this.io.setIntField(this, 49, flush_packets);
		
	}
	@Field(50) 
	public int probe_score() {
		return this.io.getIntField(this, 50);
	}
	@Field(50) 
	public void probe_score(int probe_score) {
		this.io.setIntField(this, 50, probe_score);
		
	}
	@Field(51) 
	public int format_probesize() {
		return this.io.getIntField(this, 51);
	}
	@Field(51) 
	public void format_probesize(int format_probesize) {
		this.io.setIntField(this, 51, format_probesize);
		
	}
	/** C type : char* */
	@Field(52) 
	public Pointer<Byte > codec_whitelist() {
		return this.io.getPointerField(this, 52);
	}
	/** C type : char* */
	@Field(52) 
	public void codec_whitelist(Pointer<Byte > codec_whitelist) {
		this.io.setPointerField(this, 52, codec_whitelist);
		
	}
	/** C type : char* */
	@Field(53) 
	public Pointer<Byte > format_whitelist() {
		return this.io.getPointerField(this, 53);
	}
	/** C type : char* */
	@Field(53) 
	public void format_whitelist(Pointer<Byte > format_whitelist) {
		this.io.setPointerField(this, 53, format_whitelist);
		
	}
	/** C type : AVFormatInternal* */
	@Field(54) 
	public Pointer<AVFormatInternal > internal() {
		return this.io.getPointerField(this, 54);
	}
	/** C type : AVFormatInternal* */
	@Field(54) 
	public void internal(Pointer<AVFormatInternal > internal) {
		this.io.setPointerField(this, 54, internal);
		
	}
	@Field(55) 
	public int io_repositioned() {
		return this.io.getIntField(this, 55);
	}
	@Field(55) 
	public void io_repositioned(int io_repositioned) {
		this.io.setIntField(this, 55, io_repositioned);
		
	}
	/** C type : AVCodec* */
	@Field(56) 
	public Pointer<AVCodec > video_codec() {
		return this.io.getPointerField(this, 56);
	}
	/** C type : AVCodec* */
	@Field(56) 
	public void video_codec(Pointer<AVCodec > video_codec) {
		this.io.setPointerField(this, 56, video_codec);
		
	}
	/** C type : AVCodec* */
	@Field(57) 
	public Pointer<AVCodec > audio_codec() {
		return this.io.getPointerField(this, 57);
	}
	/** C type : AVCodec* */
	@Field(57) 
	public void audio_codec(Pointer<AVCodec > audio_codec) {
		this.io.setPointerField(this, 57, audio_codec);
		
	}
	/** C type : AVCodec* */
	@Field(58) 
	public Pointer<AVCodec > subtitle_codec() {
		return this.io.getPointerField(this, 58);
	}
	/** C type : AVCodec* */
	@Field(58) 
	public void subtitle_codec(Pointer<AVCodec > subtitle_codec) {
		this.io.setPointerField(this, 58, subtitle_codec);
		
	}
	/** C type : AVCodec* */
	@Field(59) 
	public Pointer<AVCodec > data_codec() {
		return this.io.getPointerField(this, 59);
	}
	/** C type : AVCodec* */
	@Field(59) 
	public void data_codec(Pointer<AVCodec > data_codec) {
		this.io.setPointerField(this, 59, data_codec);
		
	}
	@Field(60) 
	public int metadata_header_padding() {
		return this.io.getIntField(this, 60);
	}
	@Field(60) 
	public void metadata_header_padding(int metadata_header_padding) {
		this.io.setIntField(this, 60, metadata_header_padding);
		
	}
	/** C type : void* */
	@Field(61) 
	public Pointer<? > opaque() {
		return this.io.getPointerField(this, 61);
	}
	/** C type : void* */
	@Field(61) 
	public void opaque(Pointer<? > opaque) {
		this.io.setPointerField(this, 61, opaque);
		
	}
	/** C type : av_format_control_message */
	@Field(62) 
	public Pointer<AVFormatControlMessage > control_message_cb() {
		return this.io.getPointerField(this, 62);
	}
	/** C type : av_format_control_message */
	@Field(62) 
	public void control_message_cb(Pointer<AVFormatControlMessage > control_message_cb) {
		this.io.setPointerField(this, 62, control_message_cb);
		
	}
	@Field(63) 
	public long output_ts_offset() {
		return this.io.getLongField(this, 63);
	}
	@Field(63) 
	public void output_ts_offset(long output_ts_offset) {
		this.io.setLongField(this, 63, output_ts_offset);
		
	}
	@Field(64) 
	public long max_analyze_duration2() {
		return this.io.getLongField(this, 64);
	}
	@Field(64) 
	public void max_analyze_duration2(long max_analyze_duration2) {
		this.io.setLongField(this, 64, max_analyze_duration2);
		
	}
	@Field(65) 
	public long probesize2() {
		return this.io.getLongField(this, 65);
	}
	@Field(65) 
	public void probesize2(long probesize2) {
		this.io.setLongField(this, 65, probesize2);
		
	}
	/** C type : uint8_t* */
	@Field(66) 
	public Pointer<Byte > dump_separator() {
		return this.io.getPointerField(this, 66);
	}
	/** C type : uint8_t* */
	@Field(66) 
	public void dump_separator(Pointer<Byte > dump_separator) {
		this.io.setPointerField(this, 66, dump_separator);
		
	}
	/** C type : AVCodecID */
	@Field(67) 
	public IntValuedEnum<AVCodecID > data_codec_id() {
		return this.io.getEnumField(this, 67);
	}
	/** C type : AVCodecID */
	@Field(67) 
	public void data_codec_id(IntValuedEnum<AVCodecID > data_codec_id) {
		this.io.setEnumField(this, 67, data_codec_id);
		
	}
	/** C type : open_cb_callback* */
	@Field(68) 
	public Pointer<AVFormatContext.open_cb_callback > open_cb() {
		return this.io.getPointerField(this, 68);
	}
	
	/** C type : open_cb_callback* */
	@Field(68) 
	public void open_cb(Pointer<AVFormatContext.open_cb_callback > open_cb) {
		this.io.setPointerField(this, 68, open_cb);	
	}
	
	public static abstract class open_cb_callback extends Callback<open_cb_callback > {
		public abstract int apply(Pointer<AVFormatContext > s, Pointer<Pointer<AVIOContext > > p, Pointer<Byte > url, int flags, Pointer<AVIOInterruptCB > int_cb, Pointer<Pointer<AVDictionary > > options);
	};
	
	
	
}
