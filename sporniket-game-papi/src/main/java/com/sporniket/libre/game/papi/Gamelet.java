/**
 * 
 */
package com.sporniket.libre.game.papi;

import java.util.Map;

/**
 * Base implementation of {@link GameletInterface}.
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
public abstract class Gamelet extends GameGuestBase implements GameletInterface, GameGuest
{
	private Map<String, String> myPreferencesData;

	private boolean myRedrawNeeded = false;

	/**
	 * Actual redraw.
	 * 
	 * @param graal
	 * @param session
	 */
	protected abstract void doRedraw(Map<String, Object> session);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GameletInterface#getPreferencesData()
	 */
	public Map<String, String> getPreferencesData()
	{
		return myPreferencesData;
	}

	/**
	 * @return the redrawNeeded
	 */
	public boolean isRedrawNeeded()
	{
		return myRedrawNeeded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GameletInterface#onGoBack()
	 */
	public boolean onGoBack()
	{
		// by default, use the standard behaviour.
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sporniket.libre.game.sgpapi.GameletInterface#redraw(com.sporniket.libre.game.sgpapi.GraphicAbstractionLayerInterface,
	 * java.util.Map, java.util.Map)
	 */
	public void redraw(Map<String, Object> session)
	{
		doRedraw(session);
		setRedrawNeeded(false);
	}

	/**
	 * Utility function, that returns the right value to indicate the application is finished.
	 * 
	 * @return
	 */
	protected int returnFinished()
	{
		return Game.GAMELET_RETURN__FINISH_GAME;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GameletInterface#setPreferencesData(java.util.Map)
	 */
	public void setPreferencesData(Map<String, String> preferencesData)
	{
		myPreferencesData = preferencesData;
	}

	/**
	 * @param redrawNeeded
	 *            the redrawNeeded to set
	 */
	protected void setRedrawNeeded(boolean redrawNeeded)
	{
		myRedrawNeeded = redrawNeeded;
	}

}
