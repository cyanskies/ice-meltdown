package au.edu.deakin.ice.meltdown;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

@SuppressLint("UseSparseArrays")
public class SoundManager {
	
/*
 * use
 * SOUNDPOOOL
 * example http://blog.nelsondev.net/?p=207
 * 
 * 
 */
	private SoundPool mSounds;
	private HashMap<Integer, Integer> soundsMap;
	private int soundCount;
	private static Context context;
	
	public static void setContext(Context c){
		context = c;
	}
	
	@SuppressLint("UseSparseArrays") //we need hash maps so that we can search and shut down any dangling sounds in OnDestroy
	public SoundManager() {
		mSounds = new SoundPool(8, AudioManager.USE_DEFAULT_STREAM_TYPE, 100);
		soundsMap = new HashMap<Integer, Integer>();
		soundCount = 0;
	}
	
	public int load(int soundResource){
		if(mSounds == null) {
			return -1;
		}
		
		soundsMap.put(++soundCount, mSounds.load(context, soundResource, 1));
		
		return soundCount;
	}
	
	public void play(int soundid, boolean loop){
		int looping = 0;
		
		if(loop)
			looping = -1;
		
		if(soundid < 0 || soundid > soundCount)
			return; //sound wasn't loaded correctly or a junk id is being passed.
			
		//loop until it starts playing successfully
		//this can be needed if the sound starts playing too soon after being loaded
		//we're really waiting for the filesystem to properly cache the sound file
		while(mSounds.play(soundsMap.get(soundid), 1, 1, 1, looping, 1) == 0);
	}
	
	public void pause(int sound){
		mSounds.pause(sound);
	}
	
	public void resume(int sound){
		mSounds.resume(sound);
	}
	
	public void stop(int sound){
		mSounds.stop(sound);
	}
	
	public void OnDestroy(){
		for (int value :  soundsMap.values()) {
		    mSounds.stop(value);
		    mSounds.unload(value);
		}
	}
}
