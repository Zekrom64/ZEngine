package com.zekrom_64.ze.logic.register;

public interface IRegister32 extends IRegister {
	
	public int value();
	public void value(int i);
	public int preinc();
	public int predec();
	public int postinc();
	public int postdec();
	public void set(int bit);
	public void reset(int bit);
	public boolean test(int bit);
	public void add(int i);
	
}
