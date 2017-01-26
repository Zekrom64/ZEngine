package com.zekrom_64.ze.nat.win;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Library;

import com.zekrom_64.ze.nat.win.WinTypes.HANDLE;
import com.zekrom_64.ze.nat.win.WinTypes.HBITMAP;
import com.zekrom_64.ze.nat.win.WinTypes.HDC;
import com.zekrom_64.ze.nat.win.WinTypes.HGDIOBJ;
import com.zekrom_64.ze.nat.win.structs.BITMAPINFO;
import com.zekrom_64.ze.nat.win.structs.BITMAPINFOHEADER;
import com.zekrom_64.ze.nat.win.structs.BITMAPV5HEADER;

@Library("GDI32")
public class GDI32 {

	static {
		BridJ.register();
	}
	
	/** Compression types
	 * @see com.zekrom_64.ze.nat.win.structs.BITMAPV5HEADER#bV5Compression()
	 */
	public static final int BI_RGB = 0, BI_RLE8 = 1, BI_RLE4 = 2, BI_BITFIELDS = 3, BI_JPEG = 4, BI_PNG = 5;
	
	/** Bitmap usage
	 * @see #CreateDIBSection
	 */
	public static final int DIB_PAL_COLORS = 1, DIB_RGB_COLORS = 0;
	
	/** Bitmap init
	 * @see #CreateDIBitmap
	 */
	public static final int CBM_INIT = 4;
	
	/** <p>The <b>CreateDIBitmap</b> function creates a compatible bitmap (DDB) from a DIB and, optionally, sets the bitmap bits.</p>
	 * 
	 * <p>The DDB that is created will be whatever bit depth your reference DC is. To create a bitmap that is of different bit depth,
	 * use {@link #CreateDIBSection}.</p>
	 * 
	 * <p>For a device to reach optimal bitmap-drawing speed, specify <i>fdwInit</i> as CBM_INIT. Then, use the same color depth DIB
	 * as the video mode. When the video is running 4- or 8-bpp, use DIB_PAL_COLORS.</p>
	 * 
	 * <p>The CBM_CREATDIB flag for the <i>fdwInit</i> parameter is no longer supported.</p>
	 * 
	 * <p>When you no longer need the bitmap, call the {@link #DeleteObject} function to delete it.</p>
	 * 
	 * <p><b>ICM:</b> No color management is performed. The contents of the resulting bitmap are not color matched after the bitmap
	 * has been created.</p>
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
	 * @param hdc A handle to a device context.
	 * @param lpbmih A pointer to a bitmap information header structure, {@link BITMAPV5HEADER}.<p>
	 * 		If <i>fdwInit</i> is CBM_INIT, the function uses the bitmap information header structure to obtain
	 * 		the desired width and height of the bitmap as well as other information. Note that a positive value
	 * 		for the height indicates a bottom-up DIB while a negative value for the height indicates a top-down
	 * 		DIB. Calling <b>CreateDIBitmap</b> with <i>fdwInit</i> as CBM_INIT is equivalent to calling the
	 * 		{@link #CreateCompatibleBitmap} function to create a DDB in the format of the device and then calling
	 * 		the {@link SetDIBits} function to translate the DIB bits to the DDB.</p>
	 * @param fdwInit Specifies how the system initializes the bitmap bits. THe following value is defined.
	 * 		<table border="1">
	 * 		<tr><td><b>Value</b></td><td><b>Meaning</b></td></tr>
	 * 		<tr><td><b>CBM_INIT</b></td>
	 * 		<td>
	 * 		<p>If this flag is set, the system uses the data pointed to by the <i>lpbInit</i> and <i>lpbmi</i>
	 * 		parameters to initialize the bitmap bits.</p>
	 * 		<p>If this flag is clear, the data pointed to by those parameters is not used</p>
	 * 		</td>
	 * 		</table>
	 * 		If <i>fdwInit</i> is zero, the system does not initialize the bitmap bits.
	 * @param lpbInit A pointer to an array of bytes containing the initial bitmap data. The format of the data
	 * 		depends on the <b>biBitCount</b> member of the {@link BITMAPINFO structure to which the <i>lpbmi</i>
	 * 		parameter points.
	 * @param lpbmi A pointer to a {@link BITMAPINFO} structure that describes the dimensions and color format of
	 * 		the array pointed to by the <i>lpbInit</i> parameter.
	 * @param fuUsage Specifies whether the <b>bmiColors</b> member of the {@link BITMAPINFO} structure was
	 * 		initialized and, if so, whether <b>bmiColors</b> contains explicit red, green, blue (RGB) values or
	 * 		palette indexes. The <i>fuUsage</i> parameter must be one of the following values.
	 * 		<table border="1">
	 * 		<tr><b><td>Value</td><td>Meaning</td></b></tr>
	 * 		<tr>
	 * 		<td><b>DIB_PAL_COLORS</b></td>
	 * 		<td>A color table is provided and consists of an array of 16-bit indexes into the logical palette of
	 * 		the device context into which the bitmap is to be selected.</td>
	 * 		</tr><tr>
	 * 		<td><b>DIB_RGB_COLORS</b></td>
	 * 		<td>A color table is provided and contains literal RGB values.</td>
	 * 		</tr>
	 * 		</table>
	 * @return <p>If the function succeeds, the return value is a handle to the compatible bitmap.</p>
	 * 		<p>If the function fails, the return value is <b>NULL</b>.</p>
	 */
	public static native Pointer<HBITMAP> CreateDIBitmap(
			Pointer<HDC> hdc,
			Pointer<BITMAPINFOHEADER> lpbmih,
			int fdwInit,
			Pointer<?> lpbInit,
			Pointer<BITMAPINFO> lpbmi,
			int fuUsage
	);
	
	/** <p>The <b>DeleteObject</b> function deletes a logical pen, brush, font, bitmap, region, or palette, freeing
	 * all the system resources associated with the object. After the object is deleted, the specified handle is
	 * no longer valid.</p>
	 * <p>Do not delete a drawing object (pen or brush) while it is still selected into a DC.</p>
	 * <p>When a pattern brush is deleted, the bitmap associated with the brush is not deleted. The bitmap must be
	 * deleted independently.</p>
	 * 
	 * @param hObject A handle to a logical pen, brush, font, bitmap, region, or palette.
	 * @return <p>If the function succeeds, the return value is nonzero.</p>
	 * 		<p>If the specified handle is not valid or is currently selected into a DC, the return value is zero.
	 */
	public static native boolean DeleteObject(Pointer<HGDIOBJ<?>> hObject);
	
	/** <p>The <b>CreateDIBSection</b> function creates a DIB that applications can write to directly. The function gives
	 * you a pointer to the location of the bitmap bit values. You can supply a handle to a file-mapping object that the
	 * function will use to create the bitmap, or you can let the system allocate the memory for the bitmap.</p>
	 * 
	 * <p>As noted above, if <i>hSection</i> is <b>NULL</b> the system allocates memory for the DIB. The system closes the
	 * handle to that memory when you later delete the DIB by calling the {@link #DeleteObject} function. If <i>hSection</i>
	 * is not <b>NULL</b>, you must close the <i>hSection</i> memory handle yourself after calling {@link #DeleteObject}
	 * to delete the bitmap.</p>
	 * 
	 * <p>You cannot paste a DIB section from one application into another application.</p>
	 * 
	 * <p><b>CreateDIBSection</b> does not use the {@link BITMAPINFOHEADER} parameters <i>biXPelsPerMeter</i> or
	 * <i>biYPelsPerMeter</i> and will not provide resolution information in the {@link BITMAPINFO} strcture.</p>
	 * 
	 * <p>You need to guarantee that the GDI subsystem has completed any drawing to a bitmap created by
	 * <b>CreateDIBSection</b> before you draw to the bitmap yourself. Access to the bitmap must be synchronized. Do
	 * this by calling the {@link DgiFlush} function. This applies to any use of the pointer to the bitmap bit values,
	 * including passing the pointer in calls to functions such as {@link SetDIBits}.</p>
	 * 
	 * <p><b>ICM:</b> No color management is done.
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
	 * @param hdc A handle to a device context. If the value of <i>iUsage</i> is DIB_PAL_COLORS, the function uses this
	 * 		device context's logical palette to initialize the DIB colors.
	 * @param pbmi A pointer to a {@link BITMAPINFO} structure that specifies various attributes of the DIB, including
	 * 		the bitmap dimensions and colors.
	 * @param iUsage The type of data contained in the <b>bmiColors</b> array member of the {@link BITMAPINFO} structure
	 * 		pointed to by <i>pbmi</i> (either logical) palette indexes or literal RGB values). The following values are defined.
	 * 		<table border="1">
	 * 		<tr><b>
	 * 		<td>Value</td><td>Meaning</td>
	 * 		</b></tr><tr>
	 * 		<td><b>DIB_PAL_COLORS</b></td>
	 * 		<td>The <b>bmiColors</b> member is an array of 16-bit indexes into the logical palette of the device context specified
	 * 		by the <i>hdc</i>.</td>
	 * 		</tr><tr>
	 * 		<td><b>DIB_RGB_COLORS</b></td>
	 * 		<td>The {@link BITMAPINFO} structure contains an array of literal RGB values.</td>
	 * 		</tr>
	 * 		</table>
	 * @param ppvBits A pointer to a variable that receives a pointer to the location of the DIB bit values.
	 * @param hSection A handle to a file mapping object that the function will use to create the DIB. This parameter can be <b>NULL</b>.
	 * 		<p>If <i>hSection</i> is not <b>NULL</b>, it must be a handle to a file-mapping object created by calling the
	 * 		{@link Kernel32#CreateFileMapping} function with the PAGE_READWRITE or PAGE_WRITECOPY flag. Read-only DIB sections are not
	 * 		supported. Handles created by other means will cause <b>CreateDIBSection</b> to fail.</p>
	 * 
	 * 		<p>If <i>hSection</i> is not <b>NULL</b>, the <b>CreateDIBSection</b> function locates the bitmap values at offset
	 * 		<i>dwOffset</i> in the file-mapping object referred to by <i>hSection</i>. An application can later retrieve the
	 * 		<i>hSection</i> handle by calling the {@link #GetObject} function with the <b>HBITMAP</b> returned by
	 * 		<b>CreateDIBSection</b>.</p>
	 * 
	 * 		<p>If <i>hSection is <b>NULL</b>, the system allocates memory for the DIB. In this case, the <b>CreateDIBSection</b>
	 * 		function ignores the <i>dwOffset</i> parameter.An application cannot later obtain a handle to this memory. The
	 * 		<b>bshSection</b> member of the {@link DIBSECTION} structure filled in by calling the {@link #GetObject} function will be
	 * 		<b>NULL</b>.
	 * 		
	 * @param dwOffset The offset from the beginning of the file-mapping object referenced by <i>hSection</i> where storage is to
	 * 		begin. This value is ignored if <i>hSection</i> is <b>NULL</b>. The bitmap bit values are aligned on doubleword boundaries,
	 * 		so <i>dwOffset</i> must be a multiple of the size of a <b>DWORD</b>
	 * @return If the function succeeds, the return value is a handle to the newly created DIC, and <i>*ppvBits</i> points to the bitmap
	 * 		bit values.
	 * 
	 * 		<p>If the function fails, the return value is <b>NULL</b> and <i>*ppvBits</i> is <b>NULL</b>.</p>
	 * 
	 * 		<p>This function can return the following value.</p>
	 * 
	 * 		<table>
	 * 		<tr><b>
	 * 		<td>Return code</td>
	 * 		<td>Description</td>
	 * 		</b></tr><tr>
	 * 		<td><b>ERROR_INVALID_PARAMETER</b></td>
	 * 		<td>One or more of the input parameters is invalid.</td>
	 * 		</tr>
	 * 		</table>
	 */
	public static native <P> Pointer<HBITMAP> CreateDIBSection(
			Pointer<HDC> hdc,
			Pointer<BITMAPINFO> pbmi,
			int iUsage,
			Pointer<Pointer<P>> ppvBits,
			Pointer<HANDLE<?>> hSection,
			int dwOffset
	);
	
	/** <p>The <b>GetObject</b> function retrieves information for the specified graphics object.</p>
	 * 
	 * <p>The buffer pointed to by the <i>lpvObject</i> parameter must be sufficiently large to receive the information about the graphics
	 * object. Depending on the graphics object, the function uses a {@link BITMAP}, {@link DIBSECTION}, {@link EXTLOGPEN},
	 * {@link LOGBRUSH}, {@link LOGFONT}, or {@link LOGPEN} structure, or a count of table entries (for a logical palette).</p>
	 * 
	 * <p>If <i>hgdiobj</i> is a handle to a bitmap created by calling {@link #CreateDIBSection}, and the specified buffer is large enough,
	 * the <b>GetObject</b> function returns a {@link DIBSECTION} structure. In addition, the <b>bmBits</b> member of the {@link BITMAP}
	 * structure contained within the <b>DIBSECTION</b> will contain a pointer to the bitmap's bit values.</p>
	 * 
	 * <p>If <i>hgdiobj</i> is a handle to a bitmap created by other means, <b>GetObject</b> returns only the width, height, and color
	 * format information of the bitmap. You can obtain the bitmap's bit values by calling the {@link #GetDIBits} or {@link #GetBitmapBits}
	 * function.</p>
	 * 
	 * <p>If <i>hgdiobj</i> is a handle to a logical palette, <b>GetObject</b> retrieves a 2-byte integer that specifies the number of entries
	 * in the palette. The function does not retrieve the {@link LOGPALLETE} structure defining the palette. TO retrieve information about
	 * palette entries, an application can call the {@link #GetPaletteEntries} function.</p>
	 * 
	 * <p>If <i>hgdiobj</i> is a handle to a font, the {@link LOGFONT} that is returned is the <b>LOGFONT</b> used to create the font. If
	 * Windows had to make some interpolation of the font because the precise <b>LOGFONT</b> could not be represented, the interpolation
	 * will not be reflected in the <b>LOGFONT</b>. For example, if you ask for a vertical version of a font that doesn't support vertical
	 * painting, the <b>LOGFONT</b> indicates the font is vertical, but Windows will paint it horizontally.</p>
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
	 * @param hgdiobj A handle to the graphics object of interest. This can be a handle to one of the following: a logical bitmap, a brush,
	 * a font, a palette, a pen, pr a device independent bitmap created by calling the {@link #CreateDIBSection} function.
	 * @param cbBuffer The number of bytes of information to be written to the buffer.
	 * @param lpvObject A pointer to a buffer that receives the information about the specified graphics object.
	 * 		<p>The following table shows the type of information the buffer receives for each type of graphics object you can specify
	 * 		with <i>hgdiobj</i>.
	 * 		<table border="1">
	 * 		<tr><b>
	 * 		<td>Object type</td><td>Data written to buffer</td>
	 * 		</b></tr><tr>
	 * 		<td><b>HBITMAP</b></td><td>{@link BITMAP}</td>
	 * 		</tr><tr>
	 * 		<td><b>HBITMAP returned from a call to</b> {@link #CreateDIBSection}</td>
	 * 		<td>{@link DIBSECTION}, if <i>cbBuffer</i> is set to <tt>sizoef (DIBSECTION)</tt>, or {@link BITMAP}, if <i>cbBuffer</i>
	 * 		is set to <tt>sizeof (BITMAP)</tt>.</td>
	 * 		</tr><tr>
	 * 		<td><b>HPALETTE</b></td><td>A <b>WORD</b> count of the number of entries in the logical palette.</td>
	 * 		</tr><tr>
	 * 		<td><b>HPEN returned from a call to</b> {@link #ExtCreatePen}</td><td>{@link EXTLOGPEN}</td>
	 * 		</tr><tr>
	 * 		<td><b>HPEN</b></td><td>{@link LOGPEN}</td>
	 * 		</tr><tr>
	 * 		<td><b>HBRUSH</b></td><td>{@link LOGBRUSH}</td>
	 * 		</tr><tr>
	 * 		<td><b>HFONT</b></td><td>{@link LOGFONT}</td>
	 * 		</tr>
	 * 		</table>
	 * 		<p>If the <i>lpvObject</i> parameter is <b>NULL</b>, the function return value is the number of bytes required to store the
	 * 		information it writes to the buffer for the specified graphics object.</p>
	 * 		<p>The address of <i>lpvObject</i> must be on a 4-byte boundary; otherwise, <b>GetObject</b> fails.
	 * @return If the function succeeds, and <i>lpvObject</i> is a valid pointer, the return value is the number of bytes stored into
	 * 		the buffer.
	 * 		<p>If the function succeeds, and <i>lpvObject</i> is <b>NULL</b>, the return value is the number of bytes required to hold the
	 * 		information the function would store into the buffer.</p>
	 * 		<p>If the function fails, the return value is zero.</p>
	 */
	public static native int GetObject(
			Pointer<HGDIOBJ<?>> hgdiobj,
			int cbBuffer,
			Pointer<?> lpvObject
	);
	
	/** <p>The <b>CreateBitmap</b> function creates a bitmap with the specified width, height, and color format (color planes and
	 * bits-per-pixel).</p>
	 * 
	 * <p>The <b>CreateBitmap</b> function creates a device-dependent bitmap.</p>
	 * 
	 * <p>After a bitmap is created, it can be selected into a device context by calling the {@link #SelectObject} function. However,
	 * the bitmap can only be selected into a device context if the bitmap and the DC have the same format.</p>
	 * 
	 * <p>The <b>CreateBitmap</b> function can be used to create color bitmaps. However, for performance reasons applications should
	 * use <b>CreateBitmap</b> to create monochrome bitmaps and {@link #CreateCompatibleBitmap} to create color bitmaps. Whenever
	 * a color bitmap returned from <b>CreateBitmap</b> is selected into a device context, the system checks that the bitmap matches
	 * the format of the device context it is being selected into. Because <b>CreateCompatibleBitmap</b> takes a device context, it
	 * returns a bitmap that has the same format as the specified device context. Thus, subsequent calls to {@link #SelectObject} are
	 * faster with a color bitmap from <b>CreateCompatibleBitmap</b> than with a color bitmap returned from <b>CreateBitmap</b>.</p>
	 * 
	 * <p>If the bitmap is monochrome, zeros represent the foreground color and ones represent the background color for the destination
	 * device context.</p>
	 * 
	 * <p>If an application sets the <i>nWidth</i> or <i>nHeight</i> parameters to zero, <b>CreateBitmap</b> returns the handle to a
	 * 1-by-1 pixel, monochrome bitmap.</p>
	 * 
	 * <p>When you no longer need the bitmap, call the {@link #DelteObject} function to delete it.</p>
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
	 * @param nWidth The bitmap width, in pixels.
	 * @param nHeight The bitmap height, in pixels.
	 * @param cPlanes The number of color planes used by the device.
	 * @param cBitsPerPel The number of bits required to identify the color of a single pixel.
	 * @param lpvBits A pointer to an array of color data used to set the color in a rectangle of pixels. Each scan line in the rectangle
	 * 		must be word aligned (scan lines that are not word aligned must be padded with zeros). If this parameter is <b>NULL</b>, the
	 * 		contents of the new bitmap is undefined.
	 * @return If the function succeeds, the return value is a handle to a bitmap.
	 * 		<p>If the function fails, the return value is <b>NULL</b>.</p>
	 *		<p>This function can return the following value.</p>
	 *		<table>
	 *		<tr><b>
	 *		<td>Return code</td><td>Description</td>
	 *		</b></tr><tr>
	 *		<td><b>ERROR_INVALID_BITMAP</b></td><td>The calculated size of the bitmap is less than zero.</td>
	 *		</tr>
	 *		</table>
	 */
	public static native Pointer<HBITMAP> CreateBitmap(int nWidth, int nHeight, int cPlanes, int cBitsPerPel, Pointer<?> lpvBits);
	
}
