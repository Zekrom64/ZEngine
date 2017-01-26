package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVInputFormat extends Struct<AVInputFormat> {
	
	static {
		BridJ.register();
	}
	
	public AVInputFormat() {}
	
	public AVInputFormat(Pointer<AVInputFormat> p) { super(p); }
	
	/** C type : const char* */
	@Field(0) 
	public Pointer<Byte > name() {
		return this.io.getPointerField(this, 0);
	}
	/** C type : const char* */
	@Field(0) 
	public AVInputFormat name(Pointer<Byte > name) {
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
	public AVInputFormat long_name(Pointer<Byte > long_name) {
		this.io.setPointerField(this, 1, long_name);
		return this;
	}
	@Field(2) 
	public int flags() {
		return this.io.getIntField(this, 2);
	}
	@Field(2) 
	public AVInputFormat flags(int flags) {
		this.io.setIntField(this, 2, flags);
		return this;
	}
	/** C type : const char* */
	@Field(3) 
	public Pointer<Byte > extensions() {
		return this.io.getPointerField(this, 3);
	}
	/** C type : const char* */
	@Field(3) 
	public AVInputFormat extensions(Pointer<Byte > extensions) {
		this.io.setPointerField(this, 3, extensions);
		return this;
	}
	/** C type : const AVCodecTag** */
	@Field(4) 
	public Pointer<Pointer<AVCodecTag > > codec_tag() {
		return this.io.getPointerField(this, 4);
	}
	/** C type : const AVCodecTag** */
	@Field(4) 
	public AVInputFormat codec_tag(Pointer<Pointer<AVCodecTag > > codec_tag) {
		this.io.setPointerField(this, 4, codec_tag);
		return this;
	}
	/** C type : const AVClass* */
	@Field(5) 
	public Pointer<AVClass > priv_class() {
		return this.io.getPointerField(this, 5);
	}
	/** C type : const AVClass* */
	@Field(5) 
	public AVInputFormat priv_class(Pointer<AVClass > priv_class) {
		this.io.setPointerField(this, 5, priv_class);
		return this;
	}
	/** C type : const char* */
	@Field(6) 
	public Pointer<Byte > mime_type() {
		return this.io.getPointerField(this, 6);
	}
	/** C type : const char* */
	@Field(6) 
	public AVInputFormat mime_type(Pointer<Byte > mime_type) {
		this.io.setPointerField(this, 6, mime_type);
		return this;
	}
	/** C type : AVInputFormat* */
	@Field(7) 
	public Pointer<AVInputFormat > next() {
		return this.io.getPointerField(this, 7);
	}
	/** C type : AVInputFormat* */
	@Field(7) 
	public AVInputFormat next(Pointer<AVInputFormat > next) {
		this.io.setPointerField(this, 7, next);
		return this;
	}
	@Field(8) 
	public int raw_codec_id() {
		return this.io.getIntField(this, 8);
	}
	@Field(8) 
	public AVInputFormat raw_codec_id(int raw_codec_id) {
		this.io.setIntField(this, 8, raw_codec_id);
		return this;
	}
	@Field(9) 
	public int priv_data_size() {
		return this.io.getIntField(this, 9);
	}
	@Field(9) 
	public AVInputFormat priv_data_size(int priv_data_size) {
		this.io.setIntField(this, 9, priv_data_size);
		return this;
	}
	/** C type : read_probe_callback* */
	@Field(10) 
	public Pointer<AVInputFormat.read_probe_callback > read_probe() {
		return this.io.getPointerField(this, 10);
	}
	/** C type : read_probe_callback* */
	@Field(10) 
	public AVInputFormat read_probe(Pointer<AVInputFormat.read_probe_callback > read_probe) {
		this.io.setPointerField(this, 10, read_probe);
		return this;
	}
	/** C type : read_header_callback* */
	@Field(11) 
	public Pointer<AVInputFormat.read_header_callback > read_header() {
		return this.io.getPointerField(this, 11);
	}
	/** C type : read_header_callback* */
	@Field(11) 
	public AVInputFormat read_header(Pointer<AVInputFormat.read_header_callback > read_header) {
		this.io.setPointerField(this, 11, read_header);
		return this;
	}
	/** C type : read_packet_callback* */
	@Field(12) 
	public Pointer<AVInputFormat.read_packet_callback > read_packet() {
		return this.io.getPointerField(this, 12);
	}
	/** C type : read_packet_callback* */
	@Field(12) 
	public AVInputFormat read_packet(Pointer<AVInputFormat.read_packet_callback > read_packet) {
		this.io.setPointerField(this, 12, read_packet);
		return this;
	}
	/** C type : read_close_callback* */
	@Field(13) 
	public Pointer<AVInputFormat.read_close_callback > read_close() {
		return this.io.getPointerField(this, 13);
	}
	/** C type : read_close_callback* */
	@Field(13) 
	public AVInputFormat read_close(Pointer<AVInputFormat.read_close_callback > read_close) {
		this.io.setPointerField(this, 13, read_close);
		return this;
	}
	/** C type : read_seek_callback* */
	@Field(14) 
	public Pointer<AVInputFormat.read_seek_callback > read_seek() {
		return this.io.getPointerField(this, 14);
	}
	/** C type : read_seek_callback* */
	@Field(14) 
	public AVInputFormat read_seek(Pointer<AVInputFormat.read_seek_callback > read_seek) {
		this.io.setPointerField(this, 14, read_seek);
		return this;
	}
	/** C type : read_timestamp_callback* */
	@Field(15) 
	public Pointer<AVInputFormat.read_timestamp_callback > read_timestamp() {
		return this.io.getPointerField(this, 15);
	}
	/** C type : read_timestamp_callback* */
	@Field(15) 
	public AVInputFormat read_timestamp(Pointer<AVInputFormat.read_timestamp_callback > read_timestamp) {
		this.io.setPointerField(this, 15, read_timestamp);
		return this;
	}
	/** C type : read_play_callback* */
	@Field(16) 
	public Pointer<AVInputFormat.read_play_callback > read_play() {
		return this.io.getPointerField(this, 16);
	}
	/** C type : read_play_callback* */
	@Field(16) 
	public AVInputFormat read_play(Pointer<AVInputFormat.read_play_callback > read_play) {
		this.io.setPointerField(this, 16, read_play);
		return this;
	}
	/** C type : read_pause_callback* */
	@Field(17) 
	public Pointer<AVInputFormat.read_pause_callback > read_pause() {
		return this.io.getPointerField(this, 17);
	}
	/** C type : read_pause_callback* */
	@Field(17) 
	public AVInputFormat read_pause(Pointer<AVInputFormat.read_pause_callback > read_pause) {
		this.io.setPointerField(this, 17, read_pause);
		return this;
	}
	/** C type : read_seek2_callback* */
	@Field(18) 
	public Pointer<AVInputFormat.read_seek2_callback > read_seek2() {
		return this.io.getPointerField(this, 18);
	}
	/** C type : read_seek2_callback* */
	@Field(18) 
	public AVInputFormat read_seek2(Pointer<AVInputFormat.read_seek2_callback > read_seek2) {
		this.io.setPointerField(this, 18, read_seek2);
		return this;
	}
	/** C type : get_device_list_callback* */
	@Field(19) 
	public Pointer<AVInputFormat.get_device_list_callback > get_device_list() {
		return this.io.getPointerField(this, 19);
	}
	/** C type : get_device_list_callback* */
	@Field(19) 
	public AVInputFormat get_device_list(Pointer<AVInputFormat.get_device_list_callback > get_device_list) {
		this.io.setPointerField(this, 19, get_device_list);
		return this;
	}
	/** C type : create_device_capabilities_callback* */
	@Field(20) 
	public Pointer<AVInputFormat.create_device_capabilities_callback > create_device_capabilities() {
		return this.io.getPointerField(this, 20);
	}
	/** C type : create_device_capabilities_callback* */
	@Field(20) 
	public AVInputFormat create_device_capabilities(Pointer<AVInputFormat.create_device_capabilities_callback > create_device_capabilities) {
		this.io.setPointerField(this, 20, create_device_capabilities);
		return this;
	}
	/** C type : free_device_capabilities_callback* */
	@Field(21) 
	public Pointer<AVInputFormat.free_device_capabilities_callback > free_device_capabilities() {
		return this.io.getPointerField(this, 21);
	}
	/** C type : free_device_capabilities_callback* */
	@Field(21) 
	public AVInputFormat free_device_capabilities(Pointer<AVInputFormat.free_device_capabilities_callback > free_device_capabilities) {
		this.io.setPointerField(this, 21, free_device_capabilities);
		return this;
	}
	public static abstract class read_probe_callback extends Callback<read_probe_callback > {
		public abstract int apply(Pointer<AVProbeData > AVProbeDataPtr1);
	};
	public static abstract class read_header_callback extends Callback<read_header_callback > {
		public abstract int apply(Pointer<AVFormatContext > AVFormatContextPtr1);
	};
	public static abstract class read_packet_callback extends Callback<read_packet_callback > {
		public abstract int apply(Pointer<AVFormatContext > AVFormatContextPtr1, Pointer<AVPacket > pkt);
	};
	public static abstract class read_close_callback extends Callback<read_close_callback > {
		public abstract int apply(Pointer<AVFormatContext > AVFormatContextPtr1);
	};
	public static abstract class read_seek_callback extends Callback<read_seek_callback > {
		public abstract int apply(Pointer<AVFormatContext > AVFormatContextPtr1, int stream_index, long timestamp, int flags);
	};
	public static abstract class read_timestamp_callback extends Callback<read_timestamp_callback > {
		public abstract long apply(Pointer<AVFormatContext > s, int stream_index, Pointer<Long > pos, long pos_limit);
	};
	public static abstract class read_play_callback extends Callback<read_play_callback > {
		public abstract int apply(Pointer<AVFormatContext > AVFormatContextPtr1);
	};
	public static abstract class read_pause_callback extends Callback<read_pause_callback > {
		public abstract int apply(Pointer<AVFormatContext > AVFormatContextPtr1);
	};
	public static abstract class read_seek2_callback extends Callback<read_seek2_callback > {
		public abstract int apply(Pointer<AVFormatContext > s, int stream_index, long min_ts, long ts, long max_ts, int flags);
	};
	public static abstract class get_device_list_callback extends Callback<get_device_list_callback > {
		public abstract int apply(Pointer<AVFormatContext > s, Pointer<AVDeviceInfoList > device_list);
	};
	public static abstract class create_device_capabilities_callback extends Callback<create_device_capabilities_callback > {
		public abstract int apply(Pointer<AVFormatContext > s, Pointer<AVDeviceCapabilitiesQuery > caps);
	};
	public static abstract class free_device_capabilities_callback extends Callback<free_device_capabilities_callback > {
		public abstract int apply(Pointer<AVFormatContext > s, Pointer<AVDeviceCapabilitiesQuery > caps);
	};

}
