/**
 *
 */
package com.sporniket.libre.game.canvas;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import com.sporniket.libre.game.canvas.descriptor.CanvasGameDescriptor;
import com.sporniket.libre.game.canvas.descriptor.CanvasGameDescriptorUtils;
import com.sporniket.libre.game.canvas.descriptor.GraphicalDefinitionSpecs.Axis;
import com.sporniket.libre.io.Encoding;
import com.sporniket.libre.io.parser.properties.SyntaxErrorException;
import com.sporniket.libre.lang.url.UrlProviderException;

/**
 * Unit testing for {@link CanvasGameDescriptor}.
 *
 * @author dsporn
 *
 */
public class TestCanvasGameDescriptor
{
	private void doTestSampleDescriptor(final CanvasGameDescriptor descriptor)
	{
		assertThat(descriptor.getBaseUrlSpecs().getBaseUrlForData(), is("classpath:demo/game/assets"));
		assertThat(descriptor.getBaseUrlSpecs().getBaseUrlForPictures(), is("gfx-{gdef}"));
		assertThat(descriptor.getBaseUrlSpecs().getBaseUrlForSprites(), is("sprites"));
		assertThat(descriptor.getBaseUrlSpecs().getBaseUrlForSoundEffects(), is("sound-fx"));
		assertThat(descriptor.getBaseUrlSpecs().getBaseUrlForJukebox(), is("sound-music"));

		assertThat(descriptor.getCanvasManagerSpecs().getBufferingNames().length, is(3));
		assertThat(descriptor.getCanvasManagerSpecs().getBufferingNames()[0], is("screen0"));
		assertThat(descriptor.getCanvasManagerSpecs().getBufferingNames()[1], is("screen1"));
		assertThat(descriptor.getCanvasManagerSpecs().getBufferingNames()[2], is("screen2"));
		assertThat(descriptor.getCanvasManagerSpecs().getCanvasSpecs().length, is(6));
		assertThat(descriptor.getCanvasManagerSpecs().getCanvasSpecs()[0], is("screen0:offScreen"));
		assertThat(descriptor.getCanvasManagerSpecs().getCanvasSpecs()[1], is("screen1:offScreen"));
		assertThat(descriptor.getCanvasManagerSpecs().getCanvasSpecs()[2], is("screen2:offScreen"));
		assertThat(descriptor.getCanvasManagerSpecs().getCanvasSpecs()[3], is("tileset:url:spritesheet.png"));
		assertThat(descriptor.getCanvasManagerSpecs().getCanvasSpecs()[4], is("background:urlList:back01.png, back02.png, ..."));
		assertThat(descriptor.getCanvasManagerSpecs().getCanvasSpecs()[5], is("main:offScreen"));

		assertThat(descriptor.getCanvasSpecs(), is(not(nullValue())));
		assertThat(descriptor.getCanvasSpecs().length, is(2));
		assertThat(descriptor.getCanvasSpecs()[0].getPrefix(), is("qHD"));
		assertThat(descriptor.getCanvasSpecs()[0].getWidth(), is(960));
		assertThat(descriptor.getCanvasSpecs()[0].getHeight(), is(540));
		assertThat(descriptor.getCanvasSpecs()[1].getPrefix(), is("FHD"));
		assertThat(descriptor.getCanvasSpecs()[1].getWidth(), is(1920));
		assertThat(descriptor.getCanvasSpecs()[1].getHeight(), is(1080));

		assertThat(descriptor.getGraphicalDefinitionsSpecs().getAxis(), is(Axis.HORIZONTAL));
		assertThat(descriptor.getGraphicalDefinitionsSpecs().getTresholds().size(), is(2));
		final Iterator<Integer> _tresholds = descriptor.getGraphicalDefinitionsSpecs().getTresholds().iterator();
		assertThat(_tresholds.next(), is(1600));
		assertThat(_tresholds.next(), is(3200));

		Map<String, Object> _values = descriptor.getGraphicalDefinitionsSpecs().getData(0);
		assertThat(_values.get("gdef"), is("qHD"));
		assertThat(_values.get("scaleFactor"), is(1));
		assertThat(_values.get("gridSize"), is(40));
		assertThat(_values.get("sampleConst"), is(20));

		_values = descriptor.getGraphicalDefinitionsSpecs().getData(1);
		assertThat(_values.get("gdef"), is("FHD"));
		assertThat(_values.get("scaleFactor"), is(2));
		assertThat(_values.get("gridSize"), is(80));
		assertThat(_values.get("sampleConst"), is(20));
	}

	@Test
	public void testApplyValues() throws UrlProviderException, IOException, SyntaxErrorException
	{
		final CanvasGameDescriptor _descriptor = CanvasGameDescriptorUtils.load("classpath:demo/game/game.properties",
				Encoding.ISO_8859_1);
		final HashMap<String, String> _values = new HashMap<>();
		_values.put("gdef", Integer.toString(1));
		final CanvasGameDescriptor _usableDescriptor = CanvasGameDescriptorUtils.applyValues(_descriptor, _values);

		assertThat(_usableDescriptor.getBaseUrlSpecs().getBaseUrlForPictures(), is("gfx-1"));
	}

	@Test
	public void testClone() throws UrlProviderException, IOException, SyntaxErrorException, CloneNotSupportedException
	{
		final CanvasGameDescriptor _descriptorSource = CanvasGameDescriptorUtils.load("classpath:demo/game/game.properties",
				Encoding.ISO_8859_1);
		final CanvasGameDescriptor _descriptor = (CanvasGameDescriptor) _descriptorSource.clone();
		doTestSampleDescriptor(_descriptor);
	}

	@Test
	public void testLoadDescriptor() throws UrlProviderException, IOException, SyntaxErrorException
	{
		final CanvasGameDescriptor _descriptor = CanvasGameDescriptorUtils.load("classpath:demo/game/game.properties",
				Encoding.ISO_8859_1);
		doTestSampleDescriptor(_descriptor);
	}

}
