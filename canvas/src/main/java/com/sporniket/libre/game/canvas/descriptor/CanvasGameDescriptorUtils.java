package com.sporniket.libre.game.canvas.descriptor;

import java.io.IOException;
import java.net.URL;
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
	 * @return the changed descriptor.
	 */
	public static CanvasGameDescriptor applyValues(CanvasGameDescriptor descriptor, Map<String, String> values)
	{
		for (String key : values.keySet())
		{
			String _tag = "{" + key + "}";
			String _value = values.get(key);

			// replace tag in any suitable field
			descriptor.getBaseUrlSpecs()
					.setBaseUrlForPictures(descriptor.getBaseUrlSpecs().getBaseUrlForPictures().replace(_tag, _value));
		}
		return descriptor;
	}

}
