/**
 *
 */
package com.sporniket.libre.game.canvas.swing;

import java.util.List;

import com.sporniket.libre.game.canvas.CanvasException;
import com.sporniket.libre.game.canvas.descriptor.CanvasManagerSpecEntry;
import com.sporniket.libre.game.canvas.descriptor.CanvasManagerSpecEntryOffscreen;
import com.sporniket.libre.game.canvas.descriptor.CanvasManagerSpecEntryUrl;

/**
 * Utility class.
 *
 * @author dsporn
 *
 */
public abstract class CanvasUtils
{
	private static int createCanvasFromImageUrl(BufferedImagesManager canvasManager, CanvasManagerSpecEntryUrl specs,
			String baseUrlForImage) throws CanvasException
	{
		final ImageReloader _reloader = new ImageReloader(baseUrlForImage + specs.getUrl());
		final int _result = canvasManager.createCanvas(specs.getName(), _reloader.getPreferredWidth(),
				_reloader.getPreferredHeight());
		canvasManager.setFiller(_result, _reloader);
		canvasManager.regenerate(_result);
		return _result;
	}

	private static int createCanvasFromOffscreenSpecification(BufferedImagesManager canvasManager,
			CanvasManagerSpecEntryOffscreen specs) throws CanvasException
	{
		final int _result = canvasManager.createCanvas(specs.getName(), specs.getWidth(), specs.getHeight());
		canvasManager.setFiller(_result, specs.getColor());
		canvasManager.regenerate(_result);
		return _result;
	}

	public static void populateCanvasManagerFromSpecifications(final BufferedImagesManager canvasManager,
			List<CanvasManagerSpecEntry> specifications, String baseUrlForImage) throws CanvasException
	{
		for (final CanvasManagerSpecEntry _entry : specifications)
		{
			if (_entry instanceof CanvasManagerSpecEntryUrl)
			{
				createCanvasFromImageUrl(canvasManager, (CanvasManagerSpecEntryUrl) _entry, baseUrlForImage);
			}
			else
			{
				createCanvasFromOffscreenSpecification(canvasManager, (CanvasManagerSpecEntryOffscreen) _entry);
			}
		}
	}

}
