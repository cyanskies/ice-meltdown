package au.edu.deakin.ice.meltdown;

import android.view.SurfaceHolder;

//GameView based on the MainPanel from reference: http://www.javacodegeeks.com/2011/07/android-game-development-basic-game_05.html
public class GameThread extends Thread {

	private static final String mName = GameThread.class.getSimpleName();
	private boolean running;
	private SurfaceHolder mSurfaceHolder;
	private GameView mView;
	
	public GameThread(SurfaceHolder s, GameView v){
		super();
		mSurfaceHolder = s;
		mView = v;
	}
	 public void setRunning(boolean running) {
		 this.running = running;
	 }
	 
	 @Override
	 public void run() {
	 while (running) {
		 //update
		 //draw
		 //so on
	 	}
	 }

}
