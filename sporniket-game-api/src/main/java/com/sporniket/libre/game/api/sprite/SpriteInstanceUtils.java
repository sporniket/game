/**
 * 
 */
package com.sporniket.libre.game.api.sprite;

import com.sporniket.libre.game.api.canvas.BoxCopyMachine;

/**
 * Utility class for {@link SpriteInstance}.
 * 
 * @author dsporn
 *
 */
public abstract class SpriteInstanceUtils
{
	/**
	 * Macro to display a {@link SpriteInstance} using a {@link BoxCopyMachine}, <strong>without testing if the instance is
	 * active</strong>.
	 * 
	 * @param sprite
	 *            the sprite definition.
	 * @param copyMachine
	 *            the {@link BoxCopyMachine} to use.
	 * @param canvasSource
	 *            the source canvas id.
	 * @param canvasDest
	 *            the destination canvas id.
	 */
	public static void displaySpriteInstance(SpriteInstance sprite, BoxCopyMachine copyMachine, int canvasSource, int canvasDest)
	{
		copyMachine.copy(canvasSource, sprite.getDefinition().getSourceBox(), canvasDest, sprite.getTopLeftPosition());
	}
}
