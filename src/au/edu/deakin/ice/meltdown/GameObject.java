package au.edu.deakin.ice.meltdown;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class GameObject{

	private static Resources mR;
	public static void setResources(Resources r){
		mR = r;
	}
	
	private static final String mName = GameObject.class.getSimpleName();
	private Bitmap mB;
	
	public Bitmap getBitmap() {
		return mB;
	}

	private Matrix mM = new Matrix();

	public Matrix getMatrix() {
		return mM;
	}


	public GameObject(int image){
		if(mR == null)
			throw new NullPointerException();
		mB = BitmapFactory.decodeResource(mR, image);
		mM.reset();
		// TODO create drawable using constructor parameters
	}
	
	public void draw(GameView v){
		DrawData d = new DrawData();
		d.b = mB;
		d.m = mM;
		v.draw(d);
	}
	
	public void move(int x, int y){
		mM.setTranslate(x,  y);
	}
}
