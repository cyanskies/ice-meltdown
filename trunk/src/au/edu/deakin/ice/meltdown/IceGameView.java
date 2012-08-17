package au.edu.deakin.ice.meltdown;

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
	
	private final LinkedList<Threat> ThreatList = new LinkedList<Threat>();
	private ThreatGenerator mGen;

	private final int ThreatGenerateTime = 60; 
	private int ThreatGenerateCount = ThreatGenerateTime;
	private int score = 5;
	
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
		mSnowman.setPosition(50.f, mHorizontal - mSnowman.getBounds().size.y);
		mScore.setPosition(50.f,  50.f);
		
		mGen = new ThreatGenerator(mScreenSize.x, mGround.getBounds().position.y, mGround.getBounds().position.y - (mSnowman.getBounds().size.y / 2));
	}
	
	//@Override
	public void Update(){
		Log.d(mName, "Starting Update step");
		Rect bounds = mSnowman.getBounds();
		if(bounds.position.x <= 0 || bounds.position.x + bounds.size.x >= mScreenSize.x)
		{}
		else
		{
			//all com
		}
		
		--ThreatGenerateCount;
		if(ThreatGenerateCount <= 0){
			ThreatList.add(mGen.Generate());
			ThreatGenerateCount = ThreatGenerateTime;
		}
		
		for(GameObject o : ThreatList){
			o.update();
			
			if(o.getBounds().position.x + o.getBounds().size.x < 0)
				ThreatList.remove(o);
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
			mSnowman.setState(2); // 2 == IDLE
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
		Log.d(mName, "Starting Draw step");
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
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(mSnowman.IsIdle())
				mSnowman.Jump(20);
		}
		
		return super.onTouchEvent(event);
	}
	
}
