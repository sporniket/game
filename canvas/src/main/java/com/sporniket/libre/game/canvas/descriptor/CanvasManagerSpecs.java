/**
 *
 */
package com.sporniket.libre.game.canvas.descriptor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sporniket.libre.io.parser.properties.SyntaxErrorException;
import com.sporniket.libre.lang.regexp.FormattedInputSimpleParserFactory;

/**
 * Specifications of canvas to create.
 *
 * @author dsporn
 *
 */
public class CanvasManagerSpecs
{

	private static final String FORMAT_OFFSCREEN__FULLSCREEN__BLACK = "offscreen";

	private static final String FORMAT_OFFSCREEN__FULLSCREEN__COLORED = "offscreen color $";

	private static final String FORMAT_OFFSCREEN__SIZED__BLACK = "offscreen #x#";

	private static final String FORMAT_OFFSCREEN__SIZED__COLORED = "offscreen #x# color $";

	private static final Color CLEAR = new Color(255, 255, 255, 0);

	/**
	 * Canvas names of screen sized offscreen to create for the buffering, names SHOULD NOT collide.
	 */
	private String[] myBufferingNames;

	/**
	 * Specifications of all the canvas to create (name, type and definition among offscreens, sized offscreens, single image,
	 * variable image, ...), does not include the canvas for the buffering.
	 */
	private String[] myCanvasSpecs;

	private final Pattern myOffscreenParserFullscreenBlack = FormattedInputSimpleParserFactory
			.getSimpleParser(FORMAT_OFFSCREEN__FULLSCREEN__BLACK);

	private final Pattern myOffscreenParserFullscreenColored = FormattedInputSimpleParserFactory
			.getSimpleParser(FORMAT_OFFSCREEN__FULLSCREEN__COLORED);

	private final Pattern myOffscreenParserSizedBlack = FormattedInputSimpleParserFactory
			.getSimpleParser(FORMAT_OFFSCREEN__SIZED__BLACK);

	private final Pattern myOffscreenParserSizedColored = FormattedInputSimpleParserFactory
			.getSimpleParser(FORMAT_OFFSCREEN__SIZED__COLORED);

	public String[] getBufferingNames()
	{
		return myBufferingNames;
	}

	public String[] getCanvasSpecs()
	{
		return myCanvasSpecs;
	}

	public List<CanvasManagerSpecEntry> getEntries(CanvasSpecs selectedCanvasSpecs) throws SyntaxErrorException
	{
		final List<CanvasManagerSpecEntry> _result = new ArrayList<>(getCanvasSpecs().length);
		for (final String _rawSpec : getCanvasSpecs())
		{
			// ..parsing
			String _toParse = _rawSpec.trim();

			final int _posSep = _toParse.indexOf(":");
			if (0 > _posSep)
			{
				throw new SyntaxErrorException("canvas manager specs should follow 'name:...' pattern");
			}
			final String _name = _toParse.substring(0, _posSep);
			_toParse = _toParse.substring(_posSep + 1);

			if (_toParse.startsWith("url:"))
			{
				final String _url = _toParse.substring("url:".length());
				_result.add(new CanvasManagerSpecEntryUrl(_name, _url));
				continue;
			}

			Matcher _matcher = getOffscreenParserFullscreenBlack().matcher(_toParse);
			if (_matcher.matches())
			{
				_result.add(new CanvasManagerSpecEntryOffscreen(_name, Color.BLACK, selectedCanvasSpecs.getWidth(),
						selectedCanvasSpecs.getHeight()));
				continue;
			}

			_matcher = getOffscreenParserFullscreenColored().matcher(_toParse);
			if (_matcher.matches())
			{
				_result.add(new CanvasManagerSpecEntryOffscreen(_name, getEntries__parseColor(_matcher.group(1)),
						selectedCanvasSpecs.getWidth(), selectedCanvasSpecs.getHeight()));
				continue;
			}

			_matcher = getOffscreenParserSizedBlack().matcher(_toParse);
			if (_matcher.matches())
			{
				_result.add(new CanvasManagerSpecEntryOffscreen(_name, Color.BLACK, Integer.parseInt(_matcher.group(1)),
						Integer.parseInt(_matcher.group(2))));
				continue;
			}

			_matcher = getOffscreenParserSizedColored().matcher(_toParse);
			if (_matcher.matches())
			{
				_result.add(new CanvasManagerSpecEntryOffscreen(_name, getEntries__parseColor(_matcher.group(3)),
						Integer.parseInt(_matcher.group(1)), Integer.parseInt(_matcher.group(2))));
				continue;
			}

			throw new SyntaxErrorException("unrecognized :'" + _rawSpec + "'");
		}

		return _result;
	}

	private Color getEntries__parseColor(String colorName) throws SyntaxErrorException
	{
		try
		{
			Color _color = Color.BLACK;
			if ("CLEAR".equals(colorName.toUpperCase()))
			{
				_color = CLEAR;
			}
			else
			{
				_color = (Color) Color.class.getField(colorName).get(null);

			}
			return _color;
		}
		catch (IllegalAccessException | NoSuchFieldException _exception)
		{
			throw new SyntaxErrorException("the color name is not 'clear' nor a constant of the Color class.");
		}
	}

	private Pattern getOffscreenParserFullscreenBlack()
	{
		return myOffscreenParserFullscreenBlack;
	}

	private Pattern getOffscreenParserFullscreenColored()
	{
		return myOffscreenParserFullscreenColored;
	}

	private Pattern getOffscreenParserSizedBlack()
	{
		return myOffscreenParserSizedBlack;
	}

	private Pattern getOffscreenParserSizedColored()
	{
		return myOffscreenParserSizedColored;
	}

	public void setBufferingNames(String[] bufferingNames)
	{
		myBufferingNames = bufferingNames;
	}

	public void setCanvasSpecs(String[] canvasSpecs)
	{
		myCanvasSpecs = canvasSpecs;
	}

}
