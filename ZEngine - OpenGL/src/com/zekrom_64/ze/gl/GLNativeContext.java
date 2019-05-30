package com.zekrom_64.ze.gl;

import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.lwjgl.opengl.CGL;
import org.lwjgl.opengl.GLX;
import org.lwjgl.opengl.GLX12;
import org.lwjgl.opengl.WGL;
import org.lwjgl.system.Platform;
import org.lwjgl.system.windows.GDI32;

/** A <code>GLNativeContext</code> wraps a raw native OpenGL context. This is used internally as a lightweight reference
 * to an OpenGL context for resources that must be accessed by other threads. It is worth noting that the native context
 * is itself an instance of a {@link GLContext} and can be used as a render target for an OpenGL backend. This allows externally
 * created OpenGL render targets to be used with normal render backends.
 * 
 * @author Zekrom_64
 *
 */
public class GLNativeContext implements GLContext {

	// Weak map containing all known native contexts
	private static WeakHashMap<Long,GLNativeContext> nativeContexts = new WeakHashMap<>();
	
	// The native OpenGL context handle
	private long context;
	// Auxiliary handles for the context
	private long auxiliary0;
	private long auxiliary1;
	
	private Lock bindLock = new ReentrantLock();
	
	private GLNativeContext() { }
	
	private GLNativeContext(long context, long aux0, long aux1) {
		this.context = context;
		this.auxiliary0 = aux0;
		this.auxiliary1 = aux1;
	}
	
	/** Gets a native context with the given handles. The values of these
	 * handles are platform dependent:
	 * <ul>
	 * <li><b>Windows:</b></br>
	 * <i>context</i> - <b>HGLRC</b> of current context</br>
	 * <i>aux0</i> - <b>HDC</b> of current render target</br>
	 * </li>
	 * <li><b>Linux:</b></br>
	 * <i>context</i> - <b>GLXContext</b> of current context</br>
	 * <i>aux0</i> - <b>Display*</b> of current render target</br>
	 * <i>aux1</i> - <b>GLXDrawable</b> of current render target</br>
	 * </li>
	 * <li><b>MacOS X</b></br>
	 * <i>context</i> - <b>CGLContextObj</b> of current context</br>
	 * </li>
	 * </ul>
	 * Only the <i>context</i> parameter is used to lookup the context, but because
	 * this may need to create a new context object, the auxiliary handles required
	 * for constructing a native context are also necessary.
	 * 
	 * @param context The OpenGL context object
	 * @param aux0 First auxiliary handle
	 * @param aux1 Second auxiliary handle
	 */
	public static GLNativeContext getContext(long context, long aux0, long aux1) {
		GLNativeContext ctx = nativeContexts.get(Long.valueOf(context));
		if (ctx == null) {
			ctx = new GLNativeContext(context, aux0, aux1);
			nativeContexts.put(Long.valueOf(context), ctx);
		}
		return ctx;
	}
	
	/** Gets an existing native context based on its native context handle.
	 * 
	 * @param context Native context handle
	 * @return Native context bound to handle, or <b>null</b>
	 */
	public static GLNativeContext getExistingContext(long context) {
		return nativeContexts.get(Long.valueOf(context));
	}
	
	/** Gets the native OpenGL context for the current thread.
	 * 
	 * @return Current context
	 */
	public static GLNativeContext getCurrentContext() {
		long ctx;
		GLNativeContext nctx = null;
		switch(Platform.get()) {
		case WINDOWS:
			ctx = WGL.wglGetCurrentContext();
			if (ctx == 0) return null;
			nctx = nativeContexts.get(Long.valueOf(ctx));
			if (nctx == null) {
				nctx = new GLNativeContext();
				nctx.context = ctx;
				nctx.auxiliary0 = WGL.wglGetCurrentDC();
				nativeContexts.put(Long.valueOf(ctx), nctx);
			}
			break;
		case LINUX:
			ctx = GLX.glXGetCurrentContext();
			if (ctx == 0) return null;
			nctx = nativeContexts.get(Long.valueOf(ctx));
			if (nctx == null) {
				nctx = new GLNativeContext();
				nctx.context = ctx;
				nctx.auxiliary0 = GLX12.glXGetCurrentDisplay();
				nctx.auxiliary1 = GLX.glXGetCurrentDrawable();
				nativeContexts.put(Long.valueOf(ctx), nctx);
			}
			break;
		case MACOSX:
			ctx = CGL.CGLGetCurrentContext();
			if (ctx == 0) return null;
			nctx = nativeContexts.get(Long.valueOf(ctx));
			if (nctx == null) {
				nctx = new GLNativeContext();
				nctx.context = ctx;
				nativeContexts.put(Long.valueOf(ctx), nctx);
			}
			break;
		}
		return nctx;
	}

	@Override
	public void swapBuffers() {
		switch(Platform.get()) {
		case WINDOWS: GDI32.SwapBuffers(auxiliary0); break;
		case LINUX: GLX.glXSwapBuffers(auxiliary0, auxiliary1); break;
		case MACOSX: CGL.CGLUpdateContext(context); break;
		}
	}

	@Override
	public boolean isBound() {
		switch(Platform.get()) {
		case WINDOWS: return context == WGL.wglGetCurrentContext();
		case LINUX: return context == GLX.glXGetCurrentContext();
		case MACOSX: return context == CGL.CGLGetCurrentContext();
		}
		return false;
	}
	
	@Override
	public synchronized void bind() {
		switch(Platform.get()) {
		case WINDOWS: WGL.wglMakeCurrent(auxiliary0, context); break;
		case LINUX: GLX.glXMakeCurrent(auxiliary0, auxiliary1, context); break;
		case MACOSX: CGL.CGLSetCurrentContext(context); break;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof GLNativeContext) return equals((GLNativeContext)o);
		return false;
	}
	
	/** Tests if two native contexts are the same.
	 * 
	 * @param c Other context
	 * @return If the contexts are the same
	 */
	public boolean equals(GLNativeContext c) {
		if (c == this) return true;
		if (c == null) return false;
		return c.context == context && c.auxiliary0 == auxiliary0 && c.auxiliary1 == auxiliary1;
	}
	
	/** Temporarily acquires the context for exclusive use by a task.
	 * 
	 * @param r Task to run while bound
	 */
	public void executeExclusivly(Runnable r) {
		bindLock.lock();
		if (!isBound()) bind();
		try {
			r.run();
		} finally {
			bindLock.unlock();
		}
	}
	
	/** Acquires a lock on the context and binds it to the current thread.
	 * 
	 */
	public void bindExclusively() {
		bindLock.lock();
		if (!isBound()) bind();
	}
	
	/** Releases the lock on the context gained by {@link #bindExclusively()}.
	 * If the thread calling this is not the one that called bindExclusively(), the
	 * function returns immediately.
	 */
	public void unbindExclusively() {
		bindLock.unlock();
	}

	@Override
	public GLNativeContext getNativeContext() {
		return this;
	}
	
}
