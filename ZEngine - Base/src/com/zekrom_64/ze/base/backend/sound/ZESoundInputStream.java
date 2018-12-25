package com.zekrom_64.ze.base.backend.sound;

import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;

/** A sound input stream is a special type of input stream designed to read frames of audio.
 * 
 * @author Zekrom_64
 *
 */
public interface ZESoundInputStream extends ZESoundStream {

	/** Reads a frame of audio from the audio stream. If the end of stream is reached, -1 is returned.
	 * 
	 * @param frame The byte array to read into
	 * @return Number of bytes read, or -1
	 * @throws IOException If an IOException occurs
	 */
	public default int readFrame(byte[] frame) throws IOException {
		return readFrame(frame, 0, frame.length);
	}
	
	/** Reads a frame of audio from the audio stream. If the end of stream is reached, -1 is returned.
	 * 
	 * @param frame The byte array to read into
	 * @param off The initial offset into the array
	 * @param len The length of bytes to read
	 * @return Number of bytes read, or -1
	 * @throws IOException If an IOException occurs
	 */
	public int readFrame(byte[] frame, int off, int len) throws IOException;
	
	public static class ZEAudioInputStream implements ZESoundInputStream {
		
		private AudioInputStream ais;
		
		public ZEAudioInputStream(AudioInputStream in) {
			ais = in;
		}
		
		
	}
	
}
