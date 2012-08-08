package au.edu.deakin.ice.meltdown;

import android.app.Activity;
import android.os.Bundle;

public class Game extends Activity {
	
	private static final String mName = Game.class.getSimpleName();
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
