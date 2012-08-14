package au.edu.deakin.ice.meltdown;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//GameView based on the MainPanel from refference: http://www.javacodegeeks.com/2011/07/android-game-development-basic-game_05.html
// extended with generic drawing functionallity
public class GameView extends SurfaceView implements SurfaceHolder.Callback{

	private static final String mName = GameView.class.getSimpleName();
	
	private final Paint mPaint = new Paint();
	private Queue<DrawData> mDrawQueue = new LinkedList<DrawData>();
	private GameThread mThread;
	protected Vector2 mScreenSize  = new Vector2();
	
	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		mThread = new GameThread(getHolder(), this);
				
		GameObject.setResources(getResources());
		// TODO Auto-generated constructor stub
	}
	public void clear(Canvas canvas){
		canvas.drawColor(Color.BLACK);
	}
	
	public void draw(DrawData d){
		mDrawQueue.add(d);
	}
	
	
	public void draw(GameObject g){
		DrawData d = new DrawData();
		d.b = g.getBitmap();
		d.m = g.getMatrix();
		mDrawQueue.add(d);
	}
	
	public void display(Canvas canvas){
		while(!mDrawQueue.isEmpty()){
			DrawData d = mDrawQueue.peek();
			canvas.drawBitmap(d.b, d.m, mPaint);
			mDrawQueue.remove();
		}
	}
	
	//@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mScreenSize = new Vector2(width, height);
	}
	
	//@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mThread.setRunning(true);
		mThread.start();
	}
	
	//@Override
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
	
	//@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
		Log.d(mName, "Touch at: (" + event.getX() + ", " + event.getY() + ")");
		}
		return super.onTouchEvent(event);
	}
	
	public void Update() {
	}
	public void Draw(Canvas canvas){
	}
}
