/**
 * 
 */
package com.sporniket.libre.game.papi;

import java.util.Map;

import com.sporniket.libre.game.api.ResourceDefinitionLoader;

/**
 * Interface to implement to be hosted by a GameHost.
 * 
 * A gamelet is a part of a game, i.e. the intro screen, the menu, the game, the high-score. In other word, it can be likened to a
 * scene in a movie.
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
 * <i>The Sporniket Game Library &#8211; Platform API</i> is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; Platform API</i> is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
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
public interface GameletInterface
{
	/**
	 * Update the gamelet state.
	 * 
	 * @param timeElapsed
	 *            time elapsed in millisecond since the previous call.
	 * @param session
	 *            sesion state
	 * @return a numeric code to know whether one should continue to call this gamelet instance or go to the next Gamelet.
	 * @since 0-SNAPSHOT
	 */
	int run(long timeElapsed, Map<String, Object> session);

	/**
	 * Called after each call to {@link #run(long, InputAbstractionLayerInterface, Map, Map)} to know whether the
	 * {@link #redraw(Platform, Map, Map)} method must be called.
	 * 
	 * @return <code>true</code> if {@link #redraw(Platform, Map, Map)} needs to be called.
	 */
	boolean isRedrawNeeded();

	/**
	 * This method is called each time {@link #isRedrawNeeded()} returns <code>true</code>.
	 * 
	 * @param platform
	 *            implementation of the game platform
	 * @param graal
	 *            graphic abstraction layer to redraw the screen
	 * @param session
	 *            session state.
	 */
	void redraw(Map<String, Object> session);

	/**
	 * This method is called before the first call to {@link #run(long, Map)}.
	 * 
	 * @param session
	 *            session state.
	 */
	void init(Map<String, Object> session);

	/**
	 * This method is called after the last call to {@link #run(long, Map)}.
	 * 
	 * @param session
	 *            session state.
	 */
	void exit(Map<String, Object> session);

	/**
	 * get the preferences data, for editing or saving.
	 * 
	 * @return
	 */
	Map<String, String> getPreferencesData();

	/**
	 * Set the preferences data previously saved.
	 * 
	 * @param preferencesData
	 */
	void setPreferencesData(Map<String, String> preferencesData);

	/**
	 * React to a "back" button (e.g. Android back button).
	 * 
	 * @return <code>true</code> if the gamelet implements a specific behaviour, <code>false</code> if the standard back behaviour
	 *         should be used.
	 */
	boolean onGoBack();

}
