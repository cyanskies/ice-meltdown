package au.edu.deakin.ice.meltdown;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class GameObject{

	private static final String mName = GameObject.class.getSimpleName();
	private Drawable mD;
	private Bitmap mB;

	public GameObject(){
		// TODO create drawable using constructor parameters
	}
	
	public void draw(GameView v){
		v.draw(mD);
	}
}
