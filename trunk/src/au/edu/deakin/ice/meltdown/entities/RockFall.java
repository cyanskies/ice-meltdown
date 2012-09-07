/**
 * 
 */
package au.edu.deakin.ice.meltdown.entities;

/**
 * @author Steven
 *
 */
public class RockFall extends Threat {

	/**
	 * @param image
	 */
	public RockFall(int image) {
		super(image);
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		move(0, 10);
	}

}
