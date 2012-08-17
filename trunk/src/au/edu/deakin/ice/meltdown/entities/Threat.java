package au.edu.deakin.ice.meltdown.entities;

import au.edu.deakin.ice.meltdown.GameObject;

public class Threat extends GameObject {
	
	private boolean mHit = false;

	public Threat(int image) {
		super(image);
		// TODO Auto-generated constructor stub
	}

	public boolean isHit() {
		return mHit;
	}

	public void hit() {
		this.mHit = true;
	}
	
	public void update() {
		move(-5, 0);
	}

}
