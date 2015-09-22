package com.sporniket.libre.game.pal.codec;

import com.sporniket.libre.game.pal.PalException;

public class ParsingErrorException extends PalException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5260065214378426955L;

	public ParsingErrorException()
	{
	}

	public ParsingErrorException(String message)
	{
		super(message);
	}

	public ParsingErrorException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ParsingErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ParsingErrorException(Throwable cause)
	{
		super(cause);
	}

}
