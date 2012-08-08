package au.edu.deakin.ice.meltdown;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {
	
	private static final String mName = MainActivity.class.getSimpleName();
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        IceGameView mView = new IceGameView(getApplicationContext());
        setContentView(mView);
        }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    	//save game data
    }
}

