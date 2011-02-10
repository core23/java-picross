package de.core23.javapicross.controller;

import de.core23.javapicross.gui.Actions;
import de.core23.javapicross.gui.NewLevelDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NewLevelController implements ActionListener {
	private EditorController _controller;

	private NewLevelDialog _dialog;

	private DefaultComboBoxModel _levelSizeModel;

	public NewLevelController(EditorController controller) {
		_controller = controller;

		_levelSizeModel = new DefaultComboBoxModel();
		_levelSizeModel.addElement(5);
		_levelSizeModel.addElement(10);
		_levelSizeModel.addElement(15);
		_levelSizeModel.addElement(20);
	}

	public void show(Frame owner) {
		_dialog = new NewLevelDialog(owner);
		_dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		_dialog.setLocationRelativeTo(owner);
		_dialog.getJComboBoxSize().setModel(_levelSizeModel);
		_dialog.getJButtonCreate().addActionListener(this);
		_dialog.setModal(true);
		_dialog.setVisible(true);
	}

	public void closeNewLevelDialog() {
		Integer size = (Integer) _dialog.getJComboBoxSize().getSelectedItem();

		_dialog.dispose();

		_controller.newLevel(size);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals(Actions.CREATE_LEVEL)) {
			closeNewLevelDialog();
		}
	}
}
