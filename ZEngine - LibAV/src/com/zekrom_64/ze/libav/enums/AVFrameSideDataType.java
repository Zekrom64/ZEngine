package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVFrameSideDataType implements IntValuedEnum<AVFrameSideDataType> {
	AV_FRAME_DATA_PANSCAN,
	AV_FRAME_DATA_A53_CC,
	AV_FRAME_DATA_STEREO3D,
	AV_FRAME_DATA_MATRIXENCODING,
	AV_FRAME_DATA_DOWNMIX_INFO,
	AV_FRAME_DATA_REPLAYGAIN,
	AV_FRAME_DATA_DISPLAYMATRIX,
	AV_FRAME_DATA_AFD,
	AV_FRAME_DATA_MOTION_VECTORS,
	AV_FRAME_DATA_SKIP_SAMPLES,
	AV_FRAME_DATA_AUDIO_SERVICE_TYPE,
	AV_FRAME_DATA_MASTERING_DISPLAY_METADATA,
	AV_FRAME_DATA_GOP_TIMECODE;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVFrameSideDataType> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
