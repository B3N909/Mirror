package mirror.savant.tts;

import mirror.savant.display.Display;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory;

public class FreeTTS
{
	/**
	 * alan
	 * kevn
	 * kevin16
	 * mbrola_us1
	 * mbrola_us2
	 * mbrola_us3
	 */
	
	VoiceManager vm;
	
	public FreeTTS()
	{
		vm = VoiceManager.getInstance();
	}
	
	public void speak(String text, String voiceName)
	{
		try
		{
			Voice voice = vm.getVoice(voiceName);
			voice.allocate();
			voice.speak(text);
			voice.deallocate();
			
			if(Display.status.equalsIgnoreCase("Cannot find Voice Server!") || Display.status.equalsIgnoreCase("Failed to synthesis text!"))
				Display.status = "Ay, Okay";
		}
		catch (Exception e)
		{
			Display.status = "Failed to synthesis text!";
			System.out.println("Missing speech.properties in " + System.getProperty("user.home"));
			e.printStackTrace();
		}
	}
}
