/**
 * 
 */
package au.edu.deakin.ice.meltdown.entities;

/** Rockfall falls from the top of the screen before being removed, it's behaviour is like Threat 
 * in all other ways.
 * @author Steven
 *
 */
public class RockFall extends Threat {

	private static final int mFallSpeed = 3;
	
	/** Constructor
	 * @param image The bitmap resource code to load
	 */
	public RockFall(int image) {
		super(image);
		// TODO Auto-generated constructor stub
	}
	
	/** Move the rockfall down at a rate of mFallSpeed per update*/
	public void update() {
		move(0, mFallSpeed);
	}

}
