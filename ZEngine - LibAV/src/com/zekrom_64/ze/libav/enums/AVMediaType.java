package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;

public enum AVMediaType implements IntValuedEnum<AVMediaType> {
	AVMEDIA_TYPE_UNKNOWN(-1),  ///< Usually treated as AVMEDIA_TYPE_DATA
	AVMEDIA_TYPE_VIDEO(0),
	AVMEDIA_TYPE_AUDIO(1),
	AVMEDIA_TYPE_DATA(2),          ///< Opaque data information usually continuous
	AVMEDIA_TYPE_SUBTITLE(3),
	AVMEDIA_TYPE_ATTACHMENT(4),    ///< Opaque data information usually sparse
	AVMEDIA_TYPE_NB(5);
	
	static {
		BridJ.register();
	}
	
	private AVMediaType(int value) {
		this.value = value;
	}

	private int value;
	
	@Override
	public long value() {
		return value;
	}

	@Override
	public Iterator<AVMediaType> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
