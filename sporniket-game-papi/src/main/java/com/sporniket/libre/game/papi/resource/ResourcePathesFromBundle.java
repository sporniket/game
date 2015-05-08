/**
 * 
 */
package com.sporniket.libre.game.papi.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.sporniket.libre.game.papi.resource.ResourceUtils.MalformedDescriptorStringRepresentation;
import com.sporniket.libre.game.papi.resource.ResourceUtils.ResourceUtilsException;

/**
 * Implementation of the {@link ResourcePathes} that retrieve all it's resource descriptors from a {@link ResourceBundle}.
 * 
 * Each kind of resource is prefixed by a specific prefix in the ResourceBundle.
 * 
 * @author David SPORN 
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class ResourcePathesFromBundle implements ResourcePathes
{
	private Map<String, String> myActorSets = new HashMap<String, String>();

	private Map<String, String> myPictures = new HashMap<String, String>();

	private Map<String, String> mySounds = new HashMap<String, String>();

	/**
	 * Fully defined resource manager.
	 * 
	 * @param bundle
	 *            the {@link ResourceBundle} to read descriptors from.
	 * @param imagePrefix
	 *            key prefix for image descriptors.
	 * @param soundPrefix
	 *            key prefix for sound descriptors.
	 * @param actorPrefixes
	 *            key prefix for actors sets descriptors.
	 * @param context
	 *            .
	 * @throws ResourceUtilsException
	 * @since 0-SNAPSHOT
	 */
	public ResourcePathesFromBundle(ResourceBundle bundle, String imagePrefix, String soundPrefix, String actorPrefixes,
			ResourceNameContext context) throws ResourceUtilsException
	{
		// TODO Auto-generated constructor stub
		// read images
		extractImages(bundle, imagePrefix, context);
		// read sounds
		extractSounds(bundle, soundPrefix, context);
		// read actors
		extractActors(bundle, actorPrefixes, context);
	}

	/**
	 * @param bundle
	 * @param actorPrefixes
	 * @param context
	 * @throws MalformedDescriptorStringRepresentation
	 * @since 0-SNAPSHOT
	 */
	private void extractActors(ResourceBundle bundle, String actorPrefixes, ResourceNameContext context)
			throws MalformedDescriptorStringRepresentation
	{
		HashMap<String, ResourceDescriptor> _actorRegistry = new HashMap<String, ResourceDescriptor>();
		ResourceUtils.putResourceDescriptorsFromBundle(bundle, actorPrefixes, _actorRegistry);
		fillRegistry(getActorSets(), _actorRegistry, ResourceNameProviderActor.getInstance(), context);
	}

	/**
	 * @param bundle
	 * @param imagePrefix
	 * @param context
	 * @throws MalformedDescriptorStringRepresentation
	 * @since 0-SNAPSHOT
	 */
	private void extractImages(ResourceBundle bundle, String imagePrefix, ResourceNameContext context)
			throws MalformedDescriptorStringRepresentation
	{
		HashMap<String, ResourceLocalizable> _imageRegistry = new HashMap<String, ResourceLocalizable>();
		ResourceUtils.putResourceLocalizablesFromBundle(bundle, imagePrefix, _imageRegistry);
		fillRegistry(getPictures(), _imageRegistry, ResourceNameProviderImage.getInstance(), context);
	}

	/**
	 * @param bundle
	 * @param soundPrefix
	 * @param context
	 * @throws MalformedDescriptorStringRepresentation
	 * @since 0-SNAPSHOT
	 */
	private void extractSounds(ResourceBundle bundle, String soundPrefix, ResourceNameContext context)
			throws MalformedDescriptorStringRepresentation
	{
		HashMap<String, ResourceLocalizable> _soundRegistry = new HashMap<String, ResourceLocalizable>();
		ResourceUtils.putResourceLocalizablesFromBundle(bundle, soundPrefix, _soundRegistry);
		fillRegistry(getSounds(), _soundRegistry, ResourceNameProviderSound.getInstance(), context);
	}

	/**
	 * @param registryToFill
	 * @param descriptorRegistry
	 * @param resourceNameProvider
	 * @param context
	 * @since 0-SNAPSHOT
	 */
	private void fillRegistry(Map<String, String> registryToFill, HashMap<String, ? extends ResourceDescriptor> descriptorRegistry,
			ResourceNameProvider resourceNameProvider, ResourceNameContext context)
	{
		for (String _key : descriptorRegistry.keySet())
		{
			ResourceDescriptor _resource = descriptorRegistry.get(_key);
			String _name = resourceNameProvider.getName(_resource, context);
			registryToFill.put(_key, _name);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.resource.ResourceManager#getActorBankSet(java.lang.String)
	 * 
	 * @since 0-SNAPSHOT
	 */
	public String getActorBankSet(String key)
	{
		return getActorSets().get(key);
	}

	/**
	 * Get actorSets.
	 * 
	 * @return the actorSets
	 * @since 0-SNAPSHOT
	 */
	public Map<String, String> getActorSets()
	{
		return myActorSets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.resource.ResourceManager#getPicture(java.lang.String)
	 * 
	 * @since 0-SNAPSHOT
	 */
	public String getPicture(String key)
	{
		return getPictures().get(key);
	}

	/**
	 * Get pictures.
	 * 
	 * @return the pictures
	 * @since 0-SNAPSHOT
	 */
	public Map<String, String> getPictures()
	{
		return myPictures;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.game.papi.resource.ResourceManager#getSound(java.lang.String)
	 * 
	 * @since 0-SNAPSHOT
	 */
	public String getSound(String key)
	{
		return getSounds().get(key);
	}

	/**
	 * Get sound.
	 * 
	 * @return the sound
	 * @since 0-SNAPSHOT
	 */
	public Map<String, String> getSounds()
	{
		return mySounds;
	}

}
