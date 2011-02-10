package de.core23.javapicross.model;


public class LevelFile {
	private String _name;

	private String _filename;


	public LevelFile(String filename, String name) {
		_filename = filename;
		_name = name;
	}

	public String getName() {
		return _name;
	}

	public String getFilename() {
		return _filename;
	}

	@Override
	public String toString() {
		return getName();
	}
}