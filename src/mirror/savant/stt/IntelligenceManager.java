package mirror.savant.stt;

import mirror.savant.display.Display;
import mirror.savant.tts.TTSManager;

public class IntelligenceManager extends RecorderListener
{
	public TTSManager tts;
	
	private Bot bot;
	
	public IntelligenceManager(TTSManager tts)
	{
		this.tts = tts;
		
		try
		{
			bot = new Bot();
		}
		catch (Exception e)
		{
			Display.status = "Cannot create AI";
			e.printStackTrace();
		}
		
		giveCommand("Hi!");
	}

	@Override
	public void foundSpeech(String text)
	{
		giveCommand(text);
	}
	
	public void giveCommand(String text)
	{
		try
		{
			String s = bot.think(text);
			System.out.println("Spoke: " + s);
			tts.speak(s);
		}
		catch (Exception e)
		{
			Display.status = "Cannot get AI response";
			e.printStackTrace();
		}
	}
}
