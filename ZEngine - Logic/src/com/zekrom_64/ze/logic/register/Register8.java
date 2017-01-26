package com.zekrom_64.ze.logic.register;

public class Register8 implements IRegister8 {

	public byte value;
	
	@Override
	public byte value() {
		return value;
	}

	@Override
	public void value(byte val) {
		value = val;
	}

	@Override
	public byte preinc() {
		return ++value;
	}

	@Override
	public byte predec() {
		return --value;
	}

	@Override
	public byte postinc() {
		return value++;
	}

	@Override
	public byte postdec() {
		return value--;
	}

	@Override
	public void set(int bit) {
		value |= (1 << bit);
	}

	@Override
	public void reset(int bit) {
		value &= ~(1 << bit);
	}

	@Override
	public boolean test(int bit) {
		return (value & (1 << bit)) != 0;
	}

	@Override
	public void add(byte b) {
		value += b;
	}

	@Override
	public void and(byte b) {
		value &= b;
	}

	@Override
	public void or(byte b) {
		value |= b;
	}

	@Override
	public void xor(byte b) {
		value ^= b;
	}

}
