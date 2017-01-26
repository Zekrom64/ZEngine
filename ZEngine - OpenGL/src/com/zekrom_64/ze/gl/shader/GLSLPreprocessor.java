package com.zekrom_64.ze.gl.shader;

import java.util.ArrayList;

@Deprecated
public class GLSLPreprocessor {

	public static interface IGLShaderPreprocessorHook {
		
		public String parseDirective(String source, int start);
		
	}
	
	public final ArrayList<IGLShaderPreprocessorHook> hooks = new ArrayList<>();
	
	public String parse(String source) {
		return source;
	}
	
}
