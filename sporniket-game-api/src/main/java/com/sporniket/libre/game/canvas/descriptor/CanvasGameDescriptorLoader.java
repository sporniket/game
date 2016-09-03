/**
 * 
 */
package com.sporniket.libre.game.canvas.descriptor;

import java.io.IOException;
import java.net.URL;

import com.sporniket.libre.io.Encoding;
import com.sporniket.libre.io.FileTools;
import com.sporniket.libre.io.parser.properties.SyntaxErrorException;
import com.sporniket.libre.lang.url.ClasspathProtocolAwareUrlProvider;
import com.sporniket.libre.lang.url.UrlProviderException;
import com.sporniket.libre.p3.P3;
import com.sporniket.libre.p3.builtins.WrappedObjectMapperProcessor;

/**
 * Loader for {@link CanvasGameDescriptor}
 * @author dsporn
 *
 */
public class CanvasGameDescriptorLoader extends WrappedObjectMapperProcessor
{
	private final CanvasGameDescriptor myDescriptor = new CanvasGameDescriptor() ;
	
	public CanvasGameDescriptor getDescriptor()
	{
		return myDescriptor;
	}


	public void processCanvasSpecs(String name, String[] value)
	{
		//FIXME
		//parse the list of canvas specs to instanciate the canvas specs and sets them into the descriptor.
	}

	/* (non-Javadoc)
	 * @see com.sporniket.libre.p3.builtins.WrappedObjectMapperProcessor#getObject()
	 */
	@Override
	protected Object getObject()
	{
		return getDescriptor();
	}

}
