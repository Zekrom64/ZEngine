package com.zekrom_64.ze.logic.cpu;

import com.zekrom_64.ze.logic.register.IRegister16;
import com.zekrom_64.ze.logic.register.IRegister8;

public class Z80Register implements IRegister16 {

	public short value;
	
	public final IRegister8 upper = new IRegister8() {

		@Override
		public byte value() {
			return (byte) (value >> 8);
		}

		@Override
		public void value(byte val) {
			value = (short) ((value & 0xFF) | val);
		}

		@Override
		public byte preinc() {
			return 0;
		}

		@Override
		public byte predec() {
			return 0;
		}

		@Override
		public byte postinc() {
			return 0;
		}

		@Override
		public byte postdec() {
			return 0;
		}

		@Override
		public void set(int bit) {
			value |= (1 << (bit + 8));
		}

		@Override
		public void reset(int bit) {
			value &= ~(1 << (bit + 8));
		}

		@Override
		public boolean test(int bit) {
			return false;
		}

		@Override
		public void add(byte b) {
			byte val = (byte)(value >> 8);
			val += b;
			value = (short)((val << 8) | (value & 0xFF));
		}

		@Override
		public void and(byte b) {
			value &= (b << 8) | 0xFF;
		}

		@Override
		public void or(byte b) {
			value |= (b << 8);
		}

		@Override
		public void xor(byte b) {
			value ^= (b << 8);
		}
		
	};
	
	public final IRegister8 lower = new IRegister8() {

		@Override
		public byte value() {
			return (byte)(value & 0xFF);
		}

		@Override
		public void value(byte val) {
			value = (short)((value & 0xFF00) | val);
		}

		@Override
		public byte preinc() {
			byte b = (byte)(value & 0xFF);
			value = (short) ((value & 0xFF00) | (b + 1));
			return b;
		}

		@Override
		public byte predec() {
			byte b = (byte)(value & 0xFF);
			value = (short) ((value & 0xFF00) | (b - 1));
			return b;
		}

		@Override
		public byte postinc() {
			byte b = (byte)(value & 0xFF);
			b++;
			value = (short)((value & 0xFF00) | (b));
			return b;
		}

		@Override
		public byte postdec() {
			byte b = (byte)(value & 0xFF);
			b--;
			value = (short)((value & 0xFF00) | (b));
			return b;
		}

		@Override
		public void set(int bit) {
			value |= ((1 << bit) & 0xFF);
		}

		@Override
		public void reset(int bit) {
			value &= ~((1 << bit) & 0xFF) | 0xFF00;
		}

		@Override
		public boolean test(int bit) {
			return (value & (1 << bit)) != 0;
		}

		@Override
		public void add(byte b) {
			byte val = (byte)(value & 0xFF);
			val += b;
			value = (short)((value & 0xFF00) | val);
		}

		@Override
		public void and(byte b) {
			value &= b | 0xFF00;
		}

		@Override
		public void or(byte b) {
			value |= b;
		}

		@Override
		public void xor(byte b) {
			value ^= b;
		}
		
	};
	
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
