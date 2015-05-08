/**
 * 
 */
package com.sporniket.libre.game.papi.android;

import java.io.IOException;
import java.lang.Thread.State;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.sporniket.libre.game.papi.Game;
import com.sporniket.libre.game.papi.Platform;
import com.sporniket.libre.game.papi.log.LogFactory;
import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;

/**
 * Main activity for writing a game.
 * 
 * In order to write an new game, extends this class and implements required submethods.
 * 
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API for Android</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Android</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API for Android</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API for Android</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * 
 */
//TODO use the logger framework
public abstract class GameActivity extends Activity implements SurfaceHolder.Callback
{
	/**
	 * Tag for logging.
	 */
	static String TAG = GameActivity.class.getSimpleName();

	/**
	 * Feature set to use, best fit among {@link #getAcceptedScreenFeatureSets()}.
	 */
	private ScreenFeatureSet myBestScreenFeatureSet;

	/**
	 * link to the game platform.
	 */
	private final GamePlatform myGamePlatform = GamePlatform.getInstance();

	/**
	 * The game thread.
	 */
	private GameThread myGameThread;

	/**
	 * Store the view for futur reference.
	 */
	private GameView myGameView;

	protected abstract void doExit();


	protected abstract void doInit();

	/**
	 * Actually save the preferences.
	 */
	private void doSavePreferences()
	{
		getGame().updatePreferences();
		Map<String, String> _gamePreferences = getGame().getPreferencesData();
		SharedPreferences _publicPreferences = getPublicPreferences();
		SharedPreferences _privatePreferences = getPrivatePreferences();
		savePreferences(_publicPreferences.edit(), _gamePreferences, getPublicPreferenceKeys());
		savePreferences(_privatePreferences.edit(), _gamePreferences, getPrivatePreferenceKeys());
	}

	/**
	 * Android return Device pixels that are not what we want...
	 */
	private void estimateScreenRealDefinitionAndFindBestScreenFeatureSet()
	{
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int _computedScreenWidth = estimateScreenRealDimension(metrics.widthPixels, metrics.densityDpi);
		int _computedScreenHeight = estimateScreenRealDimension(metrics.heightPixels, metrics.densityDpi);

		Log.i(TAG, "display estimated real width = " + _computedScreenWidth);
		Log.i(TAG, "display estimated real height = " + _computedScreenHeight);

		setBestScreenFeatureSet(findBestScreenFeatureSet(_computedScreenWidth, _computedScreenHeight));
	}

	/**
	 * Compute the real screen dimension (in pixels) value according to the dpi.
	 * 
	 * @param dimension
	 *            dimension in virtual pixels.
	 * @param dpi
	 *            dot per inch of the display.
	 * @return an estimated dimension in real pixels (there are compensation for some rounding errors).
	 * @see http://developer.android.com/guide/practices/screens_support.html
	 */
	private int estimateScreenRealDimension(int dimension, int dpi)
	{
		return dimension;// (1 + dimension * dpi / 160);
	}

	private ScreenFeatureSet findBestScreenFeatureSet(int width, int height)
	{
		ScreenFeatureSet[] acceptedFeatureSet = getAcceptedScreenFeatureSets();
		final String _methodName = "findBestScreenFeatureSet(acceptedFeatureSet, width, height)";
		for (ScreenFeatureSet _set : acceptedFeatureSet)
		{
			if (width >= _set.getWidth() * _set.getXfactor() && height >= _set.getHeight() * _set.getYfactor())
			{
				{
					Log.i(TAG, "Found best screen features : " + _set);
					Log.i(TAG, "SUCCESS " + _methodName);
				}
				return _set;
			}
		}
		Log.i(TAG, "FAIL    " + _methodName);
		throw new IllegalStateException("Cannot find a valid ScreenFeatureSet, check acceptable list and orientation.");
	}

	/**
	 * 
	 * @return
	 */
	public abstract ScreenFeatureSet[] getAcceptedScreenFeatureSets();

	/**
	 * @return the bestScreenFeatureSet
	 */
	public ScreenFeatureSet getBestScreenFeatureSet()
	{
		return myBestScreenFeatureSet;
	}

	/**
	 * Implement this.
	 * 
	 * @return
	 */
	protected abstract Game getGame();

	/**
	 * @return the gameThread
	 */
	public GameThread getGameThread()
	{
		return myGameThread;
	}

	/**
	 * @return the gameView
	 */
	public GameView getGameView()
	{
		return myGameView;
	}

	/**
	 * @return the support
	 */
	public GamePlatform getPlatform()
	{
		return myGamePlatform;
	}

	/**
	 * @return a list of names of preferences data to save in the private preferences data.
	 */
	protected abstract String[] getPrivatePreferenceKeys();

	/**
	 * @return
	 */
	private SharedPreferences getPrivatePreferences()
	{
		return getPreferences(MODE_PRIVATE);
	}

	/**
	 * @return a list of names of preferences data to save in the public preferences data.
	 */
	protected abstract String[] getPublicPreferenceKeys();

	/**
	 * @return
	 */
	private SharedPreferences getPublicPreferences()
	{
		return getSharedPreferences(getPublicPreferencesName(), MODE_WORLD_WRITEABLE);
	}

	/**
	 * Name of the public shared preferences.
	 * 
	 * @return
	 */
	protected abstract String getPublicPreferencesName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Platform#init()
	 */
	public void init()
	{
		LogFactory.setFactory(new AndroidLoggerFactory());
		// FIXME base init code
		doInit();

		// load preferences data
		Log.i(TAG, "====> load preferences ");
		Map<String, String> _gamePreferences = new HashMap<String, String>();
		SharedPreferences _publicPreferences = getPublicPreferences();
		SharedPreferences _privatePreferences = getPrivatePreferences();
		loadPreferences(_publicPreferences, _gamePreferences, getPublicPreferenceKeys());
		loadPreferences(_privatePreferences, _gamePreferences, getPrivatePreferenceKeys());

		for (String _key : _gamePreferences.keySet())
		{
			Log.i(TAG, _key + "=" + _gamePreferences.get(_key));
		}
		getGame().setPreferencesData(_gamePreferences);
		getGame().putPreferenceDataIntoSession();

		Log.i(TAG, "<---- load preferences");

	}


	/**
	 * Load data from a preference object.
	 * 
	 * <code>null</code> values are ignored.
	 * 
	 * @param androidPreferencesEditor
	 * @param data
	 * @param keys
	 */
	private void loadPreferences(SharedPreferences androidPreferences, Map<String, String> data, final String[] keys)
	{
		if (null != keys && keys.length > 0)
		{
			for (String _key : keys)
			{
				String _value = androidPreferences.getString(_key, null);
				if (null != _value)
				{
					data.put(_key, _value);
				}
			}
		}
	}

	/**
	 * Log each picture and it's flag.
	 */
	public void logPictureIsRecycledFlag()
	{
		getPlatform().debugPictures();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed()
	{
		if (!getGame().onGoBack())
		{
			doSavePreferences();
			super.onBackPressed();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.i(TAG, "====> [onCreate(savedInstanceState)] ");
		super.onCreate(savedInstanceState);
		Log.i(TAG, "on create game activity... ");

		gameView__prepare();

		// FIXME maybe implement a container that will find the correct dimensions to use.
		
		game__loadPreferences();
		gamePlatform__setup();
		
		init() ;
		Log.i(TAG, "<---- [onCreate(savedInstanceState)]");
	}


	/**
	 * 
	 */
	private void gameView__prepare()
	{
		switchToFullScreen();
		estimateScreenRealDefinitionAndFindBestScreenFeatureSet();
		setGameView(new GameView(this, getBestScreenFeatureSet()));
	}


	/**
	 * 
	 */
	private void gamePlatform__setup()
	{
		getPlatform().setupEnvironment(getApplicationContext(), getBestScreenFeatureSet());
		getPlatform().setActivity(this); // link the platform to the activity for message sending
		gamePlatform__checkGuest();
	}


	/**
	 * 
	 */
	private void game__loadPreferences()
	{
		Map<String, String> _gamePreferences = new HashMap<String, String>();
		SharedPreferences _publicPreferences = getPublicPreferences();
		SharedPreferences _privatePreferences = getPrivatePreferences();
		loadPreferences(_publicPreferences, _gamePreferences, getPublicPreferenceKeys());
		loadPreferences(_privatePreferences, _gamePreferences, getPrivatePreferenceKeys());
		getGame().setPreferencesData(_gamePreferences);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy()
	{
		Log.i(TAG, "====> [onDestroy()] ");
		doExit();
		myGamePlatform.exit();
		super.onDestroy();
		Log.i(TAG, "System.exit(0) --> bye.");
		System.exit(0);//kill itself
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause()
	{
		Log.i(TAG, "====> [onPause()] ");
		super.onPause();
		if (null != getGameThread())
		{
			getGameThread().pause();
		}
		Log.i(TAG, "<---- [onPause()]");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume()
	{
		Log.i(TAG, "====> [onResume()] ");
		super.onResume();
		setContentView(getGameView());
		Log.i(TAG, "<---- [onResume()]");
	}


	/**
	 * 
	 */
	private void gamePlatform__checkGuest()
	{
		if (!getPlatform().isGuestHosted())
		{
			getPlatform().checkIn(getGame());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		Log.i(TAG, "====> [onSaveInstanceState(outState)] ");
		super.onSaveInstanceState(outState);
		doSavePreferences();
		Log.i(TAG, "<---- [onSaveInstanceState(outState)]");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop()
	{
		Log.i(TAG, "====> [onStop()] ");
		((GamePlatform) getPlatform()).disposeRecycledBitmaps();
		super.onStop();
		Log.i(TAG, "<---- [onStop()]");
	}

	/**
	 * resurrect the game thread.
	 */
	public void resurrectGameThread()
	{
		Log.i(TAG, "====> [resurrectGameThread()] ");
		myGameThread = GameThread.resurrect(myGameThread);
		Log.i(TAG, "<---- [resurrectGameThread()]");
	}

	/**
	 * @param androidPreferencesEditor
	 * @param data
	 * @param keys
	 */
	private void savePreferences(SharedPreferences.Editor androidPreferencesEditor, Map<String, String> data, final String[] keys)
	{
		Log.i(TAG, "====> [savePreferences(androidPreferencesEditor, data, keys)] ");
		if (null != keys && keys.length > 0)
		{
			for (String _key : keys)
			{
				String _value = data.get(_key);
				if (null != _value)
				{
					androidPreferencesEditor.putString(_key, _value);
					Log.i(TAG, "Save preference : " + _key + "=" + _value);
				}
			}
			androidPreferencesEditor.apply();
		}
		Log.i(TAG, "<---- [savePreferences(androidPreferencesEditor, data, keys)]");
	}

	/**
	 * @param bestScreenFeatureSet
	 *            the bestScreenFeatureSet to set
	 */
	private void setBestScreenFeatureSet(ScreenFeatureSet bestScreenFeatureSet)
	{
		myBestScreenFeatureSet = bestScreenFeatureSet;
	}

	/**
	 * @param gameView the gameView to set
	 */
	private void setGameView(GameView gameView)
	{
		myGameView = gameView;
		getPlatform().connectInput(getGameView());
		getGameView().getHolder().addCallback(this);
		getGameView().setGameActivity(this);
	}
	
	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
	 */
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		Log.w(TAG, "Surface changed, but it should not happen.");

	}
	
	/**
	 * <code>true</code> if one must call {@link #getGame()#init()} during {@link #surfaceCreated(SurfaceHolder)}.
	 */
	private boolean myGameInitialisationRequired = true ;

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 */
	public void surfaceCreated(SurfaceHolder holder)
	{
		Log.i(TAG, "====> [surfaceCreated(holder)] ");
		gamePlatform__checkGuest();
		getPlatform().init();
		getPlatform().setSurfaceHolder(holder);
		logPictureIsRecycledFlag();
		try
		{
			getPlatform().regenerateBitmaps();
		}
		catch (IOException _exception)
		{
			Log.w(TAG, "Error while regenerating bitmaps...", _exception);
		}
		try
		{
			getPlatform().regenerateSounds();
		}
		catch (IOException _exception)
		{
			Log.w(TAG, "Error while regenerating sounds...", _exception);
		}
		logPictureIsRecycledFlag();
		game__initializeOnlyOnce();
		gameThread__checkInstance();
		gameThread__startSafely();
		Log.i(TAG, "<---- [surfaceCreated(holder)]");
	}


	/**
	 * 
	 */
	private void game__initializeOnlyOnce()
	{
		if (isGameInitialisationRequired())
		{
			getGame().init();
			setGameInitialisationRequired(false);
		}
	}


	/**
	 * Start the {@link GameThread} only once.
	 */
	private void gameThread__startSafely()
	{
		if (Thread.State.NEW == getGameThread().getState())
		{
			if(getGameThread().isPaused()){
				getGameThread().unpause();
			}
			getGameThread().start();
		}
	}


	/**
	 * Verify and do what is required so that the instance of {@link GameThread} exists and is ready (unpaused).
	 */
	private void gameThread__checkInstance()
	{
		Log.i(TAG, "====> [checkInstance__gameThread()] ");
		if (null == getGameThread())
		{
			myGameThread = new GameThread(getApplicationContext(), getGameView().getHolder(), getGameView(), getPlatform(), getGame());
		}
		else
		{
			final State _state = getGameThread().getState();
			switch(_state)
			{
				case TERMINATED:
					resurrectGameThread();
					break ;
				case NEW:
					getGameThread().unpause();
					break;
				default:
					Log.w(TAG, "Unexpected GameThread state : " + _state);	
			}
		}
		Log.i(TAG, "<---- [checkInstance__gameThread()]");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 */
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		Log.i(TAG, "====> [surfaceDestroyed(holder)] ");
		// Stop the game and wait for the thread to end running.
		logPictureIsRecycledFlag();
		myGamePlatform.setSurfaceHolder(null);
		getGameThread().pause();

		Log.i(TAG, "<---- [surfaceDestroyed(holder)]");
	}

	/**
	 * 
	 */
	private void switchToFullScreen()
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}


	/**
	 * @return the gameInitialisationRequired
	 */
	private boolean isGameInitialisationRequired()
	{
		return myGameInitialisationRequired;
	}


	/**
	 * @param gameInitialisationRequired the gameInitialisationRequired to set
	 */
	private void setGameInitialisationRequired(boolean gameInitialisationRequired)
	{
		myGameInitialisationRequired = gameInitialisationRequired;
	}

}
