package com.sporniket.libre.game.canvas.descriptor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.sporniket.libre.io.Encoding;
import com.sporniket.libre.io.FileTools;
import com.sporniket.libre.io.parser.properties.SyntaxErrorException;
import com.sporniket.libre.lang.url.ClasspathProtocolAwareUrlProvider;
import com.sporniket.libre.lang.url.UrlProvider;
import com.sporniket.libre.lang.url.UrlProviderException;
import com.sporniket.libre.p3.P3;

/**
 * Utility class for {@link CanvasGameDescriptor}
 * 
 * @author dsporn
 *
 */
public class CanvasGameDescriptorUtils
{
	private static final String DIRECTIVES_NAME = "DIRECTIVES";

	/**
	 * Encoding used for the properties files of sporniket-game.
	 */
	private static final Encoding ENCODING_BUILTINS = Encoding.ISO_8859_1;

	private static final String LOCATION__DIRECTIVES = "classpath:com/sporniket/libre/game/descriptor/directives.properties";

	private static final UrlProvider URL_PROVIDER = new ClasspathProtocolAwareUrlProvider();

	/**
	 * Load a game descriptor.
	 * 
	 * @param url
	 *            the url of the descriptor, e.g. <code>classpath:demo/game/game.properties</code>.
	 * @param encoding
	 *            encoding of the descriptor, e.g. <code>Encoding.ISO_8859_1</code>.
	 * @return the descriptor.
	 * @throws UrlProviderException
	 *             when there is a problem.
	 * @throws IOException
	 *             when there is a problem.
	 * @throws SyntaxErrorException
	 *             when there is a problem.
	 */
	public static CanvasGameDescriptor load(String url, Encoding encoding)
			throws UrlProviderException, IOException, SyntaxErrorException
	{
		URL _directives = URL_PROVIDER.getUrl(LOCATION__DIRECTIVES);
		P3 _extractor = new P3(DIRECTIVES_NAME);
		FileTools.readPropertiesToListeners(_directives.openStream(), ENCODING_BUILTINS, _extractor);
		URL _url = URL_PROVIDER.getUrl(url);
		FileTools.readPropertiesToListeners(_url.openStream(), encoding, _extractor);
		return ((CanvasGameDescriptorLoader) _extractor.get("loader")).getDescriptor();
	}

	/**
	 * Replace any tag (e.g. <code>{gdef}</code> by the value provided.
	 * 
	 * @param descriptor
	 *            the descriptor to process.
	 * @param values
	 *            the values to use, e.g. <code>{gdef}</code> will be replaced with <code>descriptor.get("gdef")</code>.
	 * @return a descriptor with any tag replaced.
	 */
	public static CanvasGameDescriptor applyValues(CanvasGameDescriptor descriptor, Map<String, String> values)
	{
		CanvasGameDescriptor _clone = null;
		try
		{
			_clone = (CanvasGameDescriptor) descriptor.clone();
		}
		catch (CloneNotSupportedException _exception)
		{
			// SHOULD NOT HAPPEN !
			_exception.printStackTrace();
			System.exit(1);
		}
		CanvasGameDescriptor _result = _clone;
		for (String key : values.keySet())
		{
			String _tag = "{" + key + "}";
			String _value = values.get(key);

			// replace tag in any suitable field
			_result.getBaseUrlSpecs()
					.setBaseUrlForPictures(_result.getBaseUrlSpecs().getBaseUrlForPictures().replace(_tag, _value));
			String[] _canvasSpecs = _result.getCanvasManagerSpecs().getCanvasSpecs();
			List<String> _newSpecs = new ArrayList<>(_canvasSpecs.length);
			for (String _spec : _canvasSpecs)
			{
				_newSpecs.add(_spec.replace(_tag, _value));
			}
			_result.getCanvasManagerSpecs().setCanvasSpecs(_newSpecs.toArray(new String[_newSpecs.size()]));
		}
		return _result;
	}

}
