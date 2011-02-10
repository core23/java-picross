package de.core23.javapicross.controller;

import de.core23.javapicross.gui.Actions;
import de.core23.javapicross.gui.EditorFrame;
import de.core23.javapicross.model.Level;
import net.roydesign.app.AboutJMenuItem;
import net.roydesign.app.Application;
import net.roydesign.app.QuitJMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditorController extends Application implements ActionListener {
	private EditorFrame _frame;

	private boolean _blockModus;

	private Level _level;

	public EditorController() {
	}
	
	private void initMacSettings() {
		getAboutJMenuItem().setActionCommand(Actions.ABOUT);
		getAboutJMenuItem().addActionListener(this);

		getPreferencesJMenuItem().setEnabled(false);
		getPreferencesJMenuItem().setVisible(false);

		// ActionCommand not supported
		getQuitJMenuItem().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});

		if (AboutJMenuItem.isAutomaticallyPresent())
			_frame.getJMenuHelp().setVisible(false);
		if (QuitJMenuItem.isAutomaticallyPresent())
			_frame.getJMenuItemExit().setVisible(false);
	}

	public void show() {
		_frame = new EditorFrame();
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		_frame.getPicrossPanel().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Point p = _frame.getPicrossPanel().translatePoint(e.getPoint());

				if (p != null && _level.isValidBlock(p.x, p.y))
					_blockModus = !_level.getBlock(p.x, p.y);
			}

			public void mouseReleased(MouseEvent e) {
				Point p = _frame.getPicrossPanel().translatePoint(e.getPoint());
				
				if (p != null && _level.isValidBlock(p.x, p.y))
					setStatus(p.x, p.y, _blockModus);
			}
		});
		_frame.getPicrossPanel().addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = _frame.getPicrossPanel().translatePoint(e.getPoint());
				
				if (p != null && _level.isValidBlock(p.x, p.y))
					setStatus(p.x, p.y, _blockModus);
			}

		});
		_frame.getJMenuItemExit().addActionListener(this);
		_frame.getJMenuItemLoad().addActionListener(this);
		_frame.getJMenuItemSave().addActionListener(this);
		_frame.getJMenuItemAbout().addActionListener(this);
		_frame.getJMenuItemNewLevel().addActionListener(this);

		initMacSettings();

		newLevel(5);

		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}

	private void close() {
		System.exit(0);
	}

	public void newLevel(int size) {
		setLevel(new Level(size));
	}

	private void setStatus(int x, int y, boolean block) {
		if (!_level.isValidBlock(x, y))
			return;

		_level.setBlock(x, y, block);

		_frame.getPicrossPanel().setStatus(x, y, block);
		_frame.getPicrossPanel().repaint();
		_frame.getPicrossPreviewPanel().setStatus(x, y, block);
		_frame.getPicrossPreviewPanel().repaint();
	}

	public void showNewLevelDialog() {
		new NewLevelController(this).show(_frame);
	}

	private void showSaveDialog() {
		if (_level == null)
			return;
		new LevelManagerController(this, LevelManagerController.SAVE).show(_frame);
	}

	private void showLoadDialog() {
		new LevelManagerController(this, LevelManagerController.LOAD).show(_frame);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals(Actions.EXIT)) {
			close();
		} else if (ae.getActionCommand().equals(Actions.NEW_DIALOG)) {
			showNewLevelDialog();
		} else if (ae.getActionCommand().equals(Actions.LOAD)) {
			showLoadDialog();
		} else if (ae.getActionCommand().equals(Actions.SAVE)) {
			showSaveDialog();
		}
	}

	public void setLevel(Level level) {
		_level = level;

		_frame.getPicrossPreviewPanel().setGridSize(_level.getSize(), _level.getSize());
		_frame.getPicrossPanel().setGridSize(_level.getSize(), _level.getSize());

		for (int x = 0; x < _level.getSize(); x++) {
			for (int y = 0; y < _level.getSize(); y++) {
				_frame.getPicrossPreviewPanel().setStatus(x, y, _level.getBlock(x, y));
				_frame.getPicrossPanel().setStatus(x, y, _level.getBlock(x, y));
			}
		}

		_frame.getPicrossPanel().setPreferredSize(new Dimension(_frame.getPicrossPanel().getPaintWidth(), _frame.getPicrossPanel().getPaintHeight()));
		_frame.getPicrossPreviewPanel().setPreferredSize(
			new Dimension(_frame.getPicrossPreviewPanel().getPaintWidth(), _frame.getPicrossPreviewPanel().getPaintHeight()));

		resize();
	}

	private void resize() {
		int width = _frame.getPicrossPanel().getPaintWidth() + 40;
		int height = _frame.getPicrossPanel().getPaintHeight() + 200;

		_frame.setSize(Math.max(_frame.getWidth(), width), Math.max(_frame.getHeight(), height));
		_frame.setMinimumSize(new Dimension(width, height));

		_frame.getContentPane().doLayout();

		_frame.repaint();
	}

	public Level getLevel() {
		return _level;
	}

	public JFrame getFrame() {
		return _frame;
	}
}
