package com.zekrom_64.ze.base.backend.sound;

import com.zekrom_64.mathlib.tuple.impl.Vector3D;

/** A sound source is a single point that produces sound.
 * 
 * @author Zekrom_64
 *
 */
public interface ZESoundSource {

	/** Gets the position of the sound source.
	 * 
	 * @param vec Vector to get position into
	 */
	public void getPosition(Vector3D vec);
	
	/** Vector version of {@link #setPosition(double, double, double) setPosition()}.
	 * 
	 * @param vec Position to set
	 */
	public default void setPosition(Vector3D vec) {
		setPosition(vec.x, vec.y, vec.z);
	}
	
	/** Sets the position of the sound source.
	 * 
	 * @param x X position
	 * @param y Y position
	 * @param z Z position
	 */
	public void setPosition(double x, double y, double z);

	/** Gets the velocity of the sound source.
	 * 
	 * @param vec Vector to get velocity into
	 */
	public void getVelocity(Vector3D vec);
	
	/** Vector version of {@link #setVelocity(double, double, double) setVelocity()}.
	 * 
	 * @param vec Velocity to set
	 */
	public default void setVelocity(Vector3D vec) {
		setVelocity(vec.x, vec.y, vec.z);
	}
	
	/** Sets the velocity of the sound source.
	 * 
	 * @param x X position
	 * @param y Y position
	 * @param z Z position
	 */
	public void setVelocity(double x, double y, double z);
	
	/** The source play mode for a sound source controls how the source is played.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZESourcePlayMode {
		
		/** The sound source plays and stops at the end */
		PLAY,
		/** The sound source plays and repeats from the beginning if possible */
		REPEAT,
		/** The sound source is stopped and reset to the beginning if possible */
		STOP,
		/** The sound source is stopped and kept at the current position in the audio */
		PAUSE
		
	}
	
	/** Tests if the sound source supports the given play mode.
	 * 
	 * @param mode Play mode
	 * @return If the play mode is supported
	 */
	public boolean supportsPlayMode(ZESourcePlayMode mode);
	
	/** Sets the play mode of the sound source.
	 * 
	 * @param mode Play mode
	 */
	public void setPlayMode(ZESourcePlayMode mode);
	
	/** Gets the play mode of the sound source.
	 * 
	 * @return Play mode
	 */
	public ZESourcePlayMode getPlayMode();
	
	/** Sets the gain of the sound source.
	 * 
	 * @param gain Gain
	 */
	public void setGain(float gain);
	
	/** Gets the gain of the sound source.
	 * 
	 * @return Gain
	 */
	public float getGain();
	
	/** Sets the pitch of the sound source.
	 * 
	 * @param pitch Pitch
	 */
	public void setPitch(float pitch);
	
	/** Gets the pitch of the sound source.
	 * 
	 * @return Pitch
	 */
	public float getPitch();
	
}
