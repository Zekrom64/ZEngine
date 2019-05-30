package com.zekrom_64.ze.base.gui;

import com.zekrom_64.ze.base.backend.render.ZERenderBackend;

/** A UI system is a structured way of rendering user interface components such as windows, buttons, text
 * boxes, etc.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEUISystem {
	
	/** Initializes the UI system using the given render backend. This will optimize the output from
	 * the UI system to the specific backend (ex. using vector font output instead of rasterization)
	 * as well as setting default values for the UI system.
	 */
	public void initialize(ZERenderBackend<?> backend);
	
	/** Returns if the UI system is accelerated for the backend it was initialized with. An accelerated 
	 * UI system is optimized for the render backend and will output the UI render work in the most
	 * efficient format.
	 * 
	 * @return If the UI system is accelerated
	 */
	public boolean isAccelerated();
	
	/** Deinitializes the UI system and releases all resources used by this system.
	 * 
	 */
	public void deinitialize();
	
}
