package au.edu.deakin.ice.meltdown;

import java.util.Queue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


//GameView based on the MainPanel from refference: http://www.javacodegeeks.com/2011/07/android-game-development-basic-game_05.html
// extended with generic drawing functionallity
public class GameView extends SurfaceView implements SurfaceHolder.Callback{

	private static final String mName = GameView.class.getSimpleName();
	private Queue<Drawable> mDrawQueue;
	private final RectShape mClear = new RectShape();
	private GameThread mThread;
	
	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		
		mThread = new GameThread(getHolder(), this);
		// TODO Auto-generated constructor stub
	}
	public void Clear(Canvas canvas){
		mClear.draw(canvas, null);
	}
	
	public void draw(Drawable d){
		mDrawQueue.add(d);
	}
	
	public void display(Canvas canvas){
		while(!mDrawQueue.isEmpty()){
			mDrawQueue.remove().draw(canvas);
		}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mThread.setRunning(true);
		mThread.start();
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while(retry) {
			try {
				mThread.join();
				retry = false;
			}
			catch (InterruptedException e) {
				//try again
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
		Log.d(mName, "Touch at: (" + event.getRawX() + ", " + event.getRawY() + ")");
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
	}

}
