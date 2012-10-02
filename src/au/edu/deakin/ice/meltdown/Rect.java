package au.edu.deakin.ice.meltdown;

/** Represents a logical rectangle on the screen. */
public class Rect {
	/** Vectors representing the position and size of the rectangle. */
	public Vector2 position = new Vector2(), size = new Vector2();
	
	/** Default constructor */
	public Rect() {
		position = new Vector2(0.f, 0.f);
		size = new Vector2(0.f, 0.f);
		// TODO Auto-generated constructor stub
	}
	
	/** Constructs a rect at a certain position and size.
	 * @param position The position of the created rect
	 * @param size The size of the created rect */
	public Rect(Vector2 position, Vector2 size) {
		this.position = position;
		this.size = size;
	}
	
	/** Constructs a rect at a certain position and size.
	 * @param posx The horizontal position of the created rect
	 * @param posy The vertical position of the created rect
	 * @param width The horizontal size of the created rect
	 * @param height The vertical size of the created rect */
	public Rect(float posx, float posy, float width, float height) {
		position = new Vector2(posx, posy);
		size = new Vector2(width, height);
	}
	
	/** Tests to ditermine if the provided point is within the bounds of the rect.
	 * @param v The point to test
	 * @return True if the point is inside the rect, false otherwise */
	public boolean contains(Vector2 v){
		return (v.x >= position.x) && (v.x < position.x + size.x) && (v.y >= position.y) && (v.y < position.y + size.y);
	}
	
	/** Tests to ditermine if the provided rect intersects the bounds of the rect.
	 * @param r The rect to test against
	 * @return True if rects intersect, false otherwise */
	public boolean intersects(Rect r){
		Rect o = GetOverlapRect(r);
		if(o == null) {
			return false;
		}
		else
			return true;
	}
	
	/** Tests to determine if the provided rect intersects the bounds of the rect.
	 *  If so, it returns a rect which represents the area the two rects overlap.
	 * @param r The rect to test against
	 * @return The overlap area */
	public Rect GetOverlapRect(Rect r){
		float Left;
		float Top;
		float Right;
		float Bottom;
	    
	    if(position.x > r.position.x)
	    	Left = position.x;
	    else
	    	Left = r.position.x;
	    
	    if(position.y > r.position.y)
	    	Top = position.y;
	    else
	    	Top = r.position.y;
	    
	    if(position.x + size.x > r.position.x + r.size.x)
	    	Right = r.position.x + r.size.x;
	    else
	    	Right = position.x + size.x;
	    
	    if(position.y + size.y > r.position.y + r.size.y)
	    	Bottom = r.position.y + r.size.y;
	    else
	    	Bottom = position.y + size.y;

	    if ((Left < Right) && (Top < Bottom))
	    {
	        return new Rect(Left, Top, Right - Left, Bottom - Top);
	    }
	    else
	    {
	        return null;
	    }
	}

}
