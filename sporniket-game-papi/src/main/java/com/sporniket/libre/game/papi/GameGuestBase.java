/**
 * 
 */
package com.sporniket.libre.game.papi;

import com.sporniket.libre.game.api.ResourceDefinitionLoader;

/**
 * Code common to any {@link GameGuest} implementation.
 * @author David SPORN 
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public abstract class GameGuestBase implements GameGuest
{

	/**
	 * The game host.
	 * @since 0-SNAPSHOT
	 */
	private GameHost myHost;

	/**
	 * 
	 * @since 0-SNAPSHOT
	 */
	public GameGuestBase()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GameEnvironment#getGraal()
	 * 
	 * @since 0-SNAPSHOT
	 */
	public GraphicAbstractionLayerInterface getGraal()
	{
		return getHost().getGraal();
	}

	/**
	 * Get host.
	 * 
	 * @return the host
	 * @since 0-SNAPSHOT
	 */
	private GameHost getHost()
	{
		return myHost;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GameEnvironment#getInput()
	 * 
	 * @since 0-SNAPSHOT
	 */
	public InputAbstractionLayerInterface getInput()
	{
		return getHost().getInput();
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.GameEnvironment#getLoader()
	 * @since 0-SNAPSHOT
	 */
	public ResourceDefinitionLoader getLoader()
	{
		return getHost().getLoader();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GameEnvironment#getSender()
	 * 
	 * @since 0-SNAPSHOT
	 */
	public MessageSender getSender()
	{
		return getHost().getSender();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GameEnvironment#getSound()
	 * 
	 * @since 0-SNAPSHOT
	 */
	public SoundAbstractionLayerInterface getSound()
	{
		return getHost().getSound();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.GameGuest#moveIn(com.sporniket.libre.game.papi.GameHost)
	 * 
	 * @since 0-SNAPSHOT
	 */
	public void moveIn(GameHost host)
	{
		setHost(host);
	}

	/**
	 * Change host.
	 * 
	 * @param host
	 *            the host to set
	 * @since 0-SNAPSHOT
	 */
	private void setHost(GameHost host)
	{
		myHost = host;
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.game.papi.GameEnvironment#getTime()
	 * @since 0-SNAPSHOT
	 */
	public TimeAbstractionLayerInterface getTime()
	{
		return getHost().getTime();
	}

}
