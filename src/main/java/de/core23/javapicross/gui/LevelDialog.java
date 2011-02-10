package de.core23.javapicross.gui;

import de.core23.javapicross.helper.LanguageManager;

import javax.swing.*;
import java.awt.*;


public class LevelDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel _jContentPane = null;

	private JList _jListLevel = null; // @jve:decl-index=0:visual-constraint="-1,218"

	private EditorPicrossPanel _picrossPreviewPanel = null;

	private JLabel _jLabelLevel = null;

	private JButton _jButtonLoad = null;

	private JButton _jButtonSave = null;

	private JScrollPane _jScrollPaneLevel = null;

	private JLabel _jLabelFile = null;

	private JComboBox _jComboBoxFile = null;

	private JButton _jButtonNewFile = null;

	public LevelDialog(Frame owner) {
		super(owner);
		initialize();
	}

	private void initialize() {
		this.setSize(380, 220);
		this.setContentPane(getJContentPane());
		this.setResizable(false);
	}

	public JPanel getJContentPane() {
		if (_jContentPane == null) {
			_jLabelFile = new JLabel();
			_jLabelFile.setBounds(new Rectangle(10, 10, 100, 25));
			_jLabelFile.setText(LanguageManager.getString("manage.file")); //$NON-NLS-1$
			_jContentPane = new JPanel();
			_jContentPane.setLayout(null);

			_jLabelLevel = new JLabel();
			_jLabelLevel.setText(LanguageManager.getString("manage.level")); //$NON-NLS-1$
			_jLabelLevel.setBounds(10, 40, 100, 25);
			_jContentPane.add(_jLabelLevel);

			_jContentPane.add(getPicrossPreviewPanel());
			_jContentPane.add(getJScrollPaneLevel());

			Box box = Box.createHorizontalBox();
			box.add(getJButtonLoad());
			box.add(getJButtonSave());
			box.add(Box.createHorizontalGlue());
			box.setBounds(120, 150, 130, 25);

			_jContentPane.add(box);
			_jContentPane.add(_jLabelFile, null);
			_jContentPane.add(getJComboBoxFile(), null);
			_jContentPane.add(getJButtonNewFile(), null);
		}
		return _jContentPane;
	}

	public JList getJListLevel() {
		if (_jListLevel == null) {
			_jListLevel = new JList();
		}
		return _jListLevel;
	}

	public EditorPicrossPanel getPicrossPreviewPanel() {
		if (_picrossPreviewPanel == null) {
			_picrossPreviewPanel = new EditorPicrossPanel(Style.BLOCK_PREVIEW_SIZE, 0);
			_picrossPreviewPanel.setBounds(260, 40, 100, 100);
		}
		return _picrossPreviewPanel;
	}

	public JButton getJButtonLoad() {
		if (_jButtonLoad == null) {
			_jButtonLoad = new JButton();
			_jButtonLoad.setText(LanguageManager.getString("manage.load")); //$NON-NLS-1$
			_jButtonLoad.setActionCommand(Actions.LOAD);
			_jButtonLoad.setBounds(60, 150, 100, 25);
		}
		return _jButtonLoad;
	}

	public JButton getJButtonSave() {
		if (_jButtonSave == null) {
			_jButtonSave = new JButton();
			_jButtonSave.setText(LanguageManager.getString("manage.save")); //$NON-NLS-1$
			_jButtonSave.setActionCommand(Actions.SAVE);
			_jButtonSave.setBounds(170, 150, 100, 25);
		}
		return _jButtonSave;
	}

	public JScrollPane getJScrollPaneLevel() {
		if (_jScrollPaneLevel == null) {
			_jScrollPaneLevel = new JScrollPane(getJListLevel());
			_jScrollPaneLevel.setBounds(120, 40, 130, 100);
		}
		return _jScrollPaneLevel;
	}

	public JComboBox getJComboBoxFile() {
		if (_jComboBoxFile == null) {
			_jComboBoxFile = new JComboBox();
			_jComboBoxFile.setBounds(new Rectangle(120, 10, 130, 25));
			_jComboBoxFile.setBackground(UIManager.getColor("TextArea.background")); //$NON-NLS-1$
		}
		return _jComboBoxFile;
	}

	public JButton getJButtonNewFile() {
		if (_jButtonNewFile == null) {
			_jButtonNewFile = new JButton();
			_jButtonNewFile.setText(LanguageManager.getString("manage.new")); //$NON-NLS-1$
			_jButtonNewFile.setToolTipText(LanguageManager.getString("manage.new.info")); //$NON-NLS-1$
			_jButtonNewFile.setActionCommand(Actions.NEW_FILE);
			_jButtonNewFile.setBounds(new Rectangle(260, 10, 100, 25));
		}
		return _jButtonNewFile;
	}
} // @jve:decl-index=0:visual-constraint="157,10"
