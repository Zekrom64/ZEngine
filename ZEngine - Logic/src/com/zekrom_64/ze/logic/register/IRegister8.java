package com.zekrom_64.ze.logic.register;

public interface IRegister8 extends IRegister {

	public byte value();
	public void value(byte val);
	public byte preinc();
	public byte predec();
	public byte postinc();
	public byte postdec();
	public void set(int bit);
	public void reset(int bit);
	public boolean test(int bit);
	public void add(byte b);
	public void and(byte b);
	public void or(byte b);
	public void xor(byte b);
}
