/**
 * 
 */
package com.sporniket.libre.game.input;

/**
 * Input event from a sensor (e.g. accelerometer, gyroscope, gps, ...).
 * @author dsporn
 *
 */
public class SensorEvent extends InputEvent
{

	public SensorEvent(InputTranslator source)
	{
		super(source);
	}

}
