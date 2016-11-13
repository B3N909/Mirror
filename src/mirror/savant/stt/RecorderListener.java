package mirror.savant.stt;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public abstract class RecorderListener
{
	
	private float localMin;
	
	public RecorderListener()
	{
		final Recorder rec = new Recorder();
		Thread recorderThread = new Thread(rec); recorderThread.start();
		
		Thread listenerThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (true)
				{
//					System.out.println("1");
				}
			}
		});
		listenerThread.start();
	}
	
	public abstract void foundSpeech(String text);
	
	public static class Recorder implements Runnable
	{
		public float volume;
		public float peak;
		
		@Override
	    public void run()
		{
	        AudioFormat fmt = new AudioFormat(44100f, 16, 1, true, false);
	        final int bufferByteSize = 2048;

	        TargetDataLine line;
	        try
	        {
	            line = AudioSystem.getTargetDataLine(fmt);
	            line.open(fmt, bufferByteSize);
	        }
	        catch(LineUnavailableException e)
	        {
	            System.err.println(e);
	            return;
	        }

	        byte[] buf = new byte[bufferByteSize];
	        float[] samples = new float[bufferByteSize / 2];

	        float lastPeak = 0f;

	        line.start();
	        for(int b; (b = line.read(buf, 0, buf.length)) > -1;) {

	            for(int i = 0, s = 0; i < b;) {
	                int sample = 0;

	                sample |= buf[i++] & 0xFF;
	                sample |= buf[i++] << 8;

	                samples[s++] = sample / 32768f;
	            }

	            float rms = 0f;
	            float peak = 0f;
	            for(float sample : samples)
	            {

	                float abs = Math.abs(sample);
	                if(abs > peak)
	                {
	                    peak = abs;
	                }

	                rms += sample * sample;
	            }

	            rms = (float)Math.sqrt(rms / samples.length);

	            if(lastPeak > peak)
	            {
	                peak = lastPeak * 0.875f;
	            }

	            lastPeak = peak;
	            
	            this.volume = rms * 500;
	            this.peak = peak * 500;
	        }  
	    }
	}
}
