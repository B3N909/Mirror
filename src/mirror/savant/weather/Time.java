package mirror.savant.weather;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time
{
	public static String getTime()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		String t = sdf.format(cal.getTime());
		if(t.charAt(0) == '0')
			t = t.split(":")[0].replace("0", "") + ":" + t.split(":")[1];
			
		return t;
	}
}
