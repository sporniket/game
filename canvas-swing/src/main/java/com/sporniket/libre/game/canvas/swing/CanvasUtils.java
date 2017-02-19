/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.Color;

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
		canvasManager.setFiller(_result, Color.BLACK);
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
		canvasManager.setFiller(_result, Color.BLACK);
		canvasManager.regenerate(_result);
		return _result ;
	}
	
	public static int createCanvasFromImage(BufferedImagesManager canvasManager, String guid, String imageUrl) throws CanvasException
	{
		ImageReloader _reloader = new ImageReloader(imageUrl);
		int _result = canvasManager.createCanvas(guid, _reloader.getPreferredWidth(), _reloader.getPreferredHeight());
		canvasManager.setFiller(_result, _reloader);
		canvasManager.regenerate(_result);
		return _result ;
	}
}
