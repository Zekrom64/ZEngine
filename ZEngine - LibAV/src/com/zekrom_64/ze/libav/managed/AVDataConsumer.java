package com.zekrom_64.ze.libav.managed;

@FunctionalInterface
public interface AVDataConsumer {
	
	public static final AVDataConsumer NULL = new AVDataConsumer() {

		@Override
		public void consumeData(byte[] data) { }
	};

	public void consumeData(byte[] data);
	
}
