/**
 * 
 */
package com.sporniket.libre.game.papi.swing;

import java.text.MessageFormat;

import com.sporniket.libre.game.papi.log.Logger;

/**
 * @author David SPORN 
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class SimpleLogger implements Logger
{
	private static enum LogLevel
	{
		DEBUG,
		INFO,
		WARN,
		ERROR,
		FATAL;
	}

	private static final String FORMAT_LOG = "{0} [{1}] {2}";

	/**
	 * Format for levels that are 4 char longs, to align characters.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private static final String FORMAT_LOG_SHORT_LEVEL = "{0}  [{1}] {2}";

	private final String myName;

	private final MessageFormat myLogFormatter = new MessageFormat(FORMAT_LOG);

	private final MessageFormat myLogFormatterForShortLevel = new MessageFormat(FORMAT_LOG_SHORT_LEVEL);

	private final boolean myDebugEnabled;

	private final boolean myInfoEnabled;

	private String formatLog(LogLevel level, String message)
	{
		MessageFormat _format = myLogFormatter;
		switch (level)
		{
			case INFO:
			case WARN:
				_format = myLogFormatterForShortLevel;
		}
		Object[] _params =
		{
				level, getName(), message
		};
		return _format.format(_params);
	}

	/**
	 * Get name.
	 * 
	 * @return the name
	 * @since 0-SNAPSHOT
	 */
	private String getName()
	{
		return myName;
	}

	/**
	 * @param name
	 * @since 0-SNAPSHOT
	 */
	public SimpleLogger(String name, boolean debugEnabled, boolean infoEnabled)
	{
		myName = name;
		myDebugEnabled = debugEnabled;
		myInfoEnabled = infoEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.log.Logger#debug(java.lang.String)
	 * 
	 * @since 0-SNAPSHOT
	 */
	public void debug(String message)
	{
		if (isDebugEnabled())
		{
			System.out.println(formatLog(LogLevel.DEBUG, message));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.log.Logger#info(java.lang.String)
	 * 
	 * @since 0-SNAPSHOT
	 */
	public void info(String message)
	{
		if (isInfoEnabled())
		{
			System.out.println(formatLog(LogLevel.INFO, message));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.log.Logger#warn(java.lang.String)
	 * 
	 * @since 0-SNAPSHOT
	 */
	public void warn(String message)
	{
		System.out.println(formatLog(LogLevel.WARN, message));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.log.Logger#error(java.lang.String)
	 * 
	 * @since 0-SNAPSHOT
	 */
	public void error(String message)
	{
		System.out.println(formatLog(LogLevel.ERROR, message));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.log.Logger#fatal(java.lang.String)
	 * 
	 * @since 0-SNAPSHOT
	 */
	public void fatal(String message)
	{
		System.out.println(formatLog(LogLevel.FATAL, message));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.log.Logger#isDebugEnabled()
	 * 
	 * @since 0-SNAPSHOT
	 */
	public boolean isDebugEnabled()
	{
		return myDebugEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.log.Logger#isInfoEnabled()
	 * 
	 * @since 0-SNAPSHOT
	 */
	public boolean isInfoEnabled()
	{
		return myInfoEnabled;
	}

}
