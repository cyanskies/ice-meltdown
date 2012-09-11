package au.edu.deakin.ice.meltdown;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;

public class MainActivity extends Activity{
	
	private static final String mName = MainActivity.class.getSimpleName();
	//@Override
	private String[] mScoreDates;
	private int[] mScores;
	private ActivityThread mThread;
	private GameView mCurrent, mNext;
	private Runnable mRun;
	
	public String[] getScoreDates(){
		return mScoreDates;
	}
	
	public int[] getScores(){
		return mScores;
	}
	
	public void setScoreDates(String[] d){
		mScoreDates = d;
	}
	
	public void setScores(int[] i){
		mScores = i;
	}
	
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(mName, "Creating main activity");
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(savedInstanceState != null){
        	mScoreDates = savedInstanceState.getStringArray("dates");
        
        	mScores = savedInstanceState.getIntArray("scores");
        }
        else{
        	mScoreDates = null; 
        	mScores = null;
        }
        
        if(mScoreDates == null)
        {
        	DateFormat df = new SimpleDateFormat("dd/MM/yy");
        	String formattedDate = df.format(new Date());
        	
        	String[] ScoreDates = {formattedDate, formattedDate, formattedDate, formattedDate, formattedDate};
        	mScoreDates = ScoreDates;
        }
        
        if(mScores == null)
        {
        	int[] Scores = {0, 0, 0, 0, 0};
        	mScores = Scores;
        }
        
        mThread = new ActivityThread(this);
        mThread.setRunning(true);
        mThread.start();
        
        changeView(new ScoreView(getApplicationContext()));
    }

   // @Override
    protected void onPause() {
        super.onPause();
        mThread.setRunning(false);
        Log.d(mName, "Paused");
    }

   // @Override
    public void onSaveInstanceState(Bundle outState) {
    	Log.d(mName, "Saving Instance");
    	//save game data
    	
    	outState.putStringArray("dates", mScoreDates);
    	outState.putIntArray("scores", mScores);
    }
    
    public void changeView(GameView view){
    		mNext = view;
    		view.setParent(this);
    }
    
    public void init(){
    	ScoreView mView = new ScoreView(getApplicationContext());
        mView.setParent(this);
        mNext = mView;
    }
    
    public void tick(){
    	runOnUiThread(new Runnable() {
    	public void run() {
    	if(mNext != null){
    		//synchronized(mNext){
    		if(mCurrent != null) mCurrent.kill();
        	setContentView(mNext);
        	mCurrent = mNext;
        	mNext = null;
        	
        	mCurrent.ViewInit();
    	//}
    	}}});
    		
    }
}

