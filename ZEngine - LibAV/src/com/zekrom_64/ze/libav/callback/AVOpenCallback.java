package com.zekrom_64.ze.libav.callback;

import org.bridj.Callback;
import org.bridj.Pointer;

import com.zekrom_64.ze.libav.struct.AVDictionary;
import com.zekrom_64.ze.libav.struct.AVFormatContext;
import com.zekrom_64.ze.libav.struct.AVIOContext;
import com.zekrom_64.ze.libav.struct.AVIOInterruptCB;

public abstract class AVOpenCallback extends Callback<AVOpenCallback> {

	public abstract int invoke(Pointer<AVFormatContext> s, Pointer<Pointer<AVIOContext>> pb, Pointer<Byte> url, int flags, 
			Pointer<AVIOInterruptCB> int_cb, Pointer<Pointer<AVDictionary>> options);
	
}
