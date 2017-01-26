package com.zekrom_64.ze.libav.callback;

import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.SizeT;

import com.zekrom_64.ze.libav.struct.AVFormatContext;

public abstract class AVFormatControlMessage extends Callback<AVFormatControlMessage> {

	public abstract int invoke(Pointer<AVFormatContext> s, int type, Pointer<?> data, SizeT dataSize);
	
}
