/**
 * 
 */
package com.sporniket.libre.game.papi;

/**
 * @author David SPORN 
 *
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public interface GameGuest extends GameEnvironment
{
	/**
	 * Install the game in the game host.
	 * @param host
	 * @since 0-SNAPSHOT
	 */
	void moveIn(GameHost host) ;
}
