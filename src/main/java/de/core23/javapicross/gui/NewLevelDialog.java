package de.core23.javapicross.gui;

import de.core23.javapicross.helper.LanguageManager;

import javax.swing.*;
import java.awt.*;


public class NewLevelDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel _jContentPane = null;

	private JLabel _jLabelSize = null;

	private JComboBox _jComboBoxSize = null;

	private JButton _jButtonCreate = null;

	public NewLevelDialog(Frame owner) {
		super(owner);
		initialize();
	}

	private void initialize() {
		this.setSize(280, 140);
		this.setTitle(LanguageManager.getString("new.title")); //$NON-NLS-1$
		this.setContentPane(getJContentPane());
	}

	public JPanel getJContentPane() {
		if (_jContentPane == null) {
			_jLabelSize = new JLabel();
			_jLabelSize.setBounds(new Rectangle(20, 20, 80, 25));
			_jLabelSize.setText(LanguageManager.getString("new.size")); //$NON-NLS-1$
			_jContentPane = new JPanel();
			_jContentPane.setLayout(null);
			_jContentPane.add(_jLabelSize, null);
			_jContentPane.add(getJComboBoxSize(), null);
			_jContentPane.add(getJButtonCreate(), null);
		}
		return _jContentPane;
	}

	public JComboBox getJComboBoxSize() {
		if (_jComboBoxSize == null) {
			_jComboBoxSize = new JComboBox();
			_jComboBoxSize.setBounds(new Rectangle(110, 20, 60, 25));
		}
		return _jComboBoxSize;
	}

	public JButton getJButtonCreate() {
		if (_jButtonCreate == null) {
			_jButtonCreate = new JButton();
			_jButtonCreate.setText(LanguageManager.getString("new.create")); //$NON-NLS-1$
			_jButtonCreate.setActionCommand(Actions.CREATE_LEVEL);
			_jButtonCreate.setBounds(new Rectangle(110, 60, 110, 25));
		}
		return _jButtonCreate;
	}
}
