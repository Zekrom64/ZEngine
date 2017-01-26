package com.zekrom_64.ze.base.backend.render.shader;

public class ZEAttributeDescription {

	public final String name;
	public final ZEUniformType type;
	public final int size;
	
	public ZEAttributeDescription(String name, ZEUniformType type, int size) {
		this.name = name;
		this.type = type;
		this.size = size;
	}
	
	public ZEAttributeDescription(String name, ZEUniformType type) {
		this(name, type, 1);
	}
	
}
