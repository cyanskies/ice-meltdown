/**
 * 
 */
package au.edu.deakin.ice.meltdown.entities;

import au.edu.deakin.ice.meltdown.GameObject;

public class Snowman extends GameObject {

	private static final float GRAVITY = 9.8f;
	
	/**
	 * @param image
	 */
	public Snowman(int image) {
		super(image);
		// TODO Auto-generated constructor stub
	}

	public void update() {
		move(0, GRAVITY);
	}
}
