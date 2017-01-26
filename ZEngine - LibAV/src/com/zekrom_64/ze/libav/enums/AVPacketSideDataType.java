package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVPacketSideDataType implements IntValuedEnum<AVPacketSideDataType> {
	AV_PKT_DATA_PALETTE,
	AV_PKT_DATA_NEW_EXTRADATA,
	AV_PKT_DATA_PARAM_CHANGE,
	AV_PKT_DATA_H263_MB_INFO,
	AV_PKT_DATA_REPLAYGAIN,
	AV_PKT_DATA_DISPLAYMATRIX,
	AV_PKT_DATA_STEREO3D;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVPacketSideDataType> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
