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
	
	private static final String mName = MainActivity.class.getSimpleName();
	//@Override
	private String[] mScoreDates;
	private int[] mScores;
	private GameView mCurrent;
	
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
        
        //load save data
        loadData();
        changeView(new ScoreView(getApplicationContext()));
    }

   // @Override
    protected void onPause() {
        super.onPause();
        //mThread.setRunning(false);
        saveData();
        
        finish();
        Log.d(mName, "Paused");
    }
    
    public void onDestroy(){
    	//save game data
    	saveData();
    }
    
    public void changeView(GameView view){
    		view.setParent(this);
    		if(mCurrent != null) mCurrent.kill();
    		
        	setContentView(view);
        	mCurrent = view;
        	mCurrent.ViewInit();
    }
    
    //loading and saving code based on: http://stackoverflow.com/questions/1239026/how-to-create-a-file-in-android
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
    	       * This is done for security-reasons.
    	       * We chose MODE_WORLD_READABLE, because
    	       *  we have nothing to hide in our file */             
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
	       * This is done for security-reasons.
	       * We chose MODE_WORLD_READABLE, because
	       *  we have nothing to hide in our file */             
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

