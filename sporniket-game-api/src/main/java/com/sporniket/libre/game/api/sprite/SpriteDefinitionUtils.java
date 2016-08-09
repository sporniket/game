/**
 * 
 */
package com.sporniket.libre.game.api.sprite;

import com.sporniket.libre.game.api.canvas.Box;
import com.sporniket.libre.game.api.canvas.BoxCopyMachine;
import com.sporniket.libre.game.api.canvas.Point;

/**
 * Utility class for {@link SpriteDefinition}.
 * 
 * @author dsporn
 *
 */
public abstract class SpriteDefinitionUtils
{
	/**
	 * Macro to use a {@link BoxCopyMachine} with a {@link SpriteDefinition}.
	 * 
	 * @param sprite
	 *            the sprite definition.
	 * @param position
	 *            the position of the top-left corner.
	 * @param copyMachine
	 *            the {@link BoxCopyMachine} to use.
	 * @param canvasSource
	 *            the source canvas id.
	 * @param canvasDest
	 *            the destination canvas id.
	 */
	public static void copySpriteBloc(SpriteDefinition sprite, Point position, BoxCopyMachine copyMachine, int canvasSource,
			int canvasDest)
	{
		copyMachine.copy(canvasSource, sprite.getSourceBox(), canvasDest, position);
	}

	/**
	 * Macro to create a fully defined anonymous sprite.
	 * 
	 * @param left
	 *            x coordinate of the top-left corner.
	 * @param top
	 *            y coordinate of the top-left corner.
	 * @param width
	 *            width of the bloc.
	 * @param height
	 *            height of the bloc.
	 * @param hotX
	 *            x coordinate of the hot point.
	 * @param hotY
	 *            y coordinate of the hot point.
	 * @return a sprite definition.
	 */
	public static SpriteDefinition createSprite(int left, int top, int width, int height, int hotX, int hotY)
	{
		SpriteDefinition _result = new SpriteDefinition();
		Box _sourceBox = new Box().withX(left).withY(top).withWidth(width).withHeight(height);
		Point _hotPoint = new Point().withX(hotX).withY(hotY);
		_result.withSourceBox(_sourceBox).withHotPoint(_hotPoint);
		return _result;
	}

	/**
	 * Macro to create a fully defined anonymous sprite.
	 * 
	 * @param id
	 *            name of the sprite.
	 * @param left
	 *            x coordinate of the top-left corner.
	 * @param top
	 *            y coordinate of the top-left corner.
	 * @param width
	 *            width of the bloc.
	 * @param height
	 *            height of the bloc.
	 * @param hotX
	 *            x coordinate of the hot point.
	 * @param hotY
	 *            y coordinate of the hot point.
	 * @return a sprite definition.
	 */
	public static SpriteDefinition createSprite(String id, int left, int top, int width, int height, int hotX, int hotY)
	{
		return createSprite(left, top, width, height, hotX, hotY).withId(id);
	}
}
