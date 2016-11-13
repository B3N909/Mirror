package mirror.savant.stt;

import java.util.Random;

public class Bot
{
	private static final String[] RESPONSE_BUMP = new String[]
	{
		"Hey!", "Whats up?", "What's hanging bro?",
		"What's the dilly?", "What's the dizzle?", "What's the rumpus?",
		"What's the haps?", "What's crack alacking", "Heyo!",
		"What's shacking bacon?", "Be easy.", "bitch",
		"Ay oh", "Be easy", "Deuce out the roof"
	};
	
	private static final String[] RESPONSE_WHAT = new String[]
	{
		"Do what?", "Do what now?", "What you smoking?",
		"What in hell?", "What you saying?", "What the shit?",
		"What the dilly, yo", "What the what?", "What they do?",
		"What the fridge?", "About it?", "Barbie?"
	};
	
	private Random random;
	
	public Bot()
	{
		random = new Random();
	}
	
	public String think(String text)
	{
		text = destroy(text, new String[] { ".", "!", "?" });
		if(text.equalsIgnoreCase("mirror"))
		{
			return response(RESPONSE_BUMP);
		}
		return response(RESPONSE_WHAT);
	}
	
	private String response(String[] str)
	{
		return str[random(0, str.length)];
	}
	
	private int random(int min, int max)
	{
		return random.nextInt(max - min) + min;
	}
	
	private String destroy(String text, String... str)
	{
		for(String s : str)
			text = text.replace(s, "");
		return text;
	}
}
