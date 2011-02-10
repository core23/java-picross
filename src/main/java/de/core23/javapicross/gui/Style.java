package de.core23.javapicross.gui;

import java.awt.Color;
import java.awt.Font;

public interface Style {
	public static final int LINE_SIZE = 2;
	
	public static final int BLOCK_PREVIEW_SIZE = 7;

	public static final int BLOCK_SIZE = 16;

	public static final Color BACKGROUND_COLOR = Color.WHITE;

	public static final Color HIGHLIGHT_COLOR = Color.YELLOW.brighter();

	public static final Color GRID_COLOR = Color.LIGHT_GRAY;

	public static final Color GRID5_COLOR = Color.DARK_GRAY;

	public static final Color MARKED_COLOR = Color.DARK_GRAY;

	public static final Color BLOCK_COLOR = Color.DARK_GRAY;

	public static final Color FAILURE_COLOR = Color.RED.brighter();

	public static final Color TEXT_COLOR = Color.BLACK;

	public static final Font FONT = new Font("Helvetica", Font.BOLD, 12); //$NON-NLS-1$
}
