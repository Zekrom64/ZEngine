package com.zekrom_64.ze.libav.enums.misc;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum OutputFormat implements IntValuedEnum<OutputFormat> {
	FMT_MPEG1,
	FMT_H261,
	FMT_H263,
	FMT_MJPEG;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<OutputFormat> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
