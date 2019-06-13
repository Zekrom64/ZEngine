package com.zekrom_64.ze.edit.text;

public class NameMapping {

	public static String getFileFormatName(String format) {
		switch(format) {
		case "jpg":
		case "jpeg": return "JPEG Image";
		case "png": return "PNG Image";
		case "bmp": return "Bitmap Image";
		case "tga": return "Truevision TGA Image";
		case "psd": return "Photoshop Document";
		case "gif": return "GIF Image";
		case "hdr": return "Radiance HDR Image";
		case "pic": return "PICtor Image";
		case "pnm": return "Netpbm Image";
		}
		return format.toUpperCase();
	}
	
}
