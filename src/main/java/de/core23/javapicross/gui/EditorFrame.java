package de.core23.javapicross.gui;

import de.core23.javapicross.helper.LanguageManager;

import javax.swing.*;
import java.awt.*;


public class EditorFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel _jContentPane = null;

	private EditorPicrossPanel _Editor_picrossPanel = null;

	private EditorPicrossPanel _picrossPreviewPanel = null;

	private JMenuBar _JMenuBar = null;

	private JMenu _jMenuFile = null;

	private JMenuItem _jMenuItemExit = null;

	private JMenuItem _jMenuItemNewLevel = null;

	private JMenuItem _jMenuItemLoad = null;

	private JMenuItem _jMenuItemSave = null;

	private JMenu _jMenuHelp = null;

	private JMenuItem _jMenuItemAbout = null;

	public EditorFrame() {
		super();
		initialize();
	}

	private void initialize() {
		this.setSize(300, 400);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"))); //$NON-NLS-1$
		this.setJMenuBar(getJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("JavaPicross Editor"); //$NON-NLS-1$
	}

	public JPanel getJContentPane() {
		if (_jContentPane == null) {
			_jContentPane = new JPanel();
			_jContentPane.setLayout(new BorderLayout());

			Box box = Box.createVerticalBox();
			box.add(getPicrossPreviewPanel());
			box.add(getPicrossPanel());
			box.add(Box.createVerticalGlue());

			_jContentPane.add(box, BorderLayout.CENTER);
			_jContentPane.setBackground(Style.BACKGROUND_COLOR);
		}
		return _jContentPane;
	}

	public EditorPicrossPanel getPicrossPanel() {
		if (_Editor_picrossPanel == null) {
			_Editor_picrossPanel = new EditorPicrossPanel(Style.BLOCK_SIZE, Style.LINE_SIZE);
		}
		return _Editor_picrossPanel;
	}

	public EditorPicrossPanel getPicrossPreviewPanel() {
		if (_picrossPreviewPanel == null) {
			_picrossPreviewPanel = new EditorPicrossPanel(Style.BLOCK_PREVIEW_SIZE, 0);
		}
		return _picrossPreviewPanel;
	}

	public JMenuBar getJMenuBar() {
		if (_JMenuBar == null) {
			_JMenuBar = new JMenuBar();
			_JMenuBar.add(getJMenuFile());
			_JMenuBar.add(getJMenuHelp());
		}
		return _JMenuBar;
	}

	public JMenu getJMenuFile() {
		if (_jMenuFile == null) {
			_jMenuFile = new JMenu(LanguageManager.getString("menu.file")); //$NON-NLS-1$
			_jMenuFile.add(getJMenuItemNewLevel());
			_jMenuFile.addSeparator();
			_jMenuFile.add(getJMenuItemLoad());
			_jMenuFile.add(getJMenuItemSave());
			_jMenuFile.addSeparator();
			_jMenuFile.add(getJMenuItemExit());
		}
		return _jMenuFile;
	}

	public JMenuItem getJMenuItemExit() {
		if (_jMenuItemExit == null) {
			_jMenuItemExit = new JMenuItem(LanguageManager.getString("menu.exit")); //$NON-NLS-1$
			_jMenuItemExit.setActionCommand(Actions.EXIT);
		}
		return _jMenuItemExit;
	}

	public JMenuItem getJMenuItemNewLevel() {
		if (_jMenuItemNewLevel == null) {
			_jMenuItemNewLevel = new JMenuItem(LanguageManager.getString("menu.new")); //$NON-NLS-1$
			_jMenuItemNewLevel.setActionCommand(Actions.NEW_DIALOG);
		}
		return _jMenuItemNewLevel;
	}

	public JMenuItem getJMenuItemLoad() {
		if (_jMenuItemLoad == null) {
			_jMenuItemLoad = new JMenuItem();
			_jMenuItemLoad.setText(LanguageManager.getString("menu.load")); //$NON-NLS-1$
			_jMenuItemLoad.setActionCommand(Actions.LOAD);
		}
		return _jMenuItemLoad;
	}

	public JMenuItem getJMenuItemSave() {
		if (_jMenuItemSave == null) {
			_jMenuItemSave = new JMenuItem();
			_jMenuItemSave.setText(LanguageManager.getString("menu.save")); //$NON-NLS-1$
			_jMenuItemSave.setActionCommand(Actions.SAVE);
		}
		return _jMenuItemSave;
	}

	public JMenu getJMenuHelp() {
		if (_jMenuHelp == null) {
			_jMenuHelp = new JMenu();
			_jMenuHelp.setText(LanguageManager.getString("menu.help")); //$NON-NLS-1$
			_jMenuHelp.add(getJMenuItemAbout());
		}
		return _jMenuHelp;
	}

	public JMenuItem getJMenuItemAbout() {
		if (_jMenuItemAbout == null) {
			_jMenuItemAbout = new JMenuItem();
			_jMenuItemAbout.setText(LanguageManager.getString("menu.about")); //$NON-NLS-1$
			_jMenuItemAbout.setActionCommand(Actions.ABOUT);
		}
		return _jMenuItemAbout;
	}

}
