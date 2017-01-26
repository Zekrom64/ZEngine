package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVDiscard implements IntValuedEnum<AVDiscard> {
	AVDISCARD_NONE(-16),
	AVDISCARD_DEFAULT(0),
	AVDISCARD_NONREF(8),
	AVDISCARD_BIDIR(16),
	AVDISCARD_NONKEY(32),
	AVDISCARD_ALL(48);

	private final int value;
	
	private AVDiscard(int v) {
		value = v;
	}
	
	@Override
	public long value() {
		return value;
	}

	@Override
	public Iterator<AVDiscard> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
