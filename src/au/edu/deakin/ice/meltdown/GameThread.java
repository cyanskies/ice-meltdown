package au.edu.deakin.ice.meltdown;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**GameThread based on the tutorial from reference: http://www.javacodegeeks.com/2011/07/android-game-development-basic-game_05.html
 */
public class GameThread extends Thread {
	
	/** Class name used for logging */
	private static final String mName = GameThread.class.getSimpleName();
	
	/** Indicates if the thread is still alive */
	private boolean running;
	/** The object that holds the surface, this is needed so we can retrieve the canvas */
	private SurfaceHolder mSurfaceHolder;
	/** The gameView that this thread is attached to */
	private GameView mGame;
	
	/** The max FPS for the thread to draw at */
	private static final int MAX_FPS = 50;
	
	/** The frame time to use to maintain the correct FPS */
	private static final int FRAME_TIME = 1000 / MAX_FPS;
		
	/** Constructor.
	 *  @param s The surface holder to use for this thread
	 *  @param v The GameView that this thread will update and draw */
	public GameThread(SurfaceHolder s, GameView v){
		super();
		mSurfaceHolder = s;
		mGame = v;
	}
	
	/** Sets the running state of the thread
	 *  @param running True to continue running, false to end the thread
	 *  */
	public void setRunning(boolean running) {
		 this.running = running;
	}
	 
	/** Holds this states game loop.
	 * Responsible for calling Init() on the state,
	 * and for calling update and draw at regular intervals.*/
	 public void run() {
		 Canvas canvas;
		 
		 Log.d(mName, "Thread started")	;
		 
		 long frameStart, frameDelta;
		 int sleep;
		 
		//while (mGame.ScreenInit() == false) {
			//try {
				// sleep(10);
			 //} catch (InterruptedException e) {}
		// }
		 
		 mGame.Init();
		 
		 Log.d(mName, "Starting game loop");
		 while (running) {
			 canvas = null;
		     // try locking the canvas for exclusive pixel editing
		     // in the surface
		     try {
		    	 canvas = this.mSurfaceHolder.lockCanvas();
		         synchronized (mSurfaceHolder) {
		        	
		        	 frameStart = System.currentTimeMillis();
		        	 // update game state
		        	 mGame.Update();
		         	 // render state to the screen
		         	 // draws the canvas on the panel
		        	 if(canvas != null)
		        		 mGame.Draw(canvas);
		         	 
		         	 frameDelta = System.currentTimeMillis() - frameStart;
		         	 sleep = (int) (FRAME_TIME - frameDelta);
		         	 
		         	 //slow down game if too fast
		         	 if(sleep > 0){
		         		 try {
		         			 sleep(sleep);
		         		 } catch (InterruptedException e) {}
		         	 }
		         	 
		         	 //speed up game if too slow
		         	 while(sleep < 0){
		         		 mGame.Update();
		         		 sleep += FRAME_TIME;
		         	 }
		         }
		     } finally {
		    	 // in case of an exception the surface is not left in
		         // an inconsistent state
		         if (canvas != null) {
		        	 mSurfaceHolder.unlockCanvasAndPost(canvas);
		         }
		     }   // end finally
		 } //while
		 
		 Log.d(mName, "Thread expired");
	 }//run()
	 
}
