package de.core23.javapicross;

import java.io.IOException;

import javax.swing.SwingUtilities;

import de.core23.javapicross.controller.MainController;
import de.core23.javapicross.helper.LanguageManager;


public class PicrossAppDelegate {
	public static void main(String[] args) {
		// Load Language File
		try {
			LanguageManager.load("de"); //$NON-NLS-1$
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.setProperty("apple.laf.useScreenMenuBar", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "JavaPicross"); //$NON-NLS-1$ //$NON-NLS-2$

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainController().show();
			}
		});
	}
}
