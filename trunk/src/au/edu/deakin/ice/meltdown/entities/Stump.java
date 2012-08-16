package au.edu.deakin.ice.meltdown.entities;

import au.edu.deakin.ice.meltdown.GameObject;

public class Stump extends GameObject {

	public Stump(int image) {
		super(image);
		// TODO Auto-generated constructor stub
	}

	public void update() {
		move(-5, 0);
	}
}
