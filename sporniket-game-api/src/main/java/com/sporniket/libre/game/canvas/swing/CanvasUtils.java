/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import com.sporniket.libre.game.canvas.CanvasCallback;
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
	
	public static int createCanvasFromImage(BufferedImagesManager canvasManager, String guid, String imageUrl) throws CanvasException
	{
		ImageReloader _reloader = new ImageReloader(imageUrl);
		Dimension _imageSize = _reloader.getImageDimension();
		int _result = canvasManager.createCanvas(guid, _imageSize.width, _imageSize.height);
		CanvasCallback<BufferedImage> _callback = _reloader.getImageReloader();
		canvasManager.attachRegenerator(_result, _callback);
		canvasManager.regenerate(_result);
		return _result ;
	}
}
