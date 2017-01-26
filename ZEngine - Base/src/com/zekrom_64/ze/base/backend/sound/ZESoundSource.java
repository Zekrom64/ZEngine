package com.zekrom_64.ze.base.backend.sound;

import javax.vecmath.Vector3d;

public interface ZESoundSource {

	public void getPosition(Vector3d vec);
	
	public default void setPosition(Vector3d vec) {
		setPosition(vec.x, vec.y, vec.z);
	}
	
	public void setPosition(double x, double y, double z);

	public void getVelocity(Vector3d vec);
	
	public default void setVelocity(Vector3d vec) {
		setVelocity(vec.x, vec.y, vec.z);
	}
	
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
		STOP
		
	}
	
	public void setPlayMode(ZESourcePlayMode mode);
	
	public ZESourcePlayMode getPlayMode();
	
	public void setGain(float gain);
	
	public float getGain();
	
	public void setPitch(float pitch);
	
	public float getPitch();
	
}
