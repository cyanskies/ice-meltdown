package au.edu.deakin.ice.meltdown;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.util.Log;

public class TextObject {

	private static Resources mR = null;
	private static final String mName = GameObject.class.getSimpleName();
	
	public static void setResources(Resources r){
		mR = r;
	}
	
	private Matrix mM = new Matrix();
	private String mT = new String();
	private int mC;
	
	public TextObject(String text, int colour) {
		if(mR == null)
		{
			Log.d(mName, "Someone forgot to initialise GameObject.Resources");
			throw new NullPointerException();
		}
		
		mC = mR.getColor(colour);
		mT = text;
		mM.reset();
	}
	
	public void setText(String text){
		mT = text;
	}
	
	public String getText() {
		return mT;
	}
	
	public int getColour() {
		return mC;
	}

	public void draw(GameView v){
		TextData d = new TextData();
		d.text = mT;
		Vector2 pos = getPosition();
		d.x = pos.x;
		d.y = pos.y;
		d.colour = mC;
		v.draw(d);
	}
	
	public Vector2 getPosition(){
		float[] v = new float[9];
		mM.getValues(v);
		
		return new Vector2(v[Matrix.MTRANS_X], v[Matrix.MTRANS_Y]);
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
