package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVStreamParseType implements IntValuedEnum<AVStreamParseType> {
	AVSTREAM_PARSE_NONE,
	AVSTREAM_PARSE_FULL,
	AVSTREAM_PARSE_HEADERS,
	AVSTREAM_PARSE_TIMESTAMPS,
	AVSTREAM_PARSE_FULL_ONCE,
	AVSTREAM_PARSE_FULL_RAW;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVStreamParseType> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
