package au.edu.deakin.ice.meltdown;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.util.Log;

/** A game entity that consists of a string that can be moved and collided with. */
public class TextObject {

	/** The resource pointer required to load colours. */
	private static Resources mR = null;
	/** Class name used for logging. */
	private static final String mName = GameObject.class.getSimpleName();
	
	/** Static method for providing all gameObjects with a resource pointer
	 *  @param r The resource cache to use */
	public static void setResources(Resources r){
		mR = r;
	}
	
	/** Matrix used to record translation of the object. */
	private Matrix mM = new Matrix();
	/** The string to draw */
	private String mT = new String();
	/** The colour reference to use */
	private int mC;
	
	/** Constructor, loads the string and colour.
	 *  @param text The string to use
	 *  @param colour The colour to draw the string in */
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
	
	/** Change the string being drawn to the one provided.
	 *  @param text The string to use*/
	public void setText(String text){
		mT = text;
	}
	
	/** Get the current string being held by the object.
	 *  @return The string that this object holds*/
	public String getText() {
		return mT;
	}
	/** Get this objects colour reference.
	 *  @return the colour reference being held by the object*/
	public int getColour() {
		return mC;
	}

	/** Adds this object to a GameViews draw queue.
	 *  @param v The view to draw to*/
	public void draw(GameView v){
		TextData d = new TextData();
		d.text = mT;
		Vector2 pos = getPosition();
		d.x = pos.x;
		d.y = pos.y;
		d.colour = mC;
		v.draw(d);
	}
	
	/** Returns the vector position of the object.
	 *  @return Current position */
	public Vector2 getPosition(){
		float[] v = new float[9];
		mM.getValues(v);
		
		return new Vector2(v[Matrix.MTRANS_X], v[Matrix.MTRANS_Y]);
	}
		
	/** Moves the object by the amount passed as parameters.
	 *  @param x The delta to move horizontally 
	 *  @param y The delta to move vertically */
	public void move(float x, float y){
		mM.postTranslate(x, y);
	}
	
	/** Moves the object by the amount passed as parameter.
	 *  @param v The delta vector to move by */
	public final void move(Vector2 v){
		move(v.x,  v.y);
	}
	
	/** Sets the objects translation to the values passed as parameters.
	 *  @param x The horizontal position 
	 *  @param y The vertical position  */
	public void setPosition(float x, float y){
		mM.setTranslate(x, y);
	}
	
	/** Sets the objects translation to the values passed as parameters.
	 *  @param v The vector which represent the objects new position */
	public final void setPosition(Vector2 v){
		setPosition(v.x, v.y);
	}

}
