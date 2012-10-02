package au.edu.deakin.ice.meltdown;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

/** A game entity that consists of a bitmap that can be moved and collided with. */
public class GameObject{

	/** The resource pointer required to load bitmaps. */
	protected static Resources mR = null;
	
	/** Class name used for logging. */
	private static final String mName = GameObject.class.getSimpleName();
	
	/** Static method for providing all gameObjects with a resource pointer
	 *  @param r The resource cache to use */
	public static void setResources(Resources r){
		mR = r;
	}
	
	/** The gameObjects bitmap, this is draw when the gameObject is added to
	 *  a GameViews draw queue. */
	protected Bitmap mB;
	
	/** Matrix used to record translation of the object. */
	private Matrix mM = new Matrix();
	
	/** Constructor, loads the object bitmap and resets the matrix.
	 *  @param image The bitmap id to use */
	public GameObject(int image){
		if(mR == null)
		{
			Log.d(mName, "Someone forgot to initialise GameObject.Resources");
			throw new NullPointerException();
		}
		
		mB = BitmapFactory.decodeResource(mR, image);
		mM.reset();
	}
	
	/** update method allows derived classes to define behaviour */
	public void update(){
		
	}
	
	/** Converts the objects data into a simple strut for ease of drawing.
	 *  @param v The gameView to draw this object onto */
	public void draw(GameView v){
		DrawData d = new DrawData();
		d.b = this.getBitmap();
		d.m = this.getMatrix();
		v.draw(d);
	}
	
	/** Return true if the two objects are equivalent.
	 *  @param o The object to compare to
	 *  @return return the equality of the two objects */
	public boolean equals(GameObject o){
		if(o.getBounds() == getBounds() && o.getBitmap() == mB){
			return true;
		}
		
		return false;
	}

	/** Get the bitmap the gameObject uses.
	 *  @return The bitmap being used by the GameObject */
	public Bitmap getBitmap() {
		return mB;
	}
	
	/** Returns a rect depicting the bounding points of the object.
	 *  @return The bounding rect of this object */
	public Rect getBounds(){
		float[] v = new float[9];
		mM.getValues(v);
		
		float sx = mB.getWidth(), sy = mB.getHeight();
		return new Rect(v[Matrix.MTRANS_X], v[Matrix.MTRANS_Y], sx, sy);
	}
	
	/** Returns the translation matrix attached to the object.
	 *  @return The objects Matrix */
	public Matrix getMatrix() {
		return mM;
	}
	
	/** Moves the object by the amount passed as parameters.
	 *  @param x The delta to move horizontally 
	 *  @param y The delta to move vertically
	 *  @param deltat The amount of time that has passed between each update */
	public void move(float x, float y, float deltat){
		mM.postTranslate(x * deltat, y * deltat);
	}
	
	/** Moves the object by the amount passed as parameter.
	 *  @param v The delta vector to move by */
	public final void move(Vector2 v, float deltat){
		move(v.x,  v.y, deltat);
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
