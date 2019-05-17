package com.zekrom_64.ze.gl.objects;

import com.zekrom_64.ze.base.backend.render.obj.ZEVertexBuffer;

public class GLVertexBuffer extends GLBuffer implements ZEVertexBuffer {

	public GLVertexBuffer(GLBuffer buf) {
		this(buf.bufferObject);
	}
	
	public GLVertexBuffer(int buf) {
		super(buf);
	}

}
