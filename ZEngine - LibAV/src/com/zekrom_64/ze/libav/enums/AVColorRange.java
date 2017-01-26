package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVColorRange implements IntValuedEnum<AVColorRange> {
	AVCOL_RANGE_UNSPECIFIED,
	AVCOL_RANGE_MPEG,
	AVCOL_RANGE_JPEG,
	AVCOL_RANGE_NB;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVColorRange> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
