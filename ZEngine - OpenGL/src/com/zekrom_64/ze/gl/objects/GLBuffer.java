package com.zekrom_64.ze.gl.objects;

import java.nio.ByteBuffer;

import org.bridj.Pointer;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.mem.ZEMapMode;
import com.zekrom_64.ze.gl.GLNativeContext;

/** OpenGL implementation of a {@link ZEBuffer}.
 * 
 * @author Zekrom_64
 *
 */
public class GLBuffer implements ZEBuffer {

	/** The OpenGL buffer object */
	public final int bufferObject;
	private ByteBuffer mappedBuffer;
	private Pointer<?> mappedPointer;
	private GLNativeContext context;
	
	public GLBuffer(int buf) {
		this.bufferObject = buf;
		context = GLNativeContext.getNativeContext();
	}
	
	@Override
	public ByteBuffer mapMemory(ZEMapMode mode) {
		if (mappedBuffer == null) ensureMapped(mode);
		return mappedBuffer;
	}

	@Override
	public Pointer<?> mapMemoryRaw(ZEMapMode mode) {
		if (mappedPointer == null) ensureMapped(mode);
		return mappedPointer;
	}
	
	@SuppressWarnings("deprecation")
	private void ensureMapped(ZEMapMode mode) {
		context.ensureBound();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferObject);
		int mapmode = 0;
		switch(mode) {
		case READ_ONLY: mapmode = GL15.GL_READ_ONLY; break;
		case WRITE_ONLY: mapmode = GL15.GL_WRITE_ONLY; break;
		case READ_WRITE: mapmode = GL15.GL_READ_WRITE;
		}
		mappedBuffer = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, mapmode);
		mappedPointer = Pointer.pointerToAddress(MemoryUtil.memAddress(mappedBuffer));
	}

	@Override
	public void unmapMemory() {
		if (mappedBuffer != null) {
			context.ensureBound();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferObject);
			GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
			mappedBuffer = null;
			mappedPointer = null;
		}
	}

	@Override
	public boolean isMapped() {
		return mappedBuffer != null;
	}

	@Override
	public boolean isHostAccessible() {
		return true; // OpenGL buffers are technically always host accessible
	}

	@Override
	public long size() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferObject);
		return GL15.glGetBufferParameteri(GL15.GL_ARRAY_BUFFER, GL15.GL_BUFFER_SIZE) | 0l;
	}

}
