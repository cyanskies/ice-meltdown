package au.edu.deakin.ice.meltdown;

import android.app.Activity;
import android.os.Bundle;

public class Game extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        // do the same things as the snake android example and then start the game proper.
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
