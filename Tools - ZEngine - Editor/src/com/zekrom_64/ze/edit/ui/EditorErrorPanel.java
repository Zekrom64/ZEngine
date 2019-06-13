package com.zekrom_64.ze.edit.ui;

import java.awt.Color;
import java.awt.Component;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class EditorErrorPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public EditorErrorPanel(String message, Throwable exception) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblMessage = new JLabel(message);
		lblMessage.setAlignmentY(Component.TOP_ALIGNMENT);
		lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblMessage);
		
		if (exception != null) {
			JScrollPane scrollPaneTrace = new JScrollPane();
			add(scrollPaneTrace);
			
			JTextPane textPaneTrace = new JTextPane();
			textPaneTrace.setForeground(Color.RED);
			scrollPaneTrace.setViewportView(textPaneTrace);
			
			StringWriter sw = new StringWriter();
			exception.printStackTrace(new PrintWriter(sw));
			textPaneTrace.setText(sw.toString());
		}
	}

}
