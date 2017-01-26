package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVColorSpace implements IntValuedEnum<AVColorSpace> {
	AVCOL_SPC_RGB,
	AVCOL_SPC_BT709,
	AVCOL_SPC_UNSPECIFIED,
	AVCOL_SPC_RESERVED,
	AVCOL_SPC_FCC,
	AVCOL_SPC_BT470BG,
	AVCOL_SPC_SMPTE170M,
	AVCOL_SPC_SMPTE240M,
	AVCOL_SPC_YCOCG,
	AVCOL_SPC_BT2020_NCL,
	AVCOL_SPC_BT2020_CL;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVColorSpace> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
