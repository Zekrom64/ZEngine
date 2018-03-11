package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.ann.CLong;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVIOContext extends ZEStruct<AVIOContext> {
	public static abstract class read_packet_callback extends Callback<read_packet_callback > {
		public abstract int apply(Pointer<? > opaque, Pointer<Byte > buf, int buf_size);
	}
	public static abstract class read_pause_callback extends Callback<read_pause_callback > {
		public abstract int apply(Pointer<? > opaque, int pause);
	}
	public static abstract class read_seek_callback extends Callback<read_seek_callback > {
		public abstract long apply(Pointer<? > opaque, int stream_index, long timestamp, int flags);
	}
	public static abstract class seek_callback extends Callback<seek_callback > {
		public abstract long apply(Pointer<? > opaque, long offset, int whence);
	}
	public static abstract class update_checksum_callback extends Callback<update_checksum_callback > {
		@CLong 
		public abstract long apply(@CLong long checksum, Pointer<Byte > buf, int size);
	}
	public static abstract class write_packet_callback extends Callback<write_packet_callback > {
		public abstract int apply(Pointer<? > opaque, Pointer<Byte > buf, int buf_size);
	}
	static {
		BridJ.register();
	}
	public AVIOContext() {
		super();
	}
	public AVIOContext(Pointer<AVIOContext> pointer) {
		super(pointer);
	}
	/** C type : const AVClass* */
	@Field(0) 
	public Pointer<AVClass > av_class() {
		return this.io.getPointerField(this, 0);
	}
	/** C type : const AVClass* */
	@Field(0) 
	public AVIOContext av_class(Pointer<AVClass > av_class) {
		this.io.setPointerField(this, 0, av_class);
		return this;
	}
	/** C type : unsigned char* */
	@Field(4) 
	public Pointer<Byte > buf_end() {
		return this.io.getPointerField(this, 4);
	}
	/** C type : unsigned char* */
	@Field(4) 
	public AVIOContext buf_end(Pointer<Byte > buf_end) {
		this.io.setPointerField(this, 4, buf_end);
		return this;
	}
	/** C type : unsigned char* */
	@Field(3) 
	public Pointer<Byte > buf_ptr() {
		return this.io.getPointerField(this, 3);
	}
	/** C type : unsigned char* */
	@Field(3) 
	public AVIOContext buf_ptr(Pointer<Byte > buf_ptr) {
		this.io.setPointerField(this, 3, buf_ptr);
		return this;
	}
	/** C type : unsigned char* */
	@Field(1) 
	public Pointer<Byte > buffer() {
		return this.io.getPointerField(this, 1);
	}
	/** C type : unsigned char* */
	@Field(1) 
	public AVIOContext buffer(Pointer<Byte > buffer) {
		this.io.setPointerField(this, 1, buffer);
		return this;
	}
	@Field(2) 
	public int buffer_size() {
		return this.io.getIntField(this, 2);
	}
	@Field(2) 
	public AVIOContext buffer_size(int buffer_size) {
		this.io.setIntField(this, 2, buffer_size);
		return this;
	}
	@CLong 
	@Field(14) 
	public long checksum() {
		return this.io.getCLongField(this, 14);
	}
	@CLong 
	@Field(14) 
	public AVIOContext checksum(long checksum) {
		this.io.setCLongField(this, 14, checksum);
		return this;
	}
	/** C type : unsigned char* */
	@Field(15) 
	public Pointer<Byte > checksum_ptr() {
		return this.io.getPointerField(this, 15);
	}
	/** C type : unsigned char* */
	@Field(15) 
	public AVIOContext checksum_ptr(Pointer<Byte > checksum_ptr) {
		this.io.setPointerField(this, 15, checksum_ptr);
		return this;
	}
	/** < true if eof reached */
	@Field(11) 
	public int eof_reached() {
		return this.io.getIntField(this, 11);
	}
	/** < true if eof reached */
	@Field(11) 
	public AVIOContext eof_reached(int eof_reached) {
		this.io.setIntField(this, 11, eof_reached);
		return this;
	}
	/** < contains the error code or 0 if no error happened */
	@Field(17) 
	public int error() {
		return this.io.getIntField(this, 17);
	}
	/** < contains the error code or 0 if no error happened */
	@Field(17) 
	public AVIOContext error(int error) {
		this.io.setIntField(this, 17, error);
		return this;
	}
	@Field(13) 
	public int max_packet_size() {
		return this.io.getIntField(this, 13);
	}
	@Field(13) 
	public AVIOContext max_packet_size(int max_packet_size) {
		this.io.setIntField(this, 13, max_packet_size);
		return this;
	}
	/** < true if the next seek should flush */
	@Field(10) 
	public int must_flush() {
		return this.io.getIntField(this, 10);
	}
	/** < true if the next seek should flush */
	@Field(10) 
	public AVIOContext must_flush(int must_flush) {
		this.io.setIntField(this, 10, must_flush);
		return this;
	}
	/** C type : void* */
	@Field(5) 
	public Pointer<? > opaque() {
		return this.io.getPointerField(this, 5);
	}
	/** C type : void* */
	@Field(5) 
	public AVIOContext opaque(Pointer<? > opaque) {
		this.io.setPointerField(this, 5, opaque);
		return this;
	}
	/** < position in the file of the current buffer */
	@Field(9) 
	public long pos() {
		return this.io.getLongField(this, 9);
	}
	/** < position in the file of the current buffer */
	@Field(9) 
	public AVIOContext pos(long pos) {
		this.io.setLongField(this, 9, pos);
		return this;
	}
	/** C type : read_packet_callback* */
	@Field(6) 
	public Pointer<AVIOContext.read_packet_callback > read_packet() {
		return this.io.getPointerField(this, 6);
	}
	/** C type : read_packet_callback* */
	@Field(6) 
	public AVIOContext read_packet(Pointer<AVIOContext.read_packet_callback > read_packet) {
		this.io.setPointerField(this, 6, read_packet);
		return this;
	}
	/**
	 * Pause or resume playback for network streaming protocols - e.g. MMS.<br>
	 * C type : read_pause_callback*
	 */
	@Field(18) 
	public Pointer<AVIOContext.read_pause_callback > read_pause() {
		return this.io.getPointerField(this, 18);
	}
	/**
	 * Pause or resume playback for network streaming protocols - e.g. MMS.<br>
	 * C type : read_pause_callback*
	 */
	@Field(18) 
	public AVIOContext read_pause(Pointer<AVIOContext.read_pause_callback > read_pause) {
		this.io.setPointerField(this, 18, read_pause);
		return this;
	}
	/**
	 * Seek to a given timestamp in stream with the specified stream_index.<br>
	 * Needed for some network streaming protocols which don't support seeking<br>
	 * to byte position.<br>
	 * C type : read_seek_callback*
	 */
	@Field(19) 
	public Pointer<AVIOContext.read_seek_callback > read_seek() {
		return this.io.getPointerField(this, 19);
	}
	/**
	 * Seek to a given timestamp in stream with the specified stream_index.<br>
	 * Needed for some network streaming protocols which don't support seeking<br>
	 * to byte position.<br>
	 * C type : read_seek_callback*
	 */
	@Field(19) 
	public AVIOContext read_seek(Pointer<AVIOContext.read_seek_callback > read_seek) {
		this.io.setPointerField(this, 19, read_seek);
		return this;
	}
	/** C type : seek_callback* */
	@Field(8) 
	public Pointer<AVIOContext.seek_callback > seek() {
		return this.io.getPointerField(this, 8);
	}
	/** C type : seek_callback* */
	@Field(8) 
	public AVIOContext seek(Pointer<AVIOContext.seek_callback > seek) {
		this.io.setPointerField(this, 8, seek);
		return this;
	}
	/** A combination of AVIO_SEEKABLE_ flags or 0 when the stream is not seekable. */
	@Field(20) 
	public int seekable() {
		return this.io.getIntField(this, 20);
	};
	/** A combination of AVIO_SEEKABLE_ flags or 0 when the stream is not seekable. */
	@Field(20) 
	public AVIOContext seekable(int seekable) {
		this.io.setIntField(this, 20, seekable);
		return this;
	};
	/** C type : update_checksum_callback* */
	@Field(16) 
	public Pointer<AVIOContext.update_checksum_callback > update_checksum() {
		return this.io.getPointerField(this, 16);
	};
	/** C type : update_checksum_callback* */
	@Field(16) 
	public AVIOContext update_checksum(Pointer<AVIOContext.update_checksum_callback > update_checksum) {
		this.io.setPointerField(this, 16, update_checksum);
		return this;
	};
	/** < true if open for writing */
	@Field(12) 
	public int write_flag() {
		return this.io.getIntField(this, 12);
	};
	/** < true if open for writing */
	@Field(12) 
	public AVIOContext write_flag(int write_flag) {
		this.io.setIntField(this, 12, write_flag);
		return this;
	};
	/** C type : write_packet_callback* */
	@Field(7) 
	public Pointer<AVIOContext.write_packet_callback > write_packet() {
		return this.io.getPointerField(this, 7);
	}
	/** C type : write_packet_callback* */
	@Field(7) 
	public AVIOContext write_packet(Pointer<AVIOContext.write_packet_callback > write_packet) {
		this.io.setPointerField(this, 7, write_packet);
		return this;
	}

}
