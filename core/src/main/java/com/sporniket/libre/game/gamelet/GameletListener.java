/**
 * 
 */
package com.sporniket.libre.game.gamelet;

import com.sporniket.libre.game.gamelet.events.Backward;
import com.sporniket.libre.game.gamelet.events.Forward;
import com.sporniket.libre.game.gamelet.events.Render;

/**
 * Interface to be implemented by a gamelet controler to be notified of events.
 * 
 * @author dsporn
 *
 */
public interface GameletListener<ContextType extends GameletContext>
{
	/**
	 * @param event
	 *            the event.
	 * @throws GameletException
	 *             when there is a problem.
	 */
	void onBackward(Backward<ContextType> event) throws GameletException;

	/**
	 * @param event
	 *            the event.
	 * @throws GameletException
	 *             when there is a problem.
	 */
	void onForward(Forward<ContextType> event) throws GameletException;

	/**
	 * @param event
	 *            the event.
	 * @throws GameletException
	 *             when there is a problem.
	 */
	void onRender(Render<ContextType> event) throws GameletException;
}
