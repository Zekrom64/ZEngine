package com.zekrom_64.ze.libav.managed;

import com.zekrom_64.ze.libav.struct.AVCodecContext;

@Deprecated
public class AVVideoDecoder {

	public AVVideoConsumer videoConsumer;
	public AVAudioConsumer audioConsumer;
	public AVSubtitleConsumer subtitleConsumer;
	
	public final AVCodecContext videoCodecContext = new AVCodecContext();
	public final AVCodecContext audioCodecContext = new AVCodecContext();
	public final AVCodecContext subtitleCodecContext = new AVCodecContext();
	
}
