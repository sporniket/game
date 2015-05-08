/**
 * 
 */
package com.sporniket.libre.game.papi;

import com.sporniket.libre.game.api.ResourceDefinitionLoader;

/**
 * @author David SPORN 
 *
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public interface GameEnvironment
{
	/**
	 * Provide the graphic abstraction layer.
	 * @return the graphic abstraction layer.
	 * @since 0-SNAPSHOT
	 */
	GraphicAbstractionLayerInterface getGraal();

	/**
	 * Provide the input abstraction layer.
	 * @return the input abstraction layer.
	 * @since 0-SNAPSHOT
	 */
	InputAbstractionLayerInterface getInput();

	/**
	 * Provide the resource definition loader.
	 * @return the resource definition loader.
	 * @since 0-SNAPSHOT
	 */
	ResourceDefinitionLoader getLoader();

	/**
	 * Provide the message sender.
	 * @return the message sender.
	 * @since 0-SNAPSHOT
	 */
	MessageSender getSender();
	
	/**
	 * Provide the sound abstraction layer.
	 * @return the sound abstraction layer.
	 * @since 0-SNAPSHOT
	 */
	SoundAbstractionLayerInterface getSound();
	
	/**
	 * Provide the time abstraction layer.
	 * @return the time abstraction layer.
	 * @since 0-SNAPSHOT
	 */
	TimeAbstractionLayerInterface getTime();

}
