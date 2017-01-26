package com.zekrom_64.ze.libav;

import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.SizeT;
import org.bridj.ann.Library;
import org.bridj.ann.Optional;

import com.zekrom_64.ze.libav.enums.AVChromaLocation;
import com.zekrom_64.ze.libav.enums.AVCodecID;
import com.zekrom_64.ze.libav.enums.AVLockOp;
import com.zekrom_64.ze.libav.enums.AVMediaType;
import com.zekrom_64.ze.libav.enums.AVPacketSideDataType;
import com.zekrom_64.ze.libav.enums.AVPixelFormat;
import com.zekrom_64.ze.libav.enums.AVSampleFormat;
import com.zekrom_64.ze.libav.struct.AVBitStreamFilter;
import com.zekrom_64.ze.libav.struct.AVBitStreamFilterContext;
import com.zekrom_64.ze.libav.struct.AVClass;
import com.zekrom_64.ze.libav.struct.AVCodec;
import com.zekrom_64.ze.libav.struct.AVCodecContext;
import com.zekrom_64.ze.libav.struct.AVCodecDescriptor;
import com.zekrom_64.ze.libav.struct.AVCodecParser;
import com.zekrom_64.ze.libav.struct.AVCodecParserContext;
import com.zekrom_64.ze.libav.struct.AVDictionary;
import com.zekrom_64.ze.libav.struct.AVFrame;
import com.zekrom_64.ze.libav.struct.AVHWAccel;
import com.zekrom_64.ze.libav.struct.AVPacket;
import com.zekrom_64.ze.libav.struct.AVPicture;
import com.zekrom_64.ze.libav.struct.AVRational;
import com.zekrom_64.ze.libav.struct.AVResampleContext;
import com.zekrom_64.ze.libav.struct.AVSubtitle;
import com.zekrom_64.ze.libav.struct.misc.ReSampleContext;

@Library("avcodec")
public class LibAVCodec {
	
	public static final int
		AV_CODEC_PROP_INTRA_ONLY = 1,
		AV_CODEC_PROP_LOSSY = 2,
		AV_CODEC_PROP_LOSSLESS = 4,
		AV_CODEC_PROP_REORDER = 8;
	
	public static final int
		CODEC_FLAG_UNALIGNED = 1,
		CODEC_FLAG_QSCALE = 2,
		CODEC_FLAG_4MV = 4,
		CODEC_FLAG_OUTPUT_CORRUPT = 8,
		CODEC_FLAG_QPEL = 0x10,
		CODEC_FLAG_PASS1 = 0x200,
		CODEC_FLAG_PASS2 = 0x400,
		CODEC_FLAG_GRAY = 0x2000,
		CODEC_FLAG_PSNR = 0x8000,
		CODEC_FLAG_TRUNCATED = 0x10000,
		CODEC_FLAG_INTERLACED_DCT = 0x40000,
		CODEC_FLAG_LOW_DELAY = 0x80000,
		CODEC_FLAG_GLOBAL_HEADER = 0x400000,
		CODEC_FLAG_BITEXTRACT = 0x800000,
		CODEC_FLAG_AC_PRED = 0x1000000,
		CODEC_FLAG_LOOP_FILTER = 0x800,
		CODEC_FLAG_INTERLACED_ME = 0x20000000,
		CODEC_FLAG_CLOSED_GOP = 0x80000000,
		CODEC_FLAG2_FAST = 1,
		CODEC_FLAG2_NO_OUTPUT = 4,
		CODEC_FLAG2_LOCAL_HEADER = 8,
		CODEC_FLAG2_IGNORE_CROP = 0x10000,
		CODEC_FLAG2_CHUNKS = 0x8000;
	
	public static final int
		CODEC_CAP_DRAW_HORIZ_BAND = 1,
		CODEC_CAP_DR1 = 2,
		CODEC_CAP_TRUNCATED = 8,
		CODEC_CAP_DELAY = 0x20,
		CODEC_CAP_SUBFRAMES = 0x100,
		CODEC_CAP_EXPERIMENTAL = 0x200,
		CODEC_CAP_CHANNEL_CONF = 0x400,
		CODEC_CAP_FRAME_THREADS = 0x1000,
		CODEC_CAP_SLICE_THREADS = 0x2000,
		CODEC_CAP_PARAM_CHANGE = 0x4000,
		CODEC_CAP_AUTO_THREADS = 0x8000,
		CODEC_CAP_VARIABLE_FRAME_SIZE = 0x10000;
	
	public static final int FF_INPUT_BUFFER_PADDING_SIZE = 8;
	public static final int FF_MIN_BUFFER_SIZE = 16384;
	
	public static final int AV_NUM_DATA_POINTERS = 8;
	
	
	public static native AVRational av_codec_get_pkt_timebase(Pointer<AVCodecContext> avctx);
	
	public static native void av_codec_set_lkt_timebase(Pointer<AVCodecContext> avctx, AVRational val);
	
	public static native Pointer<AVCodecDescriptor> av_codec_get_codec_descriptor(Pointer<AVCodecContext> avctx);
	
	public static native void av_codec_set_codec_descriptor(Pointer<AVCodecContext> ctx, Pointer<AVCodecDescriptor> desc);
	
	public static native int av_codec_get_lowres(Pointer<AVCodecContext> avctx);
	
	public static native void av_codec_set_lowres(Pointer<AVCodecContext> ctx, int val);
	
	public static native int av_codec_get_seek_preroll(Pointer<AVCodecContext> avctx);
	
	public static native void av_codec_set_seek_preroll(Pointer<AVCodecContext> ctx, int val);
	
	public static native Pointer<Short> av_codec_get_chroma_intra_matrix(Pointer<AVCodecContext> avctx);
	
	public static native void av_codec_set_chroma_intra_matrix(Pointer<AVCodecContext> ctx, Pointer<Short> val);
	
	public static native int av_codec_get_max_lowres(Pointer<AVCodec> codec);
	
	
	public static native Pointer<AVCodec> av_codec_next(Pointer<AVCodec> c);
	
	public static native int avcodec_version();
	
	public static native Pointer<Byte> avcodec_configuration();
	
	public static native Pointer<Byte> avcodc_license();
	
	public static native void avcodec_register(Pointer<AVCodec> codec);
	
	public static native void avcodec_register_all();
	
	public static native Pointer<AVCodecContext> avcodec_alloc_context3(Pointer<AVCodec> codec);
	
	public static native void avcodec_free_context(Pointer<Pointer<AVCodecContext>> avctx);
	
	public static native int avcodec_get_context_defaults3(Pointer<AVCodecContext> a, Pointer<AVCodec> codec);
	
	public static native Pointer<AVClass> avcodec_get_class();
	
	public static native int avcodec_copy_context(Pointer<AVCodecContext> dest, Pointer<AVCodecContext> src);
	
	public static native int avcodec_open2(Pointer<AVCodecContext> avctx, Pointer<AVCodec> codec, Pointer<Pointer<AVDictionary>> options);
	
	public static native int avcodec_close(Pointer<AVCodecContext> avctx);
	
	public static native void avsubtitle_free(Pointer<AVSubtitle> sub);
	
	@Optional
	@Deprecated
	public static native void av_destruct_packet(Pointer<AVPacket> pkt);
	
	public static native void av_init_packet(Pointer<AVPacket> pkt);
	
	public static native int av_new_packet(Pointer<AVPacket> pkt, int size);
	
	public static native void av_shrink_packet(Pointer<AVPacket> pkt, int size);
	
	public static native int av_grow_packet(Pointer<AVPacket> pkt, int growby);
	
	public static native int av_packet_from_data(Pointer<AVPacket> pkt, Pointer<Byte> data, int size);
	
	public static native int av_dup_packet(Pointer<AVPacket> pkt);
	
	public static native void av_free_packet(Pointer<AVPacket> pkt);
	
	public static native Pointer<Byte> av_packet_new_side_data(Pointer<AVPacket> pkt, AVPacketSideDataType type, int size);
	
	public static native int av_packet_shrink_side_data(Pointer<AVPacket> pkt, AVPacketSideDataType type, int size);
	
	public static native Pointer<Byte> av_packet_get_side_data(Pointer<AVPacket> pkt, AVPacketSideDataType type, Pointer<Integer> size);
	
	public static native void av_packet_free_side_data(Pointer<AVPacket> pkt);
	
	public static native int av_packet_ref(Pointer<AVPacket> dst, Pointer<AVPacket> src);
	
	public static native void av_packet_unref(Pointer<AVPacket> pkt);
	
	public static native void av_packet_move_ref(Pointer<AVPacket> dst, Pointer<AVPacket> src);
	
	public static native int av_packet_copy_props(Pointer<AVPacket> dst, Pointer<AVPacket> src);
	
	public static native void av_packet_rescale_ts(Pointer<AVPacket> pkt, AVRational tb_src, AVRational tb_dst);
	
	
	public static native Pointer<AVCodec> avcodec_find_decoder(AVCodecID id);
	
	public static native Pointer<AVCodec> avcodec_find_decoder_by_name(Pointer<Byte> name);
	
	@Optional
	@Deprecated
	public static native int avcodec_default_get_buffer(Pointer<AVCodecContext> s, Pointer<AVFrame> pic);
	
	@Optional
	@Deprecated
	public static native void avcodec_default_release_buffer(Pointer<AVCodecContext> s, Pointer<AVFrame> pic);
	
	@Optional
	@Deprecated
	public static native int avcodec_default_reget_buffer(Pointer<AVCodecContext> s, Pointer<AVFrame> pic);
	
	public static native int avcodec_default_get_buffer2(Pointer<AVCodecContext> s, Pointer<AVFrame> frame, int flags);
	
	@Optional
	@Deprecated
	public static native int avcodec_get_edge_width();
	
	public static native void avcodec_align_dimensions(Pointer<AVCodecContext> s, Pointer<Integer> width, Pointer<Integer> height);
	
	public static native void avcodec_align_dimensions2(Pointer<AVCodecContext> s, Pointer<Integer> width, Pointer<Integer> height, Pointer<Integer> linesize_align);
	
	
	public static native int avcodec_enum_to_chroma_pos(Pointer<Integer> xpos, Pointer<Integer> ypos, AVChromaLocation pos);
	
	
	@Optional
	@Deprecated
	public static native int avcodec_decode_audio3(Pointer<AVCodecContext> avctx, Pointer<Short> samples, Pointer<Integer> frame_size_ptr, Pointer<AVPacket> avpkt);
	
	public static native int avcodec_decode_audio4(Pointer<AVCodecContext> avctx, Pointer<AVFrame> frame, Pointer<Integer> got_frame_ptr, Pointer<AVPacket> avpkt);
	
	public static native int avcodec_decode_video2(Pointer<AVCodecContext> avctx, Pointer<AVFrame> picture, Pointer<Integer> got_picture_ptr, Pointer<AVPacket> avpkt);
	
	public static native int avcodec_decode_subtitle2(Pointer<AVCodecContext> avctx, Pointer<AVSubtitle> sub, Pointer<Integer> got_sub_ptr, Pointer<AVPacket> avpkt);
	
	
	public static native Pointer<AVCodecParser> av_parser_next(Pointer<AVCodecParser> c);
	
	public static native void av_register_codec_parser(Pointer<AVCodecParser> parser);
	
	public static native Pointer<AVCodecParserContext> av_parser_init(int codec_id);
	
	public static native int av_parser_parse2(Pointer<AVCodecParserContext> s, Pointer<AVCodecContext> avctx, Pointer<Pointer<Byte>> poutbuf, Pointer<Integer> poutbuf_size, Pointer<Byte> buf, int buf_size, long pts, long dts, long pos);
	
	public static native int av_parser_change(Pointer<AVCodecParserContext> s, Pointer<AVCodecContext> avctx, Pointer<Pointer<Byte>> poutbuf, Pointer<Integer> poutbuf_size, Pointer<Byte> buf, int buf_size, int keyframe);
	
	public static native void av_parser_close(Pointer<AVCodecParserContext> s);
	
	
	public static native Pointer<AVCodec> avcodec_find_encoder(AVCodecID id);
	
	public static native Pointer<AVCodec> avcodec_find_encoder_by_name(Pointer<Byte> name);
	
	@Optional
	@Deprecated
	public static native int avcodec_encode_audio(Pointer<AVCodecContext> avctx, Pointer<Byte> buf, int buf_size, Pointer<Short> samples);
	
	public static native int avcodec_encode_audio2(Pointer<AVCodecContext> avctx, Pointer<AVPacket> avpkt, Pointer<AVFrame> frame, Pointer<Integer> got_packet_ptr);
	
	@Optional
	@Deprecated
	public static native int avcodec_encode_video(Pointer<AVCodecContext> avctx, Pointer<Byte> buf, int buf_size, Pointer<AVFrame> pict);
	
	public static native int avcodec_encode_video2(Pointer<AVCodecContext> avctx, Pointer<AVPacket> avpkt, Pointer<AVFrame> frame, Pointer<Integer> got_packet_ptr);
	
	public static native int avcodec_encode_subtitle(Pointer<AVCodecContext> avctx, Pointer<Byte> buf, int buf_size, Pointer<AVSubtitle> sub);
	
	@Optional
	@Deprecated
	public static native Pointer<ReSampleContext> av_audio_resample_init(int output_channels, int input_channels, int output_rate, int input_rate, AVSampleFormat sample_fmt_out, AVSampleFormat sample_fmt_in, int filter_length, int log2_phase_count, int linear, double cutoff);
	
	@Optional
	@Deprecated
	public static native int audio_resample(Pointer<ReSampleContext> s, Pointer<Short> output, Pointer<Short> input, int nb_samples);
	
	@Optional
	@Deprecated
	public static native void audio_resample_close(Pointer<ReSampleContext> s);
	
	@Optional
	@Deprecated
	public static native Pointer<ReSampleContext> av_resample_init(int out_rate, int in_rate, int filter_length, int log2_phase_count, int linear, double cutoff);
	
	@Optional
	@Deprecated
	public static native int av_resample(Pointer<AVResampleContext> c, Pointer<Short> dst, Pointer<Short> src, Pointer<Integer> consumed, int src_size, int dst_size, int update_ctx);
	
	@Optional
	@Deprecated
	public static native void av_resample_compensate(Pointer<AVResampleContext> c, int sample_delta, int compensation_distance);
	
	@Optional
	@Deprecated
	public static native void av_resample_close(Pointer<AVResampleContext> c);
	
	
	public static native int avpicture_alloc(Pointer<AVPicture> picture, AVPixelFormat pix_fmt, int width, int height);
	
	public static native void avpicture_free(Pointer<AVPicture> picture);
	
	public static native int avpicture_fill(Pointer<AVPicture> picture, Pointer<Byte> ptr, AVPixelFormat pix_fmt, int width, int height);
	
	public static native int avpicture_layout(Pointer<AVPicture> picture, AVPixelFormat pix_fmt, int width, int height);

	public static native int avpicture_get_size(AVPixelFormat pix_fmt, int width, int height);
	
	@Deprecated
	public static native int avpicture_deinterlace(Pointer<AVPicture> dst, Pointer<AVPicture> src, AVPixelFormat pix_fmt, int width, int height);
	
	public static native void av_picture_copy(Pointer<AVPicture> dst, Pointer<AVPicture> src, AVPixelFormat pix_fmt, int width, int height);
	
	public static native int av_picture_crop(Pointer<AVPicture> dst, Pointer<AVPicture> src, AVPixelFormat pix_fmt, int top_band, int left_band);
	
	public static native int av_picture_pad(Pointer<AVPicture> dst, Pointer<AVPicture> src, int height, int width, AVPixelFormat pix_fmt, int padtop, int padbottom, int padleft, int padright, Pointer<Integer> color);
	
	
	public static native void avcodec_get_chroma_sub_sample(AVPixelFormat pix_fmt, Pointer<Integer> h_shift, Pointer<Integer> v_shift);
	
	public static native int avcodec_pix_fmt_to_codec_tag(AVPixelFormat pix_fmt);
	
	public static native int avcodec_get_pix_fmt_loss(AVPixelFormat dst_pix_fmt, AVPixelFormat src_pix_fmt, int has_alpha);
	
	public static native AVPixelFormat avcodec_find_best_pix_fmt_of_list(Pointer<AVPixelFormat> pix_fmt_list, AVPixelFormat src_pic_fmt, int has_alpha, Pointer<Integer> loss_ptr);
	
	public static native AVPixelFormat avcodec_find_best_pix_fmt_of_2(AVPixelFormat dst_pix_fmt1, AVPixelFormat dst_pix_fmt2, AVPixelFormat src_pix_fmt, int has_alpha, Pointer<Integer> loss_ptr);
	
	@Optional
	@Deprecated
	public static native AVPixelFormat avcodec_find_base_pix_fmt2(AVPixelFormat dst_pix_fmt1, AVPixelFormat dst_pix_fmt2, AVPixelFormat src_pix_fmt, int has_alpha, Pointer<Integer> loss_ptr);
	
	public static native AVPixelFormat avcodec_default_get_format(Pointer<AVCodecContext> s, Pointer<AVPixelFormat> fmt);
	
	@Optional
	@Deprecated
	public static native void avcodec_set_dimensions(Pointer<AVCodecContext> s, int width, int height);
	
	public static native SizeT av_get_codec_tag_string(Pointer<Byte> buf, SizeT buf_size, int codec_tag);
	
	public static native void avcodec_string(Pointer<Byte> buf, int buf_size, Pointer<AVCodecContext> enc, int encode);
	
	public static native Pointer<Byte> av_get_profile_name(Pointer<AVCodec> codec, int profile);
	
	public static abstract class AVCodecExecuteCallback extends Callback<AVCodecExecuteCallback> {
		
		public abstract int invoke(Pointer<AVCodecContext> c2, Pointer<?> arg2);
		
	}
	
	public static native int avcodec_default_execute(Pointer<AVCodecContext> c, Pointer<AVCodecExecuteCallback> func, Pointer<?> arg, Pointer<Integer> ret, int count, int size);
	
	public static abstract class AVCodecExecuteCallback2 extends Callback<AVCodecExecuteCallback2> {
		
		public abstract int invoke(Pointer<AVCodecContext> c2, Pointer<?> arg2, int i1, int i2);
		
	}
	
	public static native int avcodec_default_execute(Pointer<AVCodecContext> c, Pointer<AVCodecExecuteCallback2> func, Pointer<?> arg, Pointer<Integer> ret, int count);
	
	public static native int avcodec_fill_audio_frame(Pointer<AVFrame> frame, int bn_channels, AVSampleFormat sample_fmt, Pointer<Byte> buf, int buf_size, int align);
	
	public static native void avcodec_flush_buffers(Pointer<AVCodecContext> avctx);
	
	public static native int av_get_bits_per_sample(AVCodecID codec_id);
	
	public static native int av_get_audio_frame_duration(Pointer<AVCodecContext> avctx, int frame_bytes);
	
	public static native void av_register_bitstream_filter(Pointer<AVBitStreamFilter> bsf);
	
	public static native Pointer<AVBitStreamFilterContext> av_bitstream_filter_init(Pointer<Byte> name);
	
	public static native int av_bitstream_filter_filter(Pointer<AVBitStreamFilterContext> bsfc, Pointer<AVCodecContext> avctx, Pointer<Byte> args, Pointer<Pointer<Byte>> poutbuf, Pointer<Integer> poutbuf_size, Pointer<Byte> size, int buf_size, int keyframe);
	
	public static native void av_bitstream_filter_close(Pointer<AVBitStreamFilterContext> bsf);
	
	public static native Pointer<AVBitStreamFilter> av_bitstream_filter_next(Pointer<AVBitStreamFilter> f);
	
	
	public static native void av_fast_padded_malloc(Pointer<?> ptr, Pointer<Integer> size, SizeT min_size);
	
	public static native void av_fast_padded_mallocz(Pointer<?> ptr, Pointer<Integer> size, SizeT min_size);
	
	public static native int av_ziphlacing(Pointer<Byte> c, int v);
	
	@Optional
	@Deprecated
	public static native void av_log_missing_feature(Pointer<?> avc, Pointer<Byte> feature, int want_sample);
	
	//public static native void av_log_ask_for_sample(Pointer<?> avc, Pointer<Byte> msg, )
	
	@Optional
	@Deprecated
	public static native void av_register_hwaccel(Pointer<AVHWAccel> hwaccel);
	
	public static native Pointer<AVHWAccel> av_hwaccel_next(Pointer<AVHWAccel> hwaccel);
	
	public static abstract class AVCodecLockManager extends Callback<AVCodecLockManager> {
		
		public abstract int invoke(Pointer<Pointer<?>> mutex, AVLockOp op);
		
	}
	
	public static native int av_lockmgr_register(Pointer<AVCodecLockManager> cb);
	
	public static native AVMediaType avcodec_get_type(AVCodecID codec_id);
	
	public static native Pointer<Byte> avcodec_get_name(AVCodecID id);
	
	public static native int avcodec_is_open(Pointer<AVCodecContext> s);
	
	public static native int av_codec_is_encoder(Pointer<AVCodec> codec);
	
	public static native int av_codec_is_decoder(Pointer<AVCodec> codec);
	
	public static native Pointer<AVCodecDescriptor> avcodec_descriptor_get(AVCodecID id);
	
	public static native Pointer<AVCodecDescriptor> avcodec_descriptor_next(Pointer<AVCodecDescriptor> prev);
	
	public static native Pointer<AVCodecDescriptor> avocdec_descriptor_get_by_name(Pointer<Byte> name);
	
}
