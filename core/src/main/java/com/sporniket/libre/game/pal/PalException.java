/**
 * 
 */
package com.sporniket.libre.game.pal;

/**
 * Unchecked exception for the Platform Abstraction layer.
 * @author dsporn
 *
 */
public class PalException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5998164682499384150L;

	/**
	 * 
	 */
	public PalException()
	{
	}

	/**
	 * @param message
	 */
	public PalException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public PalException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PalException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
