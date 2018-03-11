package com.zekrom_64.ze.nat.win.structs;

import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

/** The <b>BITMAPINFOHEADER</b> structure contains information about the dimensions and color format of a DIB.
 * 
 * @author Zekrom_64
 *
 */
public class BITMAPINFOHEADER extends ZEStruct<BITMAPINFOHEADER> {

	/** The number of bytes required by the structure */
	@Field(0)
	public int biSize() {
		return io.getIntField(this, 0);
	}

	/** The number of bytes required by the structure */
	@Field(0)
	public void biSize(int biSize) {
		io.setIntField(this, 0, biSize);
	}

	/** <p>The width of the bitmap, in pixels.</p>
	 * <p>
	 * If <b>biCompression</b> is BI_JPEG or BI_PNG, the <b>biWidth</b> member specifies the width of the decompressed
	 * JPEG or PNG image file.
	 * </p>
	 */
	@Field(1)
	public int biWidth() {
		return io.getIntField(this, 1);
	}

	/** <p>The width of the bitmap, in pixels.</p>
	 * <p>
	 * If <b>biCompression</b> is BI_JPEG or BI_PNG, the <b>biWidth</b> member specifies the width of the decompressed
	 * JPEG or PNG image file.
	 * </p>
	 */
	@Field(1)
	public void biWidth(int biWidth) {
		io.setIntField(this, 1, biWidth);
	}

	/** <p>The height of the bitmap, in pixels. If <b>biHeight</b> is positive, the bitmap is a bottom-up DIB and its origin
	 * is the lower-left corner. If <b>biHeight</b> is negative, the bitmap is a top-down DIB and its origin is the upper-left
	 * corner.</p>
	 * <p>If <b>biHeight</b> is negative, indicating a top-down DIB, <b>biCompression</b> must be either BI_RGB or BI_BITFIELDS.
	 * Top-down DIBs cannot be compressed.</p>
	 * <p>If <b>biCompression</b> is BI_JPEG or BI_PNG, the <b>biHeight</b> member specifies the height of the decompressed JPEG
	 * or PNG image file, respectively.
	 */
	@Field(2)
	public int biHeight() {
		return io.getIntField(this, 2);
	}

	/** <p>The height of the bitmap, in pixels. If <b>biHeight</b> is positive, the bitmap is a bottom-up DIB and its origin
	 * is the lower-left corner. If <b>biHeight</b> is negative, the bitmap is a top-down DIB and its origin is the upper-left
	 * corner.</p>
	 * <p>If <b>biHeight</b> is negative, indicating a top-down DIB, <b>biCompression</b> must be either BI_RGB or BI_BITFIELDS.
	 * Top-down DIBs cannot be compressed.</p>
	 * <p>If <b>biCompression</b> is BI_JPEG or BI_PNG, the <b>biHeight</b> member specifies the height of the decompressed JPEG
	 * or PNG image file, respectively.
	 */
	@Field(2)
	public void biHeight(int biHeight) {
		io.setIntField(this, 2, biHeight);
	}

	/** The number of planes for the target device. This value must be set to 1.*/
	@Field(3)
	public short biPlanes() {
		return io.getShortField(this, 3);
	}

	/** The number of planes for the target device. This value must be set to 1.*/
	@Field(3)
	public void biPlanes(short biPlanes) {
		io.setShortField(this, 3, biPlanes);
	}

	/** <p>The number of bits-per-pixel. The <b>biBitCount</b> member of the <b>BITMAPINFOHEADER</b> structure 
	 * determines the number of bits that define each pixel and the maximum number of colors in the bitmap. This
	 * member must be one of the following values.</p>
	 * 
	 * <style>
	 * table {
	 * border-collapse: collapse;
	 * margin: 5px;
	 * }
	 * td {
	 * padding: 5px;
	 * }
	 * </style>
	 * 
	 * <table border="1">
	 * 	<tr>
	 * 	 <td><b>Value</b></td>
	 * 	 <td><b>Meaning</b></td>
	 * 	</tr><tr>
	 *   <td>0</td>
	 *   <td>The number of bits-per-pixel is specified or is implied by the JPEG or PNG format.</td>
	 *  </tr><tr>
	 *   <td>1</td>
	 *   <td>The bitmap is monochrome, and the <b>bmiColors</b> member of {@link BITMAPINFO} contains two entries.
	 *   Each bit in the bitmap array represents a pixel. If the bit is clear, the pixel is displayed with the color
	 *   of the first entry in the <b>bmiColors</b> table; if the bit is set, the pixel has the color of the second
	 *   entry in the table.</td>
	 *  </tr><tr>
	 *   <td>4</td>
	 *   <td>The bitmap has a maximum of 16 colors, and the <b>bmiColors</b> member of {@link BITMAPINFO} contains up
	 *   to 16 entries. Each pixel in the bitmap is represented by a 4-bit index into the color table. For example,
	 *   if the first byte in the bitmap is 0x1F, the byte represents two pixels. The first pixel contains the color
	 *   in the second table entry, and the second pixel contains the color in the sixteenth table entry.</td>
	 *  </tr><tr>
	 *   <td>8</td>
	 *   <td>The bitmap has a maximum of 256 colors, and the <b>bmiColors</b> member of {@link BITMAPINFO} contains
	 *   up to 256 entries. In this case, each byte in the array represents a single pixel.</td>
	 *  </tr><tr>
	 *   <td>16</td>
	 *   <td><p>The bitmap has a maximum of 2^16 colors. If the <b>biCompression</b> member or the <b.BITMAPINFOHEADER</b>
	 *   is BI_RGB, the <b>bmiColors</b> member of {@link BITMAPINFO} is <b>NULL</b>. Each <b>WORD</b> in the bitmap
	 *   array represents a single pixel. The relative intensities of red, green, and blue are represented with five
	 *   bits for each color component. The value for blue is in the least significant five bits, followed by five bits
	 *   each for green and red. The most significant bit is not used. The <b.bmiColors</b> color table is used for
	 *   optimizing colors used on palette-based devices, and must contain the number of entries specified by the
	 *   <b>biClrUsed</b> member of the <b>BITMAPINFOHEADER</b>. If the <b>biCompression</b> member of the
	 *   <b>BITMAPINFOHEADER</b> is BI_BITFIELDS, the <b>bmiColors</b> member contains three <b>DWORD</b> color masks
	 *   that specify the red, green, and blue components, respectively, of each pixel. Each <b>WORD</b> in the bitmap
	 *   array represents a single pixel.</p>
	 *   <p>When the <b.biCompression</b> member is BI_BITFIELDS, bits set in each <b>DWORD</b> mask must be contiguous
	 *   and should not overlap the bits of another mask.All the bits in the pixel do not have to be used.</p></td>
	 *  </tr><tr>
	 *  <td>24</td>
	 *  <td>The bitmap has a maximum of 2^24 colors, and the <b>bmiColors</b> member of {@link BITMAPINFO} is <b>NULL</b>.
	 *  Each 3-byte triplet in the bitmap array represents the relative intensities of blue, green, and red, respectively,
	 *  for a pixel. THe <b>bmiColors</b> color table is used for optimizing colors used on palette-based devices, and must
	 *  contain the number of entries specified by the <b>biClrUsed</b> member of the <b>BITMAPINFOHEADER</b></td>
	 *  </tr><tr>
	 *  <td>32</td>
	 *  <td><p>The bitmap has a maximum of 2^32 colors. If the <b>biCompression</b> member of the <b.BITMAPINFOHEADER</b> is
	 *  BI_RGB, the <b>bmiColors</b> member of {@link BITMAPINFO} is <b>NULL</b>. Each <b>DWORD</b> in the bitmap array
	 *  represents the relative intensities of blue, green, and red for a pixel. The value for blue is in the least
	 *  significant 8 bits, followed by the 8 bits each for green and red. The high byte in each <b>DWORD</b> is not used.
	 *  The <b>bmiColors</b> color table is used for optimizing colors used on palette-based devices, and must contain
	 *  the number of entries specified by the <b>biClrUsed</b> member of the <b>BITMAPINFOHEADER</b>.</p>
	 *  <p>If the <b>biCompression</b> member of the <b>BITMAPINFOHEADER</b> is BI_BITFIELDS, bits set in each
	 *  <b>DWORD</b> color masks that specify the red, green, and blue components, respectively, of each pixel. Each
	 *  <b>DWORD</b> in the bitmap array represents a single pixel.</p><p>When the <b.biCompression</b> member is BI_BITFIELDS,
	 *  bits set in each <b>DWORD</b> mask must be contiguous and should not overlap the bits of another mask.All of the bits
	 *  in the pixel do not need to be used.</p></td>
	 *  </tr>
	 * </table>
	 */
	@Field(4)
	public short biBitCount() {
		return io.getShortField(this, 4);
	}


	/** <p>The number of bits-per-pixel. The <b>biBitCount</b> member of the <b>BITMAPINFOHEADER</b> structure 
	 * determines the number of bits that define each pixel and the maximum number of colors in the bitmap. This
	 * member must be one of the following values.</p>
	 * 
	 * <style>
	 * table {
	 * border-collapse: collapse;
	 * margin: 5px;
	 * }
	 * td {
	 * padding: 5px;
	 * }
	 * </style>
	 * 
	 * <table border="1">
	 * 	<tr>
	 * 	 <td><b>Value</b></td>
	 * 	 <td><b>Meaning</b></td>
	 * 	</tr><tr>
	 *   <td>0</td>
	 *   <td>The number of bits-per-pixel is specified or is implied by the JPEG or PNG format.</td>
	 *  </tr><tr>
	 *   <td>1</td>
	 *   <td>The bitmap is monochrome, and the <b>bmiColors</b> member of {@link BITMAPINFO} contains two entries.
	 *   Each bit in the bitmap array represents a pixel. If the bit is clear, the pixel is displayed with the color
	 *   of the first entry in the <b>bmiColors</b> table; if the bit is set, the pixel has the color of the second
	 *   entry in the table.</td>
	 *  </tr><tr>
	 *   <td>4</td>
	 *   <td>The bitmap has a maximum of 16 colors, and the <b>bmiColors</b> member of {@link BITMAPINFO} contains up
	 *   to 16 entries. Each pixel in the bitmap is represented by a 4-bit index into the color table. For example,
	 *   if the first byte in the bitmap is 0x1F, the byte represents two pixels. The first pixel contains the color
	 *   in the second table entry, and the second pixel contains the color in the sixteenth table entry.</td>
	 *  </tr><tr>
	 *   <td>8</td>
	 *   <td>The bitmap has a maximum of 256 colors, and the <b>bmiColors</b> member of {@link BITMAPINFO} contains
	 *   up to 256 entries. In this case, each byte in the array represents a single pixel.</td>
	 *  </tr><tr>
	 *   <td>16</td>
	 *   <td><p>The bitmap has a maximum of 2^16 colors. If the <b>biCompression</b> member or the <b.BITMAPINFOHEADER</b>
	 *   is BI_RGB, the <b>bmiColors</b> member of {@link BITMAPINFO} is <b>NULL</b>. Each <b>WORD</b> in the bitmap
	 *   array represents a single pixel. The relative intensities of red, green, and blue are represented with five
	 *   bits for each color component. The value for blue is in the least significant five bits, followed by five bits
	 *   each for green and red. The most significant bit is not used. The <b.bmiColors</b> color table is used for
	 *   optimizing colors used on palette-based devices, and must contain the number of entries specified by the
	 *   <b>biClrUsed</b> member of the <b>BITMAPINFOHEADER</b>. If the <b>biCompression</b> member of the
	 *   <b>BITMAPINFOHEADER</b> is BI_BITFIELDS, the <b>bmiColors</b> member contains three <b>DWORD</b> color masks
	 *   that specify the red, green, and blue components, respectively, of each pixel. Each <b>WORD</b> in the bitmap
	 *   array represents a single pixel.</p>
	 *   <p>When the <b.biCompression</b> member is BI_BITFIELDS, bits set in each <b>DWORD</b> mask must be contiguous
	 *   and should not overlap the bits of another mask.All the bits in the pixel do not have to be used.</p></td>
	 *  </tr><tr>
	 *  <td>24</td>
	 *  <td>The bitmap has a maximum of 2^24 colors, and the <b>bmiColors</b> member of {@link BITMAPINFO} is <b>NULL</b>.
	 *  Each 3-byte triplet in the bitmap array represents the relative intensities of blue, green, and red, respectively,
	 *  for a pixel. THe <b>bmiColors</b> color table is used for optimizing colors used on palette-based devices, and must
	 *  contain the number of entries specified by the <b>biClrUsed</b> member of the <b>BITMAPINFOHEADER</b></td>
	 *  </tr><tr>
	 *  <td>32</td>
	 *  <td><p>The bitmap has a maximum of 2^32 colors. If the <b>biCompression</b> member of the <b.BITMAPINFOHEADER</b> is
	 *  BI_RGB, the <b>bmiColors</b> member of {@link BITMAPINFO} is <b>NULL</b>. Each <b>DWORD</b> in the bitmap array
	 *  represents the relative intensities of blue, green, and red for a pixel. The value for blue is in the least
	 *  significant 8 bits, followed by the 8 bits each for green and red. The high byte in each <b>DWORD</b> is not used.
	 *  The <b>bmiColors</b> color table is used for optimizing colors used on palette-based devices, and must contain
	 *  the number of entries specified by the <b>biClrUsed</b> member of the <b>BITMAPINFOHEADER</b>.</p>
	 *  <p>If the <b>biCompression</b> member of the <b>BITMAPINFOHEADER</b> is BI_BITFIELDS, bits set in each
	 *  <b>DWORD</b> color masks that specify the red, green, and blue components, respectively, of each pixel. Each
	 *  <b>DWORD</b> in the bitmap array represents a single pixel.</p><p>When the <b.biCompression</b> member is BI_BITFIELDS,
	 *  bits set in each <b>DWORD</b> mask must be contiguous and should not overlap the bits of another mask.All of the bits
	 *  in the pixel do not need to be used.</p></td>
	 *  </tr>
	 * </table>
	 */
	@Field(4)
	public void biBitCount(short biBitCount) {
		io.setShortField(this, 4, biBitCount);
	}

	/** The type of compression for a compressed bottom-up bitmap (top-down DIBs cannot be compressed). This member can be one of
	 * the following values.
	 * 
	 * <style>
	 * table {
	 * border-collapse: collapse;
	 * margin: 5px;
	 * }
	 * td {
	 * padding: 5px;
	 * }
	 * </style>
	 * 
	 * <table border="1">
	 * <tr><td><b>Value</b></td><td><b>Meaning</b></td></tr>
	 * <tr>
	 * <td>BI_RGB</td>
	 * <td>An uncompressed format</td>
	 * </tr><tr>
	 * <td>BI_RLE8</td>
	 * <td>A run-length encoded (RLE) format for bitmaps with 8bpp. THe compression format is a 2-byte format consisting of a count byte
	 * followed by a byte containing a color index. For more information, see
	 * <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/dd183383(v=vs.85).aspx">Bitmap Compression</a>.</td>
	 * </tr><tr>
	 * <td>BI_RLE4</td>
	 * <td>An RLE format for bitmaps with 4 bpp. The compression format is a 2-byte format consisting of a count byte followed by two
	 * word-length color indexes. For more information, see
	 * <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/dd183383(v=vs.85).aspx">Bitmap Compression</a>.</td>
	 * </tr><tr>
	 * <td>BI_BITFIELDS</td>
	 * <td>Specifies that the bitmap is not compressed and that the color table consists of three <b>DWORD</b> color masks that specify
	 * the red, green, and blue components, respectively, of each pixel. This is valid when used with 16- and 32-bpp bitmaps.</td>
	 * </tr><tr>
	 * <td>BI_JPEG</td>
	 * <td>Indicates that the image is a JPEG image.</td>
	 * </tr><tr>
	 * <td>BI_PNG</td>
	 * <td>Indicates that the image is a PNG image.</td>
	 * </tr>
	 * </table>
	 */
	@Field(5)
	public int biCompression() {
		return io.getIntField(this, 5);
	}

	/** The type of compression for a compressed bottom-up bitmap (top-down DIBs cannot be compressed). This member can be one of
	 * the following values.
	 * 
	 * <style>
	 * table {
	 * border-collapse: collapse;
	 * margin: 5px;
	 * }
	 * td {
	 * padding: 5px;
	 * }
	 * </style>
	 * 
	 * <table border="1">
	 * <tr><td><b>Value</b></td><td><b>Meaning</b></td></tr>
	 * <tr>
	 * <td>BI_RGB</td>
	 * <td>An uncompressed format</td>
	 * </tr><tr>
	 * <td>BI_RLE8</td>
	 * <td>A run-length encoded (RLE) format for bitmaps with 8bpp. THe compression format is a 2-byte format consisting of a count byte
	 * followed by a byte containing a color index. For more information, see
	 * <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/dd183383(v=vs.85).aspx">Bitmap Compression</a>.</td>
	 * </tr><tr>
	 * <td>BI_RLE4</td>
	 * <td>An RLE format for bitmaps with 4 bpp. The compression format is a 2-byte format consisting of a count byte followed by two
	 * word-length color indexes. For more information, see
	 * <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/dd183383(v=vs.85).aspx">Bitmap Compression</a>.</td>
	 * </tr><tr>
	 * <td>BI_BITFIELDS</td>
	 * <td>Specifies that the bitmap is not compressed and that the color table consists of three <b>DWORD</b> color masks that specify
	 * the red, green, and blue components, respectively, of each pixel. This is valid when used with 16- and 32-bpp bitmaps.</td>
	 * </tr><tr>
	 * <td>BI_JPEG</td>
	 * <td>Indicates that the image is a JPEG image.</td>
	 * </tr><tr>
	 * <td>BI_PNG</td>
	 * <td>Indicates that the image is a PNG image.</td>
	 * </tr>
	 * </table>
	 */
	@Field(5)
	public void biCompression(int biCompression) {
		io.setIntField(this, 5, biCompression);
	}

	/** <p>The size, in bytes, of the image. THis may be set to zero for BI_RGB bitmaps.</p>
	 * </p>If <b>biCompression</b> is BI_JPEG or BI_PNG, <b>biSizeImage</b> indicates the size fo the JPEG or PNG image buffer, respectively.</p>
	 */
	@Field(6)
	public int biSizeImage() {
		return io.getIntField(this, 6);
	}

	/** <p>The size, in bytes, of the image. THis may be set to zero for BI_RGB bitmaps.</p>
	 * </p>If <b>biCompression</b> is BI_JPEG or BI_PNG, <b>biSizeImage</b> indicates the size fo the JPEG or PNG image buffer, respectively.</p>
	 */
	@Field(6)
	public void biSizeImage(int biSizeImage) {
		io.setIntField(this, 6, biSizeImage);
	}

	/** The horizontal resolution, in pixels-per-meter, of the target device for the bitmap. An application can use this value to select a bitmap
	 * from a resource group that best matches the characteristics of the current device.
	 */
	@Field(7)
	public int biXPelsPerMeter() {
		return io.getIntField(this, 7);
	}

	/** The horizontal resolution, in pixels-per-meter, of the target device for the bitmap. An application can use this value to select a bitmap
	 * from a resource group that best matches the characteristics of the current device.
	 */
	@Field(7)
	public void biXPelsPerMeter(int biXPelsPerMeter) {
		io.setIntField(this, 7, biXPelsPerMeter);
	}

	/** The vertical resolution, in pixels-per-meter, of the target device for the bitmap.
	 */
	@Field(8)
	public int biYPelsPerMeter() {
		return io.getIntField(this, 8);
	}

	/** The vertical resolution, in pixels-per-meter, of the target device for the bitmap.
	 */
	@Field(8)
	public void biYPelsPerMeter(int biYPelsPerMeter) {
		io.setIntField(this, 8, biYPelsPerMeter);
	}

	/** <p>The number of color indexes in the color table that are actually used by the bitmap. If this value is zero, the bitmap uses the maximum
	 * number of colors corresponding to the value of the <b>biBitCount</b> member for the compression mode specified by <b>biCompression</b>.</p>
	 * 
	 * <p>If <b>biClrUsed</b> is nonzero and the <b>biBitCount</b> member is less than 16, the <b>biClrUsed</b> member specifies the actual number
	 * of colors the graphics engine or device driver accesses. If <b>biBitCOunt</b> is 16 or greater, the<b>biClrUsed</b> member specifies the
	 * size of the color table used to optimize performance of the system color palettes. If <b>biBitCOunt</b> equals 16 or 32, the optimal color
	 * palette starts immediately following the three <b>DWORD</b> masks.</p>
	 * 
	 * <p>When the bitmap array immediately follows the {@link BITMAPINFO} structure, it is a packed bitmap. Packet bitmaps are referenced by a
	 * single pointer. Packed bitmaps require that the <b>biClrUsed</b> member must be either zero or the actual size of the color table.</p>
	 */
	@Field(9)
	public int biClrUsed() {
		return io.getIntField(this, 9);
	}

	/** <p>The number of color indexes in the color table that are actually used by the bitmap. If this value is zero, the bitmap uses the maximum
	 * number of colors corresponding to the value of the <b>biBitCount</b> member for the compression mode specified by <b>biCompression</b>.</p>
	 * 
	 * <p>If <b>biClrUsed</b> is nonzero and the <b>biBitCount</b> member is less than 16, the <b>biClrUsed</b> member specifies the actual number
	 * of colors the graphics engine or device driver accesses. If <b>biBitCOunt</b> is 16 or greater, the<b>biClrUsed</b> member specifies the
	 * size of the color table used to optimize performance of the system color palettes. If <b>biBitCOunt</b> equals 16 or 32, the optimal color
	 * palette starts immediately following the three <b>DWORD</b> masks.</p>
	 * 
	 * <p>When the bitmap array immediately follows the {@link BITMAPINFO} structure, it is a packed bitmap. Packet bitmaps are referenced by a
	 * single pointer. Packed bitmaps require that the <b>biClrUsed</b> member must be either zero or the actual size of the color table.</p>
	 */
	@Field(9)
	public void biClrUsed(int biClrUsed) {
		io.setIntField(this, 9, biClrUsed);
	}

	/** The number of color indexes that are required for displaying the bitmap. If this value is zero, all colors are required
	 */
	@Field(10)
	public int biClrImportant() {
		return io.getIntField(this, 10);
	}

	/** The number of color indexes that are required for displaying the bitmap. If this value is zero, all colors are required
	 */
	@Field(10)
	public void biClrImportant(int biClrImportant) {
		io.setIntField(this, 10, biClrImportant);
	}
	
}
