package com.zekrom_64.ze.gl;

import java.util.WeakHashMap;
import java.util.concurrent.Semaphore;

import org.lwjgl.opengl.CGL;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLX;
import org.lwjgl.opengl.WGL;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
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
	
	private Semaphore bindSemaphore = new Semaphore(1);
	
	/** Gets the native OpenGL context for the current thread.
	 * 
	 * @return Current context
	 */
	public static GLNativeContext getNativeContext() {
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
				nctx.auxiliary0 = glXGetCurrentDisplay();
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
	
	private static long func_glXGetCurrentDisplay;
	
	/** Implementation of glXGetCurrentDisplay(), as LWJGL does not provide it by default while it
	 * is necessary for GLX context handling.
	 * 
	 * @return The current GLX Display
	 */
	public static long glXGetCurrentDisplay() {
		if (func_glXGetCurrentDisplay == 0) func_glXGetCurrentDisplay =
				APIUtil.apiGetFunctionAddress(GL.getFunctionProvider(), "glXGetCurrentDisplay");
		if (Checks.DEBUG) Checks.checkFunctions(func_glXGetCurrentDisplay);
		return JNI.callP(func_glXGetCurrentDisplay);
	}

	@Override
	public void swapBuffers() {
		switch(Platform.get()) {
		case WINDOWS: GDI32.SwapBuffers(auxiliary0);
		case LINUX: GLX.glXSwapBuffers(auxiliary0, auxiliary1);
		case MACOSX: CGL.CGLUpdateContext(context);
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
	public void bind() {
		switch(Platform.get()) {
		case WINDOWS: WGL.wglMakeCurrent(auxiliary0, context); break;
		case LINUX: GLX.glXMakeCurrent(auxiliary0, auxiliary1, context); break;
		case MACOSX: CGL.CGLSetCurrentContext(context);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof GLNativeContext) return equals((GLNativeContext)o);
		return false;
	}
	
	public boolean equals(GLNativeContext c) {
		if (c == null) return false;
		return c.context == context && c.auxiliary0 == auxiliary0 && c.auxiliary1 == auxiliary1;
	}
	
	/** Temporarily acquires the context for exclusive use by a task.
	 * 
	 * @param r Task to run while bound
	 */
	public void executeExclusivly(Runnable r) {
		bindSemaphore.acquireUninterruptibly();
		if (!isBound()) bind();
		r.run();
		bindSemaphore.release();
	}
	
}
