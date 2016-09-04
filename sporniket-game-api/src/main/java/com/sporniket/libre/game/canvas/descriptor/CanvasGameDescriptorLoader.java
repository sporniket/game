/**
 * 
 */
package com.sporniket.libre.game.canvas.descriptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sporniket.libre.lang.regexp.FormattedInputSimpleParserFactory;
import com.sporniket.libre.p3.builtins.WrappedObjectMapperProcessor;

/**
 * Loader for {@link CanvasGameDescriptor}
 * 
 * @author dsporn
 *
 */
public class CanvasGameDescriptorLoader extends WrappedObjectMapperProcessor
{
	private static final String INPUT_FORMAT__CANVAS_SPEC = "$ : # x #";

	private final Pattern myCanvasSpecPattern = FormattedInputSimpleParserFactory.getSimpleParser(INPUT_FORMAT__CANVAS_SPEC);

	private final CanvasGameDescriptor myDescriptor = new CanvasGameDescriptor();

	public CanvasGameDescriptor getDescriptor()
	{
		return myDescriptor;
	}

	/**
	 * Parse the list of canvas specs to instanciate the canvas specs and sets them into the descriptor.
	 * 
	 * @param name
	 *            ignored.
	 * @param value
	 *            the list of canvas specifications following the pattern <code>prexix ':' width 'x' height</code>.
	 */
	public void processCanvasSpecs(String name, String[] value)
	{
		CanvasSpecs[] _specs = new CanvasSpecs[value.length];

		for (int _index = 0; _index < value.length; _index++)
		{
			Matcher _matcher = getCanvasSpecPattern().matcher(value[_index]);
			if (_matcher.matches())
			{
				String _label = _matcher.group(0);
				int _width = Integer.parseInt(_matcher.group(2));
				int _height = Integer.parseInt(_matcher.group(3));
				String prefix = _matcher.group(1);
				_specs[_index] = new CanvasSpecs(_label, _width, _height, prefix);
			}
		}
		getDescriptor().setCanvasSpecs(_specs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.p3.builtins.WrappedObjectMapperProcessor#getObject()
	 */
	@Override
	protected Object getObject()
	{
		return getDescriptor();
	}

	private Pattern getCanvasSpecPattern()
	{
		return myCanvasSpecPattern;
	}

}
