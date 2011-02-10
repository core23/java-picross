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


public class RandomGameDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel _jContentPane = null;

	private JLabel _jLabelSize = null;

	private JLabel _jLabelHelp = null;

	private JCheckBox _jCheckBoxHelp = null;

	private JComboBox _jComboBoxSize = null;

	private JButton _jButtonStart = null;

	public RandomGameDialog(Frame owner) {
		super(owner);
		initialize();
	}

	private void initialize() {
		this.setSize(280, 180);
		this.setTitle(LanguageManager.getString("random.game.title")); //$NON-NLS-1$
		this.setContentPane(getJContentPane());
	}

	public JPanel getJContentPane() {
		if (_jContentPane == null) {
			_jLabelHelp = new JLabel();
			_jLabelHelp.setBounds(new Rectangle(20, 60, 80, 25));
			_jLabelHelp.setText(LanguageManager.getString("random.game.help")); //$NON-NLS-1$
			_jLabelSize = new JLabel();
			_jLabelSize.setBounds(new Rectangle(20, 20, 80, 25));
			_jLabelSize.setText(LanguageManager.getString("random.game.size")); //$NON-NLS-1$
			_jContentPane = new JPanel();
			_jContentPane.setLayout(null);
			_jContentPane.add(_jLabelSize, null);
			_jContentPane.add(_jLabelHelp, null);
			_jContentPane.add(getJCheckBoxHelp(), null);
			_jContentPane.add(getJComboBoxSize(), null);
			_jContentPane.add(getJButtonStart(), null);
		}
		return _jContentPane;
	}

	public JCheckBox getJCheckBoxHelp() {
		if (_jCheckBoxHelp == null) {
			_jCheckBoxHelp = new JCheckBox();
			_jCheckBoxHelp.setBounds(new Rectangle(110, 60, 150, 25));
			_jCheckBoxHelp.setText(LanguageManager.getString("game.load.showhelp")); //$NON-NLS-1$
		}
		return _jCheckBoxHelp;
	}

	public JComboBox getJComboBoxSize() {
		if (_jComboBoxSize == null) {
			_jComboBoxSize = new JComboBox();
			_jComboBoxSize.setBounds(new Rectangle(110, 20, 60, 25));
			_jComboBoxSize.setBackground(UIManager.getColor("TextArea.background")); //$NON-NLS-1$
		}
		return _jComboBoxSize;
	}

	public JButton getJButtonStart() {
		if (_jButtonStart == null) {
			_jButtonStart = new JButton();
			_jButtonStart.setText(LanguageManager.getString("random.game.start")); //$NON-NLS-1$
			_jButtonStart.setBounds(new Rectangle(110, 100, 110, 25));
		}
		return _jButtonStart;
	}

}
