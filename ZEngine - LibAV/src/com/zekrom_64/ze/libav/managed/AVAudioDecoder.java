package com.zekrom_64.ze.libav.managed;

import org.bridj.Pointer;

import com.zekrom_64.ze.libav.LibAVCodec;
import com.zekrom_64.ze.libav.enums.AVCodecID;
import com.zekrom_64.ze.libav.enums.AVMediaType;
import com.zekrom_64.ze.libav.struct.AVCodec;
import com.zekrom_64.ze.libav.struct.AVCodecContext;

public class AVAudioDecoder implements AVContext, AVSeekable{

	public AVAudioConsumer consumer = AVAudioConsumer.NULL;
	
	private boolean isRunning;
	public final AVCodecContext codecContext;
	public final AVCodec codec;
	
	private AVAudioDecoder(AVCodecID id) throws IllegalArgumentException, NullPointerException {
		Pointer<AVCodec> codec = LibAVCodec.avcodec_find_decoder(id);
		if (Pointer.NULL.equals(codec)) throw new IllegalArgumentException("Cannot find decoder for codec " + id.name());
		this.codec = new AVCodec(codec);
		Pointer<AVCodecContext> codecContext = LibAVCodec.avcodec_alloc_context3(codec);
		if (Pointer.NULL.equals(codecContext)) throw new NullPointerException("Failed to allocate codec context");
		this.codecContext = new AVCodecContext(codecContext);
		int err = LibAVCodec.avcodec_open2(codecContext, codec, null);
		if (err < 0) throw new IllegalStateException("Failed to open codec");
	}
	
	public static AVAudioDecoder create(AVCodecID id) {
		if (LibAVCodec.avcodec_get_type(id) != AVMediaType.AVMEDIA_TYPE_AUDIO) return null;
		return new AVAudioDecoder(id);
	}

	@Override
	public void seekToTime(double time) {
		
	}

	@Override
	public void seekToFrame(int frame) {
		
	}

	@Override
	public double getCurrentTime() {
		return 0;
	}

	@Override
	public int getCurrentFrame() {
		return 0;
	}

	@Override
	public void setRunning(boolean running) {
		isRunning = running;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}
	
}
