package mirror.savant.spotify;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Spotify
{
	public static final String API = "5e2f5dcbe0b3105b626fc74a5093cac5";
	public static final String USERNAME = "savantgamer";
	public static final String BASE_URL = "http://ws.audioscrobbler.com/";
	
	public Spotify()
	{
		
	}
	
	public SpotifyDetails getInfo()
	{
		try
		{
			String url = BASE_URL + "2.0/?method=user.getrecenttracks&user=" + USERNAME + "&api_key=" + API;
			
			HttpGet request = new HttpGet(url);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(request);
			String content = EntityUtils.toString(response.getEntity());
						
			if(content.contains("track nowplaying=\"true\""))
			{
				if((StringUtils.countMatches(content, "track") / 2) - 1 > 1)
					content = content.split("<track nowplaying=\"true\">")[1].split("</track>")[0];
				
				String title = split(content, "name");
				String album = content.split("album")[1].split("</album>")[0].split(">")[1].replace("</", "");
				String artist = content.split("</artist>")[0].split(">")[1];
				String small = findSize(content, "small");
				String medium = findSize(content, "medium");
				String large = findSize(content, "large");
				String extraLarge = findSize(content, "extralarge");
				
				SpotifyDetails details = new SpotifyDetails(title, album, artist, small, medium, large, extraLarge);				
				return details;
			}
			else
			{
				return null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private String split(String json, String divider)
	{
		String half1 = json.split("<" + divider + ">")[1];
		String value = half1.split("</" + divider + ">")[0];
		return value;
	}
	
	private String findSize(String json, String size)
	{
		return json.split("\"" + size + "\">")[1].split("</image>")[0];
	}
	
	public void startStream()
	{
		WebClient wc = new WebClient(BrowserVersion.FIREFOX_38);
		
		/** LOGGING **/
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
				
		/** WEB CLIENT SETTINGS **/
		wc.setAjaxController(new NicelyResynchronizingAjaxController());
		wc.waitForBackgroundJavaScript(10000);
		wc.waitForBackgroundJavaScriptStartingBefore(10000);
		wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
		wc.getOptions().setCssEnabled(true);
		wc.getOptions().setRedirectEnabled(true);
		wc.getOptions().setThrowExceptionOnScriptError(false);
		wc.getOptions().setRedirectEnabled(true);
		
		/** COOKIES **/
		CookieManager cookieManager = new CookieManager();
		cookieManager = wc.getCookieManager();
		cookieManager.setCookiesEnabled(true);
		
		try {
			HtmlPage page = wc.getPage("https://open.spotify.com/embed/user/frrancis909/playlist/2JDqEz8Kb76a8rG8IWIRGB");
			System.out.println(page.asXml());
			System.out.println(page.asText());
			HtmlButton button = (HtmlButton)page.getElementById("play-button");
			System.out.println("==============================================================================");
			HtmlPage page1 = button.click();
			System.out.println("==============================================================================");
			System.out.println(page1.asText());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
