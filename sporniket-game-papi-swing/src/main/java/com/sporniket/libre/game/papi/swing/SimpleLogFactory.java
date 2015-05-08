/**
 * 
 */
package com.sporniket.libre.game.papi.swing;

import java.util.HashMap;
import java.util.Map;

import com.sporniket.libre.game.papi.log.LogFactory;
import com.sporniket.libre.game.papi.log.Logger;

/**
 * @author David SPORN 
 *
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class SimpleLogFactory extends LogFactory
{
	private Map<String, Logger> myLoggers = new HashMap<String, Logger>();
	private final boolean myDebugEnabled ;
	private final boolean myInfoEnabled ;
	
	/**
	 * @param debugEnabled
	 * @param infoEnabled
	 * @since 0-SNAPSHOT
	 */
	public SimpleLogFactory(boolean debugEnabled, boolean infoEnabled)
	{
		myDebugEnabled = debugEnabled;
		myInfoEnabled = infoEnabled;
	}

	public Logger getLoggerInstance(String name)
	{
		if(!myLoggers.containsKey(name))
		{
			myLoggers.put(name, new SimpleLogger(name, myDebugEnabled, myInfoEnabled));
		}
		return myLoggers.get(name);
	}
	

}
