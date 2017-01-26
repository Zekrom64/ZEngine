package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVLockOp implements IntValuedEnum<AVLockOp> {
	AV_LOCK_CREATE,
	AV_LOCK_OBTAIN,
	AV_LOCK_RELEASE,
	AV_LOCK_DESTROY;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVLockOp> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
