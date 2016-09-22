/**
 * 
 */
package com.sporniket.libre.game.input;

/**
 * Special {@link InputTranslator}, that will listen to others {@link InputTranslator} and emit game event.
 * 
 * <p>
 * A game controller event may be generated from some keyboard events, to simulate a game controller.
 * 
 * @author dsporn
 *
 */
public class InputSimulator extends InputTranslator implements InputTranslatorListener
{

}
