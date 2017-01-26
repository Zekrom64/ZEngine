package com.zekrom_64.ze.logic.register;

public class Register16 implements IRegister16 {

	public short value;
	
	@Override
	public short value() {
		return value;
	}

	@Override
	public void value(short s) {
		value = s;
	}

	@Override
	public short preinc() {
		return ++value;
	}

	@Override
	public short predec() {
		return --value;
	}

	@Override
	public short postinc() {
		return value++;
	}

	@Override
	public short postdec() {
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
	public void add(short s) {
		value += s;
	}

	@Override
	public void and(short s) {
		value &= s;
	}

	@Override
	public void or(short s) {
		value |= s;
	}

	@Override
	public void xor(short s) {
		value ^= s;
	}

}
