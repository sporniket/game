/**
 * 
 */
package com.sporniket.libre.game.papi;

import com.sporniket.libre.game.api.ResourceDefinitionLoader;

/**
 * Models a generic game platform.
 * 
 * The game platform manage a surface for screen rendering.
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
public abstract class PlatformBase implements Platform
{
	private Game myGame;

	/**
	 * Graphic Abstraction Layer.
	 */
	private GraphicAbstractionLayerInterface myGraal;

	/**
	 * Input Abstraction Layer.
	 */
	private InputAbstractionLayerInterface myInput;

	/**
	 * Message Sender.
	 */
	private MessageSender mySender;

	/**
	 * Sound Abstraction Layer.
	 */
	private SoundAbstractionLayerInterface mySound;

	/**
	 * Time Abstraction Layer.
	 */
	private TimeAbstractionLayerInterface myTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GameHost#checkIn(com.sporniket.libre.game.papi.Game)
	 * 
	 * @since 0-SNAPSHOT
	 */
	public void checkIn(Game guest)
	{
		setGame(guest);
		guest.moveIn(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GameHost#checkOut()
	 */
	public void checkOut()
	{
		if (null != getGame()) //could be called several time.
		{
			getGame().exit();
			doCheckOut();
			setGame(null); //past this point, using game is no more possible.
		}
	}

	/**
	 * Shutdown the platform.
	 */
	public abstract void doExit();

	/**
	 * Initialize the platform.
	 */
	public abstract void doInit();

	/**
	 * Update the input information (pointer, game controller, keyboard, ...).
	 */
	public abstract void doUpdate();
	
	/**
	 * Shutdown the game (save current state, progression, whatever...).
	 */
	public abstract void doCheckOut();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Platform#exit()
	 */
	/**
	 * Shutdown the platform.
	 */
	public void exit()
	{
		checkOut();
		doExit();
	}

	/**
	 * @return the graal
	 */
	public GraphicAbstractionLayerInterface getGraal()
	{
		return myGraal;
	}

	/**
	 * @return the input
	 */
	public InputAbstractionLayerInterface getInput()
	{
		return myInput;
	}

	/**
	 * @return the sender
	 */
	public MessageSender getSender()
	{
		return mySender;
	}

	/**
	 * @return the sound
	 */
	public SoundAbstractionLayerInterface getSound()
	{
		return mySound;
	}

	/**
	 * @return the time
	 */
	public TimeAbstractionLayerInterface getTime()
	{
		return myTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Platform#init()
	 */
	/**
	 * Initialize the platform.
	 */
	public void init()
	{
		doInit();
		// look for NPE
		if (null == getGame())
		{
			throw new NullPointerException("getGame()");
		}
		if (null == getGraal())
		{
			throw new NullPointerException("getGraal()");
		}
		if (null == getInput())
		{
			throw new NullPointerException("getInput()");
		}
		if (null == getTime())
		{
			throw new NullPointerException("getTime()");
		}
		getGame().init();
	}

	// run a game

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Platform#run(com.sporniket.libre.game.papi.Game)
	 */
	public void run()
	{
		init();
		if (getGame().isValidPlatform())
		{
			doRun();
		}
		exit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.Platform#update()
	 */
	/**
	 * Update the input information (pointer, game controller, keyboard, ...).
	 */
	public void update()
	{
		if (this != getInput())  // Avoid infinite loop
		{
			getInput().update();
		}
		doUpdate();
	}// clearJoysticksState() ; updateJoysticksState() ;

	/**
	 * Game loop, to be implemented by subclasses.
	 * 
	 * @param game
	 */
	protected abstract void doRun();

	/**
	 * @return the game
	 */
	protected Game getGame()
	{
		return myGame;
	}

	/**
	 * @return the resource definition loader.
	 */
	protected abstract ResourceDefinitionLoader getResourceDefinitionLoader();

	/**
	 * @param graal
	 *            the graal to set
	 */
	protected void setGraal(GraphicAbstractionLayerInterface graal)
	{
		myGraal = graal;
	}

	/**
	 * @param input
	 *            the input to set
	 */
	protected void setInput(InputAbstractionLayerInterface input)
	{
		myInput = input;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	protected void setSender(MessageSender sender)
	{
		mySender = sender;
	}

	/**
	 * @param sound
	 *            the sound to set
	 */
	protected void setSound(SoundAbstractionLayerInterface sound)
	{
		mySound = sound;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	protected void setTime(TimeAbstractionLayerInterface time)
	{
		myTime = time;
	}

	/**
	 * @param game
	 *            the game to set
	 */
	private void setGame(Game game)
	{
		myGame = game;
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.GameHost#isGuestHosted()
	 */
	@Override
	public boolean isGuestHosted()
	{
		return null != getGame();
	}

}
