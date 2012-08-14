package au.edu.deakin.ice.meltdown;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class IceGameView extends GameView {

	private static final String mName = IceGameView.class.getSimpleName();
	private final GameObject mSnowman = new GameObject(R.drawable.ic_launcher);
	private final GameObject mGround = new GameObject(R.drawable.ground);
	
	private float mSnowmanMove = -5.f;
	
	public IceGameView(Context context) {
		super(context);
		Log.d(mName, "Creating gameview");
		mGround.setPosition(-5.f, 650.f);
		mSnowman.setPosition(50.f, mGround.getBounds().position.y - mSnowman.getBounds().size.y);
		// TODO Auto-generated constructor stub
	}
	
	//@Override
	public void Update(){
		Log.d(mName, "Starting Update step");
		Rect bounds = mSnowman.getBounds();
		if(bounds.position.x <= 0 || bounds.position.x + bounds.size.x >= mScreenSize.x)
			mSnowmanMove *= -1;
		
		mSnowman.move(mSnowmanMove, 0);
	}
	
	//@Override
	public void Draw(Canvas canvas){
		Log.d(mName, "Starting Draw step");
		// clear, go through each entity and call draw, then call display
		clear(canvas);
		
		draw(mSnowman);
		draw(mGround);
		
		display(canvas);
	}
	
	//@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
		Log.d(mName, "Touch at: (" + event.getX() + ", " + event.getY() + ")");
		}
		return super.onTouchEvent(event);
	}
	
}
