package com.zekrom_64.ze.nat.win.structs;

import org.bridj.ann.CLong;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

/** The <b>BITMAPV5HEADER</b> structure is the bitmap information header file. It is an extended version of the
 * {@link BITMAPINFOHEADER} structure.
 * 
 * @author Zekrom_64
 *
 */
public class BITMAPV5HEADER extends ZEStruct<BITMAPV5HEADER> {

	/** The number of bytes required by the structure. Applications should use this member to determine which bitmap information header
	 * structure is being used.
	 */
	@Field(0)
	public int bV5Size() {
		return io.getIntField(this, 0);
	}

	/** The number of bytes required by the structure. Applications should use this member to determine which bitmap information header
	 * structure is being used.
	 */
	@Field(0)
	public void bV5Size(int bV5Size) {
		io.setIntField(this, 0, bV5Size);
	}
	
	/** <p>The width of the bitmap, in pixels.</p>
	 * <p>If <b>bV5Compression</b> is BI_JPEG or BI_PNG, the <b>bV5Width</b> member specifies the width of the decompressed JPEG or PNG image
	 * in pixels.</p>
	 */
	@CLong
	@Field(1)
	public long bV5Width() {
		return io.getCLongField(this, 1);
	}

	/** <p>The width of the bitmap, in pixels.</p>
	 * <p>If <b>bV5Compression</b> is BI_JPEG or BI_PNG, the <b>bV5Width</b> member specifies the width of the decompressed JPEG or PNG image
	 * in pixels.</p>
	 */
	@CLong
	@Field(1)
	public void bV5Width(long bV5Width) {
		io.setCLongField(this, 1, bV5Width);
	}
	
	/** <p>The height of the bitmap, in pixels. If the value of <b>bV5Height</b> is positive, the bitmap is a bottom-up DIB and its origin is
	 * the lower-left corner. If <b>bV5Height</b> value is negative, the bitmap is a top-down DIB and its origin is the upper-left corner.</p>
	 * 
	 * <p>If <b>bV5Height</b> is negative, indicating a top-down DIB, <b>bV5Compression</b> must be either BI_RGB or BI_BITFIELDS. Top-down
	 * DIBs cannot be compressed.</p>
	 * 
	 * <p>If <b>bV5Compression</b> is BI_JPEG or BI_PNG, the <b>bV5Height</b> member specifies the height of the decompressed JPEG or PNG
	 * image in pixels.</p>
	 */
	@CLong
	@Field(2)
	public long bV5Height() {
		return io.getCLongField(this, 2);
	}

	/** <p>The height of the bitmap, in pixels. If the value of <b>bV5Height</b> is positive, the bitmap is a bottom-up DIB and its origin is
	 * the lower-left corner. If <b>bV5Height</b> value is negative, the bitmap is a top-down DIB and its origin is the upper-left corner.</p>
	 * 
	 * <p>If <b>bV5Height</b> is negative, indicating a top-down DIB, <b>bV5Compression</b> must be either BI_RGB or BI_BITFIELDS. Top-down
	 * DIBs cannot be compressed.</p>
	 * 
	 * <p>If <b>bV5Compression</b> is BI_JPEG or BI_PNG, the <b>bV5Height</b> member specifies the height of the decompressed JPEG or PNG
	 * image in pixels.</p>
	 */
	@CLong
	@Field(2)
	public void bV5Height(long bV5Height) {
		io.setCLongField(this, 2, bV5Height);
	}
	
	/** The number of planes for the target device. This value must be set to 1.
	 */
	@Field(3)
	public short bV5Planes() {
		return io.getShortField(this, 3);
	}

	/** The number of planes for the target device. This value must be set to 1.
	 */
	@Field(3)
	public void bV5Planes(short bV5Planes) {
		io.setShortField(this, 3, bV5Planes);
	}

	/** <p>The number of bits-per-pixel. The <b>bV5BitCount</b> member of the <b>BITMAPINFOHEADER</b> structure 
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
	 *   <td><p>The bitmap has a maximum of 2^16 colors. If the <b>bV5Compression</b> member or the <b.BITMAPINFOHEADER</b>
	 *   is BI_RGB, the <b>bmiColors</b> member of {@link BITMAPINFO} is <b>NULL</b>. Each <b>WORD</b> in the bitmap
	 *   array represents a single pixel. The relative intensities of red, green, and blue are represented with five
	 *   bits for each color component. The value for blue is in the least significant five bits, followed by five bits
	 *   each for green and red. The most significant bit is not used. The <b.bmiColors</b> color table is used for
	 *   optimizing colors used on palette-based devices, and must contain the number of entries specified by the
	 *   <b>bV5ClrUsed</b> member of the <b>BITMAPINFOHEADER</b>. If the <b>bV5Compression</b> member of the
	 *   <b>BITMAPINFOHEADER</b> is BI_BITFIELDS, the <b>bmiColors</b> member contains three <b>DWORD</b> color masks
	 *   that specify the red, green, and blue components, respectively, of each pixel. Each <b>WORD</b> in the bitmap
	 *   array represents a single pixel.</p>
	 *   <p>When the <b>bV5Compression</b> member is BI_BITFIELDS, bits set in each <b>DWORD</b> mask must be contiguous
	 *   and should not overlap the bits of another mask.All the bits in the pixel do not have to be used.</p></td>
	 *  </tr><tr>
	 *  <td>24</td>
	 *  <td>The bitmap has a maximum of 2^24 colors, and the <b>bmiColors</b> member of {@link BITMAPINFO} is <b>NULL</b>.
	 *  Each 3-byte triplet in the bitmap array represents the relative intensities of blue, green, and red, respectively,
	 *  for a pixel. THe <b>bmiColors</b> color table is used for optimizing colors used on palette-based devices, and must
	 *  contain the number of entries specified by the <b>bV5ClrUsed</b> member of the <b>BITMAPINFOHEADER</b></td>
	 *  </tr><tr>
	 *  <td>32</td>
	 *  <td><p>The bitmap has a maximum of 2^32 colors. If the <b>bV5Compression</b> member of the <b.BITMAPINFOHEADER</b> is
	 *  BI_RGB, the <b>bmiColors</b> member of {@link BITMAPINFO} is <b>NULL</b>. Each <b>DWORD</b> in the bitmap array
	 *  represents the relative intensities of blue, green, and red for a pixel. The value for blue is in the least
	 *  significant 8 bits, followed by the 8 bits each for green and red. The high byte in each <b>DWORD</b> is not used.
	 *  The <b>bmiColors</b> color table is used for optimizing colors used on palette-based devices, and must contain
	 *  the number of entries specified by the <b>bV5ClrUsed</b> member of the <b>BITMAPINFOHEADER</b>.</p>
	 *  <p>If the <b>bV5Compression</b> member of the <b>BITMAPINFOHEADER</b> is BI_BITFIELDS, bits set in each
	 *  <b>DWORD</b> color masks that specify the red, green, and blue components, respectively, of each pixel. Each
	 *  <b>DWORD</b> in the bitmap array represents a single pixel.</p><p>When the <b.bV5Compression</b> member is BI_BITFIELDS,
	 *  bits set in each <b>DWORD</b> mask must be contiguous and should not overlap the bits of another mask.All of the bits
	 *  in the pixel do not need to be used.</p></td>
	 *  </tr>
	 * </table>
	 */
	@Field(4)
	public short bV5BitCount() {
		return io.getShortField(this, 4);
	}

	/** <p>The number of bits-per-pixel. The <b>bV5BitCount</b> member of the <b>BITMAPINFOHEADER</b> structure 
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
	 *   <td><p>The bitmap has a maximum of 2^16 colors. If the <b>bV5Compression</b> member or the <b.BITMAPINFOHEADER</b>
	 *   is BI_RGB, the <b>bmiColors</b> member of {@link BITMAPINFO} is <b>NULL</b>. Each <b>WORD</b> in the bitmap
	 *   array represents a single pixel. The relative intensities of red, green, and blue are represented with five
	 *   bits for each color component. The value for blue is in the least significant five bits, followed by five bits
	 *   each for green and red. The most significant bit is not used. The <b.bmiColors</b> color table is used for
	 *   optimizing colors used on palette-based devices, and must contain the number of entries specified by the
	 *   <b>bV5ClrUsed</b> member of the <b>BITMAPINFOHEADER</b>. If the <b>bV5Compression</b> member of the
	 *   <b>BITMAPINFOHEADER</b> is BI_BITFIELDS, the <b>bmiColors</b> member contains three <b>DWORD</b> color masks
	 *   that specify the red, green, and blue components, respectively, of each pixel. Each <b>WORD</b> in the bitmap
	 *   array represents a single pixel.</p>
	 *   <p>When the <b>bV5Compression</b> member is BI_BITFIELDS, bits set in each <b>DWORD</b> mask must be contiguous
	 *   and should not overlap the bits of another mask.All the bits in the pixel do not have to be used.</p></td>
	 *  </tr><tr>
	 *  <td>24</td>
	 *  <td>The bitmap has a maximum of 2^24 colors, and the <b>bmiColors</b> member of {@link BITMAPINFO} is <b>NULL</b>.
	 *  Each 3-byte triplet in the bitmap array represents the relative intensities of blue, green, and red, respectively,
	 *  for a pixel. THe <b>bmiColors</b> color table is used for optimizing colors used on palette-based devices, and must
	 *  contain the number of entries specified by the <b>bV5ClrUsed</b> member of the <b>BITMAPINFOHEADER</b></td>
	 *  </tr><tr>
	 *  <td>32</td>
	 *  <td><p>The bitmap has a maximum of 2^32 colors. If the <b>bV5Compression</b> member of the <b.BITMAPINFOHEADER</b> is
	 *  BI_RGB, the <b>bmiColors</b> member of {@link BITMAPINFO} is <b>NULL</b>. Each <b>DWORD</b> in the bitmap array
	 *  represents the relative intensities of blue, green, and red for a pixel. The value for blue is in the least
	 *  significant 8 bits, followed by the 8 bits each for green and red. The high byte in each <b>DWORD</b> is not used.
	 *  The <b>bmiColors</b> color table is used for optimizing colors used on palette-based devices, and must contain
	 *  the number of entries specified by the <b>bV5ClrUsed</b> member of the <b>BITMAPINFOHEADER</b>.</p>
	 *  <p>If the <b>bV5Compression</b> member of the <b>BITMAPINFOHEADER</b> is BI_BITFIELDS, bits set in each
	 *  <b>DWORD</b> color masks that specify the red, green, and blue components, respectively, of each pixel. Each
	 *  <b>DWORD</b> in the bitmap array represents a single pixel.</p><p>When the <b.bV5Compression</b> member is BI_BITFIELDS,
	 *  bits set in each <b>DWORD</b> mask must be contiguous and should not overlap the bits of another mask.All of the bits
	 *  in the pixel do not need to be used.</p></td>
	 *  </tr>
	 * </table>
	 */
	@Field(4)
	public void bV5BitCount(short bV5BitCount) {
		io.setShortField(this, 4, bV5BitCount);
	}
	
	/** Specifies that the bitmap is not compressed. The <b>bV5RedMask</b>, <b>bV5GreenMask</b>, <b>bV5BlueMask</b> members specify the red,
	 * green, and blue components of each pixel. This is valid when used with 16- and 32-bpp bitmaps. This member can be one of the following
	 * values.
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
	 * <td>BI_RGB</td><td>An uncompressed format.</td>
	 * </tr><tr>
	 * <td>BI_RLE8</td>
	 * <td>A run-length encoded (RLE) format for bitmaps with 8 bpp. The compression format is a two-byte format consisting of a count byte
	 * followed by a byte containing a color index. If <b>bV5Compression</b> is BI_RGB and the <b>bV5BitCount</b> member is 16, 24, or 32,
	 * the bitmap array specifies the actual intensities of blue, green, and red rather than using color table indexes. For more information,
	 * see <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/dd183383(v=vs.85).aspx">Bitmap Compression</a>.</td>
	 * </tr><tr>
	 * <td>BI_RLE4</td>An RLE format for bitmaps with 4 bpp. The compression format is a two-byte format consisting of a count byte followed
	 * by two word-length color indexes. For more information, see
	 * <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/dd183383(v=vs.85).aspx">Bitmap Compression</a>.</td>
	 * </tr><tr>
	 * <td>BI_BITFIELDS</td>
	 * <td>Specifies that the bitmap is not compressed and that the color masks for the red, green, and blue components of each pixel are
	 * specified in the <b>bV5RedMask</b>, <b>bV5GreenMask</b>, and <b>bV5BlueMask</b> members. This is valid when used with 16- and 32-bpp
	 * bitmaps.</td>
	 * </tr><tr>
	 * <td>BI_JPEG</td>
	 * <td>Specifies that the image is compressed using the JPEG file Interchange Format. JPEG compression trades off compression agains loss;
	 * it can achieve a compression ratio of 20:1 with little noticeable loss.</td>
	 * </tr><tr>
	 * <td>BI_PNG</td>
	 * <td>Specifies that the image is compressed using the PNG file Interchange Format.</td>
	 * </tr>
	 * </table>
	 */
	@Field(5)
	public int bV5Compression() {
		return io.getIntField(this, 5);
	}
	
	/** Specifies that the bitmap is not compressed. The <b>bV5RedMask</b>, <b>bV5GreenMask</b>, <b>bV5BlueMask</b> members specify the red,
	 * green, and blue components of each pixel. This is valid when used with 16- and 32-bpp bitmaps. This member can be one of the following
	 * values.
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
	 * <td>BI_RGB</td><td>An uncompressed format.</td>
	 * </tr><tr>
	 * <td>BI_RLE8</td>
	 * <td>A run-length encoded (RLE) format for bitmaps with 8 bpp. The compression format is a two-byte format consisting of a count byte
	 * followed by a byte containing a color index. If <b>bV5Compression</b> is BI_RGB and the <b>bV5BitCount</b> member is 16, 24, or 32,
	 * the bitmap array specifies the actual intensities of blue, green, and red rather than using color table indexes. For more information,
	 * see <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/dd183383(v=vs.85).aspx">Bitmap Compression</a>.</td>
	 * </tr><tr>
	 * <td>BI_RLE4</td>An RLE format for bitmaps with 4 bpp. The compression format is a two-byte format consisting of a count byte followed
	 * by two word-length color indexes. For more information, see
	 * <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/dd183383(v=vs.85).aspx">Bitmap Compression</a>.</td>
	 * </tr><tr>
	 * <td>BI_BITFIELDS</td>
	 * <td>Specifies that the bitmap is not compressed and that the color masks for the red, green, and blue components of each pixel are
	 * specified in the <b>bV5RedMask</b>, <b>bV5GreenMask</b>, and <b>bV5BlueMask</b> members. This is valid when used with 16- and 32-bpp
	 * bitmaps.</td>
	 * </tr><tr>
	 * <td>BI_JPEG</td>
	 * <td>Specifies that the image is compressed using the JPEG file Interchange Format. JPEG compression trades off compression agains loss;
	 * it can achieve a compression ratio of 20:1 with little noticeable loss.</td>
	 * </tr><tr>
	 * <td>BI_PNG</td>
	 * <td>Specifies that the image is compressed using the PNG file Interchange Format.</td>
	 * </tr>
	 * </table>
	 */
	@Field(5)
	public void bV5Compression(int bV5Compression) {
		io.setIntField(this, 5, bV5Compression);
	}
	
	/** <p>The size, in bytes, of the image. This may be set to zero for BI_RGB bitmaps.</p>
	 * <p>If <b>bV5Compression</b> is BI_JPEG or BI_PNG, <b>bv5SizeImage</b> is the size of the JPEG or PNG image buffer.</p>
	 */
	@Field(6)
	public int bV5SizeImage() {
		return io.getIntField(this, 6);
	}

	/** <p>The size, in bytes, of the image. This may be set to zero for BI_RGB bitmaps.</p>
	 * <p>If <b>bV5Compression</b> is BI_JPEG or BI_PNG, <b>bv5SizeImage</b> is the size of the JPEG or PNG image buffer.</p>
	 */
	@Field(6)
	public void bV5SizeImage(int bV5SizeImage) {
		io.setIntField(this, 6, bV5SizeImage);
	}
	
	/** The horizontal resolution, in pixels-per-meter, of the target device for the bitmap. An application can use this value to select a
	 * bitmap from a resource group that best matches the characteristics of the current device.
	 */
	@CLong
	@Field(7)
	public long bV5XPelsPerMeter() {
		return io.getCLongField(this, 7);
	}

	/** The horizontal resolution, in pixels-per-meter, of the target device for the bitmap. An application can use this value to select a
	 * bitmap from a resource group that best matches the characteristics of the current device.
	 */
	@CLong
	@Field(7)
	public void bV5XPelsPerMeter(long bV5XPelsPerMeter) {
		io.setCLongField(this, 7, bV5XPelsPerMeter);
	}
	
	/** The vertical resolution, in pixels-per-meter, of the target device for the bitmap.
	 */
	@CLong
	@Field(8)
	public long bV5YPelsPerMeter() {
		return io.getCLongField(this, 8);
	}

	/** The vertical resolution, in pixels-per-meter, of the target device for the bitmap.
	 */
	@CLong
	@Field(8)
	public void bV5YPelsPerMeter(long bV5YPelsPerMeter) {
		io.setCLongField(this, 8, bV5YPelsPerMeter);
	}
	
	/** <p>The number of color indexes in the color table that are actually used by the bitmap. If this value is zero, the bitmap uses the
	 * maximum number of colors corresponding to the value of the <b>bV5BitCount</b> member for the compression mode specified by
	 * <b>bV5Cinoression</b>.</p>
	 * 
	 * <p>If <b>bV5ClrUsed</b> is nonzero and <b>bV5BitCOunt</b> is less than 16, the <b>bV5ClrUsed</b> member specifies the actual number
	 * of colors the graphics engine or device driver accesses. If <b>bV5BitCount</b> is 16 or greater, the <b>bV5ClrUsed</b> member specifies
	 * the size of the color table used to optimize performance of the system color palette. If <b>bV5BitCount</b> equals 16 or 32, the
	 * optimal color palette starts immediately following the <b>BITMAPV5HEADER</b>. If <b>bV5ClrUsed</b> is nonzero, the color table is used
	 * on palettized devices, and <b>bV5ClrUsed</b> specifies the number of entries.</p>
	 */
	@Field(9)
	public int bV5ClrUsed() {
		return io.getIntField(this, 9);
	}

	/** <p>The number of color indexes in the color table that are actually used by the bitmap. If this value is zero, the bitmap uses the
	 * maximum number of colors corresponding to the value of the <b>bV5BitCount</b> member for the compression mode specified by
	 * <b>bV5Cinoression</b>.</p>
	 * 
	 * <p>If <b>bV5ClrUsed</b> is nonzero and <b>bV5BitCOunt</b> is less than 16, the <b>bV5ClrUsed</b> member specifies the actual number
	 * of colors the graphics engine or device driver accesses. If <b>bV5BitCount</b> is 16 or greater, the <b>bV5ClrUsed</b> member specifies
	 * the size of the color table used to optimize performance of the system color palette. If <b>bV5BitCount</b> equals 16 or 32, the
	 * optimal color palette starts immediately following the <b>BITMAPV5HEADER</b>. If <b>bV5ClrUsed</b> is nonzero, the color table is used
	 * on palettized devices, and <b>bV5ClrUsed</b> specifies the number of entries.</p>
	 */
	@Field(9)
	public void bV5ClrUsed(int bV5ClrUsed) {
		io.setIntField(this, 9, bV5ClrUsed);
	}

	/** The number of color indexes that are required for displaying the bitmap. If this value is zero, all colors are required.
	 */
	@Field(10)
	public int bV5ClrImportant() {
		return io.getIntField(this, 10);
	}

	/** The number of color indexes that are required for displaying the bitmap. If this value is zero, all colors are required.
	 */
	@Field(10)
	public void bV5ClrImportant(int bV5ClrImportant) {
		io.setIntField(this, 10, bV5ClrImportant);
	}

	/** Color mask that specifies the red component of each pixel, valid only if <b>bV5Compression</b> is set to BI_BITFIELDS.
	 */
	@Field(11)
	public int bV5RedMask() {
		return io.getIntField(this, 11);
	}

	/** Color mask that specifies the red component of each pixel, valid only if <b>bV5Compression</b> is set to BI_BITFIELDS.
	 */
	@Field(11)
	public void bV5RedMask(int bV5RedMask) {
		io.setIntField(this, 11, bV5RedMask);
	}

	/** Color mask that specifies the green component of each pixel, valid only if <b>bV5Compression</b> is set to BI_BITFIELDS.
	 */
	@Field(12)
	public int bV5GreenMask() {
		return io.getIntField(this, 12);
	}

	/** Color mask that specifies the green component of each pixel, valid only if <b>bV5Compression</b> is set to BI_BITFIELDS.
	 */
	@Field(12)
	public void bV5GreenMask(int bV5GreenMask) {
		io.setIntField(this, 12, bV5GreenMask);
	}

	/** Color mask that specifies the blue component of each pixel, valid only if <b>bV5Compression</b> is set to BI_BITFIELDS.
	 */
	@Field(13)
	public int bV5BlueMask() {
		return io.getIntField(this, 13);
	}

	/** Color mask that specifies the blue component of each pixel, valid only if <b>bV5Compression</b> is set to BI_BITFIELDS.
	 */
	@Field(13)
	public void bV5BlueMask(int bV5BlueMask) {
		io.setIntField(this, 13, bV5BlueMask);
	}

	/** Color mask that specifies the alpha component of each pixel.
	 */
	@Field(14)
	public int bV5AlphaMask() {
		return io.getIntField(this, 14);
	}

	/** Color mask that specifies the alpha component of each pixel.
	 */
	@Field(14)
	public void bV5AlphaMask(int bV5AlphaMask) {
		io.setIntField(this, 14, bV5AlphaMask);
	}

	/** <p>The color space of the DIB.</p>
	 * 
	 * <p>The following table specifies the values of <b>bV5CSType</b>.</p>
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
	 * <td>LCS_CALIBRATED_RGB</td>
	 * <td>This value implies that endpoints and gamma values are given in the appropriate fields.</td>
	 * </tr><tr>
	 * <td>LCS_sRGB</td>
	 * <td>Specifies that the bitmap is in sRGB color space.</td>
	 * </tr><tr>
	 * <td>LCS_WINDOWS_COLOR_SPACE</td>
	 * <td>This value indicates that the bitmap is in the system default color space, sRGB.</td>
	 * </tr><tr>
	 * <td>PROFILE_LINKED</td>
	 * <td>This value indicates that <b>bV5ProfileData</b> points to the file name of the profile to use (gamma and endpoints values are
	 * ignored).</td>
	 * </tr><tr>
	 * <td>PROFILE_EMBEDDED</td>
	 * <td>This value indicates that <b>bV5ProfileData</b> points to a memory buffer that contains the profile to be used (gamma and
	 * endpoints values are ignored).</td>
	 * </tr>
	 * </table>
	 * <p>See the {@link LOGCOLORPSPACE} structure for information that defines a logical color space.</p>
	 */
	@Field(15)
	public int bV5CSType() {
		return io.getIntField(this, 15);
	}

	/** <p>The color space of the DIB.</p>
	 * 
	 * <p>The following table specifies the values of <b>bV5CSType</b>.</p>
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
	 * <td>LCS_CALIBRATED_RGB</td>
	 * <td>This value implies that endpoints and gamma values are given in the appropriate fields.</td>
	 * </tr><tr>
	 * <td>LCS_sRGB</td>
	 * <td>Specifies that the bitmap is in sRGB color space.</td>
	 * </tr><tr>
	 * <td>LCS_WINDOWS_COLOR_SPACE</td>
	 * <td>This value indicates that the bitmap is in the system default color space, sRGB.</td>
	 * </tr><tr>
	 * <td>PROFILE_LINKED</td>
	 * <td>This value indicates that <b>bV5ProfileData</b> points to the file name of the profile to use (gamma and endpoints values are
	 * ignored).</td>
	 * </tr><tr>
	 * <td>PROFILE_EMBEDDED</td>
	 * <td>This value indicates that <b>bV5ProfileData</b> points to a memory buffer that contains the profile to be used (gamma and
	 * endpoints values are ignored).</td>
	 * </tr>
	 * </table>
	 * <p>See the {@link LOGCOLORPSPACE} structure for information that defines a logical color space.</p>
	 */
	@Field(15)
	public void bV5CSType(int bV5CSType) {
		io.setIntField(this, 15, bV5CSType);
	}

	/** A {@link CIEXYZTRIPLE} structure that specifies the x, y, and z coordinates of the three colors that correspond to the red, green,
	 * and blue endpoints for the logical color space associated with the bitmap. This member is ignored unless the <b>bV5CSType</b> member
	 * specifies LCS_CALIBRATED_RGB.
	 */
	@Field(16)
	public CIEXYZTRIPLE bV5Endpoints() {
		return io.getNativeObjectField(this, 16);
	}

	/** A {@link CIEXYZTRIPLE} structure that specifies the x, y, and z coordinates of the three colors that correspond to the red, green,
	 * and blue endpoints for the logical color space associated with the bitmap. This member is ignored unless the <b>bV5CSType</b> member
	 * specifies LCS_CALIBRATED_RGB.
	 */
	@Field(16)
	public void bV5Endpoints(CIEXYZTRIPLE bV5Endpoints) {
		io.setNativeObjectField(this, 16, bV5Endpoints);
	}

	/** Toned response curve for red. Used if <b>bV5CSType</b> is set to LCS_CALIBRATED_RGB. Specify in unsigned fixed 16.16 format. The
	 * upper 16 bits are the unsigned integer value. The lower 16 bits are the fractional part.
	 */
	@Field(17)
	public int bV5GammaRed() {
		return io.getIntField(this, 17);
	}

	/** Toned response curve for red. Used if <b>bV5CSType</b> is set to LCS_CALIBRATED_RGB. Specify in unsigned fixed 16.16 format. The
	 * upper 16 bits are the unsigned integer value. The lower 16 bits are the fractional part.
	 */
	@Field(17)
	public void bV5GammaRed(int bV5GammaRed) {
		io.setIntField(this, 17, bV5GammaRed);
	}

	/** Toned response curve for green. Used if <b>bV5CSType</b> is set to LCS_CALIBRATED_RGB. Specify in unsigned fixed 16.16 format. The
	 * upper 16 bits are the unsigned integer value. The lower 16 bits are the fractional part.
	 */
	@Field(18)
	public int bV5GammaGreen() {
		return io.getIntField(this, 18);
	}

	/** Toned response curve for green. Used if <b>bV5CSType</b> is set to LCS_CALIBRATED_RGB. Specify in unsigned fixed 16.16 format. The
	 * upper 16 bits are the unsigned integer value. The lower 16 bits are the fractional part.
	 */
	@Field(18)
	public void bV5GammaGreen(int bV5GammaGreen) {
		io.setIntField(this, 18, bV5GammaGreen);
	}

	/** Toned response curve for blue. Used if <b>bV5CSType</b> is set to LCS_CALIBRATED_RGB. Specify in unsigned fixed 16.16 format. The
	 * upper 16 bits are the unsigned integer value. The lower 16 bits are the fractional part.
	 */
	@Field(19)
	public int bV5GammaBlue() {
		return io.getIntField(this, 19);
	}

	/** Toned response curve for blue. Used if <b>bV5CSType</b> is set to LCS_CALIBRATED_RGB. Specify in unsigned fixed 16.16 format. The
	 * upper 16 bits are the unsigned integer value. The lower 16 bits are the fractional part.
	 */
	@Field(19)
	public void bV5GammaBlue(int bV5GammaBlue) {
		io.setIntField(this, 19, bV5GammaBlue);
	}

	/** Rendering intent for bitmap. This can be one of the following values.
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
	 * <tr>
	 * <td><b>Value</b></td>
	 * <td><b>Intent</b></td>
	 * <td><b>ICC name</b></td>
	 * <td><b>Meaning</b></td>
	 * </tr><tr>
	 * <td>LCS_GM_ABS_COLORIMETRIC</td>
	 * <td>Match</td>
	 * <td>Absolute Colorimetric</td>
	 * <td>Maintains the white point. Matches the colors to their nearest color in the destination gamut.</td>
	 * </tr><tr>
	 * <td>LCS_GM_BUSINESS</td>
	 * <td>Graphic</td>
	 * <td>Saturation</td>
	 * <td>Maintains saturation. Used for business charts and other situations in which undithered colors are required.</td>
	 * </tr><tr>
	 * <td>LCS_GM_GRAPHICS</td>
	 * <td>Proof</td>
	 * <td>Relative Colorimetric</td>
	 * <td>Maintains colorimetric match. Used for graphic designs and named colors.</td>
	 * </tr><tr>
	 * <td>LCS_GM_IMAGES</td>
	 * <td>Picture</td>
	 * <td>Perceptual</td>
	 * <td>Maintains contrast. Used for photographs and natural images.</td>
	 * </tr>
	 * </table>
	 */
	@Field(20)
	public int bV5Intent() {
		return io.getIntField(this, 20);
	}

	/** Rendering intent for bitmap. This can be one of the following values.
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
	 * <tr>
	 * <td><b>Value</b></td>
	 * <td><b>Intent</b></td>
	 * <td><b>ICC name</b></td>
	 * <td><b>Meaning</b></td>
	 * </tr><tr>
	 * <td>LCS_GM_ABS_COLORIMETRIC</td>
	 * <td>Match</td>
	 * <td>Absolute Colorimetric</td>
	 * <td>Maintains the white point. Matches the colors to their nearest color in the destination gamut.</td>
	 * </tr><tr>
	 * <td>LCS_GM_BUSINESS</td>
	 * <td>Graphic</td>
	 * <td>Saturation</td>
	 * <td>Maintains saturation. Used for business charts and other situations in which undithered colors are required.</td>
	 * </tr><tr>
	 * <td>LCS_GM_GRAPHICS</td>
	 * <td>Proof</td>
	 * <td>Relative Colorimetric</td>
	 * <td>Maintains colorimetric match. Used for graphic designs and named colors.</td>
	 * </tr><tr>
	 * <td>LCS_GM_IMAGES</td>
	 * <td>Picture</td>
	 * <td>Perceptual</td>
	 * <td>Maintains contrast. Used for photographs and natural images.</td>
	 * </tr>
	 * </table>
	 */
	@Field(20)
	public void bV5Intent(int bV5Intent) {
		io.setIntField(this, 20, bV5Intent);
	}

	/** The offset, in bytes, from the beginning of the <b>BITMAPV5HEADER</b> structure to the start of the profile data. If the profile is
	 * embedded, profile data is the actual profile, and it is linked. (THe profile data is the null-terminated file name of the profile.)
	 * This cannot be a Unicode string. It must be composed exclusively of characters from the Windows character set (code page 1252). These
	 * profile members are ignored unless the <b>bV5CSType</b> member specifies PROFILE_LINKED or PROFILE_EMBEDDED.
	 */
	@Field(21)
	public int bV5ProfileData() {
		return io.getIntField(this, 21);
	}

	/** The offset, in bytes, from the beginning of the <b>BITMAPV5HEADER</b> structure to the start of the profile data. If the profile is
	 * embedded, profile data is the actual profile, and it is linked. (THe profile data is the null-terminated file name of the profile.)
	 * This cannot be a Unicode string. It must be composed exclusively of characters from the Windows character set (code page 1252). These
	 * profile members are ignored unless the <b>bV5CSType</b> member specifies PROFILE_LINKED or PROFILE_EMBEDDED.
	 */
	@Field(21)
	public void bV5ProfileData(int bV5ProfileData) {
		io.setIntField(this, 21, bV5ProfileData);
	}

	/** Size, in bytes, of embedded profile data.
	 */
	@Field(22)
	public int bV5ProfileSize() {
		return io.getIntField(this, 22);
	}

	/** Size, in bytes, of embedded profile data.
	 */
	@Field(22)
	public void bV5ProfileSize(int bV5ProfileSize) {
		io.setIntField(this, 22, bV5ProfileSize);
	}

	/** This member has been reserved. Its value should be set to zero.
	 */
	@Field(23)
	public int bV5Reserved() {
		return io.getIntField(this, 23);
	}

	/** This member has been reserved. Its value should be set to zero.
	 */
	@Field(23)
	public void bV5Reserved(int bV5Reserved) {
		io.setIntField(this, 23, bV5Reserved);
	}
	
}
