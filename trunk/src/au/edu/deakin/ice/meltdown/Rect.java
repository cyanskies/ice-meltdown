package au.edu.deakin.ice.meltdown;

public class Rect {
	public Vector2 position = new Vector2(), size = new Vector2();
	
	public Rect() {
		// TODO Auto-generated constructor stub
	}
	
	public Rect(Vector2 position, Vector2 size) {
		this.position = position;
		this.size = size;
	}
	
	public Rect(int posx, int posy, int width, int height) {
		position = new Vector2(posx, posy);
		size = new Vector2(width, height);
	}
	
	public boolean contains(Vector2 v){
		return (v.x >= position.x) && (v.x < position.x + size.x) && (v.y >= position.y) && (v.y < position.y + size.y);
	}
	
	public boolean intersects(Rect r, Rect overlap){
	    int Left;
	    int Top;
	    int Right;
	    int Bottom;
	    
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
	    	overlap = new Rect(0, 0, 0, 0);
	        return false;
	    }
	}

}
