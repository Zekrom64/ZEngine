package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVSubtitleType implements IntValuedEnum<AVSubtitleType> {
	SUBTITLE_NONE,
	SUBTITLE_BITMAP,
	SUBTITLE_TEXT,
	SUBTITLE_ASS;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVSubtitleType> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
