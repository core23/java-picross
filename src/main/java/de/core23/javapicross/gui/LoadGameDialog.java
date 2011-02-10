package de.core23.javapicross.gui;

import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import de.core23.javapicross.helper.LanguageManager;


public class LoadGameDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel _jContentPane = null;

	private JLabel _jLabelDifficulty = null;

	private JLabel _jLabelStart = null;

	private JComboBox _jComboBoxLevel = null;

	private JComboBox _jComboBoxFiles = null;

	private JButton _jButtonStart = null;

	private JLabel _jLabelHelp = null;

	private JCheckBox _jCheckBoxHelp = null;

	public LoadGameDialog(Frame owner) {
		super(owner);
		initialize();
	}

	private void initialize() {
		this.setSize(280, 220);
		this.setTitle(LanguageManager.getString("game.load.title")); //$NON-NLS-1$
		this.setContentPane(getJContentPane());
	}

	public JPanel getJContentPane() {
		if (_jContentPane == null) {
			_jLabelHelp = new JLabel();
			_jLabelHelp.setBounds(new Rectangle(20, 100, 80, 25));
			_jLabelHelp.setText(LanguageManager.getString("game.load.help")); //$NON-NLS-1$
			_jLabelStart = new JLabel();
			_jLabelStart.setBounds(new Rectangle(20, 60, 80, 25));
			_jLabelStart.setText(LanguageManager.getString("game.load.level")); //$NON-NLS-1$
			_jLabelDifficulty = new JLabel();
			_jLabelDifficulty.setBounds(new Rectangle(20, 20, 80, 25));
			_jLabelDifficulty.setText(LanguageManager.getString("game.load.difficulty")); //$NON-NLS-1$
			_jContentPane = new JPanel();
			_jContentPane.setLayout(null);
			_jContentPane.add(_jLabelDifficulty, null);
			_jContentPane.add(_jLabelStart, null);
			_jContentPane.add(getJComboBoxLevel(), null);
			_jContentPane.add(getJComboBoxFiles(), null);
			_jContentPane.add(getJButtonStart(), null);
			_jContentPane.add(_jLabelHelp, null);
			_jContentPane.add(getJCheckBoxHelp(), null);
		}
		return _jContentPane;
	}

	public JComboBox getJComboBoxLevel() {
		if (_jComboBoxLevel == null) {
			_jComboBoxLevel = new JComboBox();
			_jComboBoxLevel.setBounds(new Rectangle(110, 60, 50, 25));
			_jComboBoxLevel.setBackground(UIManager.getColor("TextArea.background")); //$NON-NLS-1$
		}
		return _jComboBoxLevel;
	}

	public JComboBox getJComboBoxFiles() {
		if (_jComboBoxFiles == null) {
			_jComboBoxFiles = new JComboBox();
			_jComboBoxFiles.setBounds(new Rectangle(110, 20, 100, 25));
			_jComboBoxFiles.setBackground(UIManager.getColor("TextArea.background")); //$NON-NLS-1$
		}
		return _jComboBoxFiles;
	}

	public JButton getJButtonStart() {
		if (_jButtonStart == null) {
			_jButtonStart = new JButton();
			_jButtonStart.setText(LanguageManager.getString("game.load.start")); //$NON-NLS-1$
			_jButtonStart.setBounds(new Rectangle(110, 140, 110, 25));
		}
		return _jButtonStart;
	}

	public JCheckBox getJCheckBoxHelp() {
		if (_jCheckBoxHelp == null) {
			_jCheckBoxHelp = new JCheckBox();
			_jCheckBoxHelp.setBounds(new Rectangle(110, 100, 150, 25));
			_jCheckBoxHelp.setText(LanguageManager.getString("game.load.showhelp")); //$NON-NLS-1$
		}
		return _jCheckBoxHelp;
	}
}
