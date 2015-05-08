/**
 * 
 */
package com.sporniket.libre.game.papi.android;

import com.sporniket.libre.game.papi.log.Logger;

import android.util.Log;

//FIXME put in the sporniket-game-papi-android or sporniket-core-android
/**
 * Encapsulate android {@link Log} to have consistant log tags.
 * 
 * @author dsporn
 * 
 */
public class AndroidLogger implements Logger
{
	private String myTag;

	/**
	 * The tag to mark logs with.
	 * 
	 * @param tag
	 */
	public AndroidLogger(String tag)
	{
		super();
		myTag = tag;
	}

	/**
	 * @return the tag
	 */
	private String getTag()
	{
		return myTag;
	}

	public boolean isDebugEnabled()
	{
		return Log.isLoggable(getTag(), Log.DEBUG);
	}

	public void debug(String message)
	{
		Log.d(getTag(), message);
	}

	public void debug(String message, Throwable throwable)
	{
		Log.d(getTag(), message, throwable);
	}

	public void info(String message)
	{
		Log.i(getTag(), message);
	}

	public void info(String message, Throwable throwable)
	{
		Log.i(getTag(), message, throwable);
	}

	public void warn(String message)
	{
		Log.w(getTag(), message);
	}

	public void warn(String message, Throwable throwable)
	{
		Log.w(getTag(), message, throwable);
	}

	public void error(String message)
	{
		Log.e(getTag(), message);
	}

	public void error(String message, Throwable throwable)
	{
		Log.e(getTag(), message, throwable);
	}

	public void fatal(String message)
	{
		Log.e(getTag(), message);
		
	}

	public boolean isInfoEnabled()
	{
		return false;
	}
}
