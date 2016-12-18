/**
 * Description of a canvas game.
 * 
 * The lifecycle of the descriptor is as described here :
 * 
 * <ol>
 * <li>Reading and parsing</li>
 * <li>Select (user or programmatically) crucial settings like canvas size.</li>
 * <li>Use selection to compute specific values (e.g. graphical definition)</li>
 * <li>In the descriptor values (e.g. url bases), substitute any tag (e.g. <code>{gdef}</code>) with the computed value.</li>
 * <li>The descriptor is ready to use for running the game.</li>
 * </ol>
 * 
 * @author dsporn
 *
 */
package com.sporniket.libre.game.canvas.descriptor;