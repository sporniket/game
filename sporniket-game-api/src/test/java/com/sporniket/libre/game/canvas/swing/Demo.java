/**
 * 
 */
package com.sporniket.libre.game.canvas.swing;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sporniket.libre.game.canvas.CanvasException;

/**
 * Demo application of a canvas.
 * 
 * @author dsporn
 *
 */
public class Demo
{

	private static final String CANVAS_GUID = "main";

	/**
	 * Canvas manager, for a qHD landscape screen.
	 */
	private final BufferedImagesManager myCanvasManager = new BufferedImagesManager(960, 540);

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			new Demo().init();
		}
		catch (CanvasException _exception)
		{
			_exception.printStackTrace();
		}

	}

	private void init() throws CanvasException
	{
		CanvasUtils.createBlackFilledCanvas(myCanvasManager, CANVAS_GUID);

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				init__createAndShowGui();
			}
		});
	}

	private void init__createAndShowGui()
	{
		CanvasView _view = new CanvasView();
		_view.setCanvasManager(myCanvasManager);
		_view.setCanvasId(myCanvasManager.getCanvasId(CANVAS_GUID));

		JFrame f = new JFrame("Canvas Demo");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(_view);
		f.pack();
		f.setVisible(true);
	}

}
