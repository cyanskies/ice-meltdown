package au.edu.deakin.ice.meltdown;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import au.edu.deakin.ice.meltdown.entities.Snowman;

public class IceGameView extends GameView {

	private static final String mName = IceGameView.class.getSimpleName();
	private final Snowman mSnowman = new Snowman(R.drawable.ic_launcher);
	private final GameObject mGround = new GameObject(R.drawable.ground);
	
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
	}
	
	//@Override
	public void Update(){
		//Log.d(mName, "Starting Update step");
		Rect bounds = mSnowman.getBounds();
		if(bounds.position.x <= 0 || bounds.position.x + bounds.size.x >= mScreenSize.x)
			mSnowmanMove *= -1;
		
		mSnowman.update();
		mSnowman.move(mSnowmanMove, 0);
		
		CheckCollisions();
	}
	
	public void CheckCollisions(){
		Rect r;
				
		if(mSnowman.getBounds().intersects(mGround.getBounds())){
			r = mSnowman.getBounds().GetOverlapRect(mGround.getBounds());
			mSnowman.move(0,  -r.size.y);
		}
	}
	
	//@Override
	public void Draw(Canvas canvas){
		//Log.d(mName, "Starting Draw step");
		// clear, go through each entity and call draw, then call display
		clear(canvas);
		
		draw(mSnowman);
		draw(mGround);
		
		display(canvas);
	}
	
	//@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
	
}
