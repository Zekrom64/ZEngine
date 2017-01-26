package com.zekrom_64.ze.logic.io;

import com.zekrom_64.ze.logic.IDevice;
import com.zekrom_64.ze.logic.IDevice.IDeviceState;

public interface IIODevice<S extends IDeviceState> extends IDevice<S> {

	public void write(int address, byte data);
	public byte read(int address);
	
}
