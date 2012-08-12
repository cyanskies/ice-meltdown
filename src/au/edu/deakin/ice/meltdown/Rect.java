package au.edu.deakin.ice.meltdown;

public class Rect {
	public Vector2 position = new Vector2(), size = new Vector2();
	
	public Rect() {
		position = new Vector2(0.f, 0.f);
		size = new Vector2(0.f, 0.f);
		// TODO Auto-generated constructor stub
	}
	
	public Rect(Vector2 position, Vector2 size) {
		this.position = position;
		this.size = size;
	}
	
	public Rect(float posx, float posy, float width, float height) {
		position = new Vector2(posx, posy);
		size = new Vector2(width, height);
	}
	
	public boolean contains(Vector2 v){
		return (v.x >= position.x) && (v.x < position.x + size.x) && (v.y >= position.y) && (v.y < position.y + size.y);
	}
	
	public boolean intersects(Rect r, Rect overlap){
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
	    	Right = position.x + size.x;
	    else
	    	Right = r.position.x + r.size.x;
	    
	    if(position.y + size.y > r.position.y + r.size.y)
	    	Bottom = position.y + size.y;
	    else
	    	Bottom = r.position.y + r.size.y;

	    if ((Left < Right) && (Top < Bottom))
	    {
	        overlap = new Rect(Left, Top, Right - Left, Bottom - Top);
	        return true;
	    }
	    else
	    {
	    	overlap = new Rect();
	        return false;
	    }
	}

}
