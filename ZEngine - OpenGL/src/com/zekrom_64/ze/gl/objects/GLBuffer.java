package com.zekrom_64.ze.gl.objects;

import java.nio.ByteBuffer;

import org.bridj.Pointer;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEGraphicsMemory;
import com.zekrom_64.ze.base.mem.ZEMapMode;
import com.zekrom_64.ze.gl.GLNativeContext;
import com.zekrom_64.ze.gl.GLRenderBackend;

/** OpenGL implementation of a {@link ZEGraphicsMemory}.
 * 
 * @author Zekrom_64
 *
 */
public class GLBuffer implements ZEBuffer {

	/** The OpenGL buffer object */
	public final int bufferObject;
	private ByteBuffer mappedBuffer;
	private Pointer<?> mappedPointer;
	private long size = 0;
	
	
	private final GLNativeContext context;
	private final GLRenderBackend backend;
	
	public GLBuffer(GLRenderBackend backend, int buf, long size) {
		this.bufferObject = buf;
		this.backend = backend;
		this.context = backend.getNativeContext();
		this.size = size;
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
		context.executeExclusivly(() -> {
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferObject);
			backend.checkErrorFine();
			int mapmode = 0;
			switch(mode) {
			case READ_ONLY: mapmode = GL15.GL_READ_ONLY; break;
			case WRITE_ONLY: mapmode = GL15.GL_WRITE_ONLY; break;
			case READ_WRITE: mapmode = GL15.GL_READ_WRITE;
			}
			mappedBuffer = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, mapmode);
			backend.checkErrorFine();
			backend.checkErrorCoarse("Failed to map buffer memory");
		});
		mappedPointer = Pointer.pointerToAddress(MemoryUtil.memAddress(mappedBuffer));
	}

	@Override
	public void unmapMemory() {
		if (mappedBuffer != null) {
			context.executeExclusivly(() -> {
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferObject);
				backend.checkErrorFine();
				GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
				backend.checkErrorFine();
				backend.checkErrorCoarse("Failed to unmap buffer memory");
			});
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
		return size;
	}

	@Override
	public ZEBufferUsage[] getValidUsages() {
		return new ZEBufferUsage[] {
			ZEBufferUsage.TRANSFER_SRC,
			ZEBufferUsage.TRANSFER_DST,
			ZEBufferUsage.STORAGE_BUFFER,
			ZEBufferUsage.UNIFORM_BUFFER,
			ZEBufferUsage.STORAGE_TEXEL_BUFFER,
			ZEBufferUsage.UNIFORM_TEXEL_BUFFER,
			ZEBufferUsage.VERTEX_BUFFER,
			ZEBufferUsage.INDEX_BUFFER,
			ZEBufferUsage.INDIRECT_BUFFER
		};
	}

}
