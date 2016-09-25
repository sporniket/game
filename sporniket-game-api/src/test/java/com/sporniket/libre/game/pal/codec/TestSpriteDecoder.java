/**
 * 
 */
package com.sporniket.libre.game.pal.codec;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.sporniket.libre.game.canvas.sprite.SpriteDecoder;
import com.sporniket.libre.game.canvas.sprite.SpriteDefinition;

/**
 * Test the decoding of sprite objects.
 * 
 * @author dsporn
 *
 */
public class TestSpriteDecoder
{

	@Test
	public void testSpriteAnonymous() throws ParsingErrorException
	{
		String _source = "sprite 1,2,3,4 @ 5,6";
		List<SpriteDefinition> _got = new SpriteDecoder().decode(_source);
		assertThat(_got.size(), is(1));
		SpriteDefinition _sprite = _got.get(0);
		checkThatSpriteIsLike(_sprite, 1, 2, 3, 4, 5, 6);
	}

	@Test
	public void testSpriteGridAnonymous() throws ParsingErrorException
	{
		String _source = "spriteGrid 2x3 : 0,0,10,20 @5,10";
		List<SpriteDefinition> _got = new SpriteDecoder().decode(_source);
		assertThat(_got.size(), is(6));
		SpriteDefinition _sprite = _got.get(0);
		checkThatSpriteIsLike(_sprite, 0, 0, 10, 20, 5, 10);
		_sprite = _got.get(1);
		checkThatSpriteIsLike(_sprite, 10, 0, 10, 20, 5, 10);
		_sprite = _got.get(2);
		checkThatSpriteIsLike(_sprite, 0, 20, 10, 20, 5, 10);
	}

	@Test
	public void testSpriteGridNamed() throws ParsingErrorException
	{
		String _source = "spriteGrid foo: 2x3 : 0,0,10,20 @ 5,10";
		List<SpriteDefinition> _got = new SpriteDecoder().decode(_source);
		assertThat(_got.size(), is(6));
		SpriteDefinition _sprite = _got.get(0);
		checkThatSpriteIsLike(_sprite, 0, 0, 10, 20, 5, 10, "foo");
		_sprite = _got.get(1);
		checkThatSpriteIsLike(_sprite, 10, 0, 10, 20, 5, 10);
		_sprite = _got.get(2);
		checkThatSpriteIsLike(_sprite, 0, 20, 10, 20, 5, 10);
	}

	@Test
	public void testSpriteNamed() throws ParsingErrorException
	{
		String _source = "sprite foo:1,2,3,4 @ 5,6";
		List<SpriteDefinition> _got = new SpriteDecoder().decode(_source);
		assertThat(_got.size(), is(1));
		SpriteDefinition _sprite = _got.get(0);
		checkThatSpriteIsLike(_sprite, 1, 2, 3, 4, 5, 6, "foo");
	}

	@Test
	public void testTileAnonymous() throws ParsingErrorException
	{
		String _source = "tile 1,2,3,4";
		List<SpriteDefinition> _got = new SpriteDecoder().decode(_source);
		assertThat(_got.size(), is(1));
		SpriteDefinition _sprite = _got.get(0);
		checkThatSpriteIsLike(_sprite, 1, 2, 3, 4, 0, 0);
	}

	@Test
	public void testTileGridAnonymous() throws ParsingErrorException
	{
		String _source = "tileGrid 2x3 : 0,0,10,20";
		List<SpriteDefinition> _got = new SpriteDecoder().decode(_source);
		assertThat(_got.size(), is(6));
		SpriteDefinition _sprite = _got.get(0);
		checkThatSpriteIsLike(_sprite, 0, 0, 10, 20, 0, 0);
		_sprite = _got.get(1);
		checkThatSpriteIsLike(_sprite, 10, 0, 10, 20, 0, 0);
		_sprite = _got.get(2);
		checkThatSpriteIsLike(_sprite, 0, 20, 10, 20, 0, 0);
	}

	@Test
	public void testTileGridNamed() throws ParsingErrorException
	{
		String _source = "tileGrid foo: 2x3 : 0,0,10,20";
		List<SpriteDefinition> _got = new SpriteDecoder().decode(_source);
		assertThat(_got.size(), is(6));
		SpriteDefinition _sprite = _got.get(0);
		checkThatSpriteIsLike(_sprite, 0, 0, 10, 20, 0, 0, "foo");
		_sprite = _got.get(1);
		checkThatSpriteIsLike(_sprite, 10, 0, 10, 20, 0, 0);
		_sprite = _got.get(2);
		checkThatSpriteIsLike(_sprite, 0, 20, 10, 20, 0, 0);
	}

	@Test
	public void testTileNamed() throws ParsingErrorException
	{
		String _source = "tile foo:1,2,3,4";
		List<SpriteDefinition> _got = new SpriteDecoder().decode(_source);
		assertThat(_got.size(), is(1));
		SpriteDefinition _sprite = _got.get(0);
		checkThatSpriteIsLike(_sprite, 1, 2, 3, 4, 0, 0, "foo");
	}

	private void checkThatSpriteIsLike(SpriteDefinition sprite, int x, int y, int w, int h, int hotX, int hotY)
	{
		checkThatSpriteIsLike(sprite, x, y, w, h, hotX, hotY, null);
	}

	private void checkThatSpriteIsLike(SpriteDefinition sprite, int x, int y, int w, int h, int hotX, int hotY, String id)
	{
		assertThat(sprite.getSourceBox().getX(), is(x));
		assertThat(sprite.getSourceBox().getY(), equalTo(y));
		assertThat(sprite.getSourceBox().getWidth(), equalTo(w));
		assertThat(sprite.getSourceBox().getHeight(), equalTo(h));
		assertThat(sprite.getHotPoint().getX(), is(hotX));
		assertThat(sprite.getHotPoint().getY(), is(hotY));
		assertThat(sprite.getId(), is(id));
	}
}
