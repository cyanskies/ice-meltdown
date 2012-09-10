package au.edu.deakin.ice.meltdown;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;


//draws the five highest personal scores, and the dates on which they were achieved.
//alsi includes a button back to the main menu(or maybe touching anywhere sends you back to the menu?
public class ScoreView extends GameView {
	private static final String mName = ScoreView.class.getSimpleName();
	private GameObject mPlay, mExit;
	
	public ScoreView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	//@Override
	public void Init() {
		mPlay = new GameObject(R.drawable.button_play);
		mExit = new GameObject(R.drawable.button_exit);	
		
		mPlay.setPosition(new Vector2(50, 10));
		mExit.setPosition(new Vector2(50, 70));
	}
			
	//@Override
	public void Update(){
				
	}
			
	//@Override
	public void Draw(Canvas canvas){
		clear(canvas);
		
		draw(mPlay);
		draw(mExit);
		
		display(canvas);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
			Log.d(mName, "Pointer Up at (" + event.getX() + ", " + event.getY() + ")");
			Vector2 delta = new Vector2(event.getX(), event.getY());
			MainActivity main = (MainActivity) mParent;
			if(mPlay.getBounds().contains(delta)){
				changeView(new IceGameView(getContext()));
			}
			else if(mExit.getBounds().contains(delta)){
				main.finish();
				
			}
		}
		
		return true;
	}
}
