package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;

public enum AVOptionType implements IntValuedEnum<AVOptionType> {
	AV_OPT_TYPE_FLAGS,
	AV_OPT_TYPE_INT,
	AV_OPT_TYPE_INT64,
	AV_OPT_TYPE_DOUBLE,
	AV_OPT_TYPE_FLOAT,
	AV_OPT_TYPE_STRING,
	AV_OPT_TYPE_RATIONAL,
	AV_OPT_TYPE_BINARY,  ///< offset must point to a pointer immediately followed by an int for the length
	AV_OPT_TYPE_DICT,
	AV_OPT_TYPE_CONST(128),
	;
	
	static {
		BridJ.register();
	}
	
	private AVOptionType() {
		value = ordinal();
	}

	private AVOptionType(int v) {
		value = v;
	}
	
	private int value;
	
	@Override
	public long value() {
		return value;
	}

	@Override
	public Iterator<AVOptionType> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
