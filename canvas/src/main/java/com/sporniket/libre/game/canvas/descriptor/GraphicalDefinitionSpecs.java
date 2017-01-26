/**
 *
 */
package com.sporniket.libre.game.canvas.descriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sporniket.libre.lang.regexp.FormattedInputSimpleParserFactory;

/**
 * A specification to decide the graphical definition according to the screen size.
 *
 * @author dsporn
 *
 */
public class GraphicalDefinitionSpecs
{
	/**
	 * Enumeration of the available axis for deciding the graphical definition.
	 *
	 * @author dsporn
	 *
	 */
	public static enum Axis
	{
		HORIZONTAL,
		VERTICAL;
	}

	/**
	 * Enumeration of supported data types in data map specification.
	 *
	 * @author dsporn
	 *
	 */
	private static enum DataType
	{
		INTEGER,
		WORD;
	}

	private static final String[][] FORMAT_PLACEHOLDERS_DEFINITIONS =
	{
			{
					"#",
					"[-]?[0-9]+"
			},
			{
					"$",
					"[0-9A-Za-z-_.]+"
			},
			{
					"%",
					"[-]?[0-9]+([ \\t]*,[ \\t]*([-]?[0-9]+))*"
			},
			{
					"&",
					"[0-9A-Za-z-_.]+([ \\t]*,[ \\t]*([0-9A-Za-z-_.])+)*"
			}

	};

	private static final String INPUT_FORMAT__DATA = "$:$:&";

	private static final String INPUT_FORMAT__INTEGER_VALUES = "%";

	private static final String INPUT_FORMAT__STRING_VALUES = "&";

	private static final FormattedInputSimpleParserFactory PARSER_FACTORY = new FormattedInputSimpleParserFactory(
			FORMAT_PLACEHOLDERS_DEFINITIONS);

	private Axis myAxis = Axis.HORIZONTAL;

	private final Pattern myDataLineParser = PARSER_FACTORY.getParser(INPUT_FORMAT__DATA);

	private Map<String, Object> myDataMap = null;

	private final Pattern myIntegerValuesParser = PARSER_FACTORY.getParser(INPUT_FORMAT__INTEGER_VALUES);

	private final Pattern myStringValuesParser = PARSER_FACTORY.getParser(INPUT_FORMAT__STRING_VALUES);

	private final Set<Integer> myTresholds = new TreeSet<>();

	public Axis getAxis()
	{
		return myAxis;
	}

	/**
	 * Retrieve a map of values adapted to a graphical definition value, as specified in the descriptor.
	 *
	 * @param graphicalDefinitionValue
	 *            the graphical definition that will be used.
	 * @return a map of values adapted to a graphical definition value.
	 */
	public Map<String, Object> getData(int graphicalDefinitionValue)
	{
		final Map<String, Object> _result = new HashMap<>(getDataMap().size());
		for (final String _key : getDataMap().keySet())
		{
			final Object _raw = getDataMap().get(_key);
			@SuppressWarnings("unchecked")
			final List<Object> _values = (List<Object>) _raw;
			final int _index = (_values.size() > graphicalDefinitionValue) ? graphicalDefinitionValue : _values.size() - 1;
			_result.put(_key, _values.get(_index));
		}
		return _result;
	}

	/**
	 * Retrieve a map of strings adapted to a graphical definition value, as specified in the descriptor.
	 *
	 * @param graphicalDefinitionValue
	 *            the graphical definition that will be used.
	 * @return a map of strings adapted to a graphical definition value.
	 */
	public Map<String, String> getDataAsString(int graphicalDefinitionValue)
	{
		final Map<String, String> _result = new HashMap<>(getDataMap().size());
		final Map<String, Object> _values = getData(graphicalDefinitionValue);
		for (final String _key : _values.keySet())
		{
			_result.put(_key, _values.get(_key).toString());
		}
		return _result;
	}

	private Pattern getDataLineParser()
	{
		return myDataLineParser;
	}

	public Map<String, Object> getDataMap()
	{
		if (null == myDataMap)
		{
			myDataMap = new HashMap<>(0);
		}
		return myDataMap;
	}

	/**
	 * Return the suitable graphical definition value for a given {@link CanvasSpecs}.
	 * 
	 * @param specs
	 *            the {@link CanvasSpecs} that would be used.
	 * @return the suitable graphical definition value.
	 */
	public int getGraphicalDefinition(CanvasSpecs specs)
	{
		int _result = 0;
		if (!getTresholds().isEmpty())
		{
			int _value = (Axis.HORIZONTAL == getAxis()) ? specs.getWidth() : specs.getHeight();
			int _index = 0;
			for (Integer _treshold : getTresholds())
			{
				if (_value >= _treshold)
				{
					_result++;
				}
				else
				{
					break;
				}
			}
		}

		return _result;
	}

	private Pattern getIntegerValuesParser()
	{
		return myIntegerValuesParser;
	}

	private Pattern getStringValuesParser()
	{
		return myStringValuesParser;
	}

	public Set<Integer> getTresholds()
	{
		return myTresholds;
	}

	public void setAxis(Axis axis)
	{
		myAxis = axis;
	}

	public void setAxis(String axis)
	{
		myAxis = Axis.valueOf(axis.toUpperCase());
	}

	public void setDataMap(Map<String, Object> dataMap)
	{
		myDataMap = dataMap;
	}

	public void setDataMap(String[] dataMap)
	{
		final Map<String, Object> _dataMap = new HashMap<>(dataMap.length);
		for (final String _entry : dataMap)
		{
			// 1. parse data
			final Matcher _matcherData = getDataLineParser().matcher(_entry);
			if (_matcherData.matches())
			{
				// ok ==> retrieve name, type, raw value list
				final String _entryName = _matcherData.group(1);
				final DataType _entryType = DataType.valueOf(_matcherData.group(2).toUpperCase());
				final String _entryRawValues = _matcherData.group(3);
				switch (_entryType)
				{
					case INTEGER:
					{
						setDataMap__integer(_dataMap, _entryName, _entryRawValues);
					}
						break;
					case WORD:
					{
						setDataMap__word(_dataMap, _entryName, _entryRawValues);

					}
						break;
					default:
						throw new IllegalArgumentException("unsupported data type : '" + _entryType + "'");
				}
			}
			else
			{
				throw new IllegalArgumentException(
						"data line does not follows format '" + _matcherData.pattern().pattern() + "': got \"" + _entry + "\"");
			}
		}
		myDataMap = _dataMap;
	}

	private void setDataMap__integer(Map<String, Object> dataMap, String entryName, String entryRawValues)
	{
		if (getIntegerValuesParser().matcher(entryRawValues).matches())
		{
			// ok ==> tokenize, process, put a list of integers
			final String[] _rawValues = entryRawValues.split(",");
			final List<Integer> _values = new ArrayList<>(_rawValues.length);
			for (final String _rawValue : _rawValues)
			{
				_values.add(Integer.parseInt(_rawValue.trim()));
			}
			dataMap.put(entryName, _values);
		}
		else
		{
			throw new IllegalArgumentException(
					"data line does not follows format '" + INPUT_FORMAT__INTEGER_VALUES + "': got \"" + entryRawValues + "\"");
		}
	}

	private void setDataMap__word(Map<String, Object> dataMap, String entryName, String entryRawValues)
	{
		if (getStringValuesParser().matcher(entryRawValues).matches())
		{
			// ok ==> tokenize, process, put a list of strings
			final String[] _rawValues = entryRawValues.split(",");
			final List<String> _values = new ArrayList<>(_rawValues.length);
			for (final String _rawValue : _rawValues)
			{
				_values.add(_rawValue.trim());
			}
			dataMap.put(entryName, _values);
		}
		else
		{
			throw new IllegalArgumentException(
					"data line does not follows format '" + INPUT_FORMAT__STRING_VALUES + "': got \"" + entryRawValues + "\"");
		}
	}

	public void setTresholds(Set<Integer> tresholds)
	{
		if (tresholds != getTresholds())
		{
			getTresholds().clear();
			getTresholds().addAll(tresholds);
		}
	}

	public void setTresholds(String[] tresholds)
	{
		for (final String treshold : tresholds)
		{
			getTresholds().add(Integer.parseInt(treshold));
		}
	}
}
