package com.zekrom_64.ze.gl;

import org.lwjgl.opengl.GL11;

import com.zekrom_64.ze.base.backend.render.ZERenderOutput;
import com.zekrom_64.ze.base.backend.render.obj.ZEFramebuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderFence;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderSemaphore;
import com.zekrom_64.ze.gl.objects.GLFramebuffer;
import com.zekrom_64.ze.gl.objects.GLRenderFence;
import com.zekrom_64.ze.gl.objects.GLRenderSemaphore;

public class GLRenderOutput implements ZERenderOutput<GLRenderBackend> {

	public final GLContext context;
	public final GLNativeContext nativeContext;
	
	public GLRenderOutput(GLContext context) {
		this.context = context;
		this.nativeContext = context.getNativeContext();
	}

	@Override
	public int preRender(ZERenderSemaphore signal, ZERenderFence fence) {
		((GLRenderSemaphore)signal).semaphore.release();
		((GLRenderFence)fence).signal();
		return 0;
	}

	@Override
	public void postRender(ZERenderSemaphore... wait) {
		for(ZERenderSemaphore sem : wait) ((GLRenderSemaphore)sem).semaphore.acquireUninterruptibly();
		nativeContext.bindExclusively();
		int err = GL11.glGetError();
		if (err != GL11.GL_NO_ERROR) throw new GLException("Caught OpenGL error before presentation: ", err);
		context.swapBuffers();
		nativeContext.unbindExclusively();
	}

	@Override
	public ZEFramebuffer[] getFramebuffers() {
		return new ZEFramebuffer[] { GLFramebuffer.DEFAULT };
	}
	
}
