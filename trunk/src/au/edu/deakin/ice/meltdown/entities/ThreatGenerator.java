package au.edu.deakin.ice.meltdown.entities;

import au.edu.deakin.ice.meltdown.R;

public class ThreatGenerator {

	private float screenEdgeX, Ground;
	
	public ThreatGenerator(float x, float y, float f) {
		screenEdgeX = x; Ground = y;
	}
	
	public Threat Generate() {
		Threat out = new Threat(R.drawable.stump);
		out.setPosition(screenEdgeX, Ground - out.getBounds().size.y);
		return out;
	}

}
