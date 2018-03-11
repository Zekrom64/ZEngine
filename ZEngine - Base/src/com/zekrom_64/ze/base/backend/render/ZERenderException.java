package com.zekrom_64.ze.base.backend.render;

/** A render exception is a generic runtime exception thrown by render backends
 * in the event an error occurs.
 * 
 * @author Zekrom_64
 *
 */
public abstract class ZERenderException extends RuntimeException {
	
	public ZERenderException(String msg) {
		super(msg);
	}
	
	public ZERenderException(String msg, Throwable t) {
		super(msg, t);
	}

	private static final long serialVersionUID = -5335712489585211485L;

}
