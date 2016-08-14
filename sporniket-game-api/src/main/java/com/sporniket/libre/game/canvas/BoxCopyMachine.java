/**
 * 
 */
package com.sporniket.libre.game.canvas;

/**
 * Define the service allowing to copy a box from a canvas to another one.
 * 
 * Canvas are arbitrarily identified by an integer, usually an index.
 * 
 * @author dsporn
 *
 */
public interface BoxCopyMachine
{
	/**
	 * Copy a bloc from a canvas to another one.
	 * 
	 * @param canvasIdFrom
	 *            id of the source canvas.
	 * @param boxSpecs
	 *            definition of the bloc to copy in the source canvas.
	 * @param canvasIdTo
	 *            id of the destination canvas.
	 * @param position
	 *            position of the top-left corner in the destination canvas.
	 */
	void copy(int canvasIdFrom, Box boxSpecs, int canvasIdTo, Point position);
}
