package au.edu.deakin.ice.meltdown.entities;

import au.edu.deakin.ice.meltdown.GameObject;

/** Threat is a gameobject that collides with the player,
 * It also moves across the screen at a constant rate.
 * @author Steven
 *
 */
public class Threat extends GameObject {
	
	/** Indicates whether the Threat has hit the player already*/
	private boolean mHit = false;

	/** Constructor
	 * @param image The bitmap resource code to use
	 */
	public Threat(int image) {
		super(image);
		// TODO Auto-generated constructor stub
	}

	/** Get the hit state of the Threat
	 * @return boolean indicating if the threat has hit anything yet
	 */
	public boolean isHit() {
		return mHit;
	}

	/** Set the hit boolean for this object*/
	public void hit() {
		this.mHit = true;
	}
	
	/** Move to the left by 5 once every update*/
	public void update() {
		move(-5, 0);
	}

}
