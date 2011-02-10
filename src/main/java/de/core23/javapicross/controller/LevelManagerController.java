package de.core23.javapicross.controller;

import de.core23.javapicross.gui.Actions;
import de.core23.javapicross.gui.LevelDialog;
import de.core23.javapicross.helper.LanguageManager;
import de.core23.javapicross.helper.LevelException;
import de.core23.javapicross.helper.LevelManager;
import de.core23.javapicross.model.Level;
import de.core23.javapicross.model.LevelFile;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class LevelManagerController implements ActionListener {
	private LevelManager _manager;

	public static boolean LOAD = false;

	public static boolean SAVE = true;

	private boolean _loading = false;

	private EditorController _controller;

	private boolean _modus;

	private LevelDialog _dialog;

	private DefaultListModel _levelModel;

	private DefaultComboBoxModel _fileModel;

	public LevelManagerController(EditorController controller, boolean modus) {
		_controller = controller;
		_modus = modus;
		_manager = new LevelManager();

		_fileModel = new DefaultComboBoxModel();
		_levelModel = new DefaultListModel();

		reloadFiles();
	}

	private void reloadFiles() {
		if (_loading)
			return;

		_loading = true;

		String fileName = (String) (_dialog == null || _dialog.getJComboBoxFile().getSelectedItem() == null ? null : ((LevelFile) _dialog.getJComboBoxFile()
			.getSelectedItem()).getFilename());
		int currentLevel = _dialog == null || _dialog.getJListLevel().getSelectedValue() == null ? -1 : Integer.valueOf(_dialog.getJListLevel()
			.getSelectedValue().toString());

		// Files
		_fileModel.removeAllElements();
		for (LevelFile file : _manager.getLevelFiles())
			_fileModel.addElement(file);

		if (fileName == null && _fileModel.getSize() > 0)
			fileName = ((LevelFile) _fileModel.getElementAt(0)).getFilename();
		_manager.setFilename(fileName);

		// Change Manager
		if (_dialog != null) {
			for (int i = 0; i < _fileModel.getSize(); i++)
				if (fileName.equals(((LevelFile) _fileModel.getElementAt(i)).getFilename()))
					_dialog.getJComboBoxFile().setSelectedIndex(i);
			_dialog.getJListLevel().setSelectedValue(currentLevel, true);
			showPreview(_manager.getLevel(currentLevel));
		}

		reloadLevels();

		_loading = false;
	}

	private void reloadLevels() {
		// Level
		_levelModel.removeAllElements();

		int size = _manager.getSize() + (_modus == SAVE ? 1 : 0);
		for (int i = 1; i <= size; i++)
			_levelModel.addElement(i);
	}

	public void show(Frame owner) {
		_dialog = new LevelDialog(owner);
		_dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		_dialog.setLocationRelativeTo(owner);
		_dialog.getJButtonLoad().addActionListener(this);
		_dialog.getJButtonLoad().setVisible(_modus == LOAD);
		_dialog.getJButtonSave().addActionListener(this);
		_dialog.getJButtonSave().setVisible(_modus == SAVE);
		if (_modus == LOAD)
			_dialog.setTitle(LanguageManager.getString("manage.load.title")); //$NON-NLS-1$
		else
			_dialog.setTitle(LanguageManager.getString("manage.save.title")); //$NON-NLS-1$
		_dialog.getJButtonNewFile().setVisible(_modus == SAVE);
		_dialog.getJComboBoxFile().setModel(_fileModel);
		_dialog.getJComboBoxFile().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					reloadFiles();
			}
		});
		_dialog.getJButtonNewFile().addActionListener(this);
		_dialog.getJListLevel().setModel(_levelModel);
		_dialog.getJListLevel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					return;
				int number = _dialog.getJListLevel().getSelectedIndex();

				if (number < 0)
					return;

				showPreview(_manager.getLevel(number));
			}
		});
		_dialog.setModal(true);
		_dialog.setVisible(true);
	}

	private void save() {
		int number = Integer.valueOf(_dialog.getJListLevel().getSelectedValue().toString()) - 1;

		try {
			_manager.setLevel(number, _controller.getLevel());
			JOptionPane.showMessageDialog(_controller.getFrame(), LanguageManager.getString("manage.save.message"), LanguageManager.getString("manage.save.title"), JOptionPane.INFORMATION_MESSAGE);  //$NON-NLS-1$//$NON-NLS-2$
			_dialog.dispose();
		} catch (LevelException e) {
			JOptionPane.showMessageDialog(_controller.getFrame(), e.getMessage(), LanguageManager.getString("manage.save.title"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}
	}

	private void load() {
		if (_dialog.getJListLevel().getSelectedValue() == null)
			return;
		int number = _dialog.getJListLevel().getSelectedIndex();

		if (number < 0)
			return;

		Level level = _manager.getLevel(number);
		if (level == null || level.getSize() <= 0) {
			JOptionPane.showMessageDialog(_controller.getFrame(), LanguageManager.getString("manage.load.error"), LanguageManager.getString("manage.load.title"), JOptionPane.ERROR_MESSAGE);  //$NON-NLS-1$//$NON-NLS-2$
			return;
		}

		_controller.setLevel(level);
		_dialog.dispose();
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals(Actions.SAVE)) {
			save();
		} else if (ae.getActionCommand().equals(Actions.NEW_FILE)) {
			newFile();
		} else if (ae.getActionCommand().equals(Actions.LOAD)) {
			load();
		}
	}

	private void newFile() {
		String fileName = JOptionPane
			.showInputDialog(_dialog, LanguageManager.getString("manage.create"), "", JOptionPane.INFORMATION_MESSAGE);  //$NON-NLS-1$//$NON-NLS-2$

		if (fileName == null || fileName.isEmpty())
			return;

		try {
			_manager.createFilename(fileName);
		} catch (LevelException e) {
			JOptionPane.showMessageDialog(_dialog, e.getMessage(), LanguageManager.getString("manage.create.title"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}

		reloadFiles();
	}

	private void showPreview(Level level) {
		_dialog.getPicrossPreviewPanel().setVisible(level != null && level.getSize() > 0);
		if (level == null || level.getSize() <= 0)
			return;

		int size = level.getSize();

		_dialog.getPicrossPreviewPanel().setStyle(100 / size, 0);
		_dialog.getPicrossPreviewPanel().setGridSize(size, size);

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				_dialog.getPicrossPreviewPanel().setStatus(x, y, level.getBlock(x, y));
			}
		}

		_dialog.getPicrossPreviewPanel().setPreferredSize(
			new Dimension(_dialog.getPicrossPreviewPanel().getPaintWidth(), _dialog.getPicrossPreviewPanel().getPaintHeight()));

		_dialog.getPicrossPreviewPanel().repaint();
	}
}
