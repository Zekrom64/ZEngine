package com.zekrom_64.ze.logic.io.audio;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.zekrom_64.ze.logic.IDevice.IDeviceState;
import com.zekrom_64.ze.logic.io.IIODevice;

public interface IDeviceAudioOutput<S extends IDeviceState> extends IIODevice<S> {

	public UnitInputPort[] getInputs();
	
	public UnitOutputPort[] getOutputs();
	
}
