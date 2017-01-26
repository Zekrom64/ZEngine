package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVRounding implements IntValuedEnum<AVRounding> {
	AV_ROUND_ZERO,
	AV_ROUND_INF,
	AV_ROUND_DOWN,
	AV_ROUND_UP,
	AV_ROUND_NEAR_INF,
	AV_ROUND_PASS_MINMAX;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVRounding> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
