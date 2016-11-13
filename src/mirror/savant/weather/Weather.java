package mirror.savant.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import mirror.savant.display.Display;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.ForecastIO;

public class Weather
{
	/**
	summary: "Mostly Cloudy"
	precipProbability: 0
	visibility: 7.27
	precipIntensity: 0
	icon: "partly-cloudy-night"
	cloudCover: 0.68
	windBearing: 224
	apparentTemperature: 61.74
	pressure: 1015.01
	dewPoint: 54.4
	ozone: 280.37
	nearestStormBearing: 162
	nearestStormDistance: 30
	temperature: 61.74
	humidity: 0.77
	time: 29-10-2016 23:38:21
	windSpeed: 7.63
	 */
	
	public static String apiKey = "5827e098d35d0c9bc00e1b42876da9cc";
	/**
	 * fd92008b20e59439573698253afc9662 - savant.eclipse@gmail.com
	 * 5827e098d35d0c9bc00e1b42876da9cc - frrancis.willits@gmail.com
	 */
	public static String x = "39.4167785", y = "-75.7782679";
	public static final String DEGREE_SYMBOL = "°";
	
	private static FIOCurrently forecast;
	private static float forecastRecord;
	
	private static int queries = 0;
	
	public static FIOCurrently GetForecaster()
	{
		if(5000L < System.currentTimeMillis() - forecastRecord || forecast == null)
		{
			System.out.println("Fetching new Forecast");
			
			ForecastIO fio = new ForecastIO(apiKey);
			fio.setUnits(ForecastIO.UNITS_US);
			
			HttpClient client = HttpClientBuilder.create().build();
			
			HttpGet request = new HttpGet(fio.getUrl(x, y));
			request.addHeader("User-Agent", "NewUserAgent/1.0");
			
			try
			{
				HttpResponse response = client.execute(request);
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				
				while((line = rd.readLine()) != null)
				{
					result.append(line);
				}
				
				fio.getForecast(result.toString());
				rd.close();
				if(Display.status.equalsIgnoreCase("DarkSky API is down!") || Display.status.equalsIgnoreCase("Problem with the Weather?") || Display.status.equalsIgnoreCase("No Internet"))
					Display.status = "Ay, Okay";
				forecast = new FIOCurrently(fio);
				forecastRecord = System.currentTimeMillis();
				queries++;
				if(Display.status.contains("Ay, Okay"))
					Display.status = "Ay, Okay (" + queries + ")";
			}
			catch (com.eclipsesource.json.ParseException e)
			{
				if(!Display.status.equalsIgnoreCase("DarkSky API is down!"))
					System.out.println("ERROR: DarkSky API is down! Perhaps we have reached our maximum queries (" + queries + ")?");
				Display.status = "DarkSky API is down!";
			}
			catch (UnknownHostException e)
			{
				if(!Display.status.contains("Ay, Okay"));
				{
					System.out.println("ERROR: Weather cannot find the host (No Internet?)");
					Display.status = "No Internet";
				}
			}
			catch (IOException e)
			{
				if(!Display.status.contains("Ay, Okay"));
				{
					System.out.println("ERROR: Weather is not working as expected");
					Display.status = "Problem with the Weather?";
				}
				e.printStackTrace();
			}
		}
		return forecast;
	}
	
	public static String GetTemperature()
	{
		FIOCurrently currently = GetForecaster();
		if(currently != null)
		{
		    String [] f  = currently.get().getFieldsArray();
		    for(int i = 0; i<f.length;i++)
		    {
		        if(f[i].equalsIgnoreCase("temperature"))
		        {
		        	return currently.get().getByKey(f[i]).split("\\.")[0] + DEGREE_SYMBOL;
		        }
		    }
		}
	    return "ERR" + DEGREE_SYMBOL; 
	}

	public static String GetSummary()
	{
		FIOCurrently currently = GetForecaster();
		if(currently != null)
		{
		    String [] f  = currently.get().getFieldsArray();
		    for(int i = 0; i<f.length;i++)
		    {
		        if(f[i].equalsIgnoreCase("summary"))
		        {
		        	return currently.get().getByKey(f[i]).split("\\.")[0].replace("\"", "");
		        }
		    }
		}
	    return "Somethings Wrong!"; 
	}
}
