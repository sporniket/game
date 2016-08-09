/**
 * 
 */
package com.sporniket.libre.game.api.sprite;

import java.util.ArrayList;
import java.util.List;

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

	/**
	 * Create a set of anonymous {@link SpriteDefinition} that form a regular grid.
	 * 
	 * @param gridColspan
	 *            number of columns of the grid.
	 * @param gridRowspan
	 *            number of rows of the grid.
	 * @param left
	 *            x coordinate of the top-left corner of the grid.
	 * @param top
	 *            y coordinate of the top-left corner of the grid.
	 * @param width
	 *            width of one sprite bloc.
	 * @param height
	 *            height of one sprite bloc.
	 * @param hotX
	 *            x coordinate of the hot point of a sprite.
	 * @param hotY
	 *            y coordinate of the hot point of a sprite.
	 * @return a list of sprite definitions.
	 */
	public static List<SpriteDefinition> createSpritesFromGrid(int gridColspan, int gridRowspan, int left, int top, int width,
			int height, int hotX, int hotY)
	{
		List<SpriteDefinition> _result = new ArrayList<SpriteDefinition>(gridColspan * gridRowspan);
		int _top = top;
		for (int _row = 0; _row < gridRowspan; _row++)
		{
			int _left = left;
			for (int _col = 0; _col < gridColspan; _col++)
			{
				SpriteDefinition _sprite = createSprite(_left, _top, width, height, hotX, hotY);
				_result.add(_sprite);
				_left += width;
			}
			_top += height;
		}
		return _result;
	}

	/**
	 * Create a set of {@link SpriteDefinition} that form a regular grid, the first sprite is given an id.
	 * 
	 * @param firstSpriteId
	 *            the id of the first sprite.
	 * @param gridColspan
	 *            number of columns of the grid.
	 * @param gridRowspan
	 *            number of rows of the grid.
	 * @param left
	 *            x coordinate of the top-left corner of the grid.
	 * @param top
	 *            y coordinate of the top-left corner of the grid.
	 * @param width
	 *            width of one sprite bloc.
	 * @param height
	 *            height of one sprite bloc.
	 * @param hotX
	 *            x coordinate of the hot point of a sprite.
	 * @param hotY
	 *            y coordinate of the hot point of a sprite.
	 * @return a list of sprite definitions.
	 */
	public static List<SpriteDefinition> createSpritesFromGrid(String firstSpriteId, int gridColspan, int gridRowspan, int left,
			int top, int width, int height, int hotX, int hotY)
	{
		List<SpriteDefinition> _result = createSpritesFromGrid(gridColspan, gridRowspan, left, top, width, height, hotX, hotY);
		_result.get(0).setId(firstSpriteId);
		return _result;
	}
}
