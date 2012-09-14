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

	/** Class name used for logging */
	private static final String mName = GameView.class.getSimpleName();
	
	/** Queue of bitmaps to draw in display() */
	private Queue<DrawData> mDrawQueue = new LinkedList<DrawData>();
	/** Queue of Text objects to draw in display() */
	private Queue<TextData> mTextQueue = new LinkedList<TextData>();
	/** Thread used for calling update and draw at regular intervals */
	private GameThread mThread;
	/** Android Paint object needed to draw text objects and clear the screen */
	private final Paint mPaint = new Paint();
	/** A vector representing the size of the screen, used for calculating motion and object position within the bounds of the screen */
	protected Vector2 mScreenSize  = new Vector2();
	/** The parent activity, needed to call the ChangeView and finish() methods */
	protected Activity mParent;
	/** Is set to true once the screen is ready to use. */
	private boolean mScreenInit = false;
	
	/** Cleans up the GameView and tells its GameThread to wind down */
	public void kill(){
		mThread.setRunning(false);
		
		while(mThread.isAlive())
		try {
			mThread.join();
		} catch (InterruptedException e) {}
	}
	
	/** Let the GameView know which Activity it reports to.
	 * @param mParent The Activity that created this gameView */
	public void setParent(Activity mParent) {
		this.mParent = mParent;
	}
	
	/** Ask the View if the screen is ready to use.
	 * @return A bool indicating the ready state of the screen */
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
	/** Called when the screen is interacted with
	 * @param event Holds the information about the interaction
	 * @return True if the GameView would like to continue receiving information about this touch event
	 *  False if the GameView wants to cancel the event and ignore it in the future */
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
		Log.d(mName, "Touch at: (" + event.getX() + ", " + event.getY() + ")");
		}
		return super.onTouchEvent(event);
	}
	
	
	public void ViewInit() {
		
		mThread.setRunning(true);
		if(!mThread.isAlive())
			mThread.start();
	}
	
	public void Init(){}
	
	public void Update() {
	}
	
	public void Draw(Canvas canvas){
	}
	
	public void changeView(GameView view){
		MainActivity main = (MainActivity) mParent;
		main.changeView(view);
    }
}
