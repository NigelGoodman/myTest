package myTest.test1;



import java.util.Random;

import android.R.string;
import android.app.Activity;
import android.content.res.Resources;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class MyTestActivity extends Activity 
{
	//private TextView output;
	private String[] phraseArray;
    /** Called when the activity is first created. */
    
	private final int duration = 3; // seconds
    private final int sampleRate = 8000;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private double freqOfTone = 440; // hz

    private final byte generatedSnd[] = new byte[2 * numSamples];

    Handler handler = new Handler();
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
       /* TextView textBox= new TextView(this);
        textBox.setText("Hello, Android");*/
        //output = (TextView) findViewById(R.id.display);
        Resources res = getResources();
        phraseArray = res.getStringArray(R.array.fortunes);
        Log.d("YO MOM","MADEMAN");
    }
	
	 protected void onResume() {
	        super.onResume();
	        Log.d("YO MOM", "RUNNING");
	        // Use a new tread as this can take a while
	        final Thread thread = new Thread(new Runnable() {
	            public void run() {
	                genTone();
	                handler.post(new Runnable() {

	                    public void run() {
	                        playSound();
	                    }
	                });
	            }
	        });
	        thread.start();
	    }
	 
	 void genTone(){
	        // fill out the array
	        for (int i = 0; i < numSamples; ++i) {
	            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
	        }

	        // convert to 16 bit pcm sound array
	        // assumes the sample buffer is normalised.
	        int idx = 0;
	        for (final double dVal : sample) {
	            // scale to maximum amplitude
	            final short val = (short) ((dVal * 32767));
	            // in 16 bit wav PCM, first byte is the low order byte
	            generatedSnd[idx++] = (byte) (val & 0x00ff);
	            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

	        }
	    }

	    void playSound()
	    {
	        final AudioTrack audioTrack = new AudioTrack
	        		(AudioManager.STREAM_MUSIC,
	                sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
	                AudioFormat.ENCODING_PCM_16BIT, numSamples,
	                AudioTrack.MODE_STATIC);
	        audioTrack.write(generatedSnd, 0, generatedSnd.length);
	        audioTrack.play();
	    }
    
    public void onClick(View v) 
	{
    	//freqOfTone = 440*(2*(12));
    	//trace v.getId();
    	//System.out.println(v.getId());
    	String dave = "" + v.getId();
    	Log.d("YO MOM", dave);
    	generateEvent();
    	
    	
    	playSound();
	}
    
    public void generateEvent() 
    {
		//output.setText(phraseArray[new Random().nextInt(phraseArray.length)], TextView.BufferType.NORMAL);

    }
}