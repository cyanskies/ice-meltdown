package au.edu.deakin.ice.meltdown;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private static final String mName = MainActivity.class.getSimpleName();
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        GameView mView = new GameView(getApplicationContext());
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

