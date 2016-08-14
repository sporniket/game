/**
 * 
 */
package com.sporniket.libre.game.pal.codec;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sporniket.libre.game.api.canvas.Box;
import com.sporniket.libre.game.api.canvas.Point;
import com.sporniket.libre.game.api.sprite.SpriteDefinition;
import com.sporniket.libre.game.api.sprite.SpriteDefinitionUtils;
import com.sporniket.libre.game.api.types.canvas.Bounds;
import com.sporniket.libre.lang.regexp.FormattedInputSimpleParserFactory;

/**
 * Decoder that recognize several ways to declare a list of sprite (single sprite or tile, grid of sprite or tile, anonymous or named).
 * 
 * @author dsporn
 *
 */
public class SpriteDecoder
{

	private static final String PATTERN_SPRITE__ANONYMOUS = "sprite\u00a0 # , # , # , # @ # , #";

	private static final String PATTERN_SPRITE__NAMED = "sprite\u00a0 $ : # , # , # , # @ # , #";

	private static final String PATTERN_SPRITE_GRID__ANONYMOUS = "spriteGrid\u00a0 # x # : # , # , # , # @ # , #";

	private static final String PATTERN_SPRITE_GRID__NAMED = "spriteGrid\u00a0 $ : # x # : # , # , # , # @ # , #";

	private static final String PATTERN_TILE__ANONYMOUS = "tile\u00a0 # , # , # , #";

	private static final String PATTERN_TILE__NAMED = "tile\u00a0 $ : # , # , # , #";

	private static final String PATTERN_TILE_GRID__ANONYMOUS = "tileGrid\u00a0 # x # : # , # , # , #";

	private static final String PATTERN_TILE_GRID__NAMED = "tileGrid\u00a0 $ : # x # : # , # , # , #";

	private final Pattern myPatternSpriteAnonymous = FormattedInputSimpleParserFactory.getSimpleParser(PATTERN_SPRITE__ANONYMOUS);

	private final Pattern myPatternSpriteGridAnonymous = FormattedInputSimpleParserFactory
			.getSimpleParser(PATTERN_SPRITE_GRID__ANONYMOUS);

	private final Pattern myPatternSpriteGridNamed = FormattedInputSimpleParserFactory.getSimpleParser(PATTERN_SPRITE_GRID__NAMED);

	private final Pattern myPatternSpriteNamed = FormattedInputSimpleParserFactory.getSimpleParser(PATTERN_SPRITE__NAMED);

	private final Pattern myPatternTileAnonymous = FormattedInputSimpleParserFactory.getSimpleParser(PATTERN_TILE__ANONYMOUS);

	private final Pattern myPatternTileGridAnonymous = FormattedInputSimpleParserFactory
			.getSimpleParser(PATTERN_TILE_GRID__ANONYMOUS);

	private final Pattern myPatternTileGridNamed = FormattedInputSimpleParserFactory.getSimpleParser(PATTERN_TILE_GRID__NAMED);

	private final Pattern myPatternTileNamed = FormattedInputSimpleParserFactory.getSimpleParser(PATTERN_TILE__NAMED);

	/**
	 * 
	 */
	public SpriteDecoder()
	{
	}

	public List<SpriteDefinition> decode(String source) throws ParsingErrorException
	{
		List<SpriteDefinition> _result;
		_result = tryToParseTileAnonymous(source);
		if (null != _result)
		{
			return _result;
		}
		_result = tryToParseTileNamed(source);
		if (null != _result)
		{
			return _result;
		}
		_result = tryToParseSpriteAnonymous(source);
		if (null != _result)
		{
			return _result;
		}
		_result = tryToParseSpriteNamed(source);
		if (null != _result)
		{
			return _result;
		}
		_result = tryToParseTileGridAnonymous(source);
		if (null != _result)
		{
			return _result;
		}
		_result = tryToParseTileGridNamed(source);
		if (null != _result)
		{
			return _result;
		}
		_result = tryToParseSpriteGridAnonymous(source);
		if (null != _result)
		{
			return _result;
		}
		_result = tryToParseSpriteGridNamed(source);
		if (null != _result)
		{
			return _result;
		}
		throw new ParsingErrorException("invalid sprite definition : '" + source + "'");
	}

	private Pattern getPatternSpriteAnonymous()
	{
		return myPatternSpriteAnonymous;
	}

	private Pattern getPatternSpriteGridAnonymous()
	{
		return myPatternSpriteGridAnonymous;
	}

	private Pattern getPatternSpriteGridNamed()
	{
		return myPatternSpriteGridNamed;
	}

	private Pattern getPatternSpriteNamed()
	{
		return myPatternSpriteNamed;
	}

	private Pattern getPatternTileAnonymous()
	{
		return myPatternTileAnonymous;
	}

	private Pattern getPatternTileGridAnonymous()
	{
		return myPatternTileGridAnonymous;
	}

	private Pattern getPatternTileGridNamed()
	{
		return myPatternTileGridNamed;
	}

	private Pattern getPatternTileNamed()
	{
		return myPatternTileNamed;
	}

	private List<SpriteDefinition> tryToParseSpriteAnonymous(String source)
	{
		List<SpriteDefinition> _result = null;
		Matcher _match = getPatternSpriteAnonymous().matcher(source);
		if (_match.matches())
		{
			_result = new ArrayList<SpriteDefinition>(1);
			SpriteDefinition _sprite = SpriteDefinitionUtils.createSprite(Integer.parseInt(_match.group(1)),
					Integer.parseInt(_match.group(2)), Integer.parseInt(_match.group(3)), Integer.parseInt(_match.group(4)),
					Integer.parseInt(_match.group(5)), Integer.parseInt(_match.group(6)));
			_result.add(_sprite);
		}
		return _result;
	}

	private List<SpriteDefinition> tryToParseSpriteGridAnonymous(String source)
	{
		List<SpriteDefinition> _result = null;
		Matcher _match = getPatternSpriteGridAnonymous().matcher(source);
		if (_match.matches())
		{
			_result = SpriteDefinitionUtils.createSpritesFromGrid(Integer.parseInt(_match.group(1)),
					Integer.parseInt(_match.group(2)), Integer.parseInt(_match.group(3)), Integer.parseInt(_match.group(4)),
					Integer.parseInt(_match.group(5)), Integer.parseInt(_match.group(6)), Integer.parseInt(_match.group(7)),
					Integer.parseInt(_match.group(8)));
		}
		return _result;
	}

	private List<SpriteDefinition> tryToParseSpriteGridNamed(String source)
	{
		List<SpriteDefinition> _result = null;
		Matcher _match = getPatternSpriteGridNamed().matcher(source);
		if (_match.matches())
		{
			_result = SpriteDefinitionUtils.createSpritesFromGrid(_match.group(1), Integer.parseInt(_match.group(2)),
					Integer.parseInt(_match.group(3)), Integer.parseInt(_match.group(4)), Integer.parseInt(_match.group(5)),
					Integer.parseInt(_match.group(6)), Integer.parseInt(_match.group(7)), Integer.parseInt(_match.group(8)),
					Integer.parseInt(_match.group(9)));
		}
		return _result;
	}

	private List<SpriteDefinition> tryToParseSpriteNamed(String source)
	{
		List<SpriteDefinition> _result = null;
		Matcher _match = getPatternSpriteNamed().matcher(source);
		if (_match.matches())
		{
			_result = new ArrayList<SpriteDefinition>(1);
			SpriteDefinition _sprite = SpriteDefinitionUtils.createSprite(_match.group(1), Integer.parseInt(_match.group(2)),
					Integer.parseInt(_match.group(3)), Integer.parseInt(_match.group(4)), Integer.parseInt(_match.group(5)),
					Integer.parseInt(_match.group(6)), Integer.parseInt(_match.group(7)));
			_result.add(_sprite);
		}
		return _result;
	}

	private List<SpriteDefinition> tryToParseTileAnonymous(String source)
	{
		List<SpriteDefinition> _result = null;
		Matcher _match = getPatternTileAnonymous().matcher(source);
		if (_match.matches())
		{
			_result = new ArrayList<SpriteDefinition>(1);
			SpriteDefinition _sprite = SpriteDefinitionUtils.createSprite(Integer.parseInt(_match.group(1)),
					Integer.parseInt(_match.group(2)), Integer.parseInt(_match.group(3)), Integer.parseInt(_match.group(4)), 0, 0);
			_result.add(_sprite);
		}
		return _result;
	}

	private List<SpriteDefinition> tryToParseTileGridAnonymous(String source)
	{
		List<SpriteDefinition> _result = null;
		Matcher _match = getPatternTileGridAnonymous().matcher(source);
		if (_match.matches())
		{
			_result = SpriteDefinitionUtils.createSpritesFromGrid(Integer.parseInt(_match.group(1)),
					Integer.parseInt(_match.group(2)), Integer.parseInt(_match.group(3)), Integer.parseInt(_match.group(4)),
					Integer.parseInt(_match.group(5)), Integer.parseInt(_match.group(6)), 0, 0);
		}
		return _result;
	}

	private List<SpriteDefinition> tryToParseTileGridNamed(String source)
	{
		List<SpriteDefinition> _result = null;
		Matcher _match = getPatternTileGridNamed().matcher(source);
		if (_match.matches())
		{
			_result = SpriteDefinitionUtils.createSpritesFromGrid(_match.group(1), Integer.parseInt(_match.group(2)),
					Integer.parseInt(_match.group(3)), Integer.parseInt(_match.group(4)), Integer.parseInt(_match.group(5)),
					Integer.parseInt(_match.group(6)), Integer.parseInt(_match.group(7)), 0, 0);
		}
		return _result;
	}

	private List<SpriteDefinition> tryToParseTileNamed(String source)
	{
		List<SpriteDefinition> _result = null;
		Matcher _match = getPatternTileNamed().matcher(source);
		if (_match.matches())
		{
			_result = new ArrayList<SpriteDefinition>(1);
			SpriteDefinition _sprite = SpriteDefinitionUtils.createSprite(_match.group(1), Integer.parseInt(_match.group(2)),
					Integer.parseInt(_match.group(3)), Integer.parseInt(_match.group(4)), Integer.parseInt(_match.group(5)), 0, 0);
			_result.add(_sprite);
		}
		return _result;
	}

}
