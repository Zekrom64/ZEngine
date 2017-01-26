package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVChromaLocation implements IntValuedEnum<AVChromaLocation> {
	AVCHROMA_LOC_UNSPECIFIED,
	AVCHROMA_LOC_LEFT,
	AVCHROMA_LOC_CENTER,
	AVCHROMA_LOC_TOPLEFT,
	AVCHROMA_LOC_TOP,
	AVCHROMA_LOC_BOTTOMLEFT,
	AVCHROMA_LOC_BOTTOM;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVChromaLocation> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
