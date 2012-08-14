/**
 * 
 */
package au.edu.deakin.ice.meltdown.entities;

import au.edu.deakin.ice.meltdown.GameObject;

public class Snowman extends GameObject {

	private static final float GRAVITY = 9.8f;
	private int mJumpTime = 0;
	private static final int JUMP_POWER = 20;
	
	private int mState = 1;
	//States
	private static final int JUMP = 0;
	private static final int IDLE = 1;
	
	/**
	 * @param image
	 */
	public Snowman(int image) {
		super(image);
		// TODO Auto-generated constructor stub
	}

	public void update() {
		move(0, GRAVITY);
		
		if(mState == JUMP)
		{
			if(mJumpTime == 0)
				mState = IDLE;
			else {
				--mJumpTime;
				move(0, -JUMP_POWER);
			}
		}
		else if(mState == IDLE)
		{}
	}
	
	public boolean IsIdle(){
		return mState == IDLE;
	}
	
	public void Jump(int time){
		mState = 0;
		mJumpTime = time;
	}
}
