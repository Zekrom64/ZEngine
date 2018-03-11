package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVSubtitleType;
import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVSubtitleRect extends ZEStruct<AVSubtitleRect> {
	
	static {
		BridJ.register();
	}
	
	public AVSubtitleRect() {}
	
	public AVSubtitleRect(Pointer<AVSubtitleRect> p) { super(p); }

	@Field(0)
	public int x() {
		return io.getIntField(this, 0);
	}
	
	@Field(0)
	public void x(int x) {
		io.setIntField(this, 0, x);
	}

	@Field(1)
	public int y() {
		return io.getIntField(this, 1);
	}
	
	@Field(1)
	public void y(int y) {
		io.setIntField(this, 1, y);
	}

	@Field(2)
	public int w() {
		return io.getIntField(this, 2);
	}
	
	@Field(2)
	public void w(int w) {
		io.setIntField(this, 2, w);
	}

	@Field(3)
	public int h() {
		return io.getIntField(this, 3);
	}
	
	@Field(3)
	public void h(int h) {
		io.setIntField(this, 3, h);
	}
	
	@Field(4)
	public int nb_colors() {
		return io.getIntField(this, 4);
	}
	
	@Field(4)
	public void nb_colors(int nb_colors) {
		io.setIntField(this, 4, nb_colors);
	}
	
	@Deprecated
	@Field(5)
	public AVPicture pict() {
		return io.getNativeObjectField(this, 5);
	}
	
	@Array(4)
	@Field(6)
	public Pointer<Pointer<Byte>> data() {
		return io.getPointerField(this, 6);
	}
	
	@Array(4)
	@Field(7)
	public Pointer<Integer> linesize() {
		return io.getPointerField(this, 7);
	}
	
	@Field(8)
	public AVSubtitleType type() {
		IntValuedEnum<AVSubtitleType> e = io.getEnumField(this, 8);
		return (AVSubtitleType) e;
	}
	
	@Field(9)
	public Pointer<Byte> text() {
		return io.getPointerField(this, 9);
	}
	
	@Field(9)
	public void text(Pointer<Byte> text) {
		io.setPointerField(this, 9, text);
	}
	
	@Field(10)
	public Pointer<Byte> ass() {
		return io.getPointerField(this, 10);
	}
	
	@Field(10)
	public void ass(Pointer<Byte> ass) {
		io.setPointerField(this, 10, ass);
	}
	
	@Field(11)
	public int flags() {
		return io.getIntField(this, 11);
	}
	
	@Field(11)
	public void flags(int flags) {
		io.setIntField(this, 11, flags);
	}
	
	
	
}
