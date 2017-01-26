package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;

public enum AVSampleFormat implements IntValuedEnum<AVSampleFormat> {
	AV_SAMPLE_FMT_NONE,
	AV_SAMPLE_FMT_U8,          ///< unsigned 8 bits
	AV_SAMPLE_FMT_S16,         ///< signed 16 bits
	AV_SAMPLE_FMT_S32,         ///< signed 32 bits
	AV_SAMPLE_FMT_FLT,         ///< float
	AV_SAMPLE_FMT_DBL,         ///< double
	
	AV_SAMPLE_FMT_U8P,         ///< unsigned 8 bits, planar
	AV_SAMPLE_FMT_S16P,        ///< signed 16 bits, planar
	AV_SAMPLE_FMT_S32P,        ///< signed 32 bits, planar
	AV_SAMPLE_FMT_FLTP,        ///< float, planar
	AV_SAMPLE_FMT_DBLP,        ///< double, planar
	
	AV_SAMPLE_FMT_NB           ///< Number of sample formats. DO NOT USE if linking dynamically;
	;
	
	static {
		BridJ.register();
	}

	@Override
	public long value() {
		return ordinal()-1;
	}

	@Override
	public Iterator<AVSampleFormat> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
