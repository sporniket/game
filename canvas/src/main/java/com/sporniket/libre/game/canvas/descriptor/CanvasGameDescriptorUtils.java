package com.sporniket.libre.game.canvas.descriptor;

import java.io.IOException;
import java.net.URL;

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

	public static CanvasGameDescriptor load(String url, Encoding encoding) throws UrlProviderException, IOException, SyntaxErrorException
	{
		URL _directives = URL_PROVIDER.getUrl(LOCATION__DIRECTIVES);
		P3 _extractor = new P3(DIRECTIVES_NAME) ;
		FileTools.readPropertiesToListeners(_directives.openStream(), ENCODING_BUILTINS, _extractor);
		URL _url = URL_PROVIDER.getUrl(url);
		FileTools.readPropertiesToListeners(_url.openStream(), encoding, _extractor);
		return ((CanvasGameDescriptorLoader)_extractor.get("loader")).getDescriptor() ;
	}
	
}
