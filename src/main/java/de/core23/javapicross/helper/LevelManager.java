package de.core23.javapicross.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.core23.javapicross.model.Level;
import de.core23.javapicross.model.LevelFile;


public class LevelManager {
	private static final String PATH = "level/"; //$NON-NLS-1$

	private static final String EXTENSION = ".lvl"; //$NON-NLS-1$

	private static final String FILENAME_EASY = "1.lvl"; //$NON-NLS-1$

	private String _name = ""; //$NON-NLS-1$

	private String _filename = FILENAME_EASY;

	private ArrayList<Level> _levels;

	public LevelManager() {
		_levels = new ArrayList<Level>();

		File path = new File(PATH);
		if (!path.exists())
			path.mkdirs();

		readFile();
	}

	private void readFile() {
		_levels.clear();

		// Read All
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(PATH + _filename);
			br = new BufferedReader(fr);

			_name = br.readLine();

			String line;
			while ((line = br.readLine()) != null) {
				Level level = getLevel(line);
				if (level != null)
					_levels.add(level);
			}

		} catch (FileNotFoundException e1) {
		} catch (IOException e) {
		} finally {
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
				}
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}
		}
	}

	private void writeFile() throws LevelException {
		// Write
		FileWriter writer = null;
		try {
			writer = new FileWriter(PATH + _filename);

			writer.write(_name + '\n');

			for (Level cLevel : _levels)
				writer.write(getString(cLevel) + '\n');

		} catch (IOException e) {
			throw new LevelException(LanguageManager.getString("manage.save.error")); //$NON-NLS-1$
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
				}
		}
	}

	public String getName() {
		return _name;
	}

	public ArrayList<Level> getLevels() {
		return _levels;
	}

	public void setFilename(String filename) {
		if (_filename.equals(filename) || filename == null)
			return;
		if (filename.endsWith(EXTENSION))
			return;
		_filename = filename;

		readFile();
	}

	public String getFilename() {
		return _filename;
	}

	public int getSize() {
		return _levels.size();

	}

	public Level getLevel(int number) {
		if (number < 0 || number >= _levels.size())
			return null;
		return _levels.get(number);
	}

	public void setLevel(int number, Level level) throws LevelException {
		if (number < 0 || number > _levels.size())
			return;

		// Set
		if (number == _levels.size())
			_levels.add(level);
		else
			_levels.set(number, level);

		writeFile();
	}

	private Level getLevel(int number, String string) {
		String levelString = expand(string);
		int size = (int) Math.sqrt(levelString.length());

		if (size % 5 != 0)
			return null;

		boolean[][] map = new boolean[size][size];
		for (int i = 0; i < levelString.length(); i++) {
			int x = i % size;
			int y = i / size;
			map[x][y] = levelString.charAt(i) == 'a';
		}
		return new Level(number, map);
	}

	private Level getLevel(String string) {
		String levelString = expand(string);
		int size = (int) Math.sqrt(levelString.length());

		if (size % 5 != 0)
			return null;

		boolean[][] map = new boolean[size][size];
		for (int i = 0; i < levelString.length(); i++) {
			int x = i % size;
			int y = i / size;
			map[x][y] = levelString.charAt(i) == 'a';
		}
		return new Level(map);
	}

	private String getString(Level level) {
		boolean[][] block = level.getBlock();
		int size = level.getSize();

		String text = ""; //$NON-NLS-1$
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				text += block[x][y] ? 'a' : 'b';
			}
		}
		return compress(text);
	}

	private String expand(String text) {
		String expanded = ""; //$NON-NLS-1$

		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			Character c = text.charAt(i);

			if (c == 'a' || c == 'b') {
				if (count == 0)
					expanded += c;
				else
					for (int j = 0; j < count; j++)
						expanded += c;
				count = 0;
			} else if (c > 47 && c < 58) {
				count = count * 10 + Integer.valueOf(c + ""); //$NON-NLS-1$
			}
		}
		return expanded;
	}

	private String compress(String text) {
		String compressed = ""; //$NON-NLS-1$

		String last = ""; //$NON-NLS-1$
		int count = 0;
		for (String str : text.split("")) { //$NON-NLS-1$
			if (!str.equals(last)) {
				if (count == 1)
					compressed += last;
				else if (count > 1)
					compressed += count + last;
				count = 0;
			}
			last = str;
			count++;
		}

		if (count == 1)
			compressed += last;
		else if (count > 1)
			compressed += count + last;

		return compressed;
	}

	public static Level getRandom(int size) {
		Random rnd = new Random();
		boolean solved = true;

		boolean[][] map = new boolean[size][size];
		do {
			for (int i = 0; i < size * size; i++) {
				int x = rnd.nextInt(size);
				int y = rnd.nextInt(size);
				map[x][y] = !map[x][y];
			}

			for (int x = 0; x < size; x++) {
				for (int y = 0; y < size; y++) {
					if (map[x][y])
						solved = false;
				}
			}
		} while (solved);

		return new Level(-1, map);
	}

	public void createFilename(String title) throws LevelException {
		int i = 1;
		while (new File(PATH + i).exists())
			i++;

		_name = title;
		_filename = String.valueOf(i) + EXTENSION;
		_levels.clear();

		writeFile();
	}

	public List<LevelFile> getLevelFiles() {
		List<LevelFile> list = new ArrayList<LevelFile>();

		File levelDir = new File(PATH);
		for (File file : levelDir.listFiles()) {
			if (!file.getName().endsWith(EXTENSION))
				continue;
			setFilename(file.getName());
			list.add(new LevelFile(file.getName(), getName()));
		}
		return list;
	}
}
