package com.zekrom_64.ze.gl.vertex;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

@Deprecated
public abstract class GLVertexBuffer {

	public final int vbo = GL15.glGenBuffers();
	public final ByteBuffer buffer;
	public final GLVertexType type;
	private int nvertices = 0;
	private int vsize;
	
	protected GLVertexBuffer(int nvertices, GLVertexType type) {
		this.vsize = type.size();
		buffer = BufferUtils.createByteBuffer(vsize * nvertices);
		this.type = type;
		this.nvertices = nvertices;
	}
	
	public void bindPointers() {
		GL11.glVertexPointer(type.vertexCount, type.vertexType.glenum, vsize, 0);
		int offset = type.vertexCount * type.vertexType.size;
		if (type.colorCount>0) {
			GL11.glColorPointer(type.colorCount, type.colorType.glenum, vsize, offset);
			offset += type.colorCount * type.colorType.size;
		}
		if (type.texcoordCount>0) {
			GL11.glTexCoordPointer(type.texcoordCount, type.texcoordType.glenum, vsize, offset);
			offset += type.texcoordCount * type.texcoordType.size;
		}
		if (type.normalType!=null) {
			GL11.glNormalPointer(type.normalType.glenum, vsize, offset);
		}
	}
	
	public void bindBuffer() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
	}
	
	public void bind() {
		bindBuffer();
		bindPointers();
	}
	
	public void rebuffer() {
		rebuffer(GL15.GL_DYNAMIC_DRAW);
	}
	
	public void rebuffer(int usage) {
		int pos = buffer.position();
		buffer.rewind();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, usage);
		buffer.position(pos);
	}
	
	public int position() {
		return buffer.position() / vsize;
	}
	
	public void position(int pos) {
		buffer.position(vsize * pos);
	}
	
	public int size() {
		return nvertices;
	}
	
}
