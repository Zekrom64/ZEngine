package com.zekrom_64.ze.edit.ui;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class EditorUI extends JFrame {

	private JPanel contentPane;
	public final JMenuBar menuBarTop = new JMenuBar();
	public final JMenuBar menuBarBottom = new JMenuBar();
	public final JMenu mnFile = new JMenu("FIle");
	public final JMenu mnEdit = new JMenu("Edit");
	public final JMenu mnWindow = new JMenu("Window");
	public final JMenu mnHelp = new JMenu("Help");
	public final JDesktopPane desktopPane = new JDesktopPane();

	/**
	 * Create the frame.
	 */
	public EditorUI() {
		setTitle("ZEngine - Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		setJMenuBar(menuBarTop);
		
		menuBarTop.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmOpen);
		
		mnFile.addSeparator();
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setEnabled(false);
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mntmSaveAs.setEnabled(false);
		mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mnFile.add(mntmSaveAs);
		
		mnFile.addSeparator();
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setEnabled(false);
		mnFile.add(mntmClose);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		menuBarTop.add(mnEdit);
		
		menuBarTop.add(mnWindow);
		
		menuBarTop.add(mnHelp);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, menuBarBottom, -5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, menuBarBottom, -5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, menuBarBottom, 5, SpringLayout.EAST, contentPane);
		contentPane.add(menuBarBottom);
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, desktopPane, 0, SpringLayout.SOUTH, menuBarBottom);
		sl_contentPane.putConstraint(SpringLayout.WEST, desktopPane, 0, SpringLayout.WEST, menuBarBottom);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, desktopPane, 0, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, desktopPane, 0, SpringLayout.EAST, menuBarBottom);
		contentPane.add(desktopPane);
	}
}
