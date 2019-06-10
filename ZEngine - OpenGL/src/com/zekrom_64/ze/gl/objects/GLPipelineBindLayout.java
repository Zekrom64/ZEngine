package com.zekrom_64.ze.gl.objects;

import java.util.HashMap;
import java.util.Map;

import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindLayout;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindType;

public class GLPipelineBindLayout implements ZEPipelineBindLayout {

	public static class GLPipelineBinding {
		
		public ZEPipelineBindType bindType;
		public int arrayCount;
		
	}

	public final Map<Integer,GLPipelineBindLayout.GLPipelineBinding> pipelineBindings = new HashMap<>();
	
}
