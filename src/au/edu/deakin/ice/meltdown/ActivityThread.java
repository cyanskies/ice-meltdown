package au.edu.deakin.ice.meltdown;

public class ActivityThread extends Thread {
	
	private static final String mName = ActivityThread.class.getSimpleName();
	private boolean running;
	private MainActivity mMain;
	
	public ActivityThread(MainActivity main) {
		// TODO Auto-generated constructor stub
		mMain = main;
	}
	
	public void run() {		
		while(running){
			try {
				 sleep(10);
			} catch (InterruptedException e) {}
			
			mMain.tick();
		}
	}
	
	public void setRunning(boolean run){
		running = run;
	}
}
