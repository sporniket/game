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
 * Test the decoding of canvas objects.
 * @author dsporn
 *
 */
public class TestJavaDecoder
{

	@Test
	public void testDecodePoint() throws ParsingErrorException
	{
		String _source = "1,2";
		Point _got = new Canvas().decodePoint(_source);
		assertThat(_got.getX(), equalTo(1));
		assertThat(_got.getY(), equalTo(2));
	}

	@Test
	public void testDecodeBox() throws ParsingErrorException
	{
		String _source = "1,2,3,4";
		Box _got = new Canvas().decodeBox(_source);
		assertThat(_got.getX(), equalTo(1));
		assertThat(_got.getY(), equalTo(2));
		assertThat(_got.getWidth(), equalTo(3));
		assertThat(_got.getHeight(), equalTo(4));
	}

	@Test
	public void testDecodeBounds() throws ParsingErrorException
	{
		String _source = "1,2,3,4";
		Bounds _got = new Canvas().decodeBounds(_source);
		assertThat(_got.getLeft(), equalTo(1));
		assertThat(_got.getTop(), equalTo(2));
		assertThat(_got.getRight(), equalTo(3));
		assertThat(_got.getBottom(), equalTo(4));
	}

	@Test
	public void testDecodeSprite() throws ParsingErrorException
	{
		String _source = "foo:1,2,3,4:5,6";
		Sprite _got = new Canvas().decodeSprite(_source);
		assertThat(_got.getId(), equalTo("foo"));
		assertThat(_got.getSourceBox().getX(), equalTo(1));
		assertThat(_got.getSourceBox().getY(), equalTo(2));
		assertThat(_got.getSourceBox().getWidth(), equalTo(3));
		assertThat(_got.getSourceBox().getHeight(), equalTo(4));
	}

}
