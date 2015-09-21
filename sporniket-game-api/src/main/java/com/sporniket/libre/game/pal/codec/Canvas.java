/**
 * 
 */
package com.sporniket.libre.game.pal.codec;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.sporniket.libre.game.api.types.canvas.Bounds;
import com.sporniket.libre.game.api.types.canvas.Box;
import com.sporniket.libre.game.api.types.canvas.Point;
import com.sporniket.libre.game.api.types.canvas.Sprite;
import com.sporniket.libre.game.pal.PalException;

/**
 * Canvas type codec.
 * 
 * <p>
 * {@link Point}, {@link Box} and {@link Bounds} are compressed into a comma separated list of their values.
 * </p>
 * <p>
 * {@link Sprite} use those compressed values in a colon (':') separated values representation.
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
	private static final String METHODNAME_ENCODE = "encode";

	private static final String FORMAT__POINT = "{0},{1}";

	private static final String FORMAT__BOX = "{0},{1},{2},{3}";

	private static final String FORMAT__BOUND = "{0},{1},{2},{3}";

	private static final String FORMAT__SPRITE = "{0}:{1}:{2}";

	private static final String PATTERN_POINT = "\\s*(\\d+)\\s*,\\s*(\\d+)\\s*";

	private static final String PATTERN_BOX = "\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*";

	private static final String PATTERN_BOUND = "\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*";

	private static final String PATTERN_SPRITE = "\\s*([-._0-9A-Za-z]+)\\s*:\\s*([0-9,]+)\\s*:\\s*([0-9,]+)\\s*";

	private final Map<String, Method> myClassToEncoder = new HashMap<String, Method>();

	private final MessageFormat myFormatterPoint = new MessageFormat(FORMAT__POINT);

	private final MessageFormat myFormatterBox = new MessageFormat(FORMAT__BOX);

	private final MessageFormat myFormatterBound = new MessageFormat(FORMAT__BOUND);

	private final MessageFormat myFormatterSprite = new MessageFormat(FORMAT__SPRITE);

	/**
	 * 
	 */
	public Canvas()
	{
		// TODO Auto-generated constructor stub
		Class<?>[] _supportedClasses =
		{
				Point.class, Box.class, Bounds.class, Sprite.class
		};
		initMapOfMethods(getClassToEncoder(), METHODNAME_ENCODE, _supportedClasses);
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

	public String encode(Point source)
	{
		final Object[] _args = new Object[]
		{
				source.getX(), source.getY()
		};
		return getFormatterPoint().format(_args);
	}

	public String encode(Box source)
	{
		final Object[] _args = new Object[]
		{
				source.getX(), source.getY(), source.getWidth(), source.getHeight()
		};
		return getFormatterBox().format(_args);
	}

	public String encode(Bounds source)
	{
		final Object[] _args = new Object[]
		{
				source.getLeft(), source.getTop(), source.getRight(), source.getBottom()
		};
		return getFormatterBound().format(_args);
	}

	public String encode(Sprite source)
	{
		final String _encSourceBox = encode(source.getSourceBox());
		final String _encHotPoint = encode(source.getHotPoint());
		final Object[] _args = new Object[]
		{
				source.getId(), _encSourceBox, _encHotPoint
		};
		return getFormatterSprite().format(_args);
	}

	public Point decodePoint(String source)
	{
		// TODO Auto-generated constructor stub
		return null; // FIXME
	}

	public Box decodeBox(String source)
	{
		// TODO Auto-generated constructor stub
		return null; // FIXME
	}

	public Bounds decodeBounds(String source)
	{
		// TODO Auto-generated constructor stub
		return null; // FIXME
	}

	public Sprite decodeSprite(String source)
	{
		// TODO Auto-generated constructor stub
		return null; // FIXME
	}

	private Map<String, Method> getClassToEncoder()
	{
		return myClassToEncoder;
	}

	private MessageFormat getFormatterPoint()
	{
		return myFormatterPoint;
	}

	private MessageFormat getFormatterBox()
	{
		return myFormatterBox;
	}

	private MessageFormat getFormatterBound()
	{
		return myFormatterBound;
	}

	private MessageFormat getFormatterSprite()
	{
		return myFormatterSprite;
	}

}
