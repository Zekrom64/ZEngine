package com.zekrom_64.ze.base.input;

/** Implementation of an object that can handle input about a window area.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEWindowListener {

	/** Callback for when files are dropped into the window area.
	 * 
	 * @param files Paths to dropped files
	 */
	public void onFileDrop(String[] files);
	
	/** Called when a window is focused or unfocused from view.
	 * 
	 * @param focused If the window is focused or not
	 */
	public void onFocus(boolean focused);
	
	/** Called when a window is iconified (minimized) or restored.
	 * 
	 * @param iconified If the window is iconified or not
	 */
	public void onIconify(boolean iconified);
	
	/** Called when a window area is moved.
	 * 
	 * @param xpos New x position
	 * @param ypos New y position
	 */
	public void onMove(int xpos, int ypos);
	
	/** Called when a window area is resized.
	 * 
	 * @param width New width
	 * @param height New height
	 */
	public void onResize(int width, int height);
	
}
