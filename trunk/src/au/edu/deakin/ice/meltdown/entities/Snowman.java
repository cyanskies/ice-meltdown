package au.edu.deakin.ice.meltdown.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import au.edu.deakin.ice.meltdown.GameObject;
import au.edu.deakin.ice.meltdown.R;

public class Snowman extends GameObject {

	/** THe strength of gravity on the snowman*/
	private static final float GRAVITY = 9.8f;
	/** a counter to test how long to jump for*/
	private float mJumpTime = 0;
	/** the upward strength of the jump*/
	private static final int JUMP_POWER = 15;  // must be larger than GRAVITY
	
	/** a bitmap for when the snowman is idle*/
	private Bitmap mIdle;
	/** a bitmap for when the snowman is ducking*/
	private Bitmap mDuck;
	/** a bitmap for when the snowman is jumping*/
	private Bitmap mJump;
	
	/** the current state*/
	private int mState = 2;
	
	//States
	public static final int JUMP = 0;
	public static final int FALLING = 1;
	public static final int IDLE = 2;
	public static final int DUCK = 3;
	public static final int MOVING = 4;
	
	/** record the moving direction, true for right, false for left*/
	private boolean mMoving_Right;
	/** the horizontal move speed of the snowman*/
	private float MOVE_SPEED = 25;
	
	/** set the move speed
	 * @param speed The new speed*/
	public void setMoveSpeed(float speed){
		MOVE_SPEED = speed;
	}
	
	/** set the move state
	 * @param state The new state*/
	public void setState(int state){
		mState = state;
		if(state == DUCK)
			mB = mDuck;
		else
			mB = mIdle;
	}
	
	/** gets the state
	 * @return The current state*/
	public int getState(){
		return mState;
	}
	/** Constructor
	 * @param image sets the starting bitmap, but is overridden immediately.
	 */
	public Snowman(int image) {
		super(image);
		
		mIdle = BitmapFactory.decodeResource(mR, R.drawable.snowman_idle);
		mDuck = BitmapFactory.decodeResource(mR, R.drawable.snowman_duck);
		mJump = BitmapFactory.decodeResource(mR, R.drawable.snowman_jump);
		
		mState = IDLE;
		mB = mIdle;
	}

	/** Update step for the snowman, calculate movement for this tick
	 * @param deltat The amount of time that has passed between each update */
	public void update(float deltat) {
		move(0, GRAVITY, deltat);
		
		if(mState == JUMP)
		{
			if(mJumpTime <= 0)
				mState = FALLING;
			else {
				mJumpTime -= deltat;
				move(0.0f, -JUMP_POWER, deltat);
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
				move(MOVE_SPEED, 0.0f, deltat);
			else
				move(-MOVE_SPEED, 0.0f, deltat);
		}
	}
		
	/** return if the snowman is idle
	 * @return True if idle
	 */
	public boolean IsIdle(){
		return mState == IDLE;
	}
	
	/** start a jump
	 * 
	 * @param time The time the upward portion of the jump should last for
	 */
	public void Jump(int time){
		mState = JUMP;
		mB = mJump;
		mJumpTime = time;
	}

	/** is the snowman moving right
	 * 
	 * @return True if moving right, false if moving left
	 */
	public boolean isMoving_Right() {
		return mMoving_Right;
	}
	
	/** Set the snowman's move direction
	 * 
	 * @param mMoving_Right true for right, false for left.
	 */
	public void setMoving_Dir(boolean mMoving_Right) {
		this.mMoving_Right = mMoving_Right;
	}
}
