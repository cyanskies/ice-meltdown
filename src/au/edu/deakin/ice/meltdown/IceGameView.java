package au.edu.deakin.ice.meltdown;

import java.util.LinkedList;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import au.edu.deakin.ice.meltdown.entities.Snowman;
import au.edu.deakin.ice.meltdown.entities.ThreatGenerator;

public class IceGameView extends GameView {

	private static final String mName = IceGameView.class.getSimpleName();
	private final Snowman mSnowman = new Snowman(R.drawable.ic_launcher);
	private final GameObject mGround = new GameObject(R.drawable.ground);
	private final TextObject mScore = new TextObject("Score!!", color.primary_text_light);
	
	private final LinkedList<GameObject> ObjectList = new LinkedList<GameObject>();
	private ThreatGenerator mGen;

	private final int ThreatGenerateTime = 30; 
	private int ThreatGenerateCount = ThreatGenerateTime;
	
	private float mHorizontal = 0;
	
	private float mSnowmanMove = 1.f;
	
	public IceGameView(Context context) {
		super(context);
		Log.d(mName, "Creating gameview");
		// TODO Auto-generated constructor stub
	}
	
	
	//@Override
	public void Init() {
		mHorizontal = mScreenSize.y - 50;
		mGround.setPosition(-5.f, mHorizontal);
		mSnowman.setPosition(50.f, 0);
		mScore.setPosition(50.f,  50.f);
		
		mGen = new ThreatGenerator(mScreenSize.x, mGround.getBounds().position.y, mGround.getBounds().position.y - (mSnowman.getBounds().size.y / 2));
	}
	
	//@Override
	public void Update(){
		Log.d(mName, "Starting Update step");
		Rect bounds = mSnowman.getBounds();
		if(bounds.position.x <= 0 || bounds.position.x + bounds.size.x >= mScreenSize.x)
			mSnowmanMove *= -1;
		
		--ThreatGenerateCount;
		if(ThreatGenerateCount <= 0){
			ObjectList.add(mGen.Generate());
			ThreatGenerateCount = ThreatGenerateTime;
		}
		
		for(GameObject o : ObjectList){
			o.update();
		}
		
		mSnowman.update();
		mSnowman.move(mSnowmanMove, 0);
		
		CheckCollisions();
	}
	
	public void CheckCollisions(){
		Rect r;
				
		
		//push the snowman up if he's sinking into the ground
		if(mSnowman.getBounds().intersects(mGround.getBounds())){
			r = mSnowman.getBounds().GetOverlapRect(mGround.getBounds());
			mSnowman.move(0,  -r.size.y);
		}
		
		for(GameObject o : ObjectList){
			if(mSnowman.getBounds().intersects(o.getBounds())){
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
		
		for(GameObject o : ObjectList){
			draw(o);
		}
		
		display(canvas);
	}
	
	//@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(mSnowman.IsIdle())
				mSnowman.Jump(5);
		}
		
		return super.onTouchEvent(event);
	}
	
}
