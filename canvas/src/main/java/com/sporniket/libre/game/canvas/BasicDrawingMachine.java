/**
 * 
 */
package com.sporniket.libre.game.canvas;

import java.awt.Color;
import java.awt.Font;

/**
 * Define the instruction set to provide basic drawing capabilities (shapes and text).
 * 
 * <p>
 * It manage only one color that is used for outlines and for filling shapes.
 * </p>
 * 
 * <p>
 * There is a transparent mode. When it is disabled (default mode), pixels covered by a new drawing are replaced. When it is
 * enabled, pixels covered by a new drawing using transparent color are filtered.
 * 
 * @author dsporn
 *
 */
public interface BasicDrawingMachine
{
	/**
	 * Disable the transparent mode.
	 * 
	 * @category BasicDrawingMachine DrawingMode management
	 */
	void disableTransparentMode();

	/**
	 * Draws a box outline.
	 * 
	 * @param box
	 *            the box description.
	 * 
	 * @category BasicDrawingMachine Drawing
	 */
	void drawBox(Box box);

	/**
	 * Draws a filled box.
	 * 
	 * @param box
	 *            the box description.
	 * 
	 * @category BasicDrawingMachine Drawing
	 */
	void drawFilledBox(Box box);

	/**
	 * Draw a line from one point to another.
	 * 
	 * @param from
	 *            start of the line.
	 * @param to
	 *            end of the line.
	 * 
	 * @category BasicDrawingMachine Drawing
	 */
	void drawLine(Point from, Point to);

	/**
	 * Draw a text.
	 * 
	 * @param text
	 *            the text to draw (single line of text).
	 * @param at
	 *            top left corner of the resulting text box.
	 * 
	 * @category BasicDrawingMachine Drawing
	 */
	void drawText(String text, Point at);

	/**
	 * Enable the transparent mode.
	 * 
	 * @category BasicDrawingMachine DrawingMode management
	 */
	void enableTransparentMode();

	/**
	 * @return the current color.
	 * @category BasicDrawingMachine Color management
	 */
	Color getColor();

	/**
	 * Get the font to use to draw text.
	 * 
	 * @return the current font to draw text.
	 * @category BasicDrawingMachine Font management
	 */
	Font getFont();

	/**
	 * @return the <code>true</code> when the transparent mode is enabled.
	 * @category BasicDrawingMachine DrawingMode management
	 */
	boolean isTransparentModeEnabled();

	/**
	 * @param color
	 *            the color to use.
	 * @category BasicDrawingMachine Color management
	 */
	void setColor(Color color);

	/**
	 * Set the font to use to draw text.
	 * 
	 * @param font
	 *            the font to use.
	 * @category BasicDrawingMachine Font management
	 */
	void setFont(Font font);

}
