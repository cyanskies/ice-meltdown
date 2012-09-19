package au.edu.deakin.ice.meltdown.entities;

import au.edu.deakin.ice.meltdown.R;

public class ThreatGenerator {

	/** Float values indicating the positions to spawn ground and flying objects.*/
	private float screenEdgeX, Ground, Flying;
	/** number of threats created so far*/
	private int threatCount = 0;
	
	/** Constructor
	 * 
	 * @param x The horizontal size of the screen
	 * @param g The height of the ground object
	 * @param f The height to create flying objects
	 */
	public ThreatGenerator(float x, float g, float f) {
		screenEdgeX = x; Ground = g; Flying = f;
	}
	
	/** Generates a new threat based on the number of threats created so far
	 * 
	 * @param playerX The x position of the player, used for spawning RockFalls
	 * @return A new threat
	 */
	public Threat Generate(float playerX) {
		++threatCount;
		Threat out;
		
		if(threatCount % 2 == 0) // every second threat is a stump
		{
			out = new Threat(R.drawable.stump);
			out.setPosition(screenEdgeX, Ground - out.getBounds().size.y);
		}
		else if(threatCount % 15 == 0) // every 15'th threat is a rockfall
		{
			out = new RockFall(R.drawable.rocks);
			out.setPosition(playerX, 0 - out.getBounds().size.y); //can just use y == 0, since the coord systems origin is in the top left
		}
		else if(threatCount % 5 == 0) // every 5'th threat is a ravine
		{
			out = new Threat(R.drawable.ravine);
			out.setPosition(screenEdgeX, Ground - 1);
		}
		else // the intervening odd numbered threats are flying
		{
			out = new Threat(R.drawable.bird);
			out.setPosition(screenEdgeX, Flying - out.getBounds().size.y);
		}
		
		return out;
	}

}
