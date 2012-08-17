package au.edu.deakin.ice.meltdown;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

public class GameObject{

	private static Resources mR = null;
	private static final String mName = GameObject.class.getSimpleName();
	
	public static void setResources(Resources r){
		mR = r;
	}
	
	private Bitmap mB;
	private Matrix mM = new Matrix();
	
	public GameObject(int image){
		if(mR == null)
		{
			Log.d(mName, "Someone forgot to initialise GameObject.Resources");
			throw new NullPointerException();
		}
		
		mB = BitmapFactory.decodeResource(mR, image);
		mM.reset();
	}
	
	public void update(){
		
	}
	
	public void draw(GameView v){
		DrawData d = new DrawData();
		d.b = mB;
		d.m = mM;
		v.draw(d);
	}
	
	public boolean equals(GameObject o){
		if(o.getBounds() == getBounds() && o.getBitmap() == mB){
			return true;
		}
		
		return false;
	}


	public Bitmap getBitmap() {
		return mB;
	}
	
	public Rect getBounds(){
		float[] v = new float[9];
		mM.getValues(v);
		
		float sx = mB.getWidth(), sy = mB.getHeight();
		return new Rect(v[Matrix.MTRANS_X], v[Matrix.MTRANS_Y], sx, sy);
	}
	
	public Matrix getMatrix() {
		return mM;
	}
	
	public void move(float x, float y){
		mM.postTranslate(x, y);
	}
	
	public final void move(Vector2 v){
		move(v.x,  v.y);
	}
	
	public void setPosition(float x, float y){
		mM.setTranslate(x, y);
	}
	
	public final void setPosition(Vector2 v){
		setPosition(v.x, v.y);
	}
}
