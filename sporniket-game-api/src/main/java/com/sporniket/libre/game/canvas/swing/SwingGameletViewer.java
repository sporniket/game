package com.sporniket.libre.game.canvas.swing;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sporniket.libre.game.canvas.CanvasException;
import com.sporniket.libre.game.canvas.descriptor.CanvasGameDescriptor;
import com.sporniket.libre.game.canvas.descriptor.CanvasGameDescriptorUtils;
import com.sporniket.libre.game.canvas.descriptor.CanvasSpecs;
import com.sporniket.libre.game.canvas.gamelet.CanvasGamelet;
import com.sporniket.libre.game.canvas.gamelet.CanvasGameletContext;
import com.sporniket.libre.game.canvas.gamelet.CanvasGameletControler;
import com.sporniket.libre.game.gamelet.GameletException;
import com.sporniket.libre.game.gamelet.events.Forward;
import com.sporniket.libre.io.Encoding;
import com.sporniket.libre.io.parser.properties.SyntaxErrorException;
import com.sporniket.libre.lang.url.UrlProviderException;

/**
 * Launcher for playing a gamelet based game.
 * 
 * @author dsporn
 *
 */
// FIXME obviously a first draft, full of shortcomings...
class SwingGameletViewer
{
	private static final String SPECIAL_NAME__SEQUENCER = "$Sequencer";

	/**
	 * Run a game only if it support a landscape qHD sized canvas.
	 * 
	 * @param descriptorUrl
	 *            url to the descriptor file.
	 * @throws UrlProviderException
	 *             when there is a problem.
	 * @throws IOException
	 *             when there is a problem.
	 * @throws SyntaxErrorException
	 *             when there is a problem.
	 * @throws CanvasException
	 *             when there is a problem.
	 * @throws GameletException
	 *             when there is a problem.
	 * @throws ClassNotFoundException
	 *             when there is a problem.
	 * @throws InstantiationException
	 *             when there is a problem.
	 * @throws IllegalAccessException
	 *             when there is a problem.
	 */
	public static void runLandscapeQuarterHd(String descriptorUrl) throws UrlProviderException, IOException, SyntaxErrorException,
			CanvasException, GameletException, ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		// load the descriptor
		CanvasGameDescriptor _descriptor = CanvasGameDescriptorUtils.load(descriptorUrl, Encoding.ISO_8859_1);
		// look for the qHD canvas specs
		CanvasSpecs _selectedCanvasSpecs = null;
		for (CanvasSpecs _spec : _descriptor.getCanvasSpecs())
		{
			if (_spec.getWidth() == 960 && _spec.getHeight() == 540)
			{
				_selectedCanvasSpecs = _spec;
				break;
			}
		}
		// if not found -> abort
		if (null == _selectedCanvasSpecs)
		{
			throw new IllegalArgumentException("Game does not support landscape quarter HD (960x540)");
		}
		// process descriptor base urls to replace {gdef} by the actual canvas specs prefix
		String _realBaseUrlForPictures = _descriptor.getBaseUrlSpecs().getBaseUrlForData() + "/"
				+ _descriptor.getBaseUrlSpecs().getBaseUrlForPictures().replace("{gdef}", _selectedCanvasSpecs.getPrefix());
		_descriptor.getBaseUrlSpecs().setBaseUrlForPictures(_realBaseUrlForPictures);
		// init the canvas manager (for now, the kind url list is not supported --> black offscreen)
		final BufferedImagesManager _canvasManager = new BufferedImagesManager(_selectedCanvasSpecs.getWidth(),
				_selectedCanvasSpecs.getHeight());
		for (String _spec : _descriptor.getCanvasManagerSpecs().getCanvasSpecs())
		{
			String _toParse = _spec.trim();
			int _posSep = _toParse.indexOf(":");
			if (0 > _posSep)
			{
				throw new SyntaxErrorException("canvas manager specs should follow 'name:...' pattern");
			}
			String _name = _toParse.substring(0, _posSep);
			_toParse = _toParse.substring(_posSep + 1);
			if (_toParse.startsWith("url:"))
			{
				String _url = _descriptor.getBaseUrlSpecs().getBaseUrlForPictures() + "/" + _toParse.substring("url:".length());
				CanvasUtils.createCanvasFromImage(_canvasManager, _name, _url);
			}
			else
			{
				// default or unsupported
				CanvasUtils.createBlackFilledCanvas(_canvasManager, _name);
			}
		}
		// create the game context, links to canvas manager.
		CanvasGameletContext<BufferedImage> _context = new CanvasGameletContext<BufferedImage>();
		_context.setCanvasManager(_canvasManager);
		// create the controler, links to game context
		final CanvasGameletControler<BufferedImage> _controler = new CanvasGameletControler<BufferedImage>();
		_controler.setContext(_context);
		_controler.setCanvasBufferingList(_descriptor.getCanvasManagerSpecs().getBufferingNames());

		// register the gamelets
		for (String _gameletSpec : _descriptor.getGamelets().getRegistry())
		{
			String _toParse = _gameletSpec.trim();
			int _posSep = _toParse.indexOf(":");
			if (0 > _posSep)
			{
				throw new SyntaxErrorException("Gamelet specs should follow 'name:classname' pattern");
			}
			String _name = _toParse.substring(0, _posSep);
			String _classname = _toParse.substring(_posSep + 1);
			@SuppressWarnings("unchecked")
			Class<? extends CanvasGamelet<BufferedImage>> _gameletClass = (Class<? extends CanvasGamelet<BufferedImage>>) Class
					.forName(_classname);
			CanvasGamelet<BufferedImage> _gamelet = _gameletClass.newInstance();
			_controler.registerGamelet(_name, _gamelet);
		}
		// setup the running sequence
		_controler.registerGamelet(SPECIAL_NAME__SEQUENCER, new GameletSequencer<BufferedImage>(_descriptor.getGamelets()
				.getSequence()));
		_controler.onForward(new Forward<CanvasGameletContext<BufferedImage>>(null, SPECIAL_NAME__SEQUENCER));

		// create the view, and watch for input events
		final CanvasView _view = new CanvasView();
		_view.setCanvasManager(_canvasManager);
		_controler.addUpdatedDisplayEventListener(_view);

		// swing invoke later init :

		// link the view to the canvas manager and the first offscreen in the buffering list.
		// create a main windowed frame, a button bar with future action (share, snapshots,...)

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				init__createAndShowGui();
			}

			private void init__createAndShowGui()
			{
				JFrame f = new JFrame("Canvas Demo");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.add(_view);
				f.pack();
				f.setVisible(true);
				f.setResizable(false);
			}
		});

		// run in a thread (like swing invokelater)
		// controler.run(since last time)
		// sleep 5ms
		new Runnable()
		{
			long _lastTime = System.currentTimeMillis();

			/**
			 * should wait at least this amount since #_lastTime before calling run.
			 */
			long _timeResolution = 10;

			/**
			 * the given time elapsed will not exceed _maxTimegap.
			 */
			long _maxTimegap = 25;

			/**
			 * the loop will sleep _sleepTime millisecond between each iteration.
			 */
			long _sleepTime = 4;

			@Override
			public void run()
			{
				while (_controler.isRunning())
				{
					long _now = System.currentTimeMillis();
					long _elapsed = _now - _lastTime;
					if (_elapsed > _maxTimegap)
					{
						_elapsed = _maxTimegap;
					}
					if (_elapsed > _timeResolution)
					{
						try
						{
							_lastTime = _now;
							_controler.run(_elapsed);
						}
						catch (GameletException _exception)
						{
							_exception.printStackTrace();
							break;
						}
					}
					try
					{
						Thread.sleep(_sleepTime);
					}
					catch (InterruptedException _exception)
					{
						_exception.printStackTrace();
						break;
					}
				}
				// Game is done.
				System.exit(0);
			}
		}.run();

	}
}