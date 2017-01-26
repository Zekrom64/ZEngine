package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVColorPrimaries implements IntValuedEnum<AVColorPrimaries> {
	AVCOL_PRI_RESERVED0,
	AVCOL_PRI_BT709,
	AVCOL_PRI_UNSPECIFIED,
	AVCOL_PRI_RESERVED,
	AVCOL_PRI_BT470M,
	AVCOL_PRI_BT470BG,
	AVCOL_PRI_SMPTE170M,
	AVCOL_PRI_SMPTE240M,
	AVCOL_PRI_FILM,
	AVCOL_PRI_BT2020,
	AVCOL_PRI_SMPTEST428_1;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVColorPrimaries> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
