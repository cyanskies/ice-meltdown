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
	private Bitmap mJump;
	
	private int mState = 2;
	
	//States
	public static final int JUMP = 0;
	public static final int FALLING = 1;
	public static final int IDLE = 2;
	public static final int DUCK = 3;
	public static final int MOVING = 4;
	
	private boolean mMoving_Right;
	private float MOVE_SPEED = 25;
	
	public void setMoveSpeed(float speed){
		MOVE_SPEED = speed;
	}
	
	
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
		mJump = BitmapFactory.decodeResource(mR, R.drawable.snowman_jump);
		
		mState = IDLE;
		mB = mIdle;
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
		else if(mState == IDLE){
			
		}
		else if(mState == MOVING){
			if(mMoving_Right)
				move(MOVE_SPEED, 0);
			else
				move(-MOVE_SPEED, 0);
		}
	}
		
	public boolean IsIdle(){
		return mState == IDLE;
	}
	
	public void Jump(int time){
		mState = JUMP;
		mB = mJump;
		mJumpTime = time;
	}

	public boolean isMoving_Right() {
		return mMoving_Right;
	}
	
	public void setMoving_Dir(boolean mMoving_Right) {
		this.mMoving_Right = mMoving_Right;
	}
}
