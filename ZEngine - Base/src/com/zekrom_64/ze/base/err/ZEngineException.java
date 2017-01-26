package com.zekrom_64.ze.base.err;

/** The ZEngineException class is a special class used for exceptions that are normally handled internally by
 * the engine. It can still be thrown as a normal java exception.
 * 
 * @author Zekrom_64
 *
 */
public class ZEngineException extends Exception {

	private static final long serialVersionUID = -5480815079698622084L;

	public ZEngineException(String msg) {
		super(msg);
	}
	
	public ZEngineException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public ZEngineException(Throwable cause) {
		super(cause);
	}
	
	public ZEngineException() {}
	
}
