package au.edu.deakin.ice.meltdown;

import java.util.ArrayList;
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
	private final TextObject mScore = new TextObject("Score!!", color.primary_text_light);
	private final GameObject mSnowBall = new GameObject(R.drawable.snowball);
	
	private final LinkedList<Threat> ThreatList = new LinkedList<Threat>();
	private ThreatGenerator mGen;

	private final int ThreatGenerateTime = 60; 
	private int ThreatGenerateCount = ThreatGenerateTime;
	private int score = 5;
	
	//private final int TOUCHMAX = 10;
	//private int mTouchCount = 0;
	private Vector2 mTouchPos;
	
	private float mHorizontal = 0;
	
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
		
		mSnowman.setPosition(150.f, mHorizontal - mSnowman.getBounds().size.y);
		mScore.setPosition(50.f,  50.f);		
		
		mGen = new ThreatGenerator(mScreenSize.x, mGround.getBounds().position.y, mGround.getBounds().position.y - (mSnowman.getBounds().size.y / 2));
	}
	
	//@Override
	public void Update(){
		//Log.d(mName, "Starting Update step");
		
		--ThreatGenerateCount;
		if(ThreatGenerateCount <= 0){
			ThreatList.add(mGen.Generate());
			ThreatGenerateCount = ThreatGenerateTime;
		}
		
		ArrayList<Integer> removeList = new ArrayList<Integer>();
		
		for(GameObject o : ThreatList){
			o.update();
			
			if(o.getBounds().position.x + o.getBounds().size.x < 0)
				removeList.add(ThreatList.indexOf(o));
		}
		
		for(Integer i : removeList){
			ThreatList.remove(i);
		}
		
		mScore.setText("Score: " + score);
		mSnowman.update();
		
		CheckCollisions();
	}
	
	public void CheckCollisions(){
		Rect r;
				
		
		//push the snowman up if he's sinking into the ground
		if(mSnowman.getBounds().intersects(mGround.getBounds())){
			r = mSnowman.getBounds().GetOverlapRect(mGround.getBounds());
			mSnowman.move(0,  -r.size.y);
			
			//if we've hit the ground then we should return to the idle state
			if(mSnowman.getState() == Snowman.FALLING)
				mSnowman.setState(Snowman.IDLE); // 2 == IDLE
		}
		
		for(Threat o : ThreatList){
			if(!o.isHit() && mSnowman.getBounds().intersects(o.getBounds())){
				score--;
				
				o.hit();
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
		
		draw(mSnowman);
		draw(mGround);
		draw(mScore);
		
		for(Threat o : ThreatList){
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
				if(Math.abs(delta.y) > Math.abs(delta.x))
				{
					mSnowman.Jump(20);
					return false;
				}
				
				if(false)
					;// move horizontally?
			}
			else
				mSnowman.setState(Snowman.IDLE);
		}
		else if(event.getAction() == MotionEvent.ACTION_CANCEL)
			mSnowman.setState(Snowman.IDLE);
		
		return true;
	}
	
}
