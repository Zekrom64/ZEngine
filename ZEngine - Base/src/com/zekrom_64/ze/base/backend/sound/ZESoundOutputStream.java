package com.zekrom_64.ze.base.backend.sound;

import java.io.IOException;

/** A sound output stream is a special type of output stream designed to write frames of audio.
 * 
 * @author Zekrom_64
 *
 */
public interface ZESoundOutputStream extends ZESoundStream {

	public default void writeFrame(byte[] frame) throws IOException {
		writeFrame(frame, 0, frame.length);
	}
	
	public void writeFrame(byte[] frame, int off, int len) throws IOException;
	
}
