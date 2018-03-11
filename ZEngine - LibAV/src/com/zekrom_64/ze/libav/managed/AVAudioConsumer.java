package com.zekrom_64.ze.libav.managed;

@FunctionalInterface
public interface AVAudioConsumer {
	
	public static final AVAudioConsumer NULL = new AVAudioConsumer() {

		@Override
		public void consumeAudio(byte[] audioBytes) { }
		
	};

	public void consumeAudio(byte[] audioBytes);
	
}
