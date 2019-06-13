package com.zekrom_64.ze.edit.ui;

import java.util.Collection;
import java.util.Collections;

import javax.swing.JComponent;

public interface IEditorInternalWindow {

	public default Collection<JComponent> getContextMenuBarItems() {
		return Collections.emptyList();
	}
	
	
	
}
