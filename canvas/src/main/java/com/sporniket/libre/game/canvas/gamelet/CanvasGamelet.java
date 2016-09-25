/**
 * 
 */
package com.sporniket.libre.game.canvas.gamelet;

import com.sporniket.libre.game.gamelet.Gamelet;

/**
 * Model of a gamelet.
 * 
 * @author dsporn
 *
 */
public abstract class CanvasGamelet<CanvasType> extends Gamelet<CanvasGameletContext<CanvasType>>
{
	/**
	 * The rendering of the gamelet.
	 * 
	 * @param context
	 *            the gamelet context.
	 * @param cidDestination
	 *            the canvas id of the canvas into which the rendering will be made.
	 * @param cidPreviousRender
	 *            the canvas id of the previous rendering, may be invalid (negative).
	 */
	public abstract void render(CanvasGameletContext<CanvasType> context, int cidDestination, int cidPreviousRender);
}
