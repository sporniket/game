/**
 * 
 */
package com.sporniket.libre.game.papi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sporniket.libre.game.api.ResourceDefinitionLoader;
import com.sporniket.libre.game.papi.profile.ScreenFeatureSet;

/**
 * Model for a game.
 * 
 * A game is a collection of {@link GameletInterface} and maintains a <em>session</em> state.
 * 
 * A <em>session</em> serves to store values that are then available from gamelets to gamelets. It is a concept taken from web
 * application (e.g. jsp).
 * 
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; Platform API</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * Platform API</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * 
 */
public abstract class Game extends GameGuestBase
{
	/**
	 * {@link GameletInterface#run(long, InputAbstractionLayerInterface, java.util.Map, java.util.Map)} MUST return this value to
	 * signal the end of the application.
	 */
	public static final int GAMELET_RETURN__FINISH_GAME = -1;
	
	/**
	 * The current gamelet.
	 */
	private GameletInterface myCurrentGamelet;

	/**
	 * Store the "current" gamelet to detect gamelet change.
	 */
	private int myCurrentGameletIndex = 0;

	/**
	 * Flag to know that it's time to quit.
	 */
	private boolean myFinished = false;

	/**
	 * Array of gamelets.
	 * 
	 * ArrayList is explicitely used for fast access to any item.
	 */
	private ArrayList<Gamelet> myGamelets = new ArrayList<Gamelet>(10);

	/**
	 * Session state.
	 */
	private Map<String, Object> mySession = new HashMap<String, Object>();

	/**
	 * Extention point for saving preference, game state, etc...
	 */
	protected abstract void doExit();

	/**
	 * External end of the game (e.g. by closing the app window.
	 */
	public void exit()
	{
		updatePreferences();
		doExit();
		// setFinished(true);
	}

	/**
	 * Extension point for gathering data to save as preferences.
	 */
	public abstract void updatePreferences();

	/**
	 * Extention point for pausing the game or something else, when the game window lose focus for instance.
	 */
	public abstract void freeze();

	/**
	 * @return the currentGamelet
	 */
	protected GameletInterface getCurrentGamelet()
	{
		return myCurrentGamelet;
	}

	/**
	 * @return the currentGameletIndex
	 */
	protected int getCurrentGameletIndex()
	{
		return myCurrentGameletIndex;
	}

	/**
	 * @return the gamelets
	 */
	protected ArrayList<Gamelet> getGamelets()
	{
		return myGamelets;
	}

	/**
	 * @return the gamelets
	 */
	private ArrayList<Gamelet> getGameletsRaw()
	{
		return myGamelets;
	}

	/**
	 * @return the session
	 */
	protected Map<String, Object> getSession()
	{
		return mySession;
	}

	/**
	 * Do application wide settings here, like loading common graphics.
	 * 
	 * @param graal
	 * @param sound
	 * @param input
	 * @param timer
	 */
	//FIXME simplify here
//	public abstract void init(GraphicAbstractionLayerInterface graal, SoundAbstractionLayerInterface sound, InputAbstractionLayerInterface input,
//			TimeAbstractionLayerInterface timer);
	public abstract void init();

	/**
	 * @return the finished
	 */
	public boolean isFinished()
	{
		return myFinished;
	}

	public boolean isRedrawNeeded()
	{
		boolean _result = false;
		if (null != getCurrentGamelet())
		{
			_result = getCurrentGamelet().isRedrawNeeded();
		}
		return _result;
	}

	/**
	 * Check that the game is running on a compatible platform.
	 * 
	 * Most of the time, a game has some requirement about the input capabilities of the platform, like one pointer, multitouch
	 * screen or several game controllers.
	 * 
	 * @param graal
	 * @param input
	 * @param timer
	 * @return
	 */
//	public abstract boolean isValidPlatform(GraphicAbstractionLayerInterface graal, InputAbstractionLayerInterface input,
//			TimeAbstractionLayerInterface timer);
	public abstract boolean isValidPlatform();

	private void onChangeCurrentGameletIndex()
	{
		setCurrentGamelet(getGamelets().get(getCurrentGameletIndex()));
		getCurrentGamelet().setPreferencesData(myPreferencesData); //preferences are available in the init part of the gamelet.
		getCurrentGamelet().init(getSession());
	}

	public void redraw(GraphicAbstractionLayerInterface graal)
	{
		if (isFinished())
		{
			throw new IllegalStateException("Game is finished !");
		}
		if (null == getCurrentGamelet()) // first call
		{
			return; // silently skip.
		}
		if (getCurrentGamelet().isRedrawNeeded())
		{
			getCurrentGamelet().redraw(getSession());
			graal.commitDisplay();
		}
	}

	/**
	 * @param currentGamelet
	 *            the currentGamelet to set
	 */
	protected void setCurrentGamelet(GameletInterface currentGamelet)
	{
		myCurrentGamelet = currentGamelet;
	}

	/**
	 * @param currentGameletIndex
	 *            the currentGameletIndex to set
	 */
	protected void setCurrentGameletIndex(int currentGameletIndex)
	{
		myCurrentGameletIndex = currentGameletIndex;
	}

	/**
	 * @param finished
	 *            the finished to set
	 */
	protected void setFinished(boolean finished)
	{
		myFinished = finished;
	}

	/**
	 * Extention point for unpausing the game or something else, when the game window regain focus for instance.
	 */
	public abstract void unfreeze();

	/**
	 * @param graal
	 * @param sound
	 * @param input
	 * @param sender
	 * @param loader
	 * @param timeElapsed
	 * @since 0-SNAPSHOT
	 * @deprecated call {@link #moveIn(GameHost)} and then use {@link #update(long)}.
	 */
	public void update(GraphicAbstractionLayerInterface graal, SoundAbstractionLayerInterface sound, InputAbstractionLayerInterface input, MessageSender sender, ResourceDefinitionLoader loader, long timeElapsed)
	{
		if (isFinished())
		{
			throw new IllegalStateException("Game is finished !");
		}
		if (null == getCurrentGamelet()) // first call
		{
			onChangeCurrentGameletIndex();
		}
		int result = getCurrentGamelet().run(timeElapsed, getSession());
		if (result == GAMELET_RETURN__FINISH_GAME)
		{
			setFinished(true);
		}
		else if (result != getCurrentGameletIndex())
		{
			getCurrentGamelet().exit(getSession());
			setCurrentGameletIndex(result);
			onChangeCurrentGameletIndex();

		}
	}
	
	public void update(long elapsedTime)
	{
		if (isFinished())
		{
			throw new IllegalStateException("Game is finished !");
		}
		if (null == getCurrentGamelet()) // first call
		{
			//FIXME
			onChangeCurrentGameletIndex();
		}
		//FIXME
		int result = getCurrentGamelet().run(elapsedTime, getSession());
		if (result == GAMELET_RETURN__FINISH_GAME)
		{
			setFinished(true);
		}
		else if (result != getCurrentGameletIndex())
		{
			getCurrentGamelet().exit(getSession());
			setCurrentGameletIndex(result);
			onChangeCurrentGameletIndex();

		}
	}

	/**
	 * Allow the game to specify a list of preferred screen features set, to adapt to the available screen size.
	 * @return
	 * @since 0-SNAPSHOT
	 */
	public abstract ScreenFeatureSet[] getAcceptedScreenFeatureSets();
	
	/**
	 * Preferences data.
	 */
	private Map<String,String> myPreferencesData = new HashMap<String, String>();

	/**
	 * Get a set of preferences data to save.
	 * @return a map of data to save in a preference file.
	 */
	public Map<String,String> getPreferencesData()
	{
		Map<String,String> _preferences = new HashMap<String, String>() ;
		_preferences.putAll(myPreferencesData);
		return _preferences ;
	}
	
	/**
	 * Put a previously saved preferences data in the game (overwrite current preferences).
	 * @param preferencesData
	 */
	public void setPreferencesData(Map<String,String> preferencesData)
	{
		myPreferencesData.clear();
		myPreferencesData.putAll(preferencesData);
	}
	
	/**
	 * Access to the preferences data for the subclasses, so that they can modify it.
	 * @return
	 */
	protected Map<String,String> getMutablePreferencesData()
	{
		return myPreferencesData ;
	}
	
	/**
	 * Manage the "go back" button (e.g. on Android devices).
	 * @return <code>true</code> if the current gamelet process the back button, <code>false</code> to use the standard behaviour.
	 */
	public boolean onGoBack()
	{
		return getCurrentGamelet().onGoBack();
	}
	
	public abstract void putPreferenceDataIntoSession() ;

	public void moveIn(GameHost host)
	{
		super.moveIn(host);
		for(Gamelet _gamelet:getGameletsRaw())
		{
			_gamelet.moveIn(host);
		}
	}
}
