package au.edu.deakin.ice.meltdown;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;


/**draws the five highest personal scores, and the dates on which they were achieved. */
public class ScoreView extends GameView {
	private static final String mName = ScoreView.class.getSimpleName();
	
	/** buttons for the menu*/
	private GameObject mPlay, mExit, mSoundOn, mSoundOff;
	
	private boolean sound = SoundManager.isEnabled();
	private TextObject[] mScores, mDates;
	private TextObject mScoreTitle, mDateTitle;
	
	public ScoreView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	//@Override
	public void Init() {
		mPlay = new GameObject(R.drawable.button_play);
		mExit = new GameObject(R.drawable.button_exit);	
		
		mSoundOn = new GameObject(R.drawable.button_sound_on);	
		mSoundOff = new GameObject(R.drawable.button_sound_off);	
		
		int buttonx = (int) (mScreenSize.x / 10);
		int buttonHeight = (int) mPlay.getBounds().size.y;
		
		mPlay.setPosition(new Vector2(buttonx, buttonHeight));
		mExit.setPosition(new Vector2(buttonx, buttonHeight * 4));
		
		mSoundOn.setPosition(new Vector2(buttonx, buttonHeight * 2));
		mSoundOff.setPosition(new Vector2(buttonx, buttonHeight * 2));
		
		int scorex = (int) (mScreenSize.x / 4);
		int scorey = (int) (mScreenSize.y / 6);
		int scoreStep = scorey / 5;
		
		MainActivity main = (MainActivity) mParent;
		int[] scores = main.getScores();
		String[] dates = main.getScoreDates();
		
		mScoreTitle = new TextObject("Scores:", color.primary_text_light);
		mDateTitle = new TextObject("Date earned:", color.primary_text_light);
		
		mScoreTitle.setPosition(scorex * 2, (scorey / 2) + scoreStep);
		mDateTitle.setPosition(scorex * 3, (scorey / 2) + scoreStep);
		
		mScores = new TextObject[5];
		mDates = new TextObject[5];
		
		for(int i = 0; i < scores.length; ++i){
			mScores[i] = new TextObject(Integer.toString(scores[i]), color.primary_text_light);
			mScores[i].setPosition(scorex * 2, scorey + scorey * i + scoreStep);
		}
		
		for(int i = 0; i < dates.length; ++i){
			mDates[i] = new TextObject(dates[i], color.primary_text_light);
			mDates[i].setPosition(scorex * 3, scorey + scorey * i + scoreStep);
		}
	}
					
	//@Override
	public void Draw(Canvas canvas){
		clear(canvas);
		
		draw(mPlay);
		draw(mExit);
		
		if(sound)
			draw(mSoundOn);
		else
			draw(mSoundOff);
		
		draw(mScoreTitle);
		draw(mDateTitle);
		
		for(TextObject o : mScores)
			draw(o);
		
		for(TextObject o : mDates)
			draw(o);
		
		display(canvas);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
			Log.d(mName, "Pointer Up at (" + event.getX() + ", " + event.getY() + ")");
			Vector2 delta = new Vector2(event.getX(), event.getY());
			MainActivity main = (MainActivity) mParent;
			if(mPlay.getBounds().contains(delta)){
				changeView(new IceGameView(getContext()));
			}
			else if(mExit.getBounds().contains(delta)){
				main.finish();
				
			}
			else if(mSoundOn.getBounds().contains(delta)){
				if(sound)
					SoundManager.disable();
				else
					SoundManager.enable();
				
				sound = !sound;
			}
		}
		
		return true;
	}
}
