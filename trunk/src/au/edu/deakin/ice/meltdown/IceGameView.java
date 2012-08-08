package au.edu.deakin.ice.meltdown;

import android.content.Context;
import android.graphics.Canvas;

public class IceGameView extends GameView {

	private static final String mName = IceGameView.class.getSimpleName();
	//private final GameObject test = new GameObject(R.drawable.ic_launcher);
	
	public IceGameView(Context context) {
		super(context);
		//test.move(50, 50);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void Update(){
		
	}
	
	@Override
	public void Draw(Canvas canvas){
		// clear, go through each entity and call draw, then call display
		clear(canvas);
		
		//draw(test);
		
		display(canvas);
	}
	
}
