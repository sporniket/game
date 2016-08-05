/**
 * 
 */
package com.sporniket.libre.game.pal.codec;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sporniket.libre.game.api.canvas.Box;
import com.sporniket.libre.game.api.canvas.Point;
import com.sporniket.libre.game.api.sprite.SpriteDefinition;
import com.sporniket.libre.game.api.types.canvas.Bounds;
import com.sporniket.libre.game.pal.PalException;

/**
 * Canvas type codec.
 * 
 * <p>
 * {@link Point}, {@link Box} and {@link Bounds} are compressed into a comma separated list of their values.
 * </p>
 * <p>
 * {@link SpriteDefinition} use those compressed values in a colon (':') separated values representation.
 * </p>
 * 
 * <p>
 * Any supported type can be encoded through a call like <code>encode(value)</code>. The decoding of a supported type
 * <code>MyType</code> is done by a call like <code>decodeMyType(sourceString)</code>.
 * 
 * @author dsporn
 *
 */
public class Canvas
{
	private static final String FORMAT__BOUND = "{0},{1},{2},{3}";

	private static final String FORMAT__BOX = "{0},{1},{2},{3}";

	private static final String FORMAT__POINT = "{0},{1}";

	private static final String FORMAT__SPRITE = "{0}:{1}:{2}";

	private static final String METHODNAME_ENCODE = "encode";

	private static final String PATTERN_BOUNDS = "\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*";

	private static final String PATTERN_BOX = "\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*";

	private static final String PATTERN_POINT = "\\s*(\\d+)\\s*,\\s*(\\d+)\\s*";

	private static final String PATTERN_SPRITE = "\\s*([-._0-9A-Za-z]+)\\s*[:](" + PATTERN_BOX + ")[:](" + PATTERN_POINT + ")";

	private final Map<String, Method> myClassToEncoder = new HashMap<String, Method>();

	private final MessageFormat myFormatterBound = new MessageFormat(FORMAT__BOUND);

	private final MessageFormat myFormatterBox = new MessageFormat(FORMAT__BOX);

	private final MessageFormat myFormatterPoint = new MessageFormat(FORMAT__POINT);

	private final MessageFormat myFormatterSprite = new MessageFormat(FORMAT__SPRITE);

	private final Pattern myPatternBounds = Pattern.compile(PATTERN_BOUNDS);

	private final Pattern myPatternBox = Pattern.compile(PATTERN_BOX);

	private final Pattern myPatternPoint = Pattern.compile(PATTERN_POINT);

	private final Pattern myPatternSprite = Pattern.compile(PATTERN_SPRITE);

	/**
	 * 
	 */
	public Canvas()
	{
		// TODO Auto-generated constructor stub
		Class<?>[] _supportedClasses =
		{
				Point.class, Box.class, Bounds.class, SpriteDefinition.class
		};
		initMapOfMethods(getClassToEncoder(), METHODNAME_ENCODE, _supportedClasses);
	}

	public Bounds decodeBounds(String source) throws ParsingErrorException
	{
		final Matcher _matcher = getPatternBounds().matcher(source);
		if (_matcher.matches())
		{
			final Integer _left = extractIntegerValue(source, _matcher, 1);
			final Integer _top = extractIntegerValue(source, _matcher, 2);
			final Integer _right = extractIntegerValue(source, _matcher, 3);
			final Integer _bottom = extractIntegerValue(source, _matcher, 4);
			return new Bounds().withLeft(_left).withTop(_top).withRight(_right).withBottom(_bottom);
		}
		throw new ParsingErrorException(new IllegalArgumentException(computeErrorMessage__expectedPattern(PATTERN_BOUNDS, source)));
	}

	public Box decodeBox(String source) throws ParsingErrorException
	{
		final Matcher _matcher = getPatternBox().matcher(source);
		if (_matcher.matches())
		{
			return extractMatchedBox(source, _matcher, 1);
		}
		throw new ParsingErrorException(new IllegalArgumentException(computeErrorMessage__expectedPattern(PATTERN_BOX, source)));
	}

	public Point decodePoint(String source) throws ParsingErrorException
	{
		final Matcher _matcher = getPatternPoint().matcher(source);
		if (_matcher.matches())
		{
			return extractMatchedPoint(source, _matcher, 1);
		}
		throw new ParsingErrorException(new IllegalArgumentException(computeErrorMessage__expectedPattern(PATTERN_POINT, source)));
	}

	public SpriteDefinition decodeSprite(String source) throws ParsingErrorException
	{
		final Matcher _matcher = getPatternSprite().matcher(source);
		if (_matcher.matches())
		{
			final String _id = source.substring(_matcher.start(1), _matcher.end(1));
			Box _sourceBox = extractMatchedBox(source, _matcher, 3);
			Point _hotPoint = extractMatchedPoint(source, _matcher, 8);
			return new SpriteDefinition().withId(_id).withSourceBox(_sourceBox).withHotPoint(_hotPoint);
		}
		throw new ParsingErrorException(new IllegalArgumentException(computeErrorMessage__expectedPattern(PATTERN_SPRITE, source)));
	}

	public String encode(Bounds source)
	{
		final Object[] _args = new Object[]
		{
				source.getLeft(), source.getTop(), source.getRight(), source.getBottom()
		};
		return getFormatterBound().format(_args);
	}

	public String encode(Box source)
	{
		final Object[] _args = new Object[]
		{
				source.getX(), source.getY(), source.getWidth(), source.getHeight()
		};
		return getFormatterBox().format(_args);
	}

	public String encode(Object source) throws PalException
	{
		if (null == source)
		{
			throw new PalException("Cannot encode null object");
		}
		final Map<String, Method> _mapping = getClassToEncoder();
		Class<? extends Object> _class = findSpecificMethod(_mapping, source);
		return invokeFoundEncodeMethod(_mapping, _class, source);
	}

	public String encode(Point source)
	{
		final Object[] _args = new Object[]
		{
				source.getX(), source.getY()
		};
		return getFormatterPoint().format(_args);
	}

	public String encode(SpriteDefinition source)
	{
		final String _encSourceBox = encode(source.getSourceBox());
		final String _encHotPoint = encode(source.getHotPoint());
		final Object[] _args = new Object[]
		{
				source.getId(), _encSourceBox, _encHotPoint
		};
		return getFormatterSprite().format(_args);
	}

	private String computeErrorMessage__expectedPattern(final String expected, String got)
	{
		return "'" + got + "' does not matches /" + expected + "/";
	}

	/**
	 * Macro to extract the integer value of a matched group of a pattern.
	 * 
	 * @param source
	 *            the source String from which the value is extracted.
	 * @param matcher
	 *            the matcher that contains the matched groups.
	 * @param group
	 *            the group index.
	 * @return the integer value of the matched group.
	 */
	private Integer extractIntegerValue(String source, final Matcher matcher, final int group)
	{
		final String _strValue = source.substring(matcher.start(group), matcher.end(group));
		final Integer _intValue = Integer.parseInt(_strValue);
		return _intValue;
	}

	/**
	 * Extract the matched Box.
	 * 
	 * @param source
	 *            the source containing a box description.
	 * @param matcher
	 *            the matcher that recognised the box.
	 * @param groupIndexFrom
	 *            values constituting the box start from the given group index.
	 * @return the box.
	 */
	private Box extractMatchedBox(String source, final Matcher matcher, int groupIndexFrom)
	{
		final Integer _x = extractIntegerValue(source, matcher, groupIndexFrom);
		final Integer _y = extractIntegerValue(source, matcher, groupIndexFrom + 1);
		final Integer _width = extractIntegerValue(source, matcher, groupIndexFrom + 2);
		final Integer _height = extractIntegerValue(source, matcher, groupIndexFrom + 3);
		return new Box().withX(_x).withY(_y).withWidth(_width).withHeight(_height);
	}

	/**
	 * Extract the matched Point.
	 * 
	 * @param source
	 *            the source containing a box description.
	 * @param matcher
	 *            the matcher that recognised the Point.
	 * @param groupIndexFrom
	 *            values constituting the box start from the given group index.
	 * @return the box.
	 */
	private Point extractMatchedPoint(String source, final Matcher matcher, int groupIndexFrom)
	{
		final Integer _x = extractIntegerValue(source, matcher, groupIndexFrom);
		final Integer _y = extractIntegerValue(source, matcher, groupIndexFrom + 1);
		return new Point().withX(_x).withY(_y);
	}

	/**
	 * Find the method that most specifically matches the parameter type (polymorphic at runtime).
	 * 
	 * @param mapping
	 *            the mapping of methods.
	 * @param parameter
	 *            the parameter.
	 * @return the most specific class that is a parameter of the polymorphic method.
	 * @throws PalException
	 *             when there is a problem.
	 */
	private Class<? extends Object> findSpecificMethod(final Map<String, Method> mapping, Object parameter) throws PalException
	{
		Class<? extends Object> _class = parameter.getClass();
		while (_class != null && !mapping.containsKey(_class.getName()))
		{
			_class = _class.getSuperclass();
		}
		if (null == _class)
		{
			throw new PalException("UnsupportedType : " + parameter.getClass().getName());
		}
		return _class;
	}

	private Map<String, Method> getClassToEncoder()
	{
		return myClassToEncoder;
	}

	private MessageFormat getFormatterBound()
	{
		return myFormatterBound;
	}

	private MessageFormat getFormatterBox()
	{
		return myFormatterBox;
	}

	private MessageFormat getFormatterPoint()
	{
		return myFormatterPoint;
	}

	private MessageFormat getFormatterSprite()
	{
		return myFormatterSprite;
	}

	private Pattern getPatternBounds()
	{
		return myPatternBounds;
	}

	private Pattern getPatternBox()
	{
		return myPatternBox;
	}

	private Pattern getPatternPoint()
	{
		return myPatternPoint;
	}

	private Pattern getPatternSprite()
	{
		return myPatternSprite;
	}

	/**
	 * Populate a dictionnary that get a method from a class.
	 * 
	 * @param map
	 *            the dictionnary to populate.
	 * @param methodName
	 *            the method name.
	 * @param supportedClasses
	 *            the list of class that is the parameter of each method.
	 */
	private void initMapOfMethods(final Map<String, Method> map, String methodName, Class<?>[] supportedClasses)
	{
		for (Class<?> _class : supportedClasses)
		{
			try
			{
				final Method _method = getClass().getMethod(methodName, _class);
				map.put(_class.getName(), _method);
			}
			catch (NoSuchMethodException | SecurityException _exception)
			{
				throw new RuntimeException(_exception);
			}
		}
	}

	/**
	 * Call the encode method that matches the parameter.
	 * 
	 * @param mapping
	 *            the mapping of methods.
	 * @param recognizedClass
	 *            the class to use as parameter type.
	 * @param parameter
	 *            the parameter.
	 * @return
	 * @throws PalException
	 *             when there is a problem.
	 */
	private String invokeFoundEncodeMethod(final Map<String, Method> mapping, Class<? extends Object> recognizedClass,
			Object parameter) throws PalException
	{
		try
		{
			return (String) mapping.get(recognizedClass.getName()).invoke(this, parameter);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException _exception)
		{
			throw new PalException(_exception);
		}
	}

}
