package de.core23.javapicross.controller;

import java.awt.Window;

import javax.swing.JFrame;

import de.core23.javapicross.gui.AboutDialog;


public class AboutWindowController {
	private AboutDialog _aboutDialog = null;

	public AboutWindowController(MainController mainController) {
		_aboutDialog = new AboutDialog();
		_aboutDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		_aboutDialog.setModal(true);
	}

	public void showWindow(Window window) {
		_aboutDialog.setLocationRelativeTo(window);
		_aboutDialog.setVisible(true);
		_aboutDialog.toFront();
		_aboutDialog.requestFocus();
	}
}
