package au.edu.deakin.ice.meltdown;

/** The Vector2 class is used to hold 2d values*/
public class Vector2 {

	/** A position value of the current Vector2 */
	public float x = 0.f, y = 0.f;
	
	/** Vector2 auto-generated constructor */
	public Vector2() {
		// TODO Auto-generated constructor stub
	}
	
	/** Vector2 copy constructor 
	 *  @param other The Vector2 object to copy
	 *  */
	public Vector2(Vector2 other) {
		x = other.x;
		y = other.y;
	}
	
	/** Vector2 constructor
	 * @param x The x value of the vector
	 * @param y The y value of the vector */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	/** The function used to add a Vector2 to the current vector2
	 * @param v The Vector2 to add to the current vector2
	 * @return The new Vector2 value */
	public Vector2 add(Vector2 v){
		this.x += v.x;
		this.y += v.y;
		return new Vector2(this);
	}
	
	/** The function used to subtract a Vector2 from the current vector2
	 * @param v The Vector2 to subtract from the current vector2
	 * @return The new Vector2 value */
	public Vector2 sub(Vector2 v){
		this.x -= v.x;
		this.y -= v.y;
		return new Vector2(this);
	}

}
