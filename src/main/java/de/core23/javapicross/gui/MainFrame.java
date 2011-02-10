package de.core23.javapicross.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;

import de.core23.javapicross.helper.LanguageManager;


public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel _jContentPane = null;

	private PicrossPanel _picrossPanel = null;

	private JMenuBar _JMenuBar = null;

	private JMenu _jMenuFile = null;

	private JMenuItem _jMenuItemExit = null;

	private JMenuItem _jMenuItemRandom = null;

	private JPanel _jPanel = null;

	private JLabel _jLabelTimeText = null;

	private JLabel _jLabelTime = null;

	private JMenuItem _jMenuItemLoad = null;

	private JMenuItem _jMenuItemNewGame = null;

	private JMenu _jMenuHelp = null;

	private JMenuItem _jMenuItemAbout = null;

	public MainFrame() {
		super();
		initialize();
	}

	private void initialize() {
		this.setSize(300, 400);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"))); //$NON-NLS-1$
		this.setMinimumSize(new Dimension(350, 350));
		this.setJMenuBar(getJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("JavaPicross"); //$NON-NLS-1$
	}

	public JPanel getJContentPane() {
		if (_jContentPane == null) {
			_jContentPane = new JPanel();
			_jContentPane.setLayout(new BorderLayout());
			_jContentPane.add(getPicrossPanel(), BorderLayout.CENTER);
			_jContentPane.add(getJPanel(), BorderLayout.NORTH);
		}
		return _jContentPane;
	}

	public PicrossPanel getPicrossPanel() {
		if (_picrossPanel == null) {
			_picrossPanel = new PicrossPanel();
		}
		return _picrossPanel;
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
			_jMenuFile.add(getJMenuItemNewGame());
			_jMenuFile.addSeparator();
			_jMenuFile.add(getJMenuItemLoad());
			_jMenuFile.add(getJMenuItemRandom());
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

	public JMenuItem getJMenuItemRandom() {
		if (_jMenuItemRandom == null) {
			_jMenuItemRandom = new JMenuItem(LanguageManager.getString("menu.random")); //$NON-NLS-1$
			_jMenuItemRandom.setActionCommand(Actions.NEW_RANDOM);
		}
		return _jMenuItemRandom;
	}

	public JPanel getJPanel() {
		if (_jPanel == null) {
			_jPanel = new JPanel();
			_jPanel.setSize(0, 200);

			Box box = Box.createHorizontalBox();
			box.add(getJLabelTimeText());
			box.add(Box.createHorizontalStrut(5));
			box.add(getJLabelTime());

			_jPanel.add(box);
		}
		return _jPanel;
	}

	public JLabel getJLabelTimeText() {
		if (_jLabelTimeText == null) {
			_jLabelTimeText = new JLabel();
			_jLabelTimeText.setText(LanguageManager.getString("game.time") + ":"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return _jLabelTimeText;
	}

	public JLabel getJLabelTime() {
		if (_jLabelTime == null) {
			_jLabelTime = new JLabel();
			_jLabelTime.setText("00:00"); //$NON-NLS-1$
		}
		return _jLabelTime;
	}

	public JMenuItem getJMenuItemLoad() {
		if (_jMenuItemLoad == null) {
			_jMenuItemLoad = new JMenuItem();
			_jMenuItemLoad.setText(LanguageManager.getString("menu.load")); //$NON-NLS-1$
			_jMenuItemLoad.setActionCommand(Actions.LOAD);
		}
		return _jMenuItemLoad;
	}

	public JMenuItem getJMenuItemNewGame() {
		if (_jMenuItemNewGame == null) {
			_jMenuItemNewGame = new JMenuItem();
			_jMenuItemNewGame.setText(LanguageManager.getString("menu.new")); //$NON-NLS-1$
			_jMenuItemNewGame.setActionCommand(Actions.NEW_GAME);
		}
		return _jMenuItemNewGame;
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
