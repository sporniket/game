/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import java.io.IOException;

import com.sporniket.libre.game.canvas.CanvasException;
import com.sporniket.libre.game.gamelet.GameletException;
import com.sporniket.libre.io.parser.properties.SyntaxErrorException;
import com.sporniket.libre.lang.url.UrlProviderException;

/**
 * Demo application of a canvas.
 * 
 * @author dsporn
 *
 */
public class Demo
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			SwingGameletViewer.runLandscapeQuarterHd("classpath:demo/game/game.properties");
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UrlProviderException | IOException
				| SyntaxErrorException | CanvasException | GameletException _exception)
		{
			_exception.printStackTrace();
		}

	}

}
