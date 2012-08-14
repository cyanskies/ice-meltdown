package au.edu.deakin.ice.meltdown;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;

public class MainActivity extends Activity{
	
	private static final String mName = MainActivity.class.getSimpleName();
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(mName, "Creating main activity");
        IceGameView mView = new IceGameView(getApplicationContext());
        setContentView(mView);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(mName, "Paused");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    	Log.d(mName, "Saving Instance");
    	//save game data
    }
}
