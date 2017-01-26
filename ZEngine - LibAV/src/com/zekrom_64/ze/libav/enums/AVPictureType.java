package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVPictureType implements IntValuedEnum<AVPictureType> {
	AV_PICTURE_TYPE_NONE,
	AV_PICTURE_TYPE_I,
	AV_PICTURE_TYPE_P,
	AV_PICTURE_TYPE_B,
	AV_PICTURE_TYPE_S,
	AV_PICTURE_TYPE_SI,
	AV_PICTURE_TYPE_SP,
	AV_PICTURE_TYPE_BI;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVPictureType> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
