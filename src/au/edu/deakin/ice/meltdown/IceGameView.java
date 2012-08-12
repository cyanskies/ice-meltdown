package au.edu.deakin.ice.meltdown;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

public class IceGameView extends GameView {

	private static final String mName = IceGameView.class.getSimpleName();
	private final GameObject mSnowman = new GameObject(R.drawable.ic_launcher);
	private final GameObject mGround = new GameObject(R.drawable.ground);
	
	public IceGameView(Context context) {
		super(context);
		Log.d(mName, "Creating gameview");
		mSnowman.move(50, 550);
		mGround.move(0, 600);
		// TODO Auto-generated constructor stub
	}
	
	//@Override
	public void Update(){
		Log.d(mName, "Starting Update step");
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
	
}
