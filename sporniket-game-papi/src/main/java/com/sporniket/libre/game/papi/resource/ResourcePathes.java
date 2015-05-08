/**
 * 
 */
package com.sporniket.libre.game.papi.resource;

import com.sporniket.libre.game.api.sprite.ActorBankSet;

/**
 * Interface for a resource manager, that maps an identifier (<em>key</em>) with a path to resource.
 * 
 * <strong>The path is relative to the resources folder.</strong>.
 * 
 * @author David SPORN 
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public interface ResourcePathes
{
	/**
	 * Retrieve the path of the {@link ActorBankSet} mapped to the key.
	 * 
	 * @param key
	 *            the resource identifier.
	 * @return the path of the ActorBankSet.
	 * @since 0-SNAPSHOT
	 */
	String getActorBankSet(String key);

	/**
	 * Retrieve the path of the picture mapped to the key.
	 * 
	 * @param key
	 *            the resource identifier.
	 * @return the path of picture.
	 * @since 0-SNAPSHOT
	 */
	String getPicture(String key);

	/**
	 * Retrieve the path of the sound mapped to the key.
	 * 
	 * @param key
	 *            the resource identifier.
	 * @return the path of sound.
	 * @since 0-SNAPSHOT
	 */
	String getSound(String key);
}
