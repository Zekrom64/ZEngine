package com.zekrom_64.ze.nat.win;

import java.nio.ByteBuffer;

import org.bridj.BridJUtil;
import org.bridj.CLib;
import org.bridj.Pointer;

import com.zekrom_64.ze.nat.win.WinTypes.HANDLE;
import com.zekrom_64.ze.nat.win.WinTypes.HBITMAP;
import com.zekrom_64.ze.nat.win.WinTypes.HICON;
import com.zekrom_64.ze.nat.win.WinTypes.HWND;
import com.zekrom_64.ze.nat.win.structs.BITMAPV5HEADER;
import com.zekrom_64.ze.nat.win.structs.ICONINFO;

public class WindowsUtil {

	public static Pointer<HICON> iconFromARGB(ByteBuffer argb, int width, int height) {
		BITMAPV5HEADER bitmapInfo = new BITMAPV5HEADER();
		
		CLib.memset(bitmapInfo.getPointer(), 0, bitmapInfo.sizeof());
		bitmapInfo.bV5Size((int)bitmapInfo.sizeof());
		bitmapInfo.bV5Width(width);
		bitmapInfo.bV5Height(-height);
		bitmapInfo.bV5Planes((short)1);
		bitmapInfo.bV5BitCount((short)32);
		bitmapInfo.bV5Compression(GDI32.BI_BITFIELDS);
		bitmapInfo.bV5RedMask(0x00FF0000);
		bitmapInfo.bV5GreenMask(0x0000FF00);
		bitmapInfo.bV5BlueMask(0x000000FF);
		bitmapInfo.bV5AlphaMask(0xFF000000);
		
		@SuppressWarnings("unchecked")
		Pointer<Byte> ptrCursorImage = (Pointer<Byte>) Pointer.NULL;
		
		@SuppressWarnings("unchecked")
		Pointer<HBITMAP> colorDIB = GDI32.CreateDIBSection(
				User32.GetDC((Pointer<HWND>)Pointer.NULL),
				BridJUtil.uncheckedCast(bitmapInfo.getPointer()),
				GDI32.DIB_RGB_COLORS,
				Pointer.pointerToPointer(ptrCursorImage),
				(Pointer<HANDLE<?>>)Pointer.NULL,
				0
		);
		
		if (ptrCursorImage.getPeer()==0) throw new NullPointerException("Failed to allocate DIB section");
		
		/* 
		 * Original source code:
		 * 
		 *	for (y = 0; y < height; y++ ) {
	   	 *		dstPtr = ptrCursorImage + width*4*y;;
	   	 *		for (x = 0; x < width; x++ ) {
		 *			dstPtr[0] = (pixels[y*width+x] >> 0x10) & 0xFF;
		 *			dstPtr[1] = (pixels[y*width+x] >> 0x08) & 0xFF;
		 *			dstPtr[2] = pixels[y*width+x] & 0xFF;
		 *			dstPtr[3] = (pixels[y*width+x] >> 0x18) & 0xFF;
	   	 *			dstPtr += 4;
	   	 * 		}
	   	 *	}
	   	 *	
	   	 *		  0 1 2 3
	   	 *	C:    B G R A	ARGB, in little endian format
	   	 *	Java: R G B A	RGBA, in big endian format
	   	 *	ARGB: A R G B
	   	 *	
	   	 *	RGBA to ARGB conversion, but the data given is already in ARGB, so little to big
	   	 *
	   	 */
		
		CLib.memcpy(ptrCursorImage, Pointer.pointerToBuffer(argb), width * height * 4);
		
		@SuppressWarnings("unchecked")
		Pointer<HBITMAP> colorBitmap = GDI32.CreateDIBitmap(
				User32.GetDC((Pointer<HWND>)Pointer.NULL),
				BridJUtil.uncheckedCast(bitmapInfo.getPointer()),
				GDI32.CBM_INIT,
				ptrCursorImage,
				BridJUtil.uncheckedCast(bitmapInfo.getPointer()),
				GDI32.DIB_RGB_COLORS
		);
		
		GDI32.DeleteObject(BridJUtil.uncheckedCast(colorDIB));
		
		// Convert alpha map to pixel packed mask
		
		// number of bytes it takes to fit in a bit packed scan line.
		int widthInBytes = (width&0x7) != 0 ? (width >> 3) + 1 : (width >> 3);
		
		// number of bytes it takes to fit WORD padded scan line.
		int scanLineWidth = widthInBytes;
		if (scanLineWidth % 2 != 0) {
			scanLineWidth = (scanLineWidth / 2) * 2 + 2;
		}
		int imageSize = scanLineWidth * height;
		
		Pointer<Byte> maskPixels = Pointer.allocateBytes(imageSize);
		CLib.memset(maskPixels, (byte)0xFF, imageSize);
		
		Pointer<HBITMAP> cursorMask = GDI32.CreateBitmap(width, height, 1, 1, maskPixels);
		
		ICONINFO iconInfo = new ICONINFO();
		CLib.memset(iconInfo.getPointer(), (byte)0, iconInfo.sizeof());
		iconInfo.hbmMask(cursorMask);
		iconInfo.hbmColor(colorBitmap);
		iconInfo.fIcon(1); // TRUE
		iconInfo.xHotspot(0);
		iconInfo.yHotspot(0);
		
		Pointer<HICON> icon = User32.CreateIconIndirect(iconInfo.getPointer());
		
		GDI32.DeleteObject(BridJUtil.uncheckedCast(colorBitmap));
		GDI32.DeleteObject(BridJUtil.uncheckedCast(cursorMask));
		maskPixels.release();
		
		return icon;
	}
	
}
