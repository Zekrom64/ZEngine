package com.zekrom_64.ze.logic.gpu;

/** A palette controller controls the palette settings of a graphics device. Palettes can have
 * a specific pixel format as well as a specific palette format. The pixel format specifies how
 * pixels are stored in VRAM and the palette format specifies how palette values are stored.
 * 
 * @author Zekrom_64
 *
 */
public interface IPaletteController {

	/** The pixel format describes how pixel values are stored in memory.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum PixelFormat {
		/** A pixel format taking 16 bits, 5 used for red and blue, and 6 used for green */
		FORMAT_RGB565(16),
		/** A pixel format taking 24 bits, 8 used for red, green, and blue. */
		FORMAT_RGB8(24),
		/** A pixel format taking 32 bits, 8 used for red, green, blue, and an alpha value */
		FORMAT_RGBA8(32);
		
		/** The number of bits per pixel */
		public final int bitsPerPixel;
		private PixelFormat(int bpp) {
			bitsPerPixel = bpp;
		}
	}
	
	/** The palette format describes how palette values are stored in memory.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum PaletteFormat {
		/** No palette is used, instead, the values stored in vram correspond to each pixel */
		FORMAT_NO_PALETTE(0),
		/** The palette is made up of 256 entries, each in the pixel format specified */
		FORMAT_INDEX8(8),
		/** THe palette is made up of 65536 entries, each in the pixel format specified */
		FORMAT_INDEX16(16);
		
		public final int bitsPerPaletteIndex;
		private PaletteFormat(int bppi) {
			bitsPerPaletteIndex = bppi;
		}
	}
	
	/** Attempts to set the palette format of the controller.
	 * 
	 * @param format The new palette format
	 * @return If the format was successfully set
	 */
	public boolean setPaletteFormat(PaletteFormat format);
	
	/** Tests to see if the given palette format is compatible with this controller.
	 * 
	 * @param format The palette format
	 * @return If the palette format is supported
	 */
	public boolean isPaletteFormatSupported(PaletteFormat format);
	
	/** Gets the current palette format of the controller.
	 * 
	 * @return The current palette format
	 */
	public PaletteFormat getPaletteFormat();
	
	/** Attempts to set the pixel format of the controller.
	 * 
	 * @param format The new pixel format
	 * @return If the format was successfully set
	 */
	public boolean setPixelFormat(PixelFormat format);
	
	/** Tests to see if the given pixel format is compatible with this controller.
	 * 
	 * @param format The pixel format
	 * @return If the pixel format is supported
	 */
	public boolean isPixelFormatSupported(PixelFormat format);
	
	/** Gets the current pixel format of the controller.
	 * 
	 * @return The current pixel format
	 */
	public PixelFormat getPixelFormat();
}
