package com.zekrom_64.ze.base.backend.sound;

/** A 0D sound backend only supports sound processing and mixing,
 * and does not support spatial sound.
 * 
 * @author Zekrom_64
 *
 */
public interface ZESoundBackend0D {

	public boolean supportsFeature(String feature);

	

}
