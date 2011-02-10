package de.core23.javapicross.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;

import de.core23.javapicross.gui.Actions;
import de.core23.javapicross.gui.RandomGameDialog;


public class RandomGameController implements ActionListener {

	private RandomGameDialog _dialog;

	private MainController _controller;

	private DefaultComboBoxModel _levelSizeModel;

	public RandomGameController(MainController controller) {
		_controller = controller;

		_levelSizeModel = new DefaultComboBoxModel();
		_levelSizeModel.addElement(5);
		_levelSizeModel.addElement(10);
		_levelSizeModel.addElement(15);
		_levelSizeModel.addElement(20);
	}

	public void show(JFrame frame) {
		_dialog = new RandomGameDialog(frame);
		_dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		_dialog.getJComboBoxSize().setModel(_levelSizeModel);
		_dialog.getJButtonStart().setActionCommand(Actions.START);
		_dialog.getJButtonStart().addActionListener(this);
		_dialog.setLocationRelativeTo(frame);
		_dialog.setModal(true);
		_dialog.setVisible(true);
	}

	public void start() {
		Integer size = (Integer) _dialog.getJComboBoxSize().getSelectedItem();
		boolean help = _dialog.getJCheckBoxHelp().isSelected();

		_dialog.dispose();

		_controller.randomGame(size, help);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals(Actions.START)) {
			start();
		}
	}
}
