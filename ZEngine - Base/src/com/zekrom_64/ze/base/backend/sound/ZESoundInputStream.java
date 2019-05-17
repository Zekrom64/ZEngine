package com.zekrom_64.ze.base.backend.sound;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
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
	
	/** Reads a number of frames of audio from the audio stream. If the end of stream is reached, -1 is returned.
	 * 
	 * @param framebuf The byte array to read into
	 * @param off The initial offset into the array in bytes
	 * @param len The number of frames to read
	 * @return Number of frames read, or -1
	 * @throws IOException If an IOException occurs
	 */
	public int readFrame(byte[] framebuf, int off, int len) throws IOException;
	
	/** Wraps a Java {@link AudioInputStream} to a sound input stream.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZEAudioInputStream implements ZESoundInputStream {
		
		private AudioInputStream ais;
		private boolean canReset;
		private int frameSize;
		private int frameOffset;
		
		public ZEAudioInputStream(AudioInputStream in) {
			ais = in;
			frameSize = in.getFormat().getFrameSize();
			if (canReset = in.markSupported()) in.mark(Integer.MAX_VALUE);
		}

		@Override
		public AudioFormat getFormat() {
			return ais.getFormat();
		}

		@Override
		public boolean isSeekable() {
			return false;
		}

		@Override
		public void seekToFrame(int frame) throws IOException {
			throw new IOException("Cannot seek to an arbitary frame with an AudioInputStream");
		}

		@Override
		public int getCurrentFrame() {
			return frameOffset;
		}

		@Override
		public boolean isResetable() {
			return canReset;
		}

		@Override
		public void reset() throws IOException {
			ais.reset();
		}

		@Override
		public int readFrame(byte[] framebuf, int off, int len) throws IOException {
			int lenBytes = len * frameSize;
			int bytesRead = ais.read(framebuf, off, lenBytes);
			if (bytesRead == -1) return -1;
			int framesRead = bytesRead / frameSize;
			frameOffset += framesRead;
			return framesRead;
		}
		
		
	}
	
}
