/**
 * 
 */
package com.sporniket.libre.game.canvas.gamelet;

import com.sporniket.libre.game.canvas.CanvasException;

/**
 * Interface for listening to {@link UpdatedDisplayEvent}.
 * 
 * @author dsporn
 *
 */
public interface UpdatedDisplayEventListener
{
	void onUpdatedDisplay(UpdatedDisplayEvent event) throws CanvasException;
}
