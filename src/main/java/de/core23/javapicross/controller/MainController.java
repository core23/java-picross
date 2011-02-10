package de.core23.javapicross.controller;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import de.core23.javapicross.gui.Actions;
import de.core23.javapicross.gui.MainFrame;
import de.core23.javapicross.helper.LanguageManager;
import de.core23.javapicross.helper.LevelManager;
import de.core23.javapicross.model.FieldStatus;
import de.core23.javapicross.model.GameRules;
import de.core23.javapicross.model.Level;
import de.core23.javapicross.model.LevelFile;

import net.roydesign.app.AboutJMenuItem;
import net.roydesign.app.Application;
import net.roydesign.app.QuitJMenuItem;

public class MainController extends Application implements ActionListener {
	private enum Modus {
		RANDOM, LEVEL, NORMAL
	};

	private Modus _currentModus;

	private LevelManager _levelManager;

	private MainFrame _frame;

	private Level _level;

	private boolean _end = true;

	private Timer _timer;

	private int _time = 0;

	private int _failure = 0;

	private boolean _pause = false;

	public MainController() {
		_levelManager = new LevelManager();

		_timer = new Timer(GameRules.TICK_SPEED, this);
		_timer.setActionCommand(Actions.TIMER);
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
		_frame = new MainFrame();
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		_frame.getPicrossPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Point p = _frame.getPicrossPanel().translatePoint(e.getPoint());

				if (SwingUtilities.isRightMouseButton(e))
					setMarkedStatus(p.x, p.y);
				else if (SwingUtilities.isLeftMouseButton(e))
					setVisibleStatus(p.x, p.y);
			}
		});
		_frame.getPicrossPanel().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = _frame.getPicrossPanel().translatePoint(e.getPoint());
				_frame.getPicrossPanel().hover(p.x, p.y);
			}

		});
		_frame.getJMenuItemRandom().addActionListener(this);
		_frame.getJMenuItemNewGame().addActionListener(this);
		_frame.getJMenuItemLoad().addActionListener(this);
		_frame.getJMenuItemExit().addActionListener(this);
		_frame.getJMenuItemAbout().addActionListener(this);

		initMacSettings();

		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}

	private void tick() {
		if (_pause)
			return;

		int seconds = _time / (1000 / GameRules.TICK_SPEED);
		int minutes = seconds / 60;
		seconds %= 60;

		if (_time <= 0) {
			endGame();
		} else {
			_frame.getJLabelTime().setText((minutes > 9 ? "" : "0") + minutes + ":" + (seconds > 9 ? "" : "0") + seconds); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}
		_time--;
	}

	private void resetGame() {
		_end = false;
		_failure = 0;
		_time = 30 * 60 * (1000 / GameRules.TICK_SPEED);

		int seconds = _time / (1000 / GameRules.TICK_SPEED);
		int minutes = seconds / 60;
		seconds %= 60;
		_frame.getJLabelTime().setText((minutes > 9 ? "" : "0") + minutes + ":" + (seconds > 9 ? "" : "0") + seconds); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	}

	public void randomGame(int size, boolean help) {
		_timer.stop();

		_level = LevelManager.getRandom(size);
		_currentModus = Modus.RANDOM;
		_frame.getPicrossPanel().setGrid(_level.getColumns(), _level.getRows());

		resize();

		resetGame();

		// Help
		if (help)
			showHelp();

		_timer.start();
	}

	private void resize() {
		int width = _frame.getPicrossPanel().getPaintWidth() + 40;
		int height = _frame.getPicrossPanel().getPaintHeight() + 120;

		_frame.setSize(Math.max(_frame.getWidth(), width), Math.max(_frame.getHeight(), height));
		_frame.setMinimumSize(new Dimension(width, height));
	}

	private boolean loadLevel(String difficulty, int number, Modus modus) {
		_timer.stop();

		_levelManager.setFilename(difficulty);
		Level level = _levelManager.getLevel(number);

		if (level == null)
			return false;

		resetGame();

		_level = level;
		if (modus != _currentModus)
			_currentModus = modus;

		_frame.getPicrossPanel().setGrid(_level.getColumns(), _level.getRows());

		resize();

		_timer.start();

		return true;
	}

	public void startGame() {
		if (!loadLevel("1.lvl", 0, Modus.NORMAL)) { //$NON-NLS-1$
			JOptionPane.showMessageDialog(_frame, LanguageManager.getString("game.load.error"), LanguageManager.getString("game.load.title"), //$NON-NLS-1$ //$NON-NLS-2$
				JOptionPane.ERROR_MESSAGE);
			return;
		}

		_pause = true;
		if (JOptionPane.showConfirmDialog(_frame,
			LanguageManager.getString("game.help.message"), LanguageManager.getString("game.help.title"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) //$NON-NLS-1$ //$NON-NLS-2$
			showHelp();
		_pause = false;
	}

	public void nextLevel() {
		String currentFilename = _levelManager.getFilename();
		int number = _level.getNumber() + 1;

		// Next Difficulty
		if (number >= _levelManager.getSize()) {
			List<LevelFile> levelFiles = _levelManager.getLevelFiles();
			// FIXME: testen
			for (int i = 0; i < levelFiles.size(); i++) {
				if (currentFilename == null) {
					currentFilename = levelFiles.get(i).getFilename();
					break;
				}
				if (levelFiles.get(i).getFilename().equals(currentFilename))
					currentFilename = null;
			}

			if (currentFilename == null) {
				JOptionPane.showMessageDialog(_frame,
					LanguageManager.getString("game.end.message"), LanguageManager.getString("game.end.title"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
			number = 0;
		}

		// Load
		if (loadLevel(currentFilename, number, Modus.NORMAL)) {
			_pause = true;
			if (JOptionPane.showConfirmDialog(_frame,
				LanguageManager.getString("game.help.message"), LanguageManager.getString("game.help.title"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) //$NON-NLS-1$ //$NON-NLS-2$
				showHelp();
			_pause = false;
		}
	}

	public void startLevel(String difficulty, int number, boolean help) {
		if (!loadLevel(difficulty, number, Modus.LEVEL)) {
			JOptionPane.showMessageDialog(_frame, LanguageManager.getString("game.load.error"), LanguageManager.getString("game.load.title"), //$NON-NLS-1$ //$NON-NLS-2$
				JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (help)
			showHelp();
	}

	private void showHelp() {
		Random rnd = new Random();

		boolean solved = true;
		int row = 0;
		int column = 0;
		do {
			row = rnd.nextInt(_level.getSize());
			column = rnd.nextInt(_level.getSize());

			for (int x = 0; x < _level.getSize(); x++) {
				for (int y = 0; y < _level.getSize(); y++) {
					if (x != row && y != column && _level.isBlock(x, y))
						solved = false;
				}
			}

		} while (solved);

		showRow(row);
		showColumn(column);
	}

	private void endGame() {
		_timer.stop();
		_end = true;

		if (_level.isSolved()) {
			JOptionPane.showMessageDialog(_frame,
				LanguageManager.getString("game.level.complete"), LanguageManager.getString("game.over.title"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$

			switch (_currentModus) {
				case NORMAL:
					nextLevel();
					break;
			}

		} else {
			_frame.getJLabelTime().setText("00:00"); //$NON-NLS-1$
			JOptionPane.showMessageDialog(_frame,
				LanguageManager.getString("game.over.message"), LanguageManager.getString("game.over.title"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private void close() {
		System.exit(0);
	}

	private void showRow(int row) {
		if (row < 0 && row >= _level.getSize())
			return;

		for (int i = 0; i < _level.getSize(); i++) {
			_level.show(i, row);
			_frame.getPicrossPanel().setStatus(i, row, _level.getStatus(i, row));
		}

		_frame.getPicrossPanel().repaint();
	}

	private void showColumn(int column) {
		if (column < 0 && column >= _level.getSize())
			return;

		for (int i = 0; i < _level.getSize(); i++) {
			_level.show(column, i);
			_frame.getPicrossPanel().setStatus(column, i, _level.getStatus(column, i));
		}

		_frame.getPicrossPanel().repaint();

	}

	private boolean setMarkedStatus(int x, int y) {
		if (_end || !_level.setMarkedStatus(x, y))
			return false;

		_frame.getPicrossPanel().setStatus(x, y, _level.getStatus(x, y));
		_frame.getPicrossPanel().repaint();

		return true;
	}

	private boolean setVisibleStatus(int x, int y) {
		if (_end || !_level.setVisibleStatus(x, y))
			return false;

		_frame.getPicrossPanel().setStatus(x, y, _level.getStatus(x, y));
		_frame.getPicrossPanel().repaint();

		if (_level.getStatus(x, y) == FieldStatus.FAILURE) {
			_failure++;

			_time -= GameRules.FAILURE_TIME[Math.min(_failure, GameRules.FAILURE_TIME.length) - 1] * (1000 / GameRules.TICK_SPEED);
		}

		if (_level.isSolved()) {
			endGame();
		}

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals(Actions.NEW_RANDOM)) {
			showNewRandomDialog();
		} else if (ae.getActionCommand().equals(Actions.NEW_GAME)) {
			showNewGameDialog();
		} else if (ae.getActionCommand().equals(Actions.LOAD)) {
			showLoadGameDialog();
		} else if (ae.getActionCommand().equals(Actions.EXIT)) {
			close();
		} else if (ae.getActionCommand().equals(Actions.TIMER)) {
			tick();
		} else if (ae.getActionCommand().equals(Actions.ABOUT)) {
			showAbout();
		}
	}

	private void showAbout() {
		AboutWindowController ctrl = new AboutWindowController(this);
		ctrl.showWindow(_frame);
	}

	private void showLoadGameDialog() {
		new LoadGameController(this).show(_frame);
	}

	private void showNewRandomDialog() {
		new RandomGameController(this).show(_frame);
	}

	private void showNewGameDialog() {
		startGame();
	}
}
