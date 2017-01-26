package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVPictureStructure implements IntValuedEnum<AVPictureStructure> {
	AV_PICTURE_STRUCTURE_UNKNOWN,
	AV_PICTURE_STRUCTURE_TOP_FIELD,
	AV_PICTURE_STRUCTURE_BOTTOM_FIELD,
	AV_PICTURE_STRUCTURE_FRAME;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVPictureStructure> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
