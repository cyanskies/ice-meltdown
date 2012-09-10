package au.edu.deakin.ice.meltdown;

import java.util.LinkedList;
import java.util.Queue;

import android.app.Activity;
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
	
	private Queue<DrawData> mDrawQueue = new LinkedList<DrawData>();
	private Queue<TextData> mTextQueue = new LinkedList<TextData>();
	private GameThread mThread;
	private final Paint mPaint = new Paint();
	protected Vector2 mScreenSize  = new Vector2();
	protected Activity mParent;
	
	public void setParent(Activity mParent) {
		this.mParent = mParent;
	}
	private boolean mScreenInit = false;
	
	public boolean ScreenInit(){
		return mScreenInit;
	}
	
	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		mThread = new GameThread(getHolder(), this);
				
		GameObject.setResources(getResources());
		TextObject.setResources(getResources());
	}
	public void clear(Canvas canvas){
		canvas.drawColor(Color.CYAN);
	}
	
	public void draw(DrawData d){
		mDrawQueue.add(d);
	}
	
	public void draw(TextData d){
		mTextQueue.add(d);
	}
	
	
	public void draw(GameObject g){
		DrawData d = new DrawData();
		d.b = g.getBitmap();
		d.m = g.getMatrix();
		mDrawQueue.add(d);
	}
	
	public void draw(TextObject t){
		TextData d = new TextData();
		d.text = t.getText();
		d.colour = t.getColour();
		Vector2 pos = t.getPosition();
		d.x = pos.x;
		d.y = pos.y;

		mTextQueue.add(d);
	}
	
	public void display(Canvas canvas){
		
		//int c = mPaint.getColor();
		
		while(!mTextQueue.isEmpty()){
		TextData d = mTextQueue.peek();
		mPaint.setColor(d.colour);
		canvas.drawText(d.text, d.x, d.y , mPaint);
		mTextQueue.remove();
		}
		
		//mPaint.setColor(c);
		
		while(!mDrawQueue.isEmpty()){
			DrawData d = mDrawQueue.peek();
			canvas.drawBitmap(d.b, d.m, null);
			mDrawQueue.remove();
		}
		
		
	}
	
	//@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d(mName, "Surface Cha-Cha-Changed!!!");
		mScreenSize = new Vector2(width, height);
		
		mScreenInit = true;
		
		mThread.setRunning(true);
		if(!mThread.isAlive())
			mThread.start();
	}
	
	//@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(mName, "Surface Created!!!");
		
	}
	
	//@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(mName, "Surface Destroyed!!!");
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
	
	public void Init() {
		
	}
	
	public void Update() {
	}
	public void Draw(Canvas canvas){
	}
	
	public void changeView(GameView view){
		MainActivity main = (MainActivity) mParent;
		mThread.setRunning(false);
		main.changeView(view);
    }
}
