/**
 * 
 */
package com.sporniket.libre.game.api.types;

/**
 * Model of a moving point, having position, speed and acceleration vectors.
 * 
 * Each vector can be changed directly, and the position evolve over time.
 * 
 * <p>
 * &copy; Copyright 2010-2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Game Library &#8211; api</i>.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; api</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Game Library &#8211; api</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Game Library &#8211;
 * api</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN 
 * 
 */
public class Position {
	/**
	 * Models the position for 1 dimension.
	 * 
	 * @author David SPORN
	 * 
	 */
	private static class OneDimensionPosition {
		/**
		 * Acceleration (delta v per second).
		 */
		private int myA = 0;

		/**
		 * Speed (delta x per second).
		 */
		private int myV = 0;

		/**
		 * Position (x coordinate).
		 */
		private int myX = 0;

		/**
		 * Take into account acceleration and speed to move the position.
		 * 
		 * @param msec
		 */
		public void evolve(int msec) {
			int _accel = getAcceleration() * msec / 1000;
			setSpeed(getSpeed() + _accel);
			int _move = getSpeed() * msec / 1000;
			setPosition(getPosition() + _move);
		}

		/**
		 * @return the a
		 */
		public int getAcceleration() {
			return myA;
		}

		/**
		 * @return the x
		 */
		public int getPosition() {
			return myX;
		}

		/**
		 * @return the v
		 */
		public int getSpeed() {
			return myV;
		}

		/**
		 * @param a
		 *            the a to set
		 */
		public void setAcceleration(int a) {
			myA = a;
		}

		/**
		 * @param x
		 *            the x to set
		 */
		public void setPosition(int x) {
			myX = x;
		}

		/**
		 * @param v
		 *            the v to set
		 */
		public void setSpeed(int v) {
			myV = v;
		}
	}
	
	/**
	 * Structure for exchanging 2D data (position vector, speed vector, acceleration vector).
	 * 
	 * Since it is used merely for exchanging data expressively, there is no data encapsulation.
	 * @author David SPORN
	 *
	 */
	public static class Vector {
		public int x = 0;
		public int y = 0;
		/**
		 * For classical instanciation "a la Javabeans". Or simply instanciate (0,0) vector.
		 */
		public Vector() {
		}
		/**
		 * For direct instanciation.
		 * @param x
		 * @param y
		 */
		public Vector(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	/**
	 * X coordinate.
	 */
	private OneDimensionPosition myX = new OneDimensionPosition();
	
	/**
	 * Y coordinate.
	 */
	private OneDimensionPosition myY = new OneDimensionPosition();
	
	/**
	 * Return the current acceleration vector (ax,ay coordinates)
	 * @return
	 */
	public Vector getCurrentAccelerationVector()
	{
		return new Vector(getX().getAcceleration(), getY().getAcceleration());
	}
	
	/**
	 * Return the current position vector (x,y coordinates)
	 * @return
	 */
	public Vector getCurrentPositionVector()
	{
		return new Vector(getX().getPosition(), getY().getPosition());
	}

	/**
	 * Return the current speed vector (vx,vy coordinates)
	 * @return
	 */
	public Vector getCurrentSpeedVector()
	{
		return new Vector(getX().getSpeed(), getY().getSpeed());
	}
	
	/**
	 * @return the x
	 */
	private OneDimensionPosition getX() {
		return myX;
	}
	
	/**
	 * @return the y
	 */
	private OneDimensionPosition getY() {
		return myY;
	}
	
	/**
	 * Let the position evolve.
	 * @param msec
	 */
	public void run(int msec)
	{
		getX().evolve(msec);
		getY().evolve(msec);
	}

	/**
	 * @param acceleration
	 */
	public void setCurrentAccelerationVector(Vector acceleration)
	{
		getX().setAcceleration(acceleration.x);
		getY().setAcceleration(acceleration.y);
	}

	/**
	 * @param position
	 */
	public void setCurrentPositionVector(Vector position)
	{
		getX().setPosition(position.x);
		getY().setPosition(position.y);
	}
	
	/**
	 * @param speed
	 */
	public void setCurrentSpeedVector(Vector speed)
	{
		getX().setSpeed(speed.x);
		getY().setSpeed(speed.y);
	}
	
}
