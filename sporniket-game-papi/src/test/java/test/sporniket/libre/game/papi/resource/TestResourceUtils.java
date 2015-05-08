/**
 * 
 */
package test.sporniket.libre.game.papi.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.sporniket.libre.game.papi.resource.ResourceDescriptor;
import com.sporniket.libre.game.papi.resource.ResourceLocalizable;
import com.sporniket.libre.game.papi.resource.ResourceUtils;
import com.sporniket.libre.game.papi.resource.ResourceUtils.MalformedDescriptorStringRepresentation;

import junit.framework.TestCase;

/**
 * Test suite for {@link ResourceUtils}.
 * 
 * @author David SPORN 
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class TestResourceUtils extends TestCase
{
	private ResourceBundle myBundle;

	public void testParseResourceDescriptor()
	{
		ResourceDescriptor _result;
		try
		{
			_result = ResourceUtils.parseResourceDescriptor("foo;png");
			assertEquals("Did not extract resource path", "foo", _result.getPath());
			assertEquals("Did not extract resource type", "png", _result.getType());
		}
		catch (MalformedDescriptorStringRepresentation _exception)
		{
			fail("unexpected error : " + _exception.getClass().getName() + " -- " + _exception.getLocalizedMessage());
		}
	}

	public void testParseResourceLocalizable__noSupportedLocale()
	{
		ResourceLocalizable _result;
		try
		{
			_result = ResourceUtils.parseResourceDescriptorLocalizable("foo;;png");
			assertEquals("Did not extract resource path", "foo", _result.getPath());
			assertEquals("Did not extract resource type", "png", _result.getType());
			assertEquals("Did add unknown supported locale", 0, _result.getSupportedLocales().size());
		}
		catch (MalformedDescriptorStringRepresentation _exception)
		{
			fail("unexpected error : " + _exception.getClass().getName() + " -- " + _exception.getLocalizedMessage());
		}
	}

	public void testParseResourceLocalizable__withSupportedLocale()
	{
		ResourceLocalizable _result;
		try
		{
			_result = ResourceUtils.parseResourceDescriptorLocalizable("foo;fr|en;png");
			assertEquals("Did not extract resource path", "foo", _result.getPath());
			assertEquals("Did not extract resource type", "png", _result.getType());
			List<String> _supportedLocales = _result.getSupportedLocales();
			assertEquals("Did not extract the right number of supported locale", 2, _supportedLocales.size());
			assertEquals("The first supported locale is not fr", "fr", _supportedLocales.get(0));
			assertEquals("The second supported locale is not en", "en", _supportedLocales.get(1));
		}
		catch (MalformedDescriptorStringRepresentation _exception)
		{
			fail("unexpected error : " + _exception.getClass().getName() + " -- " + _exception.getLocalizedMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 * 
	 * @since 0-SNAPSHOT
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setBundle(ResourceBundle.getBundle(getClass().getName()+"Config"));
	}

	/**
	 * Get bundle.
	 * 
	 * @return the bundle
	 * @since 0-SNAPSHOT
	 */
	private ResourceBundle getBundle()
	{
		return myBundle;
	}

	/**
	 * Change bundle.
	 * 
	 * @param bundle
	 *            the bundle to set
	 * @since 0-SNAPSHOT
	 */
	private void setBundle(ResourceBundle bundle)
	{
		myBundle = bundle;
	}

	public void testPutResourceDescriptorsFromBundle()
	{
		try
		{
			Map<String, ResourceDescriptor> _map = ResourceUtils.putResourceDescriptorsFromBundle(getBundle(), "descriptors.",
					new HashMap<String, ResourceDescriptor>());
			assertEquals("Did not extract all the keys", 2, _map.size());
			assertTrue("Key 'foo' not found.",_map.containsKey("foo"));
			assertTrue("Key 'bar' not found.",_map.containsKey("bar"));
		}
		catch (MalformedDescriptorStringRepresentation _exception)
		{
			fail("unexpected error : " + _exception.getClass().getName() + " -- " + _exception.getLocalizedMessage());
		}
	}

	public void testPutResourceLocalizablesFromBundle()
	{
		try
		{
			Map<String, ResourceLocalizable> _map = ResourceUtils.putResourceLocalizablesFromBundle(getBundle(), "localizables.",
					new HashMap<String, ResourceLocalizable>());
			assertEquals("Did not extract all the keys", 2, _map.size());
			assertTrue("Key 'foo' not found.",_map.containsKey("foo"));
			assertTrue("Key 'bar' not found.",_map.containsKey("bar"));
		}
		catch (MalformedDescriptorStringRepresentation _exception)
		{
			fail("unexpected error : " + _exception.getClass().getName() + " -- " + _exception.getLocalizedMessage());
		}
	}
}
