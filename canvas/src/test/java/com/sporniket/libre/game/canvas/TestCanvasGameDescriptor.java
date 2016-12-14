/**
 *
 */
package com.sporniket.libre.game.canvas;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Iterator;

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
	@Test
	public void testLoadDescriptor() throws UrlProviderException, IOException, SyntaxErrorException
	{
		final CanvasGameDescriptor _descriptor = CanvasGameDescriptorUtils.load("classpath:demo/game/game.properties",
				Encoding.ISO_8859_1);
		assertThat(_descriptor.getBaseUrlSpecs().getBaseUrlForData(), is("classpath:demo/game/assets"));
		assertThat(_descriptor.getBaseUrlSpecs().getBaseUrlForPictures(), is("gfx-{gdef}"));
		assertThat(_descriptor.getBaseUrlSpecs().getBaseUrlForSprites(), is("sprites"));
		assertThat(_descriptor.getBaseUrlSpecs().getBaseUrlForSoundEffects(), is("sound-fx"));
		assertThat(_descriptor.getBaseUrlSpecs().getBaseUrlForJukebox(), is("sound-music"));

		assertThat(_descriptor.getCanvasManagerSpecs().getBufferingNames().length, is(3));
		assertThat(_descriptor.getCanvasManagerSpecs().getBufferingNames()[0], is("screen0"));
		assertThat(_descriptor.getCanvasManagerSpecs().getBufferingNames()[1], is("screen1"));
		assertThat(_descriptor.getCanvasManagerSpecs().getBufferingNames()[2], is("screen2"));
		assertThat(_descriptor.getCanvasManagerSpecs().getCanvasSpecs().length, is(6));
		assertThat(_descriptor.getCanvasManagerSpecs().getCanvasSpecs()[0], is("screen0:offScreen"));
		assertThat(_descriptor.getCanvasManagerSpecs().getCanvasSpecs()[1], is("screen1:offScreen"));
		assertThat(_descriptor.getCanvasManagerSpecs().getCanvasSpecs()[2], is("screen2:offScreen"));
		assertThat(_descriptor.getCanvasManagerSpecs().getCanvasSpecs()[3], is("tileset:url:spritesheet.png"));
		assertThat(_descriptor.getCanvasManagerSpecs().getCanvasSpecs()[4], is("background:urlList:back01.png, back02.png, ..."));
		assertThat(_descriptor.getCanvasManagerSpecs().getCanvasSpecs()[5], is("main:offScreen"));

		assertThat(_descriptor.getCanvasSpecs(), is(not(nullValue())));
		assertThat(_descriptor.getCanvasSpecs().length, is(2));
		assertThat(_descriptor.getCanvasSpecs()[0].getPrefix(), is("qHD"));
		assertThat(_descriptor.getCanvasSpecs()[0].getWidth(), is(960));
		assertThat(_descriptor.getCanvasSpecs()[0].getHeight(), is(540));
		assertThat(_descriptor.getCanvasSpecs()[1].getPrefix(), is("FHD"));
		assertThat(_descriptor.getCanvasSpecs()[1].getWidth(), is(1920));
		assertThat(_descriptor.getCanvasSpecs()[1].getHeight(), is(1080));

		assertThat(_descriptor.getGraphicalDefinitionsSpecs().getAxis(), is(Axis.HORIZONTAL));
		assertThat(_descriptor.getGraphicalDefinitionsSpecs().getTresholds().size(), is(2));
		final Iterator<Integer> _tresholds = _descriptor.getGraphicalDefinitionsSpecs().getTresholds().iterator();
		assertThat(_tresholds.next(), is(1600));
		assertThat(_tresholds.next(), is(3200));
	}

}
