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
	
	private static final int MAX_FPS = 50;
	private static final int FRAME_TIME = 1000 / MAX_FPS;
	
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
		 
		 long frameStart, frameDelta;
		 int sleep;
		 
		 mGame.Init();
		 
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
		 }
	 }//run()
	 
}
