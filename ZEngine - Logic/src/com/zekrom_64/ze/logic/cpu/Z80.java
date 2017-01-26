package com.zekrom_64.ze.logic.cpu;

import com.zekrom_64.ze.logic.register.IRegister16;
import com.zekrom_64.ze.logic.register.IRegister8;
import com.zekrom_64.ze.logic.register.Register16;
import com.zekrom_64.ze.logic.register.Register8;

public class Z80 extends CPU {

	public Z80(ICPUSocket socket) {
		super(socket);
	}
	
	private boolean internalHalt = false;
	private boolean interruptsEnabled = true;
	
	public final Z80Register AF = new Z80Register();
	public final IRegister8 A = AF.upper;
	public final IRegister8 F = AF.lower;
	public final Z80Register BC = new Z80Register();
	public final IRegister8 B = BC.upper;
	public final IRegister8 C = BC.lower;
	public final Z80Register DE = new Z80Register();
	public final IRegister8 D = DE.upper;
	public final IRegister8 E = DE.lower;
	public final Z80Register HL = new Z80Register();
	public final IRegister8 H = HL.upper;
	public final IRegister8 L = HL.lower;
	public final Z80Register AF_ALT = new Z80Register();
	public final IRegister8 A_ALT = AF_ALT.upper;
	public final IRegister8 F_ALT = AF_ALT.lower;
	public final Z80Register BC_ALT = new Z80Register();
	public final IRegister8 B_ALT = BC_ALT.upper;
	public final IRegister8 C_ALT = BC_ALT.lower;
	public final Z80Register DE_ALT = new Z80Register();
	public final IRegister8 D_ALT = DE_ALT.upper;
	public final IRegister8 E_ALT = DE_ALT.lower;
	public final Z80Register HL_ALT = new Z80Register();
	public final IRegister8 H_ALT = HL_ALT.upper;
	public final IRegister8 L_ALT = HL_ALT.lower;
	
	public final Register16 IX = new Register16();
	public final Register16 IY = new Register16();
	public final Register16 SP = new Register16();
	public final Register16 PC = new Register16();
	
	public final Register8 I = new Register8();
	public final Register8 R = new Register8();
	
	private final IRegister8 HL_POINTER = new IRegister8() {

		@Override
		public byte value() {
			return socket.readByte((int)currentRegs[3].value);
		}

		@Override
		public void value(byte val) {
			socket.writeByte((int)currentRegs[3].value, val);
		}

		@Override
		public byte preinc() {
			int addr = (int)currentRegs[3].value;
			byte val = socket.readByte(addr);
			socket.writeByte(addr, (byte) (val + 1));
			return val;
		}

		@Override
		public byte predec() {
			int addr = (int)currentRegs[3].value;
			byte val = socket.readByte(addr);
			socket.writeByte(addr, (byte) (val - 1));
			return val;
		}

		@Override
		public byte postinc() {
			int addr = (int)currentRegs[3].value;
			byte val = socket.readByte(addr);
			socket.writeByte(addr, ++val);
			return val;
		}

		@Override
		public byte postdec() {
			int addr = (int)currentRegs[3].value;
			byte val = socket.readByte(addr);
			socket.writeByte(addr, --val);
			return val;
		}

		@Override
		public void set(int bit) {
			int addr = (int)currentRegs[3].value;
			byte val = socket.readByte(addr);
			val |= (1 << bit);
			socket.writeByte(addr, val);
		}

		@Override
		public void reset(int bit) {
			int addr = (int)currentRegs[3].value;
			byte val = socket.readByte(addr);
			val &= ~(1 << bit);
			socket.writeByte(addr, val);
		}

		@Override
		public boolean test(int bit) {
			int addr = (int)currentRegs[3].value;
			return (socket.readByte(addr) & (1 << bit)) != 0;
		}

		@Override
		public void add(byte b) {
			int addr = (int)currentRegs[3].value;
			byte val = socket.readByte(addr);
			val += b;
			socket.writeByte(addr, val);
		}

		@Override
		public void and(byte b) {
			int addr = (int)currentRegs[3].value;
			byte val = socket.readByte(addr);
			val &= b;
			socket.writeByte(addr, val);
		}

		@Override
		public void or(byte b) {
			int addr = (int)currentRegs[3].value;
			byte val = socket.readByte(addr);
			val |= b;
			socket.writeByte(addr, val);
		}

		@Override
		public void xor(byte b) {
			int addr = (int)currentRegs[3].value;
			byte val = socket.readByte(addr);
			val ^= b;
			socket.writeByte(addr, val);
		}
		
	};
	
	private Z80Register[] currentRegs = new Z80Register[] {
		AF,
		BC,
		DE,
		HL
	};
	
	private IRegister8[] r = new IRegister8[] {
		B,
		C,
		D,
		E,
		H,
		L,
		HL_POINTER,
		A
	};
	
	private IRegister16[] rp = new IRegister16[] {
		BC,
		DE,
		HL,
		SP
	};
	
	private IRegister16[] rp2 = new IRegister16[] {
		BC,
		DE,
		HL,
		AF
	};
	
	private boolean registerTable = false;
	private boolean registerTableAF = false;
	
	private void exx() {
		exaf();
		if (registerTable) {
			currentRegs[1] = BC_ALT;
			currentRegs[2] = DE_ALT;
			currentRegs[3] = HL_ALT;
			r[0] = B_ALT;
			r[1] = C_ALT;
			r[2] = D_ALT;
			r[3] = E_ALT;
			r[4] = H_ALT;
			r[5] = L_ALT;
			rp[0] = BC_ALT;
			rp[1] = DE_ALT;
			rp[2] = HL_ALT;
			rp2[0] = BC_ALT;
			rp2[1] = DE_ALT;
			rp2[2] = HL_ALT;
		} else {
			currentRegs[1] = BC;
			currentRegs[2] = DE;
			currentRegs[3] = HL;
			r[0] = B;
			r[1] = C;
			r[2] = D;
			r[3] = E;
			r[4] = H;
			r[5] = L;
			rp[0] = BC;
			rp[1] = DE;
			rp[2] = HL;
			rp2[0] = BC;
			rp2[1] = DE;
			rp2[2] = HL;
		}
		registerTable = !registerTable;
	}
	
	private void exaf() {
		if (registerTableAF) {
			currentRegs[0] = AF_ALT;
			r[7] = A_ALT;
			rp2[3] = AF_ALT;
		} else {
			currentRegs[0] = AF;
			r[7] = A;
			rp2[3] = AF;
		}
	}
	
	private boolean sign() {
		return (registerTableAF ? F_ALT : F).test(8);
	}
	
	private boolean zero() {
		return (registerTableAF ? F_ALT : F).test(7);
	}
	
	private boolean halfcarry() {
		return (registerTableAF ? F_ALT : F).test(5);
	}
	
	private boolean parity() {
		return (registerTableAF ? F_ALT : F).test(3);
	}
	
	private boolean overflow() {
		return (registerTableAF ? F_ALT : F).test(3);
	}
	
	private boolean subtract() {
		return (registerTableAF ? F_ALT : F).test(2);
	}
	
	private boolean carry() {
		return (registerTableAF ? F_ALT : F).test(1);
	}
	
	private short flagsAdd16(short a, short b) {
		byte f = (registerTableAF ? F_ALT : F).value();
		int res = a + b;
		// Clear subtraction bit
		f &= 0xFD;
		// If bits carry
		if ((res&0xFFFF0000)!=0) f |= 0x1;
		// If half carry
		if ((((byte)a)+((byte)b)&0x100) != 0) f |= 0x10;
		(registerTableAF ? F_ALT : F).value(f);
		return (short)res;
	}
	
	private byte flagsAdd8(byte a, byte b) {
		byte f = (registerTableAF ? F_ALT : F).value();
		int res = a + b;
		// Clear bits
		f &= 0x28;
		// If bits carry, in addition overflow = carry
		if ((res&0xFFFF0000)!=0) f |= 0x81;
		// If half carry
		if ((a&0xF)+(b&0xF)!=0) f |= 0x10;
		// If zero
		if (res==0) f |= 0x40;
		// If negative
		if (res<0) f |= 0x80;
		(registerTableAF ? F_ALT : F).value(f);
		return (byte)res;
	}
	
	private byte flagsAddc8(byte a, byte b) {
		byte f = (registerTableAF ? F_ALT : F).value();
		int res = a + b;
		if (carry()) res += 1;
		// Clear bits
		f &= 0x28;
		// If bits carry
		if ((res&0xFFFF0000)!=0) f |= 1;
		// If half carry
		if ((a&0xF)+(b&0xF)!=0) f |= 0x10;
		// If zero
		if (res==0) f |= 0x40;
		// If negative
		if (res<0) f |= 0x80;
		// If overflow
		if (res<-128||res>127) f |= 0x4;
		(registerTableAF ? F_ALT : F).value(f);
		return (byte)res;
	}
	
	private void flagsInc8(byte v) {
		IRegister8 r = (registerTableAF ? F_ALT : F);
		// Clear subtract
		r.reset(2);
		// If zero, set overflow and zero
		if (v==0) r.or((byte) 0x44);
		// Else clear these flags
		else r.and((byte)~0x44);
		// Set sign
		r.and((byte) (0x7F | v));
		// Find half carry
		if ((v&0x1F)==0x10) r.set(5);
	}
	
	private void flagsDec8(byte v) {
		IRegister8 r = (registerTableAF ? F_ALT : F);
		// Set subtract
		r.set(2);
		// If largest value, set overflow
		if (v==0xFF) r.set(3);
		else r.reset(3);
		// If zero, set zero
		if (v==0) r.set(7);
		r.reset(7);
		// Set sign
		r.and((byte) (0x7F | v));
		// Find half carry
		if ((v&0x1F)==0x0F) r.set(5);
	} 
	
	@Override
	public int tick() {
		int decode = decode(nextByte());
		if (decode>0) {
			return decode;
		}
		return 4;
	}

	@Override
	public int reset() {
		internalHalt = false;
		return 0;
	}
	
	private boolean cc(int cc) {
		switch(cc) {
		case 0: return !zero();
		case 1: return zero();
		case 2: return !carry();
		case 3: return carry();
		case 4: return !parity();
		case 5: return parity();
		case 6: return !sign();
		case 7: return sign();
		}
		return false;
	}
	
	// BITS
	private int decodeCB(byte insn) {
		R.value = (byte) (0x7F & (R.value++));
		return 0;
	}
	
	// EXTD
	private int decodeED(byte insn) {
		R.value = (byte) (0x7F & (R.value++));
		return 0;
	}
	
	// IX
	private int decodeDD(byte insn) {
		R.value = (byte) (0x7F & (R.value++));
		return 0;
	}
	
	// IY
	private int decodeFD(byte insn) {
		R.value = (byte) (0x7F & (R.value++));
		return 0;
	}
	
	// IX BITS
	private int decodeDDCB(byte insn) {
		R.value = (byte) (0x7F & (R.value++));
		return 0;
	}
	
	// IY BITS
	private int decodeFDCB(byte insn) {
		R.value = (byte) (0x7F & (R.value++));
		return 0;
	}
	
	private int decode(byte insn) {
		R.value = (byte) (0x7F & (R.value++));
		// Fast response for NOPs
		if (insn==0) return 4;
		int z = insn & 0x7;
		int x = (insn & 0xC0) >> 6;
		int y = (insn & 0x38) >> 3;
		boolean q = (y & 1) != 0;
		int p = y >> 1;
		switch(x) {
		case 0:
			switch(z) {
			// Relative jumps and assorted ops
			case 0:
				switch(y) {
				// EX AF, AF'
				case 1: exaf(); return 4;
				// DJNZ d
				case 2: 
				{
					short pc = PC.value;
					byte jr = nextByte();
					if (r[0].postdec()!=0) {
						PC.value = (short)(pc + jr - 2);
						return 13;
					} else return 8;
				}
				// JR d
				case 3:
				{
					short pc = PC.value;
					byte jr = nextByte();
					PC.value = (short)(pc + jr - 2);
					return 12;
				}
				// JR cc[y-4],d
				default:
					short pc = PC.value;
					byte jr = nextByte();
					if (cc(y-4)) {
						PC.value = (short)(pc + jr - 2);
						return 12;
					} else return 7;
				}
			// 16-bit load immediate/add
			case 1:
				// ADD HL, rp[p]
				if (q) {
					rp[2].value(flagsAdd16(rp[p].value(), rp[2].value()));
					return 11;
				// LD rp[p], nn
				} else {
					rp[p].value(nextShort());
					return 10;
				}
			// Indirect loading
			case 2:
				if (q) {
					switch(p) {
					// LD A, (BC)
					case 0: {
						IRegister8 a = registerTableAF ? A_ALT : A;
						a.value(socket.readByte((int)rp[0].value()));
						return 7;
					}
					// LD A, (DE)
					case 1: {
						IRegister8 a = registerTableAF ? A_ALT : A;
						a.value(socket.readByte((int)rp[1].value()));
						return 7;
					}
					// LD HL, (nn)
					case 2: {
						rp[2].value(socket.readShort((int)nextShort()));
						return 16;
					}
					// LD A, (nn)
					case 3: {
						IRegister8 a = registerTableAF ? A_ALT : A;
						a.value(socket.readByte((int)nextShort()));
						return 13;
					}
					}
				} else {
					switch(p) {
					// LD (BC), A
					case 0: {
						socket.writeByte((int)rp[0].value(), (registerTableAF ? A_ALT : A).value());
						return 7;
					}
					// LD (DE), A
					case 1: {
						socket.writeByte((int)rp[1].value(), (registerTableAF ? A_ALT : A).value());
						return 7;
					}
					// LD (nn), HL
					case 2: {
						socket.writeShort((int)nextShort(), rp[2].value());
						return 16;
					}
					// LD (nn), A
					case 3: {
						socket.writeByte((int)nextShort(), (registerTableAF ? A_ALT : A).value());
						return 13;
					}
					}
				}
			// 16-bit INC/DEC
			case 3:
				// DEC rp[p]
				if (q) rp[p].postdec();
				// INC rp[p]
				else rp[p].postinc();
				return 6;
			// 8-bit INC
			case 4:
				flagsInc8(r[y].postinc());
				return y == 6 ? 11 : 4;
			// 8-bit DEC
			case 5:
				flagsDec8(r[y].postdec());
				return y == 6 ? 11 : 4;
			// 8-bit load immediate
			case 6:
				r[y].value(nextByte());
				return y == 6 ? 10 : 7;
			// Assorted operations on accumulator/flags
			case 7:
				IRegister8 f = (registerTableAF ? F_ALT : F);
				IRegister8 a = (registerTableAF ? A_ALT : A);
				switch(y) {
				// RLCA
				case 0:
				{
					// A
					byte v = a.value();
					// F
					byte ff = f.value();
					// Left shift value
					int r = v << 1;
					// Move bit 8 to bit 0
					v |= (v >> 8);
					// Reset N and H
					ff &= ~0x12;
					// Set C to bit 0
					ff = (byte) ((v & 1) | (ff & 0xFE));
					// Set the value
					a.value((byte)r);
					// Set flags
					f.value(ff);
				}
					break;
				// RRCA
				case 1:
				{
					// A
					byte v = a.value();
					// F
					byte ff = f.value();
					// Reset N and H
					ff &= ~0x12;
					// Set C to bit 0
					ff = (byte) ((v & 1) | (ff & 0xFE));
					// Right shift value
					int r = v >> 1;
					// Move bit 8 to bit 0
					v |= (v << 8);
					// Set the value
					a.value((byte)r);
					// Set flags
					f.value(ff);
				}
					break;
				// RLA
				case 2:
					break;
				// RRA
				case 3:
					break;
				// DAA
				case 4:
				{
					byte v = a.value();
					if ((v&0xF) > 9 || f.test(0x10)) v += 6;
					if ((v&0xF0) > 0x90 || f.test(1)) v += 0x60;
				}
				break;
				// CPL
				case 5:
				{
					a = (registerTableAF ? A_ALT : A);
					a.value((byte) ~a.value());
					f.or((byte)0x12);
				}
				break;
				// SCF
				case 6:
					f.or((byte) 1);
					f.and((byte)~0x12);
				// CCF
					break;
				case 7:
					f.or((byte) ((f.value() & 1) << 4));
					f.xor((byte) 1);
					f.reset(5);
				}
				return 4;
			}
		case 1:
			// HALT
			if (z==7&&y==6) {
				internalHalt = true;
				return 4;
			// LD r[y], r[z]
			} else {
				r[y].value(r[z].value());
				if (z==6||y==6) return 7;
			}
		// alu[y] r[z]
		case 2:
		case 3:
			switch(z) {
			// RET cc[y]
			case 0:
			{
				boolean ret = cc(y);
				PC.value = socket.readShort((int)SP.value);
				SP.value += 2;
				return ret ? 11 : 5;
			}
			case 1:
				if (q) {
					switch(p) {
					// RET
					case 0:
					// EXX
					case 1:
					// JP HL
					case 2:
					// LD SP, HL
					case 3:
					}
				// POP rp2[p]
				} else {
					
				}
			// JP cc[y], nn
			case 2:
			{
				
			}
			case 3:
				switch(y) {
				// JP nn
				case 0: {
					PC.value = nextShort();
					return 10;
				}
				// CB prefix
				case 1: return decodeCB(nextByte());
				// OUT (n), A
				case 2: {
					socket.output((int)nextByte(), r[7].value());
					return 11;
				}
				// IN A, (n)
				case 3: {
					r[7].value(socket.input((int)nextByte()));
					return 11;
				}
				// EX (SP), HL
				case 4: {
					short hl = rp[2].value();
					rp[2].value(socket.readShort((int)SP.value));
					socket.writeShort((int)SP.value, hl);
					return 19;
				}
				// EX DE, HL
				case 5: {
					short val = rp[2].value();
					rp[2].value(rp[3].value());
					rp[3].value(val);
					return 4;
				}
				// EI
				case 6: interruptsEnabled = false; return 4;
				// DI
				case 7: interruptsEnabled = true; return 4;
				}
			// CALL cc[y], nn
			case 4:
			{
				boolean call = cc(y);
				short addr = nextShort();
				if (call) {
					short ret = PC.value;
					ret += 3;
					pushShort(ret);
					PC.value = addr;
				}
				return call ? 17 : 10;
			}
			case 5:
				if (q) {
					switch(p) {
					// CALL nn
					case 0: {
						short ret = PC.value;
						ret += 3;
						pushShort(ret);
						PC.value = nextShort();
						return 17;
					}
					// DD prefix
					case 1: return decodeDD(nextByte());
					// ED prefix
					case 2: return decodeED(nextByte());
					// FD prefix
					case 3: return decodeFD(nextByte());
					}
				// PUSH rp2[p]
				} else {
					pushShort(rp2[p].value());
				}
			// alu[y] n
			case 6:
				return y == 6 ? 7 : 4;
			// RST y*8
			case 7:
				return 11;
			}
		}
		return -1;
	}
	
	private byte nextByte() {
		return socket.readByte((int)PC.preinc());
	}
	
	private short nextShort() {
		short v = socket.readShort((int)PC.value);
		PC.add((short)2);
		return v;
	}
	
	private void pushShort(short v) {
		socket.writeShort((int)SP.value, v);
		SP.value -= 2;
	}
	
	private short popShort() {
		return socket.readShort((int)(SP.value += 2));
	}
	
	private void alu(int y, byte val) {
		IRegister8 f = registerTableAF ? F_ALT : F;
		byte ff = f.value();
		switch(y) {
		case 0: r[7].value(flagsAdd8(r[7].value(), val)); break;
		case 1: r[7].value(flagsAddc8(r[7].value(), val)); break;
		case 2: 
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		}
	}

}
