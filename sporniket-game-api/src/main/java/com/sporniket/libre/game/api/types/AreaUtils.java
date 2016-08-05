/**
 * 
 */
package com.sporniket.libre.game.api.types;

import com.sporniket.libre.game.api.canvas.Box;
import com.sporniket.libre.game.api.canvas.Point;
import com.sporniket.libre.game.api.sprite.Actor;
import com.sporniket.libre.game.api.sprite.SequenceInstance;
import com.sporniket.libre.game.api.sprite.Sprite;
import com.sporniket.libre.game.api.sprite.SpriteBank;
import com.sporniket.libre.game.api.types.Position.Vector;
import com.sporniket.libre.game.api.types.physics.xy.PhysicVector;

/**
 * Utilities for Area.
 * 
 * @author David SPORN 
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class AreaUtils
{
	/**
	 * Convert a {@link BlocDefinition} into a {@link BoxArea}.
	 * 
	 * @param bloc
	 *            the bloc to convert.
	 * @return the boxArea.
	 */
	public static BoxArea createAreaFromBlocDefinition(Box bloc)
	{
		BoxArea _area = new BoxArea();
		_area.setLeft(bloc.getX());
		_area.setTop(bloc.getY());
		_area.setRight(bloc.getX() + bloc.getWidth() - 1);
		_area.setBottom(bloc.getY() + bloc.getHeight() - 1);
		return _area;
	}

	/**
	 * Create the box area surrounding the sprite.
	 * 
	 * @param sprite
	 *            the reference sprite.
	 * @param position
	 *            the position of the sprite.
	 * @return the box area enclosing the current sprite.
	 */
	public static BoxArea createAreaFromSprite(Sprite sprite, Point position)
	{
		BoxArea _area = new BoxArea();
		_area.setLeft(position.getX() - sprite.getHotSpotX());
		_area.setTop(position.getY() - sprite.getHotSpotY());
		_area.setRight(_area.getLeft() + sprite.getBloc().getWidth() - 1);
		_area.setBottom(_area.getTop() + sprite.getBloc().getHeight() - 1);
		return _area;
	}

	/**
	 * Create the box area surrounding the actor with it's current sprite.
	 * 
	 * @param actor
	 *            the reference actor.
	 * @param sprites
	 *            the sprite bank to retrieve its features.
	 * @return the box area enclosing the current sprite.
	 */
	public static BoxArea createAreaFromActor(Actor actor, SpriteBank sprites)
	{
		final PhysicVector _physicalPosition = actor.getPosition().getPosition();
		Point _position = new Point().withX(_physicalPosition.getX().intValue()).withY(_physicalPosition.getY().intValue());
		SequenceInstance _sequenceInstance = actor.getSequence();
		return createAreaFromSequenceInstance(_sequenceInstance, _position, sprites);
	}

	/**
	 * Create the box area surrounding the current sprite of the sequence.
	 * 
	 * @param sequenceInstance
	 *            the reference sequence.
	 * @param position
	 *            the position of the sprite.
	 * @param sprites
	 *            the sprite bank to retrieve its features.
	 * @return the box area enclosing the current sprite.
	 */
	public static BoxArea createAreaFromSequenceInstance(SequenceInstance sequenceInstance, Point position, SpriteBank sprites)
	{
		int _spriteId = sequenceInstance.getCurrentSprite();
		Sprite _sprite = sprites.get(_spriteId);
		return createAreaFromSprite(_sprite, position);
	}

}
