/**
 * 
 */
package com.sporniket.libre.game.papi.resource;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author David SPORN 
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class ResourceUtils
{
	public static class MalformedDescriptorStringRepresentation extends ResourceUtilsException
	{

		/**
		 * 
		 * @since 0-SNAPSHOT
		 */
		public MalformedDescriptorStringRepresentation()
		{
			super();
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param message
		 * @since 0-SNAPSHOT
		 */
		public MalformedDescriptorStringRepresentation(String message)
		{
			super(message);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param message
		 * @param cause
		 * @since 0-SNAPSHOT
		 */
		public MalformedDescriptorStringRepresentation(String message, Throwable cause)
		{
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param message
		 * @param cause
		 * @param enableSuppression
		 * @param writableStackTrace
		 * @since 0-SNAPSHOT
		 */
		public MalformedDescriptorStringRepresentation(String message, Throwable cause, boolean enableSuppression,
				boolean writableStackTrace)
		{
			super(message, cause, enableSuppression, writableStackTrace);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param cause
		 * @since 0-SNAPSHOT
		 */
		public MalformedDescriptorStringRepresentation(Throwable cause)
		{
			super(cause);
			// TODO Auto-generated constructor stub
		}

	}

	private static final class Markers
	{
		private static final String FIELD_SEPARATOR = ";";

		private static final String LOCALE_SEPARATOR = "\\|";
	}

	/**
	 * Exception throwed by those utilities.
	 * 
	 * @author David SPORN 
	 * 
	 * @version 0-SNAPSHOT
	 * @since 0-SNAPSHOT
	 */
	public static class ResourceUtilsException extends Exception
	{

		/**
		 * 
		 * @since 0-SNAPSHOT
		 */
		public ResourceUtilsException()
		{
			super();
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param message
		 * @since 0-SNAPSHOT
		 */
		public ResourceUtilsException(String message)
		{
			super(message);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param message
		 * @param cause
		 * @since 0-SNAPSHOT
		 */
		public ResourceUtilsException(String message, Throwable cause)
		{
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param message
		 * @param cause
		 * @param enableSuppression
		 * @param writableStackTrace
		 * @since 0-SNAPSHOT
		 */
		public ResourceUtilsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
		{
			super(message, cause, enableSuppression, writableStackTrace);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param cause
		 * @since 0-SNAPSHOT
		 */
		public ResourceUtilsException(Throwable cause)
		{
			super(cause);
			// TODO Auto-generated constructor stub
		}

	}

	/**
	 * Parse a string representation of a resource descriptor.
	 * 
	 * @param descriptorData
	 *            the string representation.
	 * @return the {@link ResourceDescriptor}.
	 * @throws MalformedDescriptorStringRepresentation
	 * @since 0-SNAPSHOT
	 */
	public static ResourceDescriptor parseResourceDescriptor(String descriptorData) throws MalformedDescriptorStringRepresentation
	{
		String[] _args = descriptorData.split(Markers.FIELD_SEPARATOR);
		if (_args.length < 2)
		{
			throw new MalformedDescriptorStringRepresentation(descriptorData);
		}
		ResourceDescriptor _result;
		_result = new ResourceDescriptor(_args[0], _args[1]);

		return _result;
	}

	/**
	 * Parse a string representation of a localizable resource descriptor.
	 * 
	 * @param descriptorData
	 *            the string representation.
	 * @return the {@link ResourceLocalizable}.
	 * @throws MalformedDescriptorStringRepresentation
	 * @since 0-SNAPSHOT
	 */
	public static ResourceLocalizable parseResourceDescriptorLocalizable(String descriptorData)
			throws MalformedDescriptorStringRepresentation
	{
		String[] _args = descriptorData.split(Markers.FIELD_SEPARATOR);
		if (_args.length < 3)
		{
			throw new MalformedDescriptorStringRepresentation(descriptorData);
		}
		ResourceLocalizable _result;
		if (_args[1].trim().length() > 0)
		{
			_result = new ResourceLocalizable(_args[0], _args[2], _args[1].split(Markers.LOCALE_SEPARATOR));
		}
		else
		{
			_result = new ResourceLocalizable(_args[0], _args[2]);

		}

		return _result;
	}

	/**
	 * Extract a list of {@link ResourceDescriptor} stored in a bundle, with keys sharing the same prefix.
	 * @param bundle the bundle containing the list of descriptors.
	 * @param prefix the prefix of the bundle properties that are a resource descriptor.
	 * @param registry the registry to fill
	 * @return the updated registry.
	 * @throws MalformedDescriptorStringRepresentation
	 * @since 0-SNAPSHOT
	 */
	public static Map<String, ResourceDescriptor> putResourceDescriptorsFromBundle(ResourceBundle bundle, String prefix,
			Map<String, ResourceDescriptor> registry) throws MalformedDescriptorStringRepresentation
	{
		for (Enumeration<String> _i = bundle.getKeys(); _i.hasMoreElements();)
		{
			String _key = _i.nextElement();
			if (_key.startsWith(prefix))
			{
				String _entryKey = _key.substring(prefix.length());
				ResourceDescriptor _value = parseResourceDescriptor(bundle.getString(_key));
				registry.put(_entryKey, _value);
			}
		}

		return registry;
	}

	/**
	 * Extract a list of {@link ResourceLocalizable} stored in a bundle, with keys sharing the same prefix.
	 * @param bundle the bundle containing the list of descriptors.
	 * @param prefix the prefix of the bundle properties that are a resource descriptor.
	 * @param registry the registry to fill
	 * @return the updated registry.
	 * @throws MalformedDescriptorStringRepresentation
	 * @since 0-SNAPSHOT
	 */
	public static Map<String, ResourceLocalizable> putResourceLocalizablesFromBundle(ResourceBundle bundle, String prefix,
			Map<String, ResourceLocalizable> registry) throws MalformedDescriptorStringRepresentation
	{
		for (Enumeration<String> _i = bundle.getKeys(); _i.hasMoreElements();)
		{
			String _key = _i.nextElement();
			if (_key.startsWith(prefix))
			{
				String _entryKey = _key.substring(prefix.length());
				ResourceLocalizable _value = parseResourceDescriptorLocalizable(bundle.getString(_key));
				registry.put(_entryKey, _value);
			}
		}

		return registry;
	}

}
