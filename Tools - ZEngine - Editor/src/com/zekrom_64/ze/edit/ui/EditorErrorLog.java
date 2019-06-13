package com.zekrom_64.ze.edit.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileFilter;

import com.zekrom_64.ze.edit.Editor;
import com.zekrom_64.ze.edit.ui.util.EditorAsyncTasks;
import com.zekrom_64.ze.edit.ui.util.EditorPersistentData;
import com.zekrom_64.ze.edit.ui.util.FileFilterExtension;

@SuppressWarnings("serial")
public class EditorErrorLog extends JInternalFrame implements ActionListener, IEditorInternalWindow {

	public static final List<EditorErrorLog> logs = new ArrayList<>();
	
	private List<JComponent> contextItems = new ArrayList<>();

	private JTextPane textPane;
	
	/**
	 * Create the frame.
	 */
	public EditorErrorLog() {
		super("Error Log", true, true, true);
		setBounds(100, 100, 600, 600);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addInternalFrameListener(new InternalFrameAdapter() {

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				contextItems.remove(EditorErrorLog.this);
			}
			
		});
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, getContentPane());
		getContentPane().add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setForeground(Color.RED);
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		logs.add(this);
		setupContextItems();
	}
	
	private void setupContextItems() {
		JMenu mnActions = new JMenu("Actions");
		
		JMenuItem mntmClear = new JMenuItem("Clear");
		mntmClear.setActionCommand("clear");
		mntmClear.addActionListener(this);
		mnActions.add(mntmClear);
		
		JMenuItem mntmSave = new JMenuItem("Save Log");
		mntmSave.setActionCommand("save");
		mntmSave.addActionListener(this);
		mnActions.add(mntmSave);
		
		contextItems.add(mnActions);
	}
	
	public void append(String text) {
		textPane.setText(textPane.getText() + text);
	}

	@Override
	public Collection<JComponent> getContextMenuBarItems() {
		return contextItems;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "clear":
			textPane.setText("");
			break;
		case "save": {
			String text = textPane.getText();
			EditorAsyncTasks.saveFile("Save Log", new FileFilter[] { FileFilterExtension.FILTER_TXT },
					EditorPersistentData.lastErrorLogPath, (File f) -> {
				EditorPersistentData.lastErrorLogPath = f;
				try (FileWriter fw = new FileWriter(f)){
					fw.write(text);
				} catch (IOException ex) {
					Editor.exceptionWarn("Failed to save error log", ex);
				}
			});
		} break;
		}
	}
}
