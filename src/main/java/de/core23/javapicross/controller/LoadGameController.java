package de.core23.javapicross.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.core23.javapicross.gui.Actions;
import de.core23.javapicross.gui.LoadGameDialog;
import de.core23.javapicross.helper.LanguageManager;
import de.core23.javapicross.helper.LevelManager;
import de.core23.javapicross.model.Level;
import de.core23.javapicross.model.LevelFile;


public class LoadGameController implements ActionListener {
	private LevelManager _manager;

	private LoadGameDialog _dialog;

	private MainController _controller;

	private DefaultComboBoxModel _fileListModel;

	private DefaultComboBoxModel _levelListModel;

	public LoadGameController(MainController controller) {
		_controller = controller;

		_manager = new LevelManager();

		_fileListModel = new DefaultComboBoxModel();
		_levelListModel = new DefaultComboBoxModel();

		_fileListModel.removeAllElements();
		for (LevelFile file : _manager.getLevelFiles())
			_fileListModel.addElement(file);
	}

	public void show(JFrame frame) {
		if (_manager.getSize() == 0) {
			JOptionPane.showMessageDialog(frame, LanguageManager.getString("game.load.fileerror"), LanguageManager.getString("game.load.title"), JOptionPane.ERROR_MESSAGE);   //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}

		_dialog = new LoadGameDialog(frame);
		_dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		_dialog.getJComboBoxFiles().setModel(_fileListModel);
		_dialog.getJComboBoxFiles().setActionCommand(Actions.FILE_CHANGED);
		_dialog.getJComboBoxFiles().addActionListener(this);

		_dialog.getJComboBoxLevel().setModel(_levelListModel);

		fileChanged();

		_dialog.getJButtonStart().setActionCommand(Actions.START);
		_dialog.getJButtonStart().addActionListener(this);
		_dialog.setLocationRelativeTo(frame);
		_dialog.setModal(true);
		_dialog.setVisible(true);
	}

	private void fileChanged() {
		LevelFile levelFile = (LevelFile) _dialog.getJComboBoxFiles().getSelectedItem();

		_levelListModel.removeAllElements();

		_manager.setFilename(levelFile.getFilename());
		for (Level level : _manager.getLevels())
			_levelListModel.addElement(level);
	}

	public void start() {
		int level = 0;
		String fileName = ""; //$NON-NLS-1$
		boolean help = _dialog.getJCheckBoxHelp().isSelected();

		try {
			level = ((Level) _dialog.getJComboBoxLevel().getSelectedItem()).getNumber();
			fileName = ((LevelFile) _dialog.getJComboBoxFiles().getSelectedItem()).getFilename();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(_dialog, LanguageManager.getString("game.load.error"), LanguageManager.getString("game.load.title"), JOptionPane.ERROR_MESSAGE);   //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}

		_dialog.dispose();

		_controller.startLevel(fileName, level, help);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals(Actions.START)) {
			start();
		} else if (ae.getActionCommand().equals(Actions.FILE_CHANGED)) {
			fileChanged();
		}
	}
}
