package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVFieldOrder implements IntValuedEnum<AVFieldOrder> {
	AV_FIELD_UNKNOWN,
	AV_FIELD_PROGRESSIVE,
	AV_FIELD_TT,
	AV_FIELD_BB,
	AV_FIELD_TB,
	AV_FIELD_BT;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVFieldOrder> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
