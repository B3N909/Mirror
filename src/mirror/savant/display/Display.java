package mirror.savant.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import mirror.savant.spotify.Spotify;
import mirror.savant.spotify.SpotifyDetails;
import mirror.savant.weather.Time;
import mirror.savant.weather.Weather;

public class Display
{	
	private static boolean isRunning = true;
	private static JFrame frame;
	private static Spotify spotify;
	
	public static void main(String[] args)
	{	
		//IntelligenceManager ai = new IntelligenceManager(new TTSManager(TTSType.MARYTTS_CLIENT));
		
		spotify = new Spotify();
//		spotify.startStream();
		
		/** User-Interface **/
		Thread thread = new Thread()
		{
			public void run()
			{
				initialize();
			}
		};
		thread.start();
		
		/** Console **/
		while(isRunning)
		{
			Scanner reader = new Scanner(System.in);
			String s = reader.next();
			if(s.equalsIgnoreCase("end") || s.equalsIgnoreCase("exit"))
			{
				isRunning = false;
			}
			else
			{
				System.out.println("Unknown Command; Non-AI Build");
				//ai.giveCommand(s);
			}
		}
		
		if(frame != null)
		{
			frame.setVisible(false);
			frame.dispose();
		}
		System.exit(-1);
	}
	
	private static JLabel temperatureLabel;
	private static JLabel timeLabel;
	private static JLabel weatherLabel;
	
	private static void weatherUpdate()
	{
		temperatureLabel.setText(Weather.GetTemperature());
		weatherLabel.setText(Weather.GetSummary());
		
		try
		{
			Thread.sleep(900000L); //15-Minutes
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		weatherUpdate();
	}
	
	private static void spotifyUpdate()
	{
		SpotifyDetails details = spotify.getInfo();
		
		if(details == null)
		{
			songTitle.setText("");
			authorLabel.setText("");
		}
		else
		{
			songTitle.setText(details.getTitle() + " - " + details.getAlbum());
			authorLabel.setText(details.getArtist());
		}
		
		try
		{
			Thread.sleep(15000L); //15-Seconds
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		spotifyUpdate();
	}
	
	private static void update()
	{
		timeLabel.setText(Time.getTime());
		statusLabel.setText(status);
		if(!frame.isVisible())
		{
			System.out.println("~Goodbye!");
			System.exit(-1);
		}
		try
		{
			Thread.sleep(5000L); //15-Seconds
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		update();
	}
	
	public static String status = "Ay, Okay";
	private static JLabel statusLabel;
	private static JLabel songTitle;
	private static JLabel authorLabel;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private static void initialize()
	{
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);		
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setForeground(Color.BLACK);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1207, 1285, 0};
		gbl_panel.rowHeights = new int[]{264, 685, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		Browser browser = new Browser();
		GridBagConstraints gbc_browser = new GridBagConstraints();
		gbc_browser.gridwidth = 2;
		gbc_browser.gridheight = 3;
		gbc_browser.gridy = 0;
		gbc_browser.gridx = 0;
//		browser.loadURL("https://open.spotify.com/embed/user/frrancis909/playlist/2JDqEz8Kb76a8rG8IWIRGB");
		BrowserView view = new BrowserView(browser);
		panel.add(view, gbc_browser);
		
		weatherLabel = new JLabel("Loading");
		weatherLabel.setVerticalAlignment(SwingConstants.TOP);
		weatherLabel.setForeground(Color.WHITE);
		weatherLabel.setBackground(Color.WHITE);
		weatherLabel.setHorizontalAlignment(SwingConstants.LEFT);
		weatherLabel.setFont(new Font("Calibri Light", Font.PLAIN, 45));
		GridBagConstraints gbc_weatherLabel = new GridBagConstraints();
		gbc_weatherLabel.fill = GridBagConstraints.VERTICAL;
		gbc_weatherLabel.anchor = GridBagConstraints.WEST;
		gbc_weatherLabel.insets = new Insets(0, 0, 5, 5);
		gbc_weatherLabel.gridx = 0;
		gbc_weatherLabel.gridy = 1;
		panel.add(weatherLabel, gbc_weatherLabel);
		
		temperatureLabel = new JLabel("0" + Weather.DEGREE_SYMBOL);
		temperatureLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		temperatureLabel.setForeground(Color.WHITE);
		temperatureLabel.setFont(new Font("Bebas Neue Book", Font.PLAIN, 300));
		temperatureLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_temperatureLabel = new GridBagConstraints();
		gbc_temperatureLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_temperatureLabel.insets = new Insets(0, 0, 5, 5);
		gbc_temperatureLabel.gridx = 0;
		gbc_temperatureLabel.gridy = 0;
		panel.add(temperatureLabel, gbc_temperatureLabel);
		
		timeLabel = new JLabel("0:00 AM");
		timeLabel.setFont(new Font("Bebas Neue Book", Font.PLAIN, 120));
		timeLabel.setVerticalAlignment(SwingConstants.TOP);
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setBackground(Color.WHITE);
		GridBagConstraints gbc_timeLabel = new GridBagConstraints();
		gbc_timeLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_timeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_timeLabel.gridx = 1;
		gbc_timeLabel.gridy = 0;
		panel.add(timeLabel, gbc_timeLabel);
		
		authorLabel = new JLabel("test");
		authorLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		authorLabel.setHorizontalAlignment(SwingConstants.LEFT);
		authorLabel.setForeground(Color.WHITE);
		authorLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
		authorLabel.setBackground(Color.WHITE);
		GridBagConstraints gbc_authorLabel = new GridBagConstraints();
		gbc_authorLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_authorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_authorLabel.gridx = 0;
		gbc_authorLabel.gridy = 2;
		panel.add(authorLabel, gbc_authorLabel);
		
		statusLabel = new JLabel(status);
		statusLabel.setForeground(Color.GRAY);
		statusLabel.setFont(new Font("Source Code Pro", Font.PLAIN, 30));
		statusLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_statusLabel.gridx = 1;
		gbc_statusLabel.gridy = 2;
		panel.add(statusLabel, gbc_statusLabel);
		
		songTitle = new JLabel("");
		songTitle.setVerticalAlignment(SwingConstants.BOTTOM);
		songTitle.setHorizontalAlignment(SwingConstants.LEFT);
		songTitle.setFont(new Font("Calibri", Font.PLAIN, 50));
		songTitle.setForeground(Color.WHITE);
		songTitle.setBackground(Color.WHITE);
		GridBagConstraints gbc_songTitle = new GridBagConstraints();
		gbc_songTitle.fill = GridBagConstraints.VERTICAL;
		gbc_songTitle.anchor = GridBagConstraints.WEST;
		gbc_songTitle.insets = new Insets(0, 0, 5, 5);
		gbc_songTitle.gridx = 0;
		gbc_songTitle.gridy = 1;
		panel.add(songTitle, gbc_songTitle);
		frame.setBounds(100, 100, 450, 300);
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
		
		Thread weatherThread = new Thread()
		{
			public void run()
			{
				weatherUpdate();
			}
		};
		weatherThread.start();
		
		Thread updateThread = new Thread()
		{
			public void run()
			{
				update();
			}
		};
		updateThread.start();
	
		Thread spotifyThread = new Thread()
		{
			public void run()
			{
				spotifyUpdate();
			}
		};
		spotifyThread.start();
	}
}
