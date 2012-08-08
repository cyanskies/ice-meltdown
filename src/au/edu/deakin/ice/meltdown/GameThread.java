package au.edu.deakin.ice.meltdown;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

//GameView based on the MainPanel from reference: http://www.javacodegeeks.com/2011/07/android-game-development-basic-game_05.html
public class GameThread extends Thread {

	private static final String mName = GameThread.class.getSimpleName();
	private boolean running;
	private SurfaceHolder mSurfaceHolder;
	private GameView mGame;
	
	public GameThread(SurfaceHolder s, GameView v){
		super();
		mSurfaceHolder = s;
		mGame = v;
	}
	 public void setRunning(boolean running) {
		 this.running = running;
	 }
	 
	 @Override
	 public void run() {
		 Canvas canvas;
		 Log.d(mName, "Starting game loop");
		 while (running) {
			 canvas = null;
		     // try locking the canvas for exclusive pixel editing
		     // in the surface
		     try {
		    	 canvas = this.mSurfaceHolder.lockCanvas();
		         synchronized (mSurfaceHolder) {
		         // update game state
		         this.mGame.Update();
		         // render state to the screen
		         // draws the canvas on the panel
		         this.mGame.Draw(canvas);
		         }
		     } finally {
		    	 // in case of an exception the surface is not left in
		         // an inconsistent state
		         if (canvas != null) {
		        	 mSurfaceHolder.unlockCanvasAndPost(canvas);
		         }
		     }   // end finally
		 }
	 }//run()
	 
}
