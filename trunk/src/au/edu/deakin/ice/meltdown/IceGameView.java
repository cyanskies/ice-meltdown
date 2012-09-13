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

	private static final String mName = IceGameView.class.getSimpleName();
	private final Snowman mSnowman = new Snowman(R.drawable.ic_launcher);
	private final GameObject mGround = new GameObject(R.drawable.ground);
	private final TextObject mLive = new TextObject("", color.primary_text_light);
	private final TextObject mScore = new TextObject("", color.primary_text_light);
	private final GameObject mSnowBall = new GameObject(R.drawable.snowball);
	
	private final LinkedList<Threat> mThreatList = new LinkedList<Threat>();
	private ThreatGenerator mGen;

	private final int ThreatGenerateTime = 60; 
	private int ThreatGenerateCount = ThreatGenerateTime;
	private int live = 5;
	private int score = 0;
	
	private boolean mDamageProtection = false;
	private static final int mMaxDamageCount = 20;
	private int mDamageCount;
	
	//private final int TOUCHMAX = 10;
	//private int mTouchCount = 0;
	private Vector2 mTouchPos;
	
	private float mHorizontal = 0;
	
	private float Vert1, Vert2, Vert3;
	private int Vert_Target = 2; //these are for moving forwards and backwards
	
	public IceGameView(Context context) {
		super(context);
		Log.d(mName, "Creating gameview");
		// TODO Auto-generated constructor stub
	}
	
	
	//@Override
	public void Init() {
		mHorizontal = mScreenSize.y - 50;
		mGround.setPosition(-5.f, mHorizontal);
		mSnowBall.setPosition(0.f, mHorizontal - mSnowBall.getBounds().size.y);
		
		Vert2 = mScreenSize.x / 2;
		Vert1 = Vert2 /2;
		Vert3 = Vert2 + Vert1;
		
		mSnowman.setMoveSpeed(Vert1 / 10);
		
		mSnowman.setPosition(Vert2, mHorizontal - mSnowman.getBounds().size.y);
		mLive.setPosition(50.f,  50.f);		
		mScore.setPosition(150.f,  50.f);
		//the values passed here control the positions the threats are spawned at, these can be tweeked as needed.
		//Ive made the flying threat high based on the size of the player sprite, if we choose the change the player sprite size(probably to make him taller
		// since he's a little short) these values should still work.
		mGen = new ThreatGenerator(mScreenSize.x, mGround.getBounds().position.y, mGround.getBounds().position.y - mSnowman.getBounds().size.y + (mSnowman.getBounds().size.y / 4));
	}
	
	//@Override
	public void Update(){
		//Log.d(mName, "Starting Update step");
		if(live <= 0){
			saveScore();
			
			mParent.runOnUiThread(new Runnable() {
				public void run() {
					changeView(new ScoreView(mParent.getApplicationContext()));
				}
			});
		}
		
		if(mDamageCount >= mMaxDamageCount)
			mDamageProtection = false;
		
		--ThreatGenerateCount;
		if(ThreatGenerateCount <= 0){
			++score;
			mThreatList.add(mGen.Generate(mSnowman.getBounds().position.x));
			ThreatGenerateCount = ThreatGenerateTime;
		}
		
		ArrayList<Integer> removeList = new ArrayList<Integer>();
		
		for(GameObject o : mThreatList){
			o.update();
			
			if(o.getBounds().position.x + o.getBounds().size.x < 0)
				removeList.add(mThreatList.indexOf(o));
			if(o.getBounds().position.y > mScreenSize.y)
				removeList.add(mThreatList.indexOf(o));
		}
		
		for(Integer i : removeList){
			mThreatList.remove(i);
		}
		
		mLive.setText("Score: " + live);
		mSnowman.update();
		
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
					mSnowman.setState(Snowman.IDLE);
			}
			else{
				if(current < target)
					mSnowman.setState(Snowman.IDLE);
			}
		}
		
		CheckCollisions();
	}
	
	private void CheckCollisions(){
		Rect r;
		
		//push the snowman up if he's sinking into the ground
		if(mSnowman.getBounds().intersects(mGround.getBounds())){
			r = mSnowman.getBounds().GetOverlapRect(mGround.getBounds());
			mSnowman.move(0,  -r.size.y);
			
			//if we've hit the ground then we should return to the idle state
			if(mSnowman.getState() == Snowman.FALLING)
				mSnowman.setState(Snowman.IDLE); // 2 == IDLE
		}
		
		for(Threat o : mThreatList){
			if(!mDamageProtection && !o.isHit() && mSnowman.getBounds().intersects(o.getBounds())){
				live--;
				
				o.hit();
				mDamageProtection = true;
				mDamageCount = 0;
				//take away health, 
				//destroy colliding object
			}
		}
	}
	
	//@Override
	public void Draw(Canvas canvas){
		//Log.d(mName, "Starting Draw step");
		// clear, go through each entity and call draw, then call display
		clear(canvas);
		
		if(mDamageProtection && mDamageCount++ % 2 == 0)
			draw(mSnowman);
		else if(!mDamageProtection)
			draw(mSnowman);
		
		draw(mGround);
		draw(mLive);
		draw(mSnowBall);
		
		for(Threat o : mThreatList){
			draw(o);
		}
		
		display(canvas);
	}
	
	//@Override
	public boolean onTouchEvent(MotionEvent event) {
		//mTouchCount = 0;
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			Log.d(mName, "Pointer Down at (" + event.getX() + ", " + event.getY() + ")");
			mTouchPos = new Vector2(event.getX(), event.getY());
			if(mSnowman.IsIdle()){
				
				float ySize = mSnowman.getBounds().size.y;
				mSnowman.setState(Snowman.DUCK);
				mSnowman.move(0, ySize - mSnowman.getBounds().size.y);
			}
			
				//mSnowman.Jump(20);
		}
		else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
			Log.d(mName, "Pointer Up at (" + event.getX() + ", " + event.getY() + ")");
			Vector2 delta = new Vector2(event.getX(), event.getY());
			delta.sub(mTouchPos);

			float value = Math.abs(delta.x) + Math.abs(delta.y);
			Log.d(mName, "Pointer move value (" + value + ")");
			if(value > 30){
				if(mSnowman.getState() == Snowman.DUCK && Math.abs(delta.y) > Math.abs(delta.x))
				{
					mSnowman.Jump(20);
					return false;
				}
				else {
					if(delta.x < 0 && Vert_Target > 0) { //Something is wrong in this code, but I'm not sure what?
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
