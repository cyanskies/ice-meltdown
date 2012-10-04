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

/** Base class for game states and views.
 * 
 * GameView based on the MainPanel from reference: http://www.javacodegeeks.com/2011/07/android-game-development-basic-game_05.html
 * extended with generic drawing functionality */
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
	/** media player object allows the playing of sounds*/
	protected SoundManager mSound;
	
	/** Is set to true once the screen is ready to use. */
	private boolean mScreenInit = false;
	
	/** Cleans up the GameView and tells its GameThread to wind down */
	public final void kill(){
		mThread.setRunning(false);
		
		if(mSound != null)
			mSound.kill();
		
		while(mThread.isAlive())
		try {
			mThread.join();
		} catch (InterruptedException e) {}
	}
	
	/** Let the GameView know which Activity it reports to.
	 * @param mParent The Activity that created this gameView */
	public final void setParent(Activity mParent) {
		this.mParent = mParent;
	}
	
	/** Ask the View if the screen is ready to use.
	 * @return A boolean indicating the ready state of the screen */
	public final boolean ScreenInit(){
		return mScreenInit;
	}
	
	/** Constructor.
	 *  Creates the game thread and links the Game Object and Text Objects static references to the resource cache.
	 * @param context The application context is needed to access resources within the apk */
	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		mThread = new GameThread(getHolder(), this);
			
		SoundManager.setContext(context);
		
		mSound = new SoundManager();
		
		GameObject.setResources(getResources());
		TextObject.setResources(getResources());
	}
	
	/** Draws a solid colour over the whole canvas.
	 * @param canvas The canvas the clear */
	public final void clear(Canvas canvas){
		clear(canvas, Color.CYAN);
	}
	
	/** Draws a solid colour over the whole canvas.
	 * @param canvas The canvas the clear
	 * @param colour The colour to draw over the canvas with */
	public final void clear(Canvas canvas, int colour){
		canvas.drawColor(colour);
	}
	
	/** Adds a drawdata to the renderqueue.
	 * @param d The data to add to the queue */
	public final void draw(DrawData d){
		mDrawQueue.add(d);
	}
	
	/** Adds a textdata to the renderqueue.
	 * @param d The text data to add to the queue */
	public final void draw(TextData d){
		mTextQueue.add(d);
	}
	
	/** Adds a game object to the draw queue.
	 * @param g The object to add to the queue */
	public final void draw(GameObject g){
		DrawData d = new DrawData();
		d.b = g.getBitmap();
		d.m = g.getMatrix();
		mDrawQueue.add(d);
	}
	
	/** Adds a text object to the draw queue.
	 * @param t The object to add to the queue */
	public final void draw(TextObject t){
		TextData d = new TextData();
		d.text = t.getText();
		d.colour = t.getColour();
		Vector2 pos = t.getPosition();
		d.x = pos.x;
		d.y = pos.y;

		mTextQueue.add(d);
	}
	
	/** Draws each object on the renderqueue to the Canvas provided.
	 * @param canvas The canvas to draw each object to */
	public final void display(final Canvas canvas){
		
		while(!mTextQueue.isEmpty()){
			TextData d = mTextQueue.peek();
			mPaint.setColor(d.colour);
			canvas.drawText(d.text, d.x, d.y , mPaint);
			mTextQueue.remove();
		}
					
		while(!mDrawQueue.isEmpty()){
			DrawData d = mDrawQueue.peek();
			canvas.drawBitmap(d.b, d.m, null);
			mDrawQueue.remove();
		}				
	}
	
	/** Notify GameView that the surface has changed.
	 *  The view watches this to keep track of the surface size.
	 * @param holder The parent object that owns the surface
	 * @param format the colour format of the surface
	 * @param width The new width of the surface
	 * @param height The new height of the surface */
	public final void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d(mName, "Surface Cha-Cha-Changed!!!");
		mScreenSize = new Vector2(width, height);
		
		mThread.setRunning(true);
		if(!mThread.isAlive())
			mThread.start();
		mScreenInit = true;
	}
	
	/** Notify GameView that the surface has been created.
	 * @param holder The parent object that owns the surface */
	public final void surfaceCreated(SurfaceHolder holder) {
		Log.d(mName, "Surface Created!!!");
	}
	
	/** Notify GameView that the surface has been destroy.
	 * @param holder The parent object that owns the surface */
	public final void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(mName, "Surface Destroyed!!!");
		kill();
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
	
	/** Called when the surface has be created.
	 * Starts the game threads main loop */
	public final void ViewInit() {
		
		Log.d(mName, "Vew Initating");
		
	}
	
	/** Init exists to allow derived classes to set up their game data. */
	public void Init(){}
	
	/** Called each game tick to let a derived class update it's game logic.*/
	public void Update() {
	}
	
	/** Derived classes use this function to draw all of their graphics.
	 * @param canvas The canvas to draw to */
	public void Draw(Canvas canvas){
	}
	
	/** Change the view being used my MainActivity to the one provided.
	 * @param view The new view to control the screen */
	public final void changeView(GameView view){
		MainActivity main = (MainActivity) mParent;
		
		//kill();
		main.changeView(view);
    }
}
