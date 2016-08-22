package com.sporniket.libre.game.gamelet;

public abstract class Gamelet
{

	/**
	 * <code>true</code> when the gamelet sends the backward event.
	 */
	private boolean myFinished;

	public Gamelet()
	{
		super();
	}

	/**
	 * method to call before quitting.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	public void exit() throws GameletException
	{
		doExit();
	}

	/**
	 * method to call before the first run.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	public void init() throws GameletException
	{
		doInit();
	}

	public boolean isFinished()
	{
		return myFinished;
	}

	/**
	 * method to call before running again the gamelet.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	public void rewind() throws GameletException
	{
		doRewind();
		setFinished(false);
	}

	/**
	 * Extension point : specific exit code.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected abstract void doExit() throws GameletException;

	/**
	 * Extension point : specific init code.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected abstract void doInit() throws GameletException;

	/**
	 * Extension point : specific before any run code.
	 * 
	 * @throws GameletException
	 *             when there is a problem.
	 */
	protected abstract void doRewind() throws GameletException;

	protected void setFinished(boolean finished)
	{
		myFinished = finished;
	}

}