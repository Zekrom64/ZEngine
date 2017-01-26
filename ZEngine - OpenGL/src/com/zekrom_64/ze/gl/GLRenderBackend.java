package com.zekrom_64.ze.gl;

import com.zekrom_64.ze.base.backend.render.ZERenderBackend;

public interface GLRenderBackend extends ZERenderBackend<GLRenderBackend> {

	public static interface GLRenderWorkFactory extends ZERenderWorkFactory {
		
		public ZERenderWork glDraw(int nVertices, int start, int glmode);
		
		public ZERenderWork glDrawIndexed(int nIndices, int startIndex, int startVertex, int glmode);
		
	}
	
}
