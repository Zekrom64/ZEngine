package com.zekrom_64.ze.edit.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.zekrom_64.ze.edit.Editor;
import com.zekrom_64.ze.edit.ui.util.EditorLookAndFeel;

@SuppressWarnings("serial")
public class EditorOptions extends JDialog {

	private LookAndFeel currentLAF;
	
	private final JPanel contentPanel = new JPanel();
	
	private JComboBox<LookAndFeel> cbxLAF;

	@SuppressWarnings({ "rawtypes", "unchecked" }) // Fuck you WindowBuilder for not supporting generics after 4 years
	public EditorOptions() {
		setTitle("Options");
		setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				cancelOptions();
			}
			
		});
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, tabbedPane, 0, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, tabbedPane, 0, SpringLayout.SOUTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, tabbedPane, 0, SpringLayout.EAST, contentPanel);
		contentPanel.add(tabbedPane);
		
		JPanel panelEditor = new JPanel();
		tabbedPane.addTab("Editor", null, panelEditor, null);
		SpringLayout sl_panelEditor = new SpringLayout();
		panelEditor.setLayout(sl_panelEditor);
		
		JScrollPane scrollPane = new JScrollPane();
		sl_panelEditor.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, panelEditor);
		sl_panelEditor.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, panelEditor);
		sl_panelEditor.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, panelEditor);
		sl_panelEditor.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, panelEditor);
		panelEditor.add(scrollPane);
		
		JPanel panelEditorBody = new JPanel();
		scrollPane.setViewportView(panelEditorBody);
		SpringLayout sl_panelEditorBody = new SpringLayout();
		panelEditorBody.setLayout(sl_panelEditorBody);
		
		JLabel lblLookFeel = new JLabel("Look & Feel:");
		sl_panelEditorBody.putConstraint(SpringLayout.NORTH, lblLookFeel, 10, SpringLayout.NORTH, panelEditorBody);
		sl_panelEditorBody.putConstraint(SpringLayout.WEST, lblLookFeel, 10, SpringLayout.WEST, panelEditorBody);
		panelEditorBody.add(lblLookFeel);
		
		List<LookAndFeel> lafs = EditorLookAndFeel.getLookAndFeels();
		LookAndFeel[] lafa = lafs.toArray(new LookAndFeel[0]);
		cbxLAF = new JComboBox(lafa);
		{
			int lafIndex = -1;
			for(int i = 0; i < lafs.size(); i++) {
				LookAndFeel laf = lafs.get(i);
				if (UIManager.getLookAndFeel().getName().equals(laf.getName())) lafIndex = i;
			}
			cbxLAF.setSelectedIndex(lafIndex);
		}
		cbxLAF.setRenderer(new DefaultListCellRenderer() {

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				LookAndFeel laf = (LookAndFeel)value;
				setText(laf.getName());
				setToolTipText(laf.getDescription());
				return c;
			}
			
		});
		cbxLAF.addItemListener((ItemEvent e) -> {
			try {
				UIManager.setLookAndFeel((LookAndFeel)cbxLAF.getSelectedItem());
				for(Component c : Editor.uiWindows) SwingUtilities.updateComponentTreeUI(c);
			} catch (UnsupportedLookAndFeelException ex) {
				Editor.exceptionWarn("Failed to set look and feel", ex);
			}
		});
		sl_panelEditorBody.putConstraint(SpringLayout.NORTH, cbxLAF, -3, SpringLayout.NORTH, lblLookFeel);
		sl_panelEditorBody.putConstraint(SpringLayout.WEST, cbxLAF, 6, SpringLayout.EAST, lblLookFeel);
		sl_panelEditorBody.putConstraint(SpringLayout.EAST, cbxLAF, 136, SpringLayout.EAST, lblLookFeel);
		panelEditorBody.add(cbxLAF);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener((ActionEvent e) -> saveOptions());
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener((ActionEvent e) -> cancelOptions());
				buttonPane.add(cancelButton);
			}
		}
		
		loadOptions();
		Editor.uiWindows.add(this);
	}
	
	private void loadOptions() {
		currentLAF = UIManager.getLookAndFeel();
	}
	
	private void saveOptions() {
		Editor.uiWindows.remove(this);
		dispose();
	}
	
	private void cancelOptions() {
		try {
			UIManager.setLookAndFeel(currentLAF);
			for(Component c : Editor.uiWindows) SwingUtilities.updateComponentTreeUI(c);
		} catch (UnsupportedLookAndFeelException e) {
			Editor.exceptionWarn("Failed to set look and feel", e);
		}
		Editor.uiWindows.remove(this);
		dispose();
	}
}
