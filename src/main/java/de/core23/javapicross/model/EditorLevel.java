package de.core23.javapicross.model;


public class EditorLevel {
	private int _number;

	private boolean[][] _block;

	private int _size;

	public EditorLevel(int size) {
		_size = size;

		_block = new boolean[_size][_size];
	}

	public EditorLevel(boolean[][] block) {
		_size = block.length;
		_block = block;
	}

	public int getSize() {
		return _size;
	}

	public int getNumber() {
		return _number;
	}

	public boolean[][] getBlock() {
		return _block;
	}

	@Override
	public String toString() {
		return String.valueOf(getNumber() + 1);
	}

	public boolean getBlock(int x, int y) {
		return _block[x][y];
	}

	public boolean isValidBlock(int x, int y) {
		return !(x<0 || y<0 || x>=_size || y>=_size);
	}

	public void setBlock(int x, int y, boolean block) {
		_block[x][y] = block;
	}
}
