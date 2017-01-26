package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVDurationEstimationMethod implements IntValuedEnum<AVDurationEstimationMethod> {
	AVFMT_DURATION_FROM_PTS,
	AVFMT_DURATION_FROM_STREAM,
	AVFMT_DURATION_FROM_BITRATE;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVDurationEstimationMethod> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
