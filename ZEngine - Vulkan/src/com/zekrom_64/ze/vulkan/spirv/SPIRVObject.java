package com.zekrom_64.ze.vulkan.spirv;

import java.io.IOException;

public interface SPIRVObject {

	public void read(SPIRVReader reader) throws IOException;
	
	public void write(SPIRVWriter writer) throws IOException;
	
}
