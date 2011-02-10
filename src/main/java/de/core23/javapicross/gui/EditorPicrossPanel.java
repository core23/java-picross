package de.core23.javapicross.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EditorPicrossPanel extends JPanel {
	private static final long serialVersionUID = -8028156967764845999L;

	private BufferedImage _bufferImage;

	private Graphics _bufferGraphics;

	private boolean[][] _grid;

	private int _blockSize;

	private int _blockLine;

	private int _columnCount = 0;

	private int _rowCount = 0;

	private int _gridWidth = 0;

	private int _gridHeight = 0;

	public EditorPicrossPanel(int blockSize, int blockLine) {
		setStyle(blockSize, blockLine);
	}

	public void setStyle(int blockSize, int blockLine) {
		_blockSize = blockSize;
		_blockLine = blockLine;

		initBuffer();
	}

	private void initBuffer() {
		if (_columnCount <= 0 || _rowCount <= 0)
			return;

		_gridWidth = _columnCount * (_blockSize + _blockLine) + _blockLine;
		_gridHeight = _rowCount * (_blockSize + _blockLine) + _blockLine;

		_bufferImage = new BufferedImage(_gridWidth, _gridHeight, BufferedImage.TYPE_INT_RGB);
		_bufferGraphics = _bufferImage.getGraphics();

		repaint();
	}

	public void setGridSize(int columnCount, int rowCount) {
		_columnCount = columnCount;
		_rowCount = rowCount;
		_grid = new boolean[_columnCount][_rowCount];

		initBuffer();
		clear();
		drawGrid();
	}

	private void clear() {
		_bufferGraphics.setColor(Style.BACKGROUND_COLOR);
		_bufferGraphics.fillRect(0, 0, _bufferImage.getWidth(), _bufferImage.getHeight());
	}

	private void drawGrid() {
		// Draw Grid
		if (_blockLine > 0) {
			_bufferGraphics.setColor(Style.GRID_COLOR);
			for (int i = 0; i <= _columnCount; i++)
				_bufferGraphics.fillRect(i * (_blockSize + _blockLine), 0, _blockLine, _gridHeight);
			for (int i = 0; i <= _rowCount; i++)
				_bufferGraphics.fillRect(0, i * (_blockSize + _blockLine), _gridWidth, _blockLine);

			_bufferGraphics.setColor(Style.GRID5_COLOR);
			for (int i = 0; i <= _columnCount; i += 5)
				_bufferGraphics.fillRect(i * (_blockSize + _blockLine), 0, _blockLine, _gridHeight);
			for (int i = 0; i <= _rowCount; i += 5)
				_bufferGraphics.fillRect(0, i * (_blockSize + _blockLine), _gridWidth, _blockLine);
		}

		repaint();
	}

	public void drawAll() {
		drawGrid();

		for (int x = 0; x < _columnCount; x++) {
			for (int y = 0; y < _rowCount; y++) {
				setStatus(x, y, _grid[x][y]);
			}
		}

		repaint();
	}

	public void setStatus(int x, int y, boolean block) {
		if (x < 0 || y < 0 || x >= _columnCount || y >= _rowCount)
			return;

		if (block)
			_bufferGraphics.setColor(Style.BLOCK_COLOR);
		else
			_bufferGraphics.setColor(Style.BACKGROUND_COLOR);

		_bufferGraphics.fillRect(x * (_blockSize + _blockLine) + _blockLine, y * (_blockSize + _blockLine) + _blockLine, _blockSize, _blockSize);

		_grid[x][y] = block;
	}

	public Point translatePoint(Point p) {
		if (_bufferImage == null)
			return null;

		p.x = p.x - (getWidth() - _bufferImage.getWidth()) / 2;
		p.y = p.y - (getHeight() - _bufferImage.getHeight()) / 2;
		
		if (p.x % (_blockSize + _blockLine) <= _blockLine || p.y % (_blockSize + _blockLine) <= _blockLine)
			p.setLocation(-1, -1);
		else {
			p.x /= (_blockSize + _blockLine);
			p.y /= (_blockSize + _blockLine);
		}
		return p;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Style.BACKGROUND_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (_bufferImage != null)
			g.drawImage(_bufferImage, (getWidth() - _bufferImage.getWidth()) / 2, (getHeight() - _bufferImage.getHeight()) / 2, this);
	}

	public int getPaintWidth() {
		return _gridWidth;
	}

	public int getPaintHeight() {
		return _gridHeight;
	}
}
