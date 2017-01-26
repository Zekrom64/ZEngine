package com.zekrom_64.ze.base.err;

import com.zekrom_64.ze.base.ZEApplication;

/** The ZEngineInternalException class is a type of exception used exclusively for handling internal errors
 * in ZEngine. It can only be thrown using static methods, and will always be passed through the main application
 * if one is running. This allows the main application to decide how the exception is handled.
 * 
 * @author Zekrom_64
 *
 */
public final class ZEngineInternalException extends ZEngineException {

	private static final long serialVersionUID = -2760310014018057603L;

	private ZEngineInternalException(String msg, Throwable cause) {
		super(msg, cause);
	}

	private ZEngineInternalException(String msg) {
		super(msg);
	}

	private ZEngineInternalException(Throwable cause) {
		super(cause);
	}
	
	private ZEngineInternalException() {}
	
	private static void throwInternally(ZEngineInternalException e) throws ZEngineInternalException {
		ZEApplication app = ZEApplication.getMainApplication();
		if (app!=null) app.onInternalException(e);
		throw e;
	}
	
	public static void throwInternally() throws ZEngineInternalException {
		throwInternally(new ZEngineInternalException());
	}
	
	public static void throwInternally(String msg, Throwable cause) throws ZEngineInternalException {
		throwInternally(new ZEngineInternalException(msg, cause));
	}
	
	public static void throwInternally(String msg) throws ZEngineInternalException {
		throwInternally(new ZEngineInternalException(msg));
	}
	
	public static void throwInternally(Throwable cause) throws ZEngineInternalException {
		throwInternally(new ZEngineInternalException(cause));
	}
	
	private static void throwInternallyNoExcept(ZEngineInternalException e) {
		ZEApplication app = ZEApplication.getMainApplication();
		if (app!=null) app.onInternalException(e);
	}
	
	public static void throwInternallyNoExcept() {
		throwInternallyNoExcept(new ZEngineInternalException());
	}
	
	public static void throwInternallyNoExcept(String msg, Throwable cause) {
		throwInternallyNoExcept(new ZEngineInternalException(msg, cause));
	}
	
	public static void throwInternallyNoExcept(String msg) {
		throwInternallyNoExcept(new ZEngineInternalException(msg));
	}
	
	public static void throwInternallyNoExcept(Throwable cause) {
		throwInternallyNoExcept(new ZEngineInternalException(cause));
	}

}
