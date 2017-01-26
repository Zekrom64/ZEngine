package com.zekrom_64.ze.logic.register;

public interface IRegister16 extends IRegister {

	public short value();
	public void value(short s);
	public short preinc();
	public short predec();
	public short postinc();
	public short postdec();
	public void set(int bit);
	public void reset(int bit);
	public boolean test(int bit);
	public void add(short s);
	public void and(short s);
	public void or(short s);
	public void xor(short s);
	
}
