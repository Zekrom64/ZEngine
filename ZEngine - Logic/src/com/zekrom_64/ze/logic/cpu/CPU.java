package com.zekrom_64.ze.logic.cpu;

public abstract class CPU {

	public final ICPUSocket socket;
	
	public CPU(ICPUSocket socket) {
		this.socket = socket;
	}
	
	public abstract int tick();
	
	public abstract int reset();
	
}
