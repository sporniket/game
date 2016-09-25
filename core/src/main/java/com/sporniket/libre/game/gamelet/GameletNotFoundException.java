package com.sporniket.libre.game.gamelet;

/**
 * @author dsporn
 *
 */
public class GameletNotFoundException extends GameletException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4257269404892050128L;

	public GameletNotFoundException()
	{
		super();
	}

	public GameletNotFoundException(String message)
	{
		super(message);
	}

	public GameletNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public GameletNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GameletNotFoundException(Throwable cause)
	{
		super(cause);
	}

}
