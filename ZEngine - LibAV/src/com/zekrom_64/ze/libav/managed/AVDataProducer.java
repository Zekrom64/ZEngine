package com.zekrom_64.ze.libav.managed;

@FunctionalInterface
public interface AVDataProducer {

	public static final AVDataProducer NULL = new AVDataProducer() {

		@Override
		public byte[] produceData() {
			return new byte[0];
		}
		
	};
	
	public byte[] produceData();
	
}
