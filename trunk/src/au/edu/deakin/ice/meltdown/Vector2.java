package au.edu.deakin.ice.meltdown;

public class Vector2 {

	public int x = 0, y = 0;
	
	public Vector2() {
		// TODO Auto-generated constructor stub
	}
	
	public Vector2(Vector2 other) {
		x = other.x;
		y = other.y;
	}
	
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2 add(Vector2 v){
		this.x += v.x;
		this.x += v.y;
		return new Vector2(this);
	}
	
	public Vector2 sub(Vector2 v){
		this.x -= v.x;
		this.x -= v.y;
		return new Vector2(this);
	}

}
