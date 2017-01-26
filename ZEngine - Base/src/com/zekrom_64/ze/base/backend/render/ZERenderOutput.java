package com.zekrom_64.ze.base.backend.render;

public interface ZERenderOutput<B extends ZERenderBackend<?>> {

	public void doOutput(B backend);
	
	public String[] getRequiredFeatures();
	
}
