package com.zekrom_64.ze.base.backend.sound;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;

/** A sound stream supports additional operations related to audio data.
 * 
 * @author Zekrom_64
 *
 */
public interface ZESoundStream {

	/** Gets the audio format of the stream.
	 * 
	 * @return Audio format
	 */
	public AudioFormat getFormat();
	
	/** Tests if the sound stream supports random access seeking.
	 * 
	 * @return If the stream is seekable
	 */
	public boolean isSeekable();
	
	/** Attempts to seek to the given frame of audio.
	 * 
	 * @param frame Frame to seek to
	 * @throws IOException If an IOException occurs
	 */
	public void seekToFrame(int frame) throws IOException;
	
	/** Gets the index of the current frame of audio
	 * 
	 * @return Current audio frame index
	 */
	public int getCurrentFrame();
	
	/** Tests if the sound stream stream can be reset to the beginning.
	 * 
	 * @return If the stream can be reset
	 */
	public boolean isResetable();
	
	/** Resets the stream to the beginning.
	 * 
	 * @throws IOException If an IOException occurs
	 */
	public void reset() throws IOException;
	
}
