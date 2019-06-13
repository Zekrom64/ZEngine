package com.zekrom_64.ze.edit.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import com.zekrom_64.ze.edit.Editor;

@SuppressWarnings("serial")
public class EditorUI extends JFrame implements InternalFrameListener, ActionListener {

	public static final JMenu EMPTY_JMENU = new JMenu(" ");
	static {
		EMPTY_JMENU.setEnabled(false);
	}
	
	public final EditorRender render = new EditorRender();
	public final EditorErrorLog errorLog = new EditorErrorLog();
	
	private JPanel contentPane;
	public final JMenuBar menuBarTop = new JMenuBar();
	public final JMenuBar menuBarBottom = new JMenuBar();
	public final JMenu mnFile = new JMenu("File");
	public final JMenu mnEdit = new JMenu("Edit");
	public final JMenu mnWindow = new JMenu("Window");
	public final JMenu mnHelp = new JMenu("Help");
	public final JDesktopPane desktopPane = new JDesktopPane();

	/**
	 * Create the frame.
	 */
	public EditorUI() {
		setTitle("ZEngine - Editor");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				Editor.signalShutdown();
			}
			
		});
		setBounds(100, 100, 800, 600);
		
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
		
		JMenuItem mntmOptions = new JMenuItem("Options");
		mntmOptions.setActionCommand("show_options");
		mntmOptions.addActionListener(this);
		mnFile.add(mntmOptions);
		
		mnFile.addSeparator();
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setEnabled(false);
		mnFile.add(mntmClose);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setActionCommand("exit");
		mntmExit.addActionListener(this);
		mnFile.add(mntmExit);
		
		menuBarTop.add(mnEdit);
		
		menuBarTop.add(mnWindow);
		
		JMenu mnShow = new JMenu("Show");
		mnWindow.add(mnShow);
		
		JMenuItem mntmRender = new JMenuItem("Render");
		mntmRender.setActionCommand("show_render");
		mntmRender.addActionListener(this);
		mnShow.add(mntmRender);
		
		JMenuItem mntmErrorLog = new JMenuItem("Error Log");
		mntmErrorLog.setActionCommand("show_error_log");
		mntmErrorLog.addActionListener(this);
		mnShow.add(mntmErrorLog);
		
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
		menuBarBottom.add(EMPTY_JMENU);
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, desktopPane, 0, SpringLayout.SOUTH, menuBarBottom);
		sl_contentPane.putConstraint(SpringLayout.WEST, desktopPane, 0, SpringLayout.WEST, menuBarBottom);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, desktopPane, 0, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, desktopPane, 0, SpringLayout.EAST, menuBarBottom);
		desktopPane.setBackground(Color.WHITE);
		contentPane.add(desktopPane);
		
		setupDesktop();
	}
	
	private void addWindow(JInternalFrame iframe) {
		desktopPane.add(iframe);
		iframe.addInternalFrameListener(this);
	}
	
	private void setupDesktop() {
		addWindow(render);
		render.setVisible(true);
		addWindow(errorLog);
		errorLog.setVisible(false);
		
		render.toFront();
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) { }

	@Override
	public void internalFrameClosing(InternalFrameEvent e) { }

	@Override
	public void internalFrameClosed(InternalFrameEvent e) { }

	@Override
	public void internalFrameIconified(InternalFrameEvent e) { }

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) { }

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		menuBarBottom.removeAll();
		menuBarBottom.add(EMPTY_JMENU);
		JInternalFrame iframe = e.getInternalFrame();
		if (iframe instanceof IEditorInternalWindow) {
			menuBarBottom.removeAll();
			Collection<JComponent> menuItems = ((IEditorInternalWindow)iframe).getContextMenuBarItems();
			for(JComponent c : menuItems) menuBarBottom.add(c);
		}
		menuBarBottom.repaint();
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		menuBarBottom.removeAll();
		menuBarBottom.add(EMPTY_JMENU);
		menuBarBottom.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {	
		case "show_options":
			EditorOptions opts = new EditorOptions();
			opts.setVisible(true);
			opts.toFront();
			break;
		case "show_render":
			render.setVisible(true);
			render.toFront();
			break;
		case "show_error_log":
			errorLog.setVisible(true);
			errorLog.toFront();
			break;
		case "exit":
			Editor.signalShutdown();
			break;
		}
	}
}
