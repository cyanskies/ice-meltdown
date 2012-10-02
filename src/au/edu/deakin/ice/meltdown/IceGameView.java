package au.edu.deakin.ice.meltdown;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import au.edu.deakin.ice.meltdown.entities.Snowman;
import au.edu.deakin.ice.meltdown.entities.Threat;
import au.edu.deakin.ice.meltdown.entities.ThreatGenerator;

public class IceGameView extends GameView {
	
	/** The previous time measured by System.currentTimeMillis() */
	private long previousTime;
	/** The change in time evaluated to approxmately 1 if the game is running at the full frame rate */
	private float deltat;
	/** class name for logging*/
	private static final String mName = IceGameView.class.getSimpleName();
	/** snowman object, represents player character handles player state and drawing of the player character*/
	private final Snowman mSnowman = new Snowman(R.drawable.ic_launcher);
	
	/** ground sprites, ground1 is for gameplay logic, ground 2 and 3 are for animating the moving snow*/
	private final GameObject mGround = new GameObject(R.drawable.ground),
			mGround2 = new GameObject(R.drawable.ground2),
			mGround3 = new GameObject(R.drawable.ground2);
	
	/** text object for displaying life*/
	private final TextObject mLive = new TextObject("empty", color.primary_text_light);
	/** text object for displaying score*/
	private final TextObject mScore = new TextObject("empty", color.primary_text_light);
	/** Sprite to represent the snowball*/
	private final GameObject mSnowBall = new GameObject(R.drawable.snowball);
	
	/** list of current threat objects*/
	private final LinkedList<Threat> mThreatList = new LinkedList<Threat>();
	/** generator for spawning new threats*/
	private ThreatGenerator mGen;

	/** number of game ticks between new threats*/
	private static final int ThreatGenerateTime = 100; 
	
	/**counter to keep track of game ticks*/
	private int ThreatGenerateCount = ThreatGenerateTime;
	
	/** number of hits the snowman can take*/
	private int live = 5;
	
	/** score*/
	private int score = 0;
	
	/** damage protection is recorded here*/
	private boolean mDamageProtection = false;
	
	/** the number of ticks to protect against damage for*/
	private static final int mMaxDamageCount = 20;
	/** then umber of ticks that have been protected so far*/
	private int mDamageCount;
	
	//private final int TOUCHMAX = 10;
	//private int mTouchCount = 0;
	/**the position of the first touch, in any gesture*/
	private Vector2 mTouchPos;
	
	/** the horizontal height of the ground*/
	private float mHorizontal = 0;
	
	/** the speed the ground sprite animates at*/
	private static final float mGroundMoveSpeed = 5;
	/** the move positions*/
	private float Vert1, Vert2, Vert3;
	/** the current move target*/
	private int Vert_Target = 2; //these are for moving forwards and backwards
	
	/** sound handles for playing sounds*/
	private int skisound, skiducksound, skijump, music; //, skijump2;
	
	/** constructor
	 * 
	 * @param context App context for loading resources
	 */
	public IceGameView(Context context) {
		super(context);
		Log.d(mName, "Creating gameview");
		
		skisound = mSound.load(R.raw.skisound);
		skiducksound = mSound.load(R.raw.skisound2);
		skijump = mSound.load(R.raw.skijump);
		music = mSound.load(R.raw.music);
		
		// TODO Auto-generated constructor stub
	}
	
	
	//@Override
	/** Init the game values and set everything to it's starting position
	 * 
	 */
	public void Init() {
		// Set the previous time variable's initial amount.
		previousTime = System.currentTimeMillis();
		
		mHorizontal = mScreenSize.y - 50;
		//this one is for gameplay logic
		mGround.setPosition(0.f, mHorizontal);
		
		//these two are for visuals
		mGround2.setPosition(0.f, mHorizontal);
		mGround3.setPosition(mGround2.getBounds().position.x + mGround2.getBounds().size.x, mHorizontal);
		mSnowBall.setPosition(0.f, mHorizontal - mSnowBall.getBounds().size.y);
		
		Vert2 = mScreenSize.x / 2;
		Vert1 = Vert2 /2;
		Vert3 = Vert2 + Vert1;
		
		mSnowman.setMoveSpeed(Vert1 / 10);
		
		mSnowman.setPosition(Vert2, mHorizontal - mSnowman.getBounds().size.y);
		mLive.setPosition(50.f,  50.f);		
		mScore.setPosition(150.f,  50.f);
		//the values passed here control the positions the threats are spawned at, these can be tweaked as needed.
		//I've made the flying threat high based on the size of the player sprite, if we choose the change the player sprite size(probably to make him taller
		// since he's a little short) these values should still work.
		mGen = new ThreatGenerator(mScreenSize.x, mGround.getBounds().position.y, mGround.getBounds().position.y - mSnowman.getBounds().size.y + (mSnowman.getBounds().size.y / 6));
		//not currently used for anything
		//skijump2 = mSound.load(R.raw.skijump2);
		
		mSound.play(skisound, true);
		mSound.play(skiducksound, true);
		
		//pause the duck ski sound, so we can resume it later
		mSound.pause(skiducksound);
		
		//and start the music sound on a loop
		mSound.play(music,  true);
	}
	
	//@Override
	/** update everything's movement and check for collisions and win conditions
	 * 
	 */
	public void Update(){
		// Get the delta T in terms of frames per second, so it will ideally be close to 1 every frame for consistency
		deltat = (System.currentTimeMillis() - previousTime) / (1000 / 20);
		previousTime = System.currentTimeMillis();
		//Log.d(mName, "Starting Update step");
		//check life
		if(live <= 0){
			saveScore();
			
			mParent.runOnUiThread(new Runnable() {
				public void run() {
					changeView(new ScoreView(mParent.getApplicationContext()));
				}
			});
		}
		
		//move ground
		mGround2.move(-mGroundMoveSpeed, 0, deltat);
		mGround3.move(-mGroundMoveSpeed, 0, deltat);
		
		float g1 = mGround2.getBounds().position.x + mGround2.getBounds().size.x;
		float g2 = mGround3.getBounds().position.x + mGround3.getBounds().size.x;
		
		if(g1 < 0)
			mGround2.setPosition(g2, mHorizontal);
		
		if(g2 < 0)
			mGround3.setPosition(g1, mHorizontal);
		
		//check damage protection		
		if(mDamageCount >= mMaxDamageCount)
			mDamageProtection = false;
		
		
		//generate new threats
		--ThreatGenerateCount;
		if(ThreatGenerateCount <= 0){
			++score;
			mThreatList.add(mGen.Generate(mSnowman.getBounds().position.x));
			ThreatGenerateCount = ThreatGenerateTime;
		}
		
		
		//remove dead threats
		ArrayList<Integer> removeList = new ArrayList<Integer>();
		
		for(Threat o : mThreatList){
			o.update(deltat);
			
			if(o.getBounds().position.x + o.getBounds().size.x < 0)
				removeList.add(mThreatList.indexOf(o));
			if(o.getBounds().position.y > mScreenSize.y)
				removeList.add(mThreatList.indexOf(o));
		}
		
		for(Integer i : removeList){
			mThreatList.remove(i);
		}
		
		
		//update UI
		mLive.setText("Life: " + live);
		mScore.setText("Score: " + score);
		mSnowman.update(deltat);
		
		
		//do move action if snowman is moving
		if(mSnowman.getState() == Snowman.MOVING){
			float target = 0;
			
			if(Vert_Target == 1)
				target = Vert1;
			else if(Vert_Target == 2)
				target = Vert2;
			else if(Vert_Target == 3)
				target = Vert3;
			
			float current = mSnowman.getBounds().position.x;
			if(mSnowman.isMoving_Right()){
				if(current > target)
				{
					mSnowman.setState(Snowman.IDLE);
					//mSnowman.setPosition(target, mHorizontal - mSnowman.getBounds().size.y);
				}
			}
			else{
				if(current < target)
				{
					mSnowman.setState(Snowman.IDLE);
					//mSnowman.setPosition(target, mHorizontal - mSnowman.getBounds().size.y);
				}
			}
		}
		
		CheckCollisions();
	}
	
	/** check the snowman against all of the threats
	 * 
	 */
	private void CheckCollisions(){
		Rect r;
		
		//push the snowman up if he's sinking into the ground
		if(mSnowman.getBounds().intersects(mGround.getBounds())){
			r = mSnowman.getBounds().GetOverlapRect(mGround.getBounds());
			
			mSnowman.move(0,  -r.size.y, 1);
			
			//if we've hit the ground then we should return to the idle state
			if(mSnowman.getState() == Snowman.FALLING)
			{
				mSound.resume(skisound);
				mSnowman.setState(Snowman.IDLE); // 2 == IDLE
			}
		}
		
		//test for hits against each of the threats
		for(Threat o : mThreatList){
			if(!mDamageProtection && !o.isHit() && mSnowman.getBounds().intersects(o.getBounds())){
				live--;
				
				o.hit();
				mDamageProtection = true;
				mDamageCount = 0;
				//take away health, 
			}
		}
	}
	
	//@Override
	/** draw all interface elements threats world objects(ground) and the snowman
	 * @param canvas the canvas to draw to.
	 */
	public void Draw(Canvas canvas){
		//Log.d(mName, "Starting Draw step");
		// clear, go through each entity and call draw, then call display
		clear(canvas);
		
		if(mDamageProtection && mDamageCount++ % 2 == 0)
			draw(mSnowman);
		else if(!mDamageProtection)
			draw(mSnowman);

		draw(mLive);
		draw(mScore);
		draw(mSnowBall);
		
		draw(mGround2);
		draw(mGround3);
		
		for(Threat o : mThreatList){
			draw(o);
		}
		
		display(canvas);
	}
	
	//@Override
	/** handle user interaction
	 * 
	 */
	public boolean onTouchEvent(MotionEvent event) {
		//mTouchCount = 0;
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			Log.d(mName, "Pointer Down at (" + event.getX() + ", " + event.getY() + ")");
			mTouchPos = new Vector2(event.getX(), event.getY());
			if(mSnowman.IsIdle()){
				
				float ySize = mSnowman.getBounds().size.y;
				mSound.pause(skisound);
				mSound.resume(skiducksound);
				mSnowman.setState(Snowman.DUCK);
				mSnowman.move(0, ySize - mSnowman.getBounds().size.y, 1);
			}
			
				//mSnowman.Jump(20);
		}
		else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
			Log.d(mName, "Pointer Up at (" + event.getX() + ", " + event.getY() + ")");
			Vector2 delta = new Vector2(event.getX(), event.getY());
			delta.sub(mTouchPos);

			mSound.pause(skiducksound);
			float value = Math.abs(delta.x) + Math.abs(delta.y);
			Log.d(mName, "Pointer move value (" + value + ")");
			if(value > 30){
				if(mSnowman.getState() == Snowman.DUCK && Math.abs(delta.y) > Math.abs(delta.x))
				{
					mSound.play(skijump, false);
					mSnowman.Jump(20);
					return false;
				}
				else {
					mSound.resume(skisound);
					if(delta.x < 0 && Vert_Target > 1) { //Something is wrong in this code, but I'm not sure what?
						Vert_Target--;
						mSnowman.setMoving_Dir(false);
						mSnowman.setState(Snowman.MOVING);
					}
					else if(delta.x > 0 && Vert_Target < 3) {
						Vert_Target++;
						mSnowman.setMoving_Dir(true);
						mSnowman.setState(Snowman.MOVING);
					}
					else
						return false;

				}
			}
			else
				mSnowman.setState(Snowman.IDLE);
		}
		else if(event.getAction() == MotionEvent.ACTION_CANCEL)
			mSnowman.setState(Snowman.IDLE);
		
		return true;
	}
	
	/** pass the current score back to MainActivity
	 * 
	 */
	private void saveScore(){
		MainActivity main = (MainActivity) mParent;
		int[] scores = main.getScores();
		String[] dates = main.getScoreDates();
		
		if(scores == null || dates == null)
			return;
		
		int smallest = 0;
		for(int i = 0; i < scores.length ; ++i)
			if(scores[i] < scores[smallest])
				smallest = i;
		
		if (score > scores[smallest]){
			scores[smallest] = score;
			
			DateFormat df = new SimpleDateFormat("dd/MM/yy");
            String formattedDate = df.format(new Date());
            
			dates[smallest] = formattedDate;
		}	
	}
	
}
