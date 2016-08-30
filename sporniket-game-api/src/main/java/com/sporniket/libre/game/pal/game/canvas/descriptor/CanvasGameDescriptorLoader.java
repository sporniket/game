/**
 * 
 */
package com.sporniket.libre.game.pal.game.canvas.descriptor;

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
