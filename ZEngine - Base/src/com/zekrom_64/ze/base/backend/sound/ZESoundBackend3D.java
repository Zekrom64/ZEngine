package com.zekrom_64.ze.base.backend.sound;

import javax.sound.sampled.AudioFormat;

public interface ZESoundBackend3D {
	/** The sound backend supports multiple listeners */
	public static final String FEATURE_MULTIPLE_LISTENERS = "ze.feature.multi.listener";
	/** The sound backend supports reading sound from a listener */
	public static final String FEATURE_LISTENER_READ = "ze.feature.listener.read";
	/** The sound backend supports adding filters and effects to sources */
	public static final String FEATURE_SOURCE_FILTER = "ze.feature.source.filter";
	
	/** Gets the audio format used by the sound backend.
	 * 
	 * @return Backend audio format
	 */
	public AudioFormat getBackendAudioFormat();
	
	/** Creates a sound source using a fixed buffer as the sound data.
	 * 
	 * @param buffer Sound data buffer
	 * @return Sound source
	 */
	public ZESoundSource createFixedSource(byte[] buffer);
	
	/** Creates a sound source
	 * 
	 * @param streamsource
	 * @return
	 */
	public ZESoundSource createStreamingSource(ZESoundInputStream stream);
	
}
