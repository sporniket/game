/**
 * 
 */
package com.sporniket.libre.game.pal.codec;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.sporniket.libre.game.api.types.canvas.Bounds;
import com.sporniket.libre.game.api.types.canvas.Box;
import com.sporniket.libre.game.api.types.canvas.Point;
import com.sporniket.libre.game.api.types.canvas.Sprite;

/**
 * Tests on the encoding of canvas objects.
 * 
 * @author dsporn
 *
 */
public class TestCanvasEncoder
{
	@Test
	public void testPoint()
	{
		Point _sample = new Point().withX(1).withY(2);
		String _encoded = new Canvas().encode(_sample);
		assertThat(_encoded, equalTo("1,2"));
	}

	@Test
	public void testBox()
	{
		Box _sample = new Box().withX(1).withY(2).withWidth(3).withHeight(4);
		String _encoded = new Canvas().encode(_sample);
		assertThat(_encoded, equalTo("1,2,3,4"));
	}

	@Test
	public void testBounds()
	{
		Bounds _sample = new Bounds().withLeft(1).withTop(2).withRight(3).withBottom(4);
		String _encoded = new Canvas().encode(_sample);
		assertThat(_encoded, equalTo("1,2,3,4"));
	}

	@Test
	public void testSprite()
	{
		Point _hotPoint = new Point().withX(5).withY(6);
		Box _sourceBox = new Box().withX(1).withY(2).withWidth(3).withHeight(4);
		Sprite _sample = new Sprite().withId("foo").withSourceBox(_sourceBox).withHotPoint(_hotPoint);
		String _encoded = new Canvas().encode(_sample);
		assertThat(_encoded, equalTo("foo:1,2,3,4:5,6"));
	}
}
