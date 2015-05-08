/**
 * 
 */
package com.sporniket.libre.game.papi.android;

import com.sporniket.libre.game.papi.log.LogFactory;
import com.sporniket.libre.game.papi.log.Logger;

/**
 * Log provider.
 * @author dsporn
 *
 */
class AndroidLoggerFactory extends LogFactory
{
	private static final AndroidLogger INSTANCE = new AndroidLogger("AndroidLogger Instance");

	/**
	 * @return the instance
	 */
	public static AndroidLogger getInstance()
	{
		return INSTANCE;
	}

	@Override
	public Logger getLoggerInstance(String name)
	{
		return new AndroidLogger(name);
	}
}
