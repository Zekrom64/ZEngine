package com.zekrom_64.ze.libav;

import org.bridj.Pointer;
import org.bridj.ann.Library;
import org.bridj.ann.Optional;

import com.zekrom_64.ze.libav.callback.AVFormatControlMessage;
import com.zekrom_64.ze.libav.callback.AVOpenCallback;
import com.zekrom_64.ze.libav.enums.AVCodecID;
import com.zekrom_64.ze.libav.enums.AVDurationEstimationMethod;
import com.zekrom_64.ze.libav.enums.AVMediaType;
import com.zekrom_64.ze.libav.enums.AVPacketSideDataType;
import com.zekrom_64.ze.libav.struct.AVClass;
import com.zekrom_64.ze.libav.struct.AVCodec;
import com.zekrom_64.ze.libav.struct.AVCodecParserContext;
import com.zekrom_64.ze.libav.struct.AVCodecTag;
import com.zekrom_64.ze.libav.struct.AVDictionary;
import com.zekrom_64.ze.libav.struct.AVFormatContext;
import com.zekrom_64.ze.libav.struct.AVFrame;
import com.zekrom_64.ze.libav.struct.AVIOContext;
import com.zekrom_64.ze.libav.struct.AVInputFormat;
import com.zekrom_64.ze.libav.struct.AVOutputFormat;
import com.zekrom_64.ze.libav.struct.AVPacket;
import com.zekrom_64.ze.libav.struct.AVProbeData;
import com.zekrom_64.ze.libav.struct.AVProgram;
import com.zekrom_64.ze.libav.struct.AVRational;
import com.zekrom_64.ze.libav.struct.AVStream;

@Library("avformat")
public class LibAVFormat {
	
	public static final int
			AVPROBE_SCORE_MAX = 100,
			AVPROBE_SCORE_RETRY = AVPROBE_SCORE_MAX / 4,
			AVPROBE_SCORE_STREAM_RETRY = AVPROBE_SCORE_MAX / 4 - 1,
			AVPROBE_SCORE_EXTENSION = 50,
			AVPROBE_SCORE_MIME = 75,
			AVPROBE_PADDING_SIZE = 32,
			
			AVFMT_NOFILE = 0x0001,
			AVFMT_NEEDNUMBER = 0x0002,
			AVFMT_SHOW_IDS = 0x0008,
			AVFMT_RAWPICTURE = 0x0020,
			AVFMT_GLOBALHEADER = 0x0040,
			AVFMT_NOTIMESTAMPS = 0x0080,
			AVFMT_GENERIC_INDEX = 0x0100,
			AVFMT_TS_DISCONT = 0x0200,
			AVFMT_VARIABLE_FPS = 0x0400,
			AVFMT_NODIMENSIONS = 0x0800,
			AVFMT_NOSTREAMS = 0x1000,
			AVFMT_NOBINSEARCH = 0x2000,
			AVFMT_NOGENSEARCH = 0x4000,
			AVFMT_NO_BYTE_SEEK = 0x8000,
			AVFMT_ALLOW_FLUSH = 0x10000,
			AVFMT_TS_NONSTRICT = 0x8020000,
			AVFMT_TS_NEGATIVE = 0x40000,
			AVFMT_SEEK_TO_POS = 0x4000000,
			
			AVINDEX_KEYFRAME = 0x0001,
			
			AV_DISPOSITION_DEFAULT = 0x0001,
			AV_DISPOSITION_DUB = 0x0002,
			AV_DISPOSITION_ORIGINAL = 0x0004,
			AV_DISPOSITION_COMMENT = 0x0008,
			AV_DISPOSITION_LYRICS = 0x0010,
			AV_DISPOSITION_KARAOKE = 0x0020,
			AV_DISPOSITION_FORCED = 0x0040,
			AV_DISPOSITION_HEARING_IMPAIRED = 0x0080,
			AV_DISPOSITION_VISUAL_IMPAIRED = 0x0100,
			AV_DISPOSITION_CLEAN_EFFECTS = 0x0200,
			AV_DISPOSITION_ATTACHED_PIC = 0x0400,
			AV_DISPOSITION_CAPTIONS = 0x10000,
			AV_DISPOSITION_DESCRIPTIONS = 0x20000,
			AV_DISPOSITION_METADATA = 0x40000,
			
			AV_PTS_WRAP_IGNORE = 0,
			AV_PTS_WRAP_ADD_OFFSET = 1,
			AV_PTS_WRAP_SUB_OFFSET = -1,
			
			AVSTREAM_EVENT_FLAG_METADATA_UPDATED = 0x0001,
			
			MAX_STD_TIMEBASES = 30 * 12 + 7 + 6,
			MAX_PROBE_PACKETS = 2500,
			MAX_RECORDER_DELAY = 16,
			AV_PROGRAM_RUNNING = 1,
			AVFMTCTX_NOHEADER = 0x0001,
			
			AVFMT_FLAG_GENPTS = 0x0001,
			AVFMT_FLAG_IGNIDX = 0x0002,
			AVFMT_FLAG_NONBLOCK = 0x0004,
			AVFMT_FLAG_IGNDTS = 0x0008,
			AVFMT_FLAG_NOFILLIN = 0x0010,
			AVFMT_FLAG_NOPARSE = 0x0020,
			AVFMT_FLAG_NOBUFFER = 0x0040,
			AVFMT_FLAG_CUSTOM_IO = 0x0080,
			AVFMT_FLAG_DISCARD_CORRUPT = 0x0100,
			AVFMT_FLAG_FLUSH_PACKETS = 0x0200,
			AVFMT_FLAG_BITEXACT = 0x0400,
			AVFMT_FLAG_MP4A_LATM = 0x8000,
			AVFMT_FLAG_SORT_DTS = 0x10000,
			AVFMT_FLAG_PRIV_OPT = 0x20000,
			AVFMT_FLAG_KEEP_SIDE_DATA = 0x40000,
			AVFMT_FLAG_FAST_SEEK = 0x80000,
			
			FF_FDEBUG_TS = 0x0001,
			AVFMT_EVENT_FLAG_METADATA_UPDATED = 0x0001,
			AVFMT_AVOID_NEG_TS_AUTO = -1,
			AVFMT_AVOID_NEG_TS_MAKE_NON_NEGATIVE = 1,
			AVFMT_AVOID_NEG_TS_MAKE_ZERO = 2,
			
			AVSEEK_FLAG_BACKWARD = 1,
			AVSEEK_FLAG_BYTE = 2,
			AVSEEK_FLAG_ANY = 4,
			AVSEEK_FLAG_FRAME = 8;

	public static native int av_get_packet(Pointer<AVIOContext> s, Pointer<AVPacket> pkt, int size);
	
	public static native int av_append_packet(Pointer<AVIOContext> s, Pointer<AVPacket> pkt, int size);
	
	public static native AVRational av_stream_get_r_frame_rate(Pointer<AVStream> s);
	
	public static native void av_stream_set_r_frame_rate(Pointer<AVStream> s, AVRational r);
	
	public static native Pointer<AVCodecParserContext> av_stream_get_parser(Pointer<AVStream> s);
	
	public static native Pointer<Byte> av_stream_get_recommended_encoder_configuration(Pointer<AVStream> s);
	
	public static native void av_stream_set_recommended_encoder_configuration(Pointer<AVStream> s, Pointer<Byte> configuration);
	
	public static native long av_stream_get_end_pts(Pointer<AVStream> st);
	
	public static native int av_format_get_probe_score(Pointer<AVFormatContext> s);
	
	public static native Pointer<AVCodec> av_format_get_video_codec(Pointer<AVFormatContext> s);
	
	public static native void av_format_set_video_codec(Pointer<AVFormatContext> s, Pointer<AVCodec> c);
	
	public static native Pointer<AVCodec> av_format_get_audio_codec(Pointer<AVFormatContext> s);
	
	public static native void av_format_set_audio_codec(Pointer<AVFormatContext> s, Pointer<AVCodec> c);
	
	public static native Pointer<AVCodec> av_format_get_subtitle_codec(Pointer<AVFormatContext> s);
	
	public static native void av_format_set_subtitle_codec(Pointer<AVFormatContext> s, Pointer<AVCodec> c);
	
	public static native Pointer<AVCodec> av_format_get_data_codec(Pointer<AVFormatContext> s);
	
	public static native void av_format_set_data_codec(Pointer<AVFormatContext> s, Pointer<AVCodec> c);
	
	public static native int av_format_get_metadata_header_padding(Pointer<AVFormatContext> s);
	
	public static native void av_format_set_metadata_header_padding(Pointer<AVFormatContext> s);
	
	public static native Pointer<?> av_format_get_opaque(Pointer<AVFormatContext> s);
	
	public static native void av_format_set_opaque(Pointer<AVFormatContext> s, Pointer<?> opaque);
	
	public static native Pointer<AVFormatControlMessage> av_format_get_control_message_cb(Pointer<AVFormatContext> s);
	
	public static native void av_format_set_control_message_cb(Pointer<AVFormatContext> s, Pointer<AVFormatControlMessage> callback);
	
	public static native AVOpenCallback av_format_get_open_cb(Pointer<AVFormatContext> s);
	
	public static native void av_format_set_open_cb(Pointer<AVFormatContext> s, AVOpenCallback callback);
	
	public static native void av_format_inject_global_side_data(Pointer<AVFormatContext> s);
	
	public static native AVDurationEstimationMethod av_fmt_ctx_get_duration_estimation_method(Pointer<AVFormatContext> ctx);
	
	public static native int avformat_version();
	
	public static native Pointer<Byte> avformat_configuration();
	
	public static native Pointer<Byte> avformat_license();
	
	public static native void av_register_all();
	
	public static native void av_register_input_format(Pointer<AVInputFormat> format);
	
	public static native void av_register_output_format(Pointer<AVOutputFormat> format);
	
	public static native int avformat_network_init();
	
	public static native int avformat_network_deinit();
	
	public static native Pointer<AVInputFormat> av_iformat_next(Pointer<AVInputFormat> f);
	
	public static native Pointer<AVOutputFormat> av_oformat_next(Pointer<AVOutputFormat> f);
	
	public static native Pointer<AVFormatContext> avformat_alloc_context();
	
	public static native void avformat_free_context(Pointer<AVFormatContext> s);
	
	public static native Pointer<AVClass> avformat_get_class();
	
	public static native Pointer<AVStream> avformat_new_stream(Pointer<AVFormatContext> s, Pointer<AVCodec> c);
	
	public static native Pointer<Byte> av_stream_get_side_data(Pointer<AVStream> stream, AVPacketSideDataType type, Pointer<Integer> size);
	
	public static native Pointer<AVProgram> av_new_program(Pointer<AVFormatContext> s, int id);
	
	public static native int avformat_alloc_output_context2(Pointer<Pointer<AVFormatContext>> ctx, Pointer<AVOutputFormat> oformat, Pointer<Byte> format_name, Pointer<Byte> filename);
	
	public static native Pointer<AVInputFormat> av_find_input_format(Pointer<Byte> short_name);
	
	public static native Pointer<AVInputFormat> av_probe_input_format(Pointer<AVProbeData> pd, int is_opened);
	
	public static native Pointer<AVInputFormat> av_probe_input_format2(Pointer<AVProbeData> pd, int is_opened, Pointer<Integer> score_max);
	
	public static native Pointer<AVInputFormat> av_probe_input_format3(Pointer<AVProbeData> pd, int is_opened, Pointer<Integer> score_ret);
	
	public static native int av_probe_input_buffer2(Pointer<AVIOContext> pb, Pointer<Pointer<AVInputFormat>> fmt, Pointer<Byte> filename, Pointer<?> logctx, int offset, int max_probe_size);
	
	public static native int av_probe_input_buffer(Pointer<AVIOContext> pb, Pointer<Pointer<AVInputFormat>> fmt, Pointer<Byte> filename, Pointer<?> logctx, int offset, int max_probe_size);
	
	public static native int avformat_open_input(Pointer<Pointer<AVFormatContext>> ps, Pointer<Byte> filename, Pointer<AVInputFormat> fnt, Pointer<Pointer<AVDictionary>> options);
	
	@Deprecated
	@Optional
	public static native int av_demuxer_open(Pointer<AVFormatContext> ic);
	
	public static native int avformat_find_stream_info(Pointer<AVFormatContext> ic, Pointer<Pointer<AVDictionary>> options);
	
	public static native Pointer<AVProgram> av_find_program_from_stream(Pointer<AVFormatContext> ic, Pointer<AVProgram> last, int s);
	
	public static native int av_find_best_stream(Pointer<AVFormatContext> ic, AVMediaType type, int wanted_stream_nb, int related_stream, Pointer<Pointer<AVCodec>> decoder_ret, int flags);
	
	public static native int av_read_frame(Pointer<AVFormatContext> s, Pointer<AVPacket> pkt);
	
	public static native int av_seek_frame(Pointer<AVFormatContext> s, int stream_index, long timestamp, int flags);
	
	public static native int avformat_seek_file(Pointer<AVFormatContext> s, int stream_index, long min_ts, long ts, long max_ts, int flags);
	
	public static native int avformat_flush(Pointer<AVFormatContext> s);
	
	public static native int av_read_play(Pointer<AVFormatContext> s);
	
	public static native int av_read_pause(Pointer<AVFormatContext> s);
	
	public static native void avformat_close_input(Pointer<Pointer<AVFormatContext>> s);
	
	public static native int avformat_write_header(Pointer<AVFormatContext> s, Pointer<Pointer<AVDictionary>> options);
	
	public static native int av_write_frame(Pointer<AVFormatContext> s, Pointer<AVPacket> pkt);
	
	public static native int av_interleaved_write_frame(Pointer<AVFormatContext> s, Pointer<AVPacket> pkt);
	
	public static native int av_write_uncoded_frame(Pointer<AVFormatContext> s, int stream_index, Pointer<AVFrame> frame);
	
	public static native int av_interleaved_write_uncoded_frame(Pointer<AVFormatContext> s, int stream_index, Pointer<AVFrame> frame);
	
	public static native int av_write_uncoded_frame_query(Pointer<AVFormatContext> s, int stream_index);
	
	public static native int av_write_trailer(Pointer<AVFormatContext> s);
	
	public static native Pointer<AVOutputFormat> av_guess_format(Pointer<Byte> short_name, Pointer<Byte> filename, Pointer<Byte> mime_type);
	
	public static native AVCodecID av_guess_codec(Pointer<AVOutputFormat> fmt, Pointer<Byte> short_name, Pointer<Byte> mime_type, AVMediaType type);

	public static native int av_get_output_timestamp(Pointer<AVFormatContext> s, int stream, Pointer<Long> dts, Pointer<Long> wall);
	
	public static native AVCodecID av_codec_get_id(Pointer<Pointer<AVCodecTag>> tags, int tag);
	
	public static native int av_codec_get_tag(Pointer<Pointer<AVCodecTag>> tags, AVCodecID id);
	
	public static native int av_codec_get_tag2(Pointer<Pointer<AVCodecTag>> tags, AVCodecID id, Pointer<Integer> tag);
	
	public static native int av_find_default_stream_index(Pointer<AVFormatContext> s);
	
	public static native int av_index_search_timestamp(Pointer<AVStream> st, long timestamp, int flags);
	
	public static native int av_add_index_entry(Pointer<AVStream> st, long pos, long timestamp, int size, int distance, int flags);
	
	public static native void av_url_split(Pointer<Byte> proto, int proto_size, Pointer<Byte> authorization, int authorization_size, Pointer<Byte> hostname, int hostname_size, Pointer<Integer> port_ptr, Pointer<Byte> path, int path_size, Pointer<Byte> uri);
	
	public static native void av_dump_format(Pointer<AVFormatContext> ic, int index, Pointer<Byte> url, int is_output);
	
	public static native int av_get_frame_filename(Pointer<Byte> buf, int buf_size, Pointer<Byte> path, int number);
	
	public static native int av_filename_number_test(Pointer<Byte> filename);
	
	public static native int av_sdp_create(Pointer<Pointer<AVFormatContext>> ac, int n_files, Pointer<Byte> buf, int size);
	
	public static native int av_match_ext(Pointer<Byte> filename, Pointer<Byte> extentions);
	
	public static native int avformat_query_codec(Pointer<AVOutputFormat> ofmt, AVCodecID codec_id, int std_compliance);
	
	public static native Pointer<AVCodecTag> avformat_get_riff_video_tags();
	
	public static native Pointer<AVCodecTag> avformat_get_riff_audio_tags();
	
	public static native Pointer<AVCodecTag> avformat_get_mov_video_tags();
	
	public static native Pointer<AVCodecTag> avformat_get_mov_audio_tags();
	
	public static native AVRational av_guess_sample_aspect_ratio(Pointer<AVFormatContext> format, Pointer<AVStream> stream, Pointer<AVFrame> frame);
	
	public static native AVRational av_guess_frame_rate(Pointer<AVFormatContext> ctx, Pointer<AVStream> stream, Pointer<AVFrame> frame);
	
	public static native int avformat_match_stream_specifier(Pointer<AVFormatContext> s, Pointer<AVStream> st, Pointer<Byte> spec);
	
	public static native int avformat_queue_attached_pictures(Pointer<AVFormatContext> s);
	
}
