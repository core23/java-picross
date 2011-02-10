package de.core23.javapicross.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import de.core23.javapicross.model.FieldStatus;


public class PicrossPanel extends JPanel {
	private static final long serialVersionUID = -8028156967764845999L;

	private BufferedImage _bufferImage;

	private Graphics _bufferGraphics;

	private Font _font;

	private FontMetrics _fontMetrics;

	private FieldStatus[][] _grid;

	private int[][] _columnNumbers = new int[0][0];

	private int[][] _rowNumbers = new int[0][0];

	private int _paddingLeft = 0;

	private int _paddingTop = 0;

	private int _gridWidth = 0;

	private int _gridHeight = 0;

	private int _hoverX = -1;

	private int _hoverY = -1;

	public PicrossPanel() {
		initBuffer();
	}

	private void initBuffer() {
		if (_columnNumbers.length <= 0 || _rowNumbers.length <= 0)
			return;

		_paddingLeft = (int) Math.ceil(_columnNumbers.length * 0.5) * Style.BLOCK_SIZE;
		_paddingTop = (int) Math.ceil(_rowNumbers.length * 0.5) * Style.BLOCK_SIZE;
		_gridWidth = _columnNumbers.length * (Style.BLOCK_SIZE + Style.LINE_SIZE) + Style.LINE_SIZE;
		_gridHeight = _rowNumbers.length * (Style.BLOCK_SIZE + Style.LINE_SIZE) + Style.LINE_SIZE;

		_bufferImage = new BufferedImage(_paddingLeft + _gridWidth, _paddingTop + _gridHeight, BufferedImage.TYPE_INT_RGB);
		_bufferGraphics = _bufferImage.getGraphics();

		setMinimumSize(new Dimension(getPaintWidth(), getPaintHeight()));

		_font = new Font("Arial", Font.BOLD, 11); //$NON-NLS-1$
		_bufferGraphics.setFont(_font);
		_fontMetrics = _bufferGraphics.getFontMetrics();

		for (int x = 0; x < _columnNumbers.length; x++) {
			for (int y = 0; y < _rowNumbers.length; y++) {
				_grid[x][y] = FieldStatus.HIDDEN;
			}
		}

		repaint();
	}

	public void setGrid(int[][] columnNumbers, int[][] rowNumbers) {
		_columnNumbers = columnNumbers;
		_rowNumbers = rowNumbers;
		_grid = new FieldStatus[_columnNumbers.length][_rowNumbers.length];

		initBuffer();
		clear();
		drawGrid();
	}

	private void clear() {
		_bufferGraphics.setColor(Style.BACKGROUND_COLOR);
		_bufferGraphics.fillRect(0, 0, _bufferImage.getWidth(), _bufferImage.getHeight());
	}

	private void drawGrid() {
		// Draw Preview
		_bufferGraphics.setColor(Style.GRID_COLOR);
		_bufferGraphics.drawRect(0, 0, Style.BLOCK_PREVIEW_SIZE * _rowNumbers.length + 1, Style.BLOCK_PREVIEW_SIZE * _columnNumbers.length + 1);

		// Draw Grid
		_bufferGraphics.setColor(Style.GRID_COLOR);
		for (int i = 0; i <= _columnNumbers.length; i++)
			_bufferGraphics.fillRect(_paddingLeft + i * (Style.BLOCK_SIZE + Style.LINE_SIZE), _paddingTop, Style.LINE_SIZE, _paddingTop + _gridHeight);
		for (int i = 0; i <= _rowNumbers.length; i++)
			_bufferGraphics.fillRect(_paddingLeft, _paddingTop + i * (Style.BLOCK_SIZE + Style.LINE_SIZE), _paddingLeft + _gridWidth, Style.LINE_SIZE);

		_bufferGraphics.setColor(Style.GRID5_COLOR);
		for (int i = 0; i <= _columnNumbers.length; i += 5)
			_bufferGraphics.fillRect(_paddingLeft + i * (Style.BLOCK_SIZE + Style.LINE_SIZE), _paddingTop, Style.LINE_SIZE, _paddingTop + _gridHeight);
		for (int i = 0; i <= _rowNumbers.length; i += 5)
			_bufferGraphics.fillRect(_paddingLeft, _paddingTop + i * (Style.BLOCK_SIZE + Style.LINE_SIZE), _paddingLeft + _gridWidth, Style.LINE_SIZE);

		// Draw Numbers
		_bufferGraphics.setColor(Style.TEXT_COLOR);
		int strWidth = 0;
		int strHeight = (_fontMetrics.getAscent() + (Style.BLOCK_SIZE - (_fontMetrics.getAscent() + _fontMetrics.getDescent())) / 2);

		for (int i = 0; i < _columnNumbers.length; i++) {
			int count = _columnNumbers[i].length;
			for (int k = 0; k < count; k++) {
				strWidth = _fontMetrics.stringWidth(String.valueOf(_columnNumbers[i][k]));

				_bufferGraphics.drawString(String.valueOf(_columnNumbers[i][k]), _paddingLeft + i * (Style.BLOCK_SIZE + Style.LINE_SIZE)
					+ (Style.BLOCK_SIZE - strWidth) / 2 + Style.LINE_SIZE, _paddingTop - (count - k - 1) * Style.BLOCK_SIZE - (Style.BLOCK_SIZE - strHeight)
					/ 2);
			}
		}

		for (int i = 0; i < _rowNumbers.length; i++) {
			int count = _rowNumbers[i].length;
			for (int k = 0; k < count; k++) {
				strWidth = _fontMetrics.stringWidth(String.valueOf(_rowNumbers[i][k]));

				_bufferGraphics.drawString(String.valueOf(_rowNumbers[i][k]), _paddingLeft - (count - k - 1) * Style.BLOCK_SIZE - (Style.BLOCK_SIZE + strWidth)
					/ 2, _paddingTop + i * (Style.BLOCK_SIZE + Style.LINE_SIZE) + (Style.BLOCK_SIZE + strHeight) / 2 + Style.LINE_SIZE);
			}
		}

		repaint();
	}

	public void drawAll() {
		drawGrid();

		for (int x = 0; x < _columnNumbers.length; x++) {
			for (int y = 0; y < _rowNumbers.length; y++) {
				setStatus(x, y, _grid[x][y]);
			}
		}

		repaint();
	}

	public void hover(int hoverX, int hoverY) {
		if (hoverX < 0 || hoverY < 0 || hoverX >= _columnNumbers.length || hoverY >= _rowNumbers.length) {
			hoverX = -1;
			hoverY = -1;
		}

		// Same Highlight
		if (hoverX == _hoverX && hoverY == _hoverY)
			return;

		// Clear Highlight
		hightlightColumn(_hoverX, Style.BACKGROUND_COLOR);
		hightlightRow(_hoverY, Style.BACKGROUND_COLOR);

		// New Highlight
		_hoverX = hoverX;
		_hoverY = hoverY;

		hightlightColumn(_hoverX, Style.HIGHLIGHT_COLOR);
		hightlightRow(_hoverY, Style.HIGHLIGHT_COLOR);

		// Redraw Grid
		drawAll();
	}

	public void hightlightColumn(int col, Color color) {
		if (col < 0 || col >= _columnNumbers.length)
			return;

		_bufferGraphics.setColor(color);

		_bufferGraphics.setColor(color);
		_bufferGraphics.fillRect(_paddingLeft + Style.LINE_SIZE + (Style.LINE_SIZE + Style.BLOCK_SIZE) * col, _paddingTop, (Style.BLOCK_SIZE),
			(Style.LINE_SIZE + Style.BLOCK_SIZE) * _columnNumbers.length);
	}

	public void hightlightRow(int row, Color color) {
		if (row < 0 || row >= _rowNumbers.length)
			return;

		_bufferGraphics.setColor(color);
		_bufferGraphics.fillRect(_paddingLeft, _paddingTop + Style.LINE_SIZE + (Style.LINE_SIZE + Style.BLOCK_SIZE) * row, (Style.LINE_SIZE + Style.BLOCK_SIZE)
			* _columnNumbers.length, (Style.BLOCK_SIZE));
	}

	public void setStatus(int x, int y, FieldStatus status) {
		if (x < 0 || y < 0 || x >= _columnNumbers.length || y >= _rowNumbers.length)
			return;

		switch (status) {
			case BLOCK:
				_bufferGraphics.setColor(Style.BLOCK_COLOR);
				_bufferGraphics.fillRect(_paddingLeft + x * (Style.BLOCK_SIZE + Style.LINE_SIZE) + Style.LINE_SIZE, _paddingTop + y
					* (Style.BLOCK_SIZE + Style.LINE_SIZE) + Style.LINE_SIZE, Style.BLOCK_SIZE, Style.BLOCK_SIZE);

				_bufferGraphics
					.fillRect(1 + x * Style.BLOCK_PREVIEW_SIZE, 1 + y * Style.BLOCK_PREVIEW_SIZE, Style.BLOCK_PREVIEW_SIZE, Style.BLOCK_PREVIEW_SIZE);
				break;

			case FAILURE:
				_bufferGraphics.setColor(Style.FAILURE_COLOR);
				drawX(x, y);
				break;

			case MARKED:
				_bufferGraphics.setColor(Style.MARKED_COLOR);
				drawX(x, y);
				break;
		}

		_grid[x][y] = status;
	}

	private void drawX(int x, int y) {
		for (int i = 0; i < 2; i++) {
			_bufferGraphics.drawLine(_paddingLeft + x * (Style.BLOCK_SIZE + Style.LINE_SIZE) + Style.LINE_SIZE, _paddingTop + y
				* (Style.BLOCK_SIZE + Style.LINE_SIZE) + Style.LINE_SIZE + i, _paddingLeft + x * (Style.BLOCK_SIZE + Style.LINE_SIZE) + Style.LINE_SIZE
				+ Style.BLOCK_SIZE - 1 - i, _paddingTop + y * (Style.BLOCK_SIZE + Style.LINE_SIZE) + Style.LINE_SIZE + Style.BLOCK_SIZE - 1);

			_bufferGraphics.drawLine(_paddingLeft + x * (Style.BLOCK_SIZE + Style.LINE_SIZE) + Style.LINE_SIZE + Style.BLOCK_SIZE - 1, _paddingTop + y
				* (Style.BLOCK_SIZE + Style.LINE_SIZE) + Style.LINE_SIZE + i, _paddingLeft + x * (Style.BLOCK_SIZE + Style.LINE_SIZE) + Style.LINE_SIZE + i,
				_paddingTop + y * (Style.BLOCK_SIZE + Style.LINE_SIZE) + Style.LINE_SIZE + Style.BLOCK_SIZE - 1);
		}
	}

	public Point translatePoint(Point p) {
		if (_bufferImage == null)
			return new Point(-1, -1);

		p.x = p.x - _paddingLeft - (getWidth() - _bufferImage.getWidth()) / 2;
		p.y = p.y - _paddingTop - (getHeight() - _bufferImage.getHeight()) / 2;

		if (p.x % (Style.BLOCK_SIZE + Style.LINE_SIZE) <= Style.LINE_SIZE || p.y % (Style.BLOCK_SIZE + Style.LINE_SIZE) <= Style.LINE_SIZE)
			p.setLocation(-1, -1);
		else {
			p.x /= (Style.BLOCK_SIZE + Style.LINE_SIZE);
			p.y /= (Style.BLOCK_SIZE + Style.LINE_SIZE);
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
		return _paddingLeft + _gridWidth;
	}

	public int getPaintHeight() {
		return _paddingTop + _gridHeight;
	}
}
