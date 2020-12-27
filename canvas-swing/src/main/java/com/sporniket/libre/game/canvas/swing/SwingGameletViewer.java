package com.sporniket.libre.game.canvas.swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

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
public class SwingGameletViewer
{
	private static KeyListener DEBUG_KEY_LISTENER = new KeyListener()
	{
		
		@Override
		public void keyTyped(KeyEvent e)
		{
			String _prompt = "keyTyped   :";
			debugKey(_prompt, e);
		}

		/**
		 * @param prompt
		 * @param e
		 */
		private void debugKey(String prompt, KeyEvent e)
		{
			long _physicalKey = getPhysicalKey(e);
			System.out.println(prompt+e.getKeyCode()+ "\t "+_physicalKey + "\t "+Long.toHexString(_physicalKey));
		}
		
		@Override
		public void keyReleased(KeyEvent e)
		{
			String _prompt = "keyReleased:";
			debugKey(_prompt, e);
			
		}
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			String _prompt = "keyPressed :";
			debugKey(_prompt, e);
			
		}
		

		private long getPhysicalKey(KeyEvent e)
		{
			Long _result = getPrivateFieldValueAsLong(e, "scancode");
			if (null == _result || 0 == _result)
			{
				_result = getPrivateFieldValueAsLong(e, "rawCode");
			}
			return _result;
		}

		private Long getPrivateFieldValueAsLong(final KeyEvent holder, String fieldName)
		{
			try
			{
				Field field = KeyEvent.class.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field.getLong(holder);
			}
			catch (NoSuchFieldException | SecurityException | IllegalAccessException __exception)
			{
				__exception.printStackTrace();
				return null;
			}
		}
	};
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
		for (final CanvasSpecs _spec : _descriptor.getCanvasSpecs())
		{
			if ((_spec.getWidth() == 960) && (_spec.getHeight() == 540))
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
		// find the graphical definition
		final int _graphicalDefinition = _descriptor.getGraphicalDefinitionsSpecs().getGraphicalDefinition(_selectedCanvasSpecs);
		// apply the values binded to the graphical definition
		final Map<String, String> _values = _descriptor.getGraphicalDefinitionsSpecs().getDataAsString(_graphicalDefinition);
		_descriptor = CanvasGameDescriptorUtils.applyValues(_descriptor, _values);

		// init the canvas manager
		final BufferedImagesManager _canvasManager = new BufferedImagesManager(_selectedCanvasSpecs.getWidth(),
				_selectedCanvasSpecs.getHeight());
		String _baseUrlForImage = _descriptor.getBaseUrlSpecs().getBaseUrlForData() + "/"
				+ _descriptor.getBaseUrlSpecs().getBaseUrlForPictures() + "/";
		CanvasUtils.populateCanvasManagerFromSpecifications(_canvasManager,
				_descriptor.getCanvasManagerSpecs().getEntries(_selectedCanvasSpecs), _baseUrlForImage);

		// create the game context, links to canvas manager.
		final CanvasGameletContext<BufferedImage> _context = new CanvasGameletContext<>();
		_context.setCanvasManager(_canvasManager);
		_context.getData().putAll(_descriptor.getGraphicalDefinitionsSpecs().getData(_graphicalDefinition));

		// create the controler, links to game context
		final CanvasGameletControler<BufferedImage> _controler = new CanvasGameletControler<>();
		_controler.setContext(_context);
		_controler.setCanvasBufferingList(_descriptor.getCanvasManagerSpecs().getBufferingNames());

		// register the gamelets
		for (final String _gameletSpec : _descriptor.getGamelets().getRegistry())
		{
			final String _toParse = _gameletSpec.trim();
			final int _posSep = _toParse.indexOf(":");
			if (0 > _posSep)
			{
				throw new SyntaxErrorException("Gamelet specs should follow 'name:classname' pattern");
			}
			final String _name = _toParse.substring(0, _posSep);
			final String _classname = _toParse.substring(_posSep + 1);
			@SuppressWarnings("unchecked")
			final Class<? extends CanvasGamelet<BufferedImage>> _gameletClass = (Class<? extends CanvasGamelet<BufferedImage>>) Class
					.forName(_classname);
			final CanvasGamelet<BufferedImage> _gamelet = _gameletClass.newInstance();
			_controler.registerGamelet(_name, _gamelet);
		}

		// setup the running sequence
		_controler.registerGamelet(SPECIAL_NAME__SEQUENCER,
				new GameletSequencer<BufferedImage>(_descriptor.getGamelets().getSequence()));
		_controler.onForward(new Forward<CanvasGameletContext<BufferedImage>>(null, SPECIAL_NAME__SEQUENCER));

		// create the view, and watch for input events
		final CanvasView _view = new CanvasView();
		_view.setCanvasManager(_canvasManager);

		// watch for mouse event from the view
		final MouseInputTranslator _mouseInputTranslator = new MouseInputTranslator();
		_mouseInputTranslator.addListener(_controler.getInputProxy());

		_view.addMouseListener(_mouseInputTranslator);
		_view.addMouseMotionListener(_mouseInputTranslator);
		_view.addMouseWheelListener(_mouseInputTranslator);
		
		// watch for keyboard event from the view
		final KeyboardInputTranslator _keyboardInputTranslator = new KeyboardInputTranslator() ;
		_keyboardInputTranslator.addListener(_controler.getInputProxy());
		
		_view.addKeyListener(DEBUG_KEY_LISTENER);
		_view.addKeyListener(_keyboardInputTranslator);

		_controler.addUpdatedDisplayEventListener(_view);

		// swing invoke later init :

		// link the view to the canvas manager and the first offscreen in the buffering list.
		// create a main windowed frame, a button bar with future action (share, snapshots,...)

		SwingUtilities.invokeLater(new Runnable()
		{
			private void init__createAndShowGui()
			{
				final JFrame f = new JFrame("Canvas Demo");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.add(_view);
				f.pack();
				f.setVisible(true);
				f.setResizable(false);
				_view.grabFocus();
			}

			@Override
			public void run()
			{
				init__createAndShowGui();
			}
		});

		// run in a thread (like swing invokelater)
		// controler.run(since last time)
		// sleep 5ms
		new Runnable()
		{
			long _lastTime = System.currentTimeMillis();

			/**
			 * the given time elapsed will not exceed _maxTimegap.
			 */
			long _maxTimegap = 25;

			/**
			 * the loop will sleep _sleepTime millisecond between each iteration.
			 */
			long _sleepTime = 4;

			/**
			 * should wait at least this amount since #_lastTime before calling run.
			 */
			long _timeResolution = 10;

			@Override
			public void run()
			{
				while (_controler.isRunning())
				{
					final long _now = System.currentTimeMillis();
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
						catch (final GameletException _exception)
						{
							_exception.printStackTrace();
							break;
						}
					}
					try
					{
						Thread.sleep(_sleepTime);
					}
					catch (final InterruptedException _exception)
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