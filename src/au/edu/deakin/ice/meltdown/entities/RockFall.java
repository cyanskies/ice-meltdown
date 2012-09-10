/**
 * 
 */
package au.edu.deakin.ice.meltdown.entities;

/**
 * @author Steven
 *
 */
public class RockFall extends Threat {

	private static final int mFallSpeed = 3;
	
	/**
	 * @param image
	 */
	public RockFall(int image) {
		super(image);
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		move(0, mFallSpeed);
	}

}
