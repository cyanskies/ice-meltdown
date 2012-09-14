package au.edu.deakin.ice.meltdown;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;

public class MainActivity extends Activity{
	
	/** Class name used for logging */
	private static final String mName = MainActivity.class.getSimpleName();
	
	/** Ordered list of dates, each date corresponds to a score in mScores */
	private String[] mScoreDates;
	/** Ordered list of scores, each score has an associated date in mScoreDates */
	private int[] mScores;
	/** The current GameView, this is kept so that it can be cleaned up properly before moving onto the next view */
	private GameView mCurrent;
	
	/** Getter for the Score Dates.
	 * @return The array of Score Dates */
	public String[] getScoreDates(){
		return mScoreDates;
	}
	
	/** Getter for the Scores.
	 * @return The array of Scores */
	public int[] getScores(){
		return mScores;
	}
		
	/** Method called after activity creation.
	 *  Loads score data from file and then starts the menu view.
	 *  @param savedInstanceState A record of saved data for this instance of the activity
	 *  */
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(mName, "Creating main activity");
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //load save data
        loadData();
        changeView(new ScoreView(getApplicationContext()));
    }

    /** Method called when activity is paused.
	 *  we don't want to support pausing, so we save our data and kill the app.
	 *  */
    protected void onPause() {
        super.onPause();
        //mThread.setRunning(false);
        saveData();
        
        finish();
        Log.d(mName, "Paused");
    }
    
    /** Method called when activity destroyed.
   	 *  We use this chance to save our data for the next time the app is opened.
   	 *  */
    public void onDestroy(){
    	//save game data
    	saveData();
    }
    
    /** Sets the current view to the one provided to the method.
	 *  Cleans up the old view and.
	 *  @param view The new GameView to run
	 *  */
    public void changeView(GameView view){
    		view.setParent(this);
    		if(mCurrent != null) mCurrent.kill();
    		
    		mCurrent = view;
        	mCurrent.ViewInit();
        	setContentView(view);
    }
    
    //loading and saving code based on: http://stackoverflow.com/questions/1239026/how-to-create-a-file-in-android
    
    /** Loads score data from file.
   	 *  If loading fails, the score arrays are filled with default values.
   	 *  */
    private void loadData(){
    	try{
    		
    		//first load the scores
    		FileInputStream fIn = openFileInput("scores");
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader inBuff = new BufferedReader(isr);
            
            String inputLine;
            String[] input = new String[5];
            int count = 0;
            /* Prepare a char-Array that will
             * hold the chars we read back in. */
            while((inputLine = inBuff.readLine()) != null && count < 5) {
                input[count++] =  inputLine;
            }
            
            inBuff.close();

            mScores = new int[5];
            for(count = 0; count < input.length; ++count){
            	try{
            		mScores[count] = Integer.parseInt(input[count]);
            	}
            	catch(NumberFormatException ex){
            		mScores[count] = 0;
            	}
            }
            
            //and then the dates
            fIn = openFileInput("dates");
            isr = new InputStreamReader(fIn);
            inBuff = new BufferedReader(isr);
            
            input = new String[5];
            count = 0;
            /* Prepare a char-Array that will
             * hold the chars we read back in. */
            while((inputLine = inBuff.readLine()) != null && count < 5) {
                input[count++] =  inputLine;
            }
            
            inBuff.close();

            mScoreDates = input;
            
    	} catch (IOException ioe) {
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            String formattedDate = df.format(new Date());
            	
            String[] ScoreDates = {formattedDate, formattedDate, formattedDate, formattedDate, formattedDate};
            mScoreDates = ScoreDates;
 
            int[] Scores = {0, 0, 0, 0, 0};
            mScores = Scores;
        }
    }
    
    /** Saves the current score data to file */
    private void saveData(){
    	//first write the ordered scores, one per line
    	
    	if(mScores == null || mScoreDates == null)
    		return; //abort
    	
    	try { 
    	       // catches IOException below
    			String out = new String();
    			for(int i: mScores){
    				out += Integer.toString(i) + System.getProperty("line.separator");
    			}

    	       /* We have to use the openFileOutput()-method
    	       * the ActivityContext provides, to
    	       * protect your file from others and
    	       * This is done for security-reasons.*/             
    	       FileOutputStream fOut = openFileOutput("scores", MODE_PRIVATE);
    	       OutputStreamWriter osw = new OutputStreamWriter(fOut); 

    	       // Write the string to the file
    	       osw.write(out);

    	       /* ensure that everything is
    	        * really written out and close */
    	       osw.flush();
    	       osw.close();
    	}catch (IOException ioe) 
        {ioe.printStackTrace();}
    	
    	//now write out the ordered dates
    	try { 
    		String out = new String();
			for(String s: mScoreDates){
				out += s + System.getProperty("line.separator");
			}

	       /* We have to use the openFileOutput()-method
	       * the ActivityContext provides, to
	       * protect your file from others and
	       * This is done for security-reasons.*/             
	       FileOutputStream fOut = openFileOutput("dates", MODE_PRIVATE);
	       OutputStreamWriter osw = new OutputStreamWriter(fOut); 

	       // Write the string to the file
	       osw.write(out);

	       /* ensure that everything is
	        * really written out and close */
	       osw.flush();
	       osw.close();
 	}catch (IOException ioe) 
     {ioe.printStackTrace();}
    }
}

