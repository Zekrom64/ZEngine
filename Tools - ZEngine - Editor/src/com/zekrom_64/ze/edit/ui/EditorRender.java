package com.zekrom_64.ze.edit.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;

import com.zekrom_64.ze.edit.Editor;
import com.zekrom_64.ze.edit.io.IOImages;
import com.zekrom_64.ze.edit.io.IOImages.Image;
import com.zekrom_64.ze.edit.render.ModelRender;
import com.zekrom_64.ze.edit.ui.util.EditorAsyncTasks;
import com.zekrom_64.ze.edit.ui.util.EditorPersistentData;

@SuppressWarnings("serial")
public class EditorRender extends JInternalFrame implements ActionListener, IEditorInternalWindow {

	public static final List<EditorRender> renders = new ArrayList<>();
	
	private BufferedImage outputImage = new BufferedImage(ModelRender.getWidth(), ModelRender.getHeight(), BufferedImage.TYPE_INT_ARGB);
	
	private List<JComponent> contextItems = new ArrayList<>();
	
	@Override
	public Collection<JComponent> getContextMenuBarItems() {
		return contextItems;
	}

	public EditorRender() {
		super("Render", true, true, true, true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setBounds(100, 100, ModelRender.getWidth(), ModelRender.getHeight());
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		getContentPane().setBackground(Color.BLACK);
		
		JPanel panel = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ModelRender.getOutput(outputImage);
				g.drawImage(outputImage, 0, 0, null);
			}
			
		};
		panel.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				int width = panel.getWidth(), height = panel.getHeight();
				if (width < 1) width = 1;
				if (height < 1) height = 1;
				outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				ModelRender.setSize(width, height);
			}
			
		});
		panel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
		});
		panel.setBackground(Color.BLACK);
		springLayout.putConstraint(SpringLayout.NORTH, panel, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 0, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, getContentPane());
		getContentPane().add(panel);
		
		setupContextItems();
		
		synchronized(renders) {
			renders.add(this);
		}
	}
	
	private void setupContextItems() {
		JMenu mnCapture = new JMenu("Capture");
		
		JMenuItem mntmScreenshot = new JMenuItem("Screenshot");
		mntmScreenshot.setActionCommand("screenshot");
		mntmScreenshot.addActionListener(this);
		mntmScreenshot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
		mnCapture.add(mntmScreenshot);
		
		contextItems.add(mnCapture);
		
		JMenu mnCamera = new JMenu("Camera");
		
		JMenuItem mntmFront = new JMenuItem("To Front");
		mntmFront.setActionCommand("camera_snap_front");
		mntmFront.addActionListener(this);
		mnCamera.add(mntmFront);
		
		contextItems.add(mnCamera);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setTitle("Render: " + ModelRender.getFramerate() + " FPS");
	}

	@Override
	protected void finalize() {
		synchronized(renders) {
			renders.remove(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "screenshot": {
			BufferedImage img = new BufferedImage(outputImage.getWidth(), outputImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
			outputImage.copyData(img.getRaster());
			EditorAsyncTasks.saveFile("Save Screenshot", IOImages.getWriteFileFilters(), EditorPersistentData.lastScreenshotPath, 
					(File f) -> {
						EditorPersistentData.lastScreenshotPath = f;
						try {
							IOImages.write(new Image(outputImage), f);
						} catch (IOException e1) {
							Editor.exceptionWarn("Failed to save screenshot", e1);
						}
					});
		} break;
		}
	}
}
