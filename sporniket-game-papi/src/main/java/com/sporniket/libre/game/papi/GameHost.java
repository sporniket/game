/**
 * 
 */
package com.sporniket.libre.game.papi;

/**
 * Interface of the framework that will run the {@link Game}. The host provide access to all the abstractions layers of the
 * framework.
 * 
 * @author David SPORN 
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public interface GameHost extends GameEnvironment
{
	
	/**
	 * Prepare the given game to be run in the host.
	 * @param guest the game that will be run.
	 * @since 0-SNAPSHOT
	 */
	void checkIn(Game guest) ;
	
	/**
	 * Prepare the last checked in game to end : save preferences, current game progress, whatever...
	 */
	void checkOut();
	
	/**
	 * Predicate whether there is a game hosted, thus the platform can be started.
	 * @return
	 */
	boolean isGuestHosted();
}
