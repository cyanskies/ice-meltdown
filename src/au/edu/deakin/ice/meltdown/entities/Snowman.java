/**
 * 
 */
package au.edu.deakin.ice.meltdown.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import au.edu.deakin.ice.meltdown.GameObject;
import au.edu.deakin.ice.meltdown.R;

public class Snowman extends GameObject {

	private static final float GRAVITY = 9.8f;
	private int mJumpTime = 0;
	private static final int JUMP_POWER = 15;  // must be larger than GRAVITY
	
	private Bitmap mIdle;
	private Bitmap mDuck;
	
	private int mState = 2;
	
	//States
	public static final int JUMP = 0;
	public static final int FALLING = 1;
	public static final int IDLE = 2;
	public static final int DUCK = 3;
	
	
	public void setState(int state){
		mState = state;
		if(state == DUCK)
			mB = mDuck;
		else
			mB = mIdle;
	}
	
	public int getState(){
		return mState;
	}
	/**
	 * @param image
	 */
	public Snowman(int image) {
		super(image);
		
		mIdle = BitmapFactory.decodeResource(mR, R.drawable.snowman_idle);
		mDuck = BitmapFactory.decodeResource(mR, R.drawable.snowman_duck);
		
		mState = IDLE;
		mB = mIdle;
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
		else if(mState == DUCK){
			
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
