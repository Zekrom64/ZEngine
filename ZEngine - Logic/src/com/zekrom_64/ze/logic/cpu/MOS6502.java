package com.zekrom_64.ze.logic.cpu;

public class MOS6502 extends CPU {

	public MOS6502(ICPUSocket socket) {
		super(socket);
	}

	@Override
	public int tick() {
		return 0;
	}

	@Override
	public int reset() {
		return 0;
	}

}
