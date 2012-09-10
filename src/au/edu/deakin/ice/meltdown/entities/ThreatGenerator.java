package au.edu.deakin.ice.meltdown.entities;

import au.edu.deakin.ice.meltdown.R;

public class ThreatGenerator {

	private float screenEdgeX, Ground, Flying;
	private int threatCount = 0;
	
	public ThreatGenerator(float x, float g, float f) {
		screenEdgeX = x; Ground = g; Flying = f;
	}
	
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
