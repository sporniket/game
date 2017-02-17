/**
 *
 */
package com.sporniket.libre.game.canvas;

/**
 * Delegate to "fill" a canvas, e.g. by loading a picture.
 *
 * @author dsporn
 *
 */
public interface CanvasFiller<CanvasType>
{
	/**
	 * Actual attachment to a canvas.
	 *
	 * A call to {@link #fill(Object)} is recommended just after the attachment.
	 *
	 * @param canvas
	 *            the canvas that will be filled from now.
	 */
	void attachTo(CanvasDescriptor<CanvasType> canvas);

	/**
	 * Fills the attached canvas.
	 *
	 * @throws CanvasException
	 *             when there is a problem.
	 */
	void fill() throws CanvasException;

	/**
	 * Get the preferred height for this filler.
	 *
	 * @return the preferred height.
	 */
	int getPreferredHeight();

	/**
	 * Get the preferred width for this filler.
	 *
	 * @return the preferred width.
	 */
	int getPreferredWidth();
}
