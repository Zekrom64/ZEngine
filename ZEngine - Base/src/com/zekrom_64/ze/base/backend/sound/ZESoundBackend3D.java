package com.zekrom_64.ze.base.backend.sound;

import javax.sound.sampled.AudioFormat;

/** A 3D sound backend supports processing sound in a 3D environment.
 * 
 * @author Zekrom_64
 *
 */
public interface ZESoundBackend3D {
	
	/** The sound backend supports multiple listeners */
	public static final String FEATURE_MULTIPLE_LISTENERS = "ze.feature.multi.listener";
	/** The sound backend supports reading sound from a listener */
	public static final String FEATURE_LISTENER_READ = "ze.feature.listener.read";
	/** The sound backend supports adding filters and effects to sources */
	public static final String FEATURE_SOURCE_FILTER = "ze.feature.source.filter";
	
	/** Tests if the sound backend supports a given feature. If the given feature string is not
	 * recognized, false will be returned.
	 * 
	 * @param feature The feature to test for
	 * @return If the feature is available
	 */
	public boolean supportsFeature(String feature);
	
	/** Gets the audio format used by the sound backend.
	 * 
	 * @return Backend audio format
	 */
	public AudioFormat getBackendAudioFormat();
	
	/** Creates a sound source using a fixed buffer as the sound data.
	 * 
	 * @param buffer Sound data buffer
	 * @param format Audio format for sound
	 * @return Sound source
	 */
	public ZESoundSource createFixedSource(AudioFormat format, byte[] buffer);
	
	/** Creates a sound source using a sound input stream as the source
	 * of sound data.
	 * 
	 * @param streamsource The stream providing sound data
	 * @return Sound source
	 */
	public ZESoundSource createStreamingSource(ZESoundInputStream stream);
	
}
