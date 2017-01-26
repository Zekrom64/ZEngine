package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVColorTransferCharacteristic implements IntValuedEnum<AVColorTransferCharacteristic> {
	AVCOL_TRC_RESERVED0,
	AVCOL_TRC_BT709,
	AVCOL_TRC_UNSPECIFIED,
	AVCOL_TRC_RESERVED,
	AVCOL_TRC_GAMMA22,
	AVCOL_TRC_GAMMA28,
	AVCOL_TRC_SMPTE170M,
	AVCOL_TRC_SMPTE240M,
	AVCOL_TRC_LINEAR,
	AVCOL_TRC_LOG,
	AVCOL_TRC_LOG_SQRT,
	AVCOL_TRC_IEC61966_2_4,
	AVCOL_TRC_BT1361_ECG,
	AVCOL_TRC_IEC61966_2_1,
	AVCOL_TRC_BT2020_10,
	AVCOL_TRC_BT2020_12,
	AVCOL_TRC_SMPTEST2084,
	AVCOL_TRC_SMPTEST428_1,
	AVCOL_TRC_ARIB_STD_B67;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVColorTransferCharacteristic> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
