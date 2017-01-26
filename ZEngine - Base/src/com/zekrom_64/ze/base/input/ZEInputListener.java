package com.zekrom_64.ze.base.input;

/** Implementation of an object that can listen to mouse and keyboard input.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEInputListener {

	/** Callback for when a key is pressed.
	 * 
	 * @param glfwkey The GLFW key code
	 * @param glfwaction The GLFW key action
	 * @param glfwmods The GLFW modifiers
	 */
	public void onKey(int glfwkey, int glfwaction, int glfwmods);
	
	/** Callback for when a character is entered.
	 * 
	 * @param character The character entered
	 * @param glfwmods The GLFW modifiers
	 */
	public void onChar(char character, int glfwmods);
	
	/** Callback for when a mouse is moved in the area.
	 * 
	 * @param xpos Mouse X position
	 * @param ypos Mouse Y position
	 * @param dx Change in X position
	 * @param dy Change in Y position
	 */
	public void onMouseMove(double xpos, double ypos, double dx, double dy);
	
	/** Callback for when a mouse button is pressed in the area.
	 * 
	 * @param xpos Cursor X position
	 * @param ypos Cursor Y position
	 * @param glfwbutton The GLFW mouse button
	 * @param glfwaction The GLFW button action
	 * @param glfwmods The GLFW modifiers
	 */
	public void onMouseButton(double xpos, double ypos, int glfwbutton, int glfwaction, int glfwmods);
	
	/** Callback for when the mouse enters or leaves the area.
	 * 
	 * @param entered If the mouse entered or left the window
	 */
	public void onMouseEnter(boolean entered);
	
	/** Callback for when a scroll wheel is moved.
	 * 
	 * @param dx Change in scroll wheel X
	 * @param dy Change in scroll wheel Y
	 */
	public void onMouseScroll(double dx, double dy);
	
}
