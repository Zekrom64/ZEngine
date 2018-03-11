package com.zekrom_64.ze.libav.managed;

import com.zekrom_64.ze.libav.LibAVCodec;
import com.zekrom_64.ze.libav.enums.AVCodecID;

public class AVCodecs {
	
	static {
		LibAVCodec.avcodec_register_all();
	}

	public static interface AVDecoder extends AVContext {
		
		public void consumeAudio(AVAudioConsumer consumer);
		
		public void consumeVideo(AVVideoConsumer consumer);
		
		public void consumeSubtitles(AVSubtitleConsumer consumer);
		
	}
	
	public static AVDecoder findDecoder(AVCodecID id) {
		
	}
	
}
