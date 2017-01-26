package com.zekrom_64.ze.logic;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import com.zekrom_64.ze.logic.IDevice.IDeviceState;

public interface IDevice<S extends IDeviceState> {

	public interface IDeviceState extends Cloneable {
		
		public void write(DataOutput out) throws IOException;
		
		public void read(DataInput in) throws IOException;
		
	}
	
	public IDeviceState saveState();
	
	public void loadState(S state);
	
}
