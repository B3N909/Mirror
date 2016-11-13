package mirror.savant.tts;

public class TTSManager
{
	public TTSType type;
	private MaryTTS mtts;
	private FreeTTS ftts;
	
	public TTSManager(TTSType type)
	{
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		//System.setProperty("mbrola.base", "C:/Users/Ben/workspace7/Mbrola");
		
		this.type = type;
		if(type == TTSType.FREETTS)
		{
			ftts = new FreeTTS();
		}
		else if(type == TTSType.MARYTTS_CLIENT)
		{
			mtts = new MaryTTS(false);
		}
		else
		{
			mtts = new MaryTTS(true);
		}
	}
	
	public void speak(String text)
	{
		if(type == TTSType.FREETTS)
		{
			ftts.speak(text, "kevin16");
		}
		else if(type == TTSType.MARYTTS_CLIENT || type == TTSType.MARYTTS_SERVER)
		{
			mtts.speak(text);
		}
	}
	
	public void speak(String text, String voiceName)
	{
		if(type == TTSType.FREETTS)
		{
			ftts.speak(text, voiceName);
		}
		else if(type == TTSType.MARYTTS_CLIENT || type == TTSType.MARYTTS_SERVER)
		{
			mtts.speak(text);
		}
	}
}
