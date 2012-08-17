/**
 * 
 */
package au.edu.deakin.ice.meltdown.entities;

import au.edu.deakin.ice.meltdown.GameObject;

public class Snowman extends GameObject {

	private static final float GRAVITY = 9.8f;
	private int mJumpTime = 0;
	private static final int JUMP_POWER = 15;  // must be larger than GRAVITY
	
	private int mState = 2;
	//States
	private static final int JUMP = 0;
	private static final int FALLING = 1;
	private static final int IDLE = 2;
	
	
	public void setState(int state){
		mState = state;
	}
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
				mState = FALLING;
			else {
				--mJumpTime;
				move(0, -JUMP_POWER);
			}
		}
		else if(mState == FALLING){
			
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
