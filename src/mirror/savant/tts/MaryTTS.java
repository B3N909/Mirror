package mirror.savant.tts;

import java.io.IOException;
import java.util.Locale;

import javax.sound.sampled.AudioInputStream;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.client.RemoteMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.MaryAudioUtils;
import mirror.savant.display.Display;

public class MaryTTS
{
	public static final String SERVER = "192.168.1.2";
	public static final int PORT = 59125;
	
	private boolean useServer;
	private MaryInterface mtts;

	public MaryTTS(boolean useServer)
	{
		try
		{
			if(useServer)
			{
				mtts = new RemoteMaryInterface(SERVER, PORT);
			}
			else
			{
				mtts = new LocalMaryInterface();
			}
		}
		catch (IOException | MaryConfigurationException e)
		{
			Display.status = "Cannot find Voice Server!";
			e.printStackTrace();
		}
	}
	
	public void speak(String text)
	{
		try
		{
			AudioInputStream audio = mtts.generateAudio(text);
			MaryAudioUtils.writeWavFile(MaryAudioUtils.getSamplesAsDoubleArray(audio), "/tmp/tts.wav", audio.getFormat());
			MaryAudioUtils.playWavFile("/tmp/tts.wav", 0, true);
			
			if(Display.status.equalsIgnoreCase("Cannot find Voice Server!") || Display.status.equalsIgnoreCase("Failed to synthesis text!"))
				Display.status = "Ay, Okay";
		}
		catch (IOException | SynthesisException e)
		{
			if(useServer)
				Display.status = "Cannot find Voice Server!";
			else
				Display.status = "Failed to synthesis text!";
			e.printStackTrace();
		}
	}
}
