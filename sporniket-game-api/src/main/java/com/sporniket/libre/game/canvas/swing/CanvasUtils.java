/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import com.sporniket.libre.game.canvas.CanvasException;

/**
 * Utility class.
 * 
 * @author dsporn
 *
 */
public abstract class CanvasUtils
{
	/**
	 * Create a screen sized canvas filled in black.
	 * @param canvasManager the canvas manager.
	 * @param guid the canvas guid.
	 * @return the canvas id.
	 * @throws CanvasException when there is a problem.
	 */
	public static int createBlackFilledCanvas(BufferedImagesManager canvasManager, String guid) throws CanvasException
	{
		int _result = canvasManager.createCanvas(guid);
		canvasManager.attachRegenerator(_result, RegeneratorFillers.getBlackFiller());
		canvasManager.regenerate(_result);
		return _result ;
	}

	/**
	 * Create a screen sized canvas filled in black.
	 * @param canvasManager the canvas manager.
	 * @param guid the canvas guid.
	 * @return the canvas id.
	 * @throws CanvasException when there is a problem.
	 */
	public static int createBlackFilledCanvas(BufferedImagesManager canvasManager, String guid, int width, int height) throws CanvasException
	{
		int _result = canvasManager.createCanvas(guid, width, height);
		canvasManager.attachRegenerator(_result, RegeneratorFillers.getBlackFiller());
		canvasManager.regenerate(_result);
		return _result ;
	}
}
