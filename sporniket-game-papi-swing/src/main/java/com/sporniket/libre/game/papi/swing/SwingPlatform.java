/**
 * 
 */
package com.sporniket.libre.game.papi.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sporniket.libre.game.api.ResourceDefinitionLoader;
import com.sporniket.libre.game.papi.Game;
import com.sporniket.libre.game.papi.MessageSender;
import com.sporniket.libre.game.papi.MessageSenderException;
import com.sporniket.libre.game.papi.PlatformBase;
import com.sporniket.libre.game.papi.log.LogFactory;
import com.sporniket.libre.game.papi.log.Logger;
import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;
import com.sporniket.libre.lang.SystemProperties;
import com.sporniket.libre.ui.swing.JFrameUtils;

/**
 * Implementation of the platform for Java native images.
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API for Swing</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Swing</i> is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Swing</i> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API for Swing</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * 
 */
public class SwingPlatform extends PlatformBase implements WindowListener
{
	private static Logger theLogger = LogFactory.getLogger(SwingPlatform.class.getSimpleName());

	/**
	 * Implementation of the MessageSender layer that use the {@link Desktop} API to use system apps.
	 * 
	 * <p>
	 * &copy; Copyright 2010-2013 David Sporn
	 * </p>
	 * <hr>
	 * 
	 * <p>
	 * This file is part of <i>The Sporniket Game Library &#8211; Platform API for Swing</i>.
	 * 
	 * <p>
	 * <i>The Sporniket Game Library &#8211; Platform API for Swing</i> is free software: you can redistribute it and/or modify it
	 * under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of
	 * the License, or (at your option) any later version.
	 * 
	 * <p>
	 * <i>The Sporniket Game Library &#8211; Platform API for Swing</i> is distributed in the hope that it will be useful, but
	 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
	 * Lesser General Public License for more details.
	 * 
	 * <p>
	 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
	 * Platform API for Swing</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
	 * 
	 * <hr>
	 * 
	 * @author David SPORN 
	 * 
	 * @version 0-SNAPSHOT
	 * @since 0-SNAPSHOT
	 */
	private static class DesktopSender implements MessageSender
	{
		private Desktop myDesktop = null;

		public DesktopSender()
		{
			theLogger.debug("desktop sender");
			if (Desktop.isDesktopSupported())
			{
				theLogger.debug("desktop is supported...");
				myDesktop = Desktop.getDesktop();
			}
			else
			{
				theLogger.warn("Desktop is not supported");
			}
		}

		public void openWebPage(String url) throws MessageSenderException
		{
			theLogger.debug("openWebPage " + url);
			if (null != myDesktop && myDesktop.isSupported(Desktop.Action.BROWSE))
			{
				try
				{
					String _finalUrl = url;
					if (url.startsWith("market://search"))
					{
						_finalUrl = url.replace("market://search", "http://play.google.com/store/search");
					}
					else if (url.startsWith("market://details"))
					{
						_finalUrl = url.replace("market://details", "http://play.google.com/store/apps/details");
					}
					myDesktop.browse(new URI(_finalUrl));
				}
				catch (IOException _exception)
				{
					logError(_exception);
				}
				catch (URISyntaxException _exception)
				{
					logError(_exception);
				}
			}
			else
			{
				theLogger.warn("Desktop.Action.BROWSE is not supported");
			}
		}

		public void shareMessage(String subject, String message) throws MessageSenderException
		{
			theLogger.debug("shareMessage " + subject + " // " + message);
			if (null != myDesktop && myDesktop.isSupported(Desktop.Action.MAIL))
			{
				try
				{
					String _mailto = MessageFormat.format("?subject={0}&body={1}", new Object[]
					{
							subject, message
					});
					Desktop.getDesktop().mail(new URI("mailto", _mailto, null));
				}
				catch (UnsupportedEncodingException _exception)
				{
					logError(_exception);
				}
				catch (IOException _exception)
				{
					logError(_exception);
				}
				catch (URISyntaxException _exception)
				{
					logError(_exception);
				}
			}
			else
			{
				theLogger.warn("Desktop.Action.MAIL is not supported");
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.sporniket.libre.game.papi.MessageSender#shareMessage(java.lang.String, java.lang.String, java.io.File)
		 */
		@Override
		public void shareMessage(String subject, String message, File attachement) throws MessageSenderException
		{
			// FIXME implement message with attachement
			shareMessage(subject, message);
		}

		/**
		 * @param exception
		 * @since 0-SNAPSHOT
		 */
		private void logError(Exception exception)
		{
			StringWriter _writer = new StringWriter();
			PrintWriter _print = new PrintWriter(_writer);
			exception.printStackTrace(_print);
			theLogger.error(exception.getClass().getName() + ":" + exception.getMessage() + "\n" + _writer.toString());
		}

	}

	/**
	 * @param exception
	 * @since 0-SNAPSHOT
	 */
	private void logError(Exception exception)
	{
		StringWriter _writer = new StringWriter();
		PrintWriter _print = new PrintWriter(_writer);
		exception.printStackTrace(_print);
		theLogger.error(exception.getClass().getName() + ":" + exception.getMessage() + "\n" + _writer.toString());
	}

	public void doExit()
	{
		File _preferencesFile = retrievePreferencesFile();
		Properties _properties = new Properties();
		Map<String, String> _preferencesData = getGame().getPreferencesData();
		for (String _key : _preferencesData.keySet())
		{
			_properties.setProperty(_key, _preferencesData.get(_key));
		}
		try
		{
			FileOutputStream _out = new FileOutputStream(_preferencesFile);
			_properties.store(_out, "Game preference");
			_out.close();
		}
		catch (FileNotFoundException _exception)
		{
			logError(_exception);
		}
		catch (IOException _exception)
		{
			logError(_exception);
		}

	}

	private static final int MAX_DIMENSION = 10000;

	private ResourceDefinitionLoader myResourceDefinitionLoader;

	/**
	 * Max Width to find the best {@link ScreenFeatureSet}.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private int myMaxWidth = MAX_DIMENSION;

	/**
	 * Max Height to find the best {@link ScreenFeatureSet}.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private int myMaxHeight = MAX_DIMENSION;

	public void doInit()
	{
		// specify log factory
		LogFactory.setFactory(new SimpleLogFactory(true, true));
		theLogger = LogFactory.getLogger(SwingPlatform.class.getSimpleName());

		// Timer
		setTime(new SystemTimer());

		// Swing infrastructure for graal and input
		// FIXME use keyboard as game controller, like steem.
		JFrame _appWindows = new JFrame("Sporniket Game Platform");// FIXME title handling
		_appWindows.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// set the background color
		_appWindows.getContentPane().setBackground(Color.DARK_GRAY);
		// set the window maximised, no decorations
		JFrameUtils.maximizeFrame(_appWindows).setUndecorated(true);
		_appWindows.setVisible(true);

		// wait for the Frame to have a size
		Rectangle _frameBounds = JFrameUtils.getMaximisedFrameOuterBounds();
		theLogger.info("Screen dimensions : " + _frameBounds.width + " x " + _frameBounds.height + " pixels.");

		_appWindows.addWindowListener(this);

		// find the right _screenFeatures to instanciate the game panel
		GamePanel _gamePanel = null;
		int _maxHeight = _frameBounds.height;
		if (getMaxHeight() < _maxHeight)
		{
			_maxHeight = getMaxHeight();
		}
		int _maxWidth = _frameBounds.width;
		if (getMaxWidth() < _maxWidth)
		{
			_maxWidth = getMaxWidth();
		}

		for (ScreenFeatureSet _candidate : getGame().getAcceptedScreenFeatureSets())
		{
			if (_candidate.getWidth() <= _maxWidth && _candidate.getHeight() <= _maxHeight)
			{
				_gamePanel = new GamePanel(_candidate);

				// "center" the game panel
				int _leftMargin = (_frameBounds.width - _candidate.getWidth()) / 2;
				int _topMargin = (_frameBounds.height - _candidate.getHeight()) / 2;
				_appWindows.getContentPane().add(Box.createVerticalStrut(_topMargin), BorderLayout.NORTH);
				_appWindows.getContentPane().add(Box.createHorizontalStrut(_leftMargin), BorderLayout.WEST);

				// ok, found, nothing more to do
				break;
			}
		}

		if (null == _gamePanel)
		{
			throw new RuntimeException("Cannot find compatible screen feature set");
		}
		theLogger.info("Screen features dimensions : " + _gamePanel.getScreenFeatures().getWidth() + " x "
				+ _gamePanel.getScreenFeatures().getHeight() + " pixels.");

		// load preferences
		loadPreferences();
		getGame().putPreferenceDataIntoSession();

		myResourceDefinitionLoader = _gamePanel.getResourceDefinitionLoader();
		_appWindows.getContentPane().add(_gamePanel, BorderLayout.CENTER);
		((JPanel) _appWindows.getContentPane()).revalidate();

		_gamePanel.init();
		setGraal(_gamePanel);
		setInput(_gamePanel);
		setSound(_gamePanel);
		setSender(new DesktopSender());

	}

	/**
	 * 
	 * @since 0-SNAPSHOT
	 */
	private void loadPreferences()
	{
		File _prefFile = retrievePreferencesFile();
		if (_prefFile.exists())
		{
			try
			{
				Map<String, String> _data = new HashMap<String, String>();
				Properties _properties = new Properties();
				_properties.load(new FileInputStream(_prefFile));
				for (Object _key : _properties.keySet())
				{
					String _keyName = (String) _key;
					String _value = _properties.getProperty(_keyName);
					_data.put(_keyName, _value);
				}
				getGame().setPreferencesData(_data);
			}
			catch (FileNotFoundException _exception)
			{
				throw new RuntimeException("Cannot find existing preference file", _exception);
			}
			catch (IOException _exception)
			{
				throw new RuntimeException("I/O Exception while loading preferences", _exception);
			}
		}
	}

	/**
	 * @return
	 * @since 0-SNAPSHOT
	 */
	private File retrievePreferencesFile()
	{
		File _prefFile = new File(SystemProperties.Private.getUserHome(), getGame().getClass().getName());
		return _prefFile;
	}

	public void doUpdate()
	{
		// override this method
	}

	// //TODO initiate a Frame with a custom Panel (mouse input listener etc...), an offscreen canvas
	// //TODO implement all the graphics
	//
	// public static void main(String[] args)
	// {
	// SwingPlatform _instance = new SwingPlatform();
	// _instance.init();
	//
	// File _here = new File(".");
	// System.out.println(_here.getAbsolutePath());
	//
	// try
	// {
	// PictureHandler _picture = _instance.getGraal().loadPicture("hp7_2.jpg", false);
	// _instance.getGraal().displayPicture(_picture, 60, 60);
	// _instance.getGraal().commitDisplay();
	// }
	// catch (IOException _exception)
	// {
	// _exception.printStackTrace();
	// }
	// }

	protected void doRun()
	{
		// FIXME extraire l'algo si réutilisable
		// FIXME utiliser des constantes pour la temporisation
		Game game = getGame();
		checkIn(game);
		long lastTime = getTime().getClock();
		long now, elapsed;
		while (!game.isFinished())
		{
			while (!game.isFinished() && !game.isRedrawNeeded())
			{
				update();
				now = getTime().getClock();
				elapsed = now - lastTime;
				if (elapsed < 0)
				{
					elapsed = 0;
				}
				game.update(elapsed);
				lastTime = now;
				try
				{
					Thread.sleep(10);
				}
				catch (InterruptedException _exception)
				{
					_exception.printStackTrace();
				}
				if (!game.isFinished() && game.isRedrawNeeded())
				{
					game.redraw(getGraal());
					getGraal().commitDisplay();
				}
			}
		}
	}

	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void windowClosing(WindowEvent e)
	{
		exit();
		System.exit(0);
	}

	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	protected ResourceDefinitionLoader getResourceDefinitionLoader()
	{
		return myResourceDefinitionLoader;
	}

	public static void launch(Game game, int maxWidth, int maxHeight)
	{
		SwingPlatform _platform = new SwingPlatform();
		_platform.setMaxWidth(maxWidth);
		_platform.setMaxHeight(maxHeight);
		_platform.checkIn(game);
		_platform.run();
	}

	/**
	 * Get maxWidth.
	 * 
	 * @return the maxWidth
	 * @since 0-SNAPSHOT
	 */
	public int getMaxWidth()
	{
		return myMaxWidth;
	}

	/**
	 * Change maxWidth.
	 * 
	 * @param maxWidth
	 *            the maxWidth to set
	 * @since 0-SNAPSHOT
	 */
	public void setMaxWidth(int maxWidth)
	{
		myMaxWidth = maxWidth;
	}

	/**
	 * Get maxHeight.
	 * 
	 * @return the maxHeight
	 * @since 0-SNAPSHOT
	 */
	public int getMaxHeight()
	{
		return myMaxHeight;
	}

	/**
	 * Change maxHeight.
	 * 
	 * @param maxHeight
	 *            the maxHeight to set
	 * @since 0-SNAPSHOT
	 */
	public void setMaxHeight(int maxHeight)
	{
		myMaxHeight = maxHeight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GameEnvironment#getLoader()
	 * 
	 * @since 0-SNAPSHOT
	 */
	public ResourceDefinitionLoader getLoader()
	{
		return getResourceDefinitionLoader();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.PlatformBase#doCheckOut()
	 */
	@Override
	public void doCheckOut()
	{
		// FIXME save preferences

	}

}
