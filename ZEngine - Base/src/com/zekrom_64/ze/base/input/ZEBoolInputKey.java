package com.zekrom_64.ze.base.input;

public class ZEBoolInputKey implements ZEBoolInput, ZEInputListener {
	
	public boolean isInverted;
	public int modifierMask;
	public int inputKey;
	private boolean state;
	
	@Override
	public void onKey(int glfwkey, int glfwaction, int glfwmods) {
		boolean instate = glfwaction != 0;
		instate &= ((~modifierMask | glfwmods) & 0xF) == 0xF;
		if (isInverted) instate = !instate;
		state = instate;
	}

	@Override
	public void onChar(char character, int glfwmods) { }

	@Override
	public void onMouseMove(double xpos, double ypos, double dx, double dy) { }

	@Override
	public void onMouseButton(double xpos, double ypos, int glfwbutton, int glfwaction, int glfwmods) { }

	@Override
	public void onMouseEnter(boolean entered) { }

	@Override
	public void onMouseScroll(double dx, double dy) { }

	@Override
	public boolean getBoolInputState() {
		return state;
	}

	@Override
	public void setBoolInputState(boolean state) {
		this.state = state;
	}

}
