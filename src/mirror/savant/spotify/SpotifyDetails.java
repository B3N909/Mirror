package mirror.savant.spotify;

public class SpotifyDetails
{
	private String title;
	private String album;
	private String artist;
	private String smallAlbumLink;
	private String mediumAlbumLink;
	private String largeAlbumLink;
	private String extraLargeAlbumLink;
	
	public SpotifyDetails(String title, String album, String artist, String smallAlbumLink, String mediumAlbumLink, String largeAlbumLink, String extraLargeAlbumLink)
	{
		this.title = title;
		this.album = album;
		this.artist = artist;
		this.smallAlbumLink = smallAlbumLink;
		this.mediumAlbumLink = mediumAlbumLink;
		this.largeAlbumLink = largeAlbumLink;
		this.extraLargeAlbumLink = extraLargeAlbumLink;
	}

	public String getTitle()
	{
		return title;
	}

	public String getAlbum()
	{
		return album.replace("&amp;", "&");
	}

	public String getArtist()
	{
		return artist;
	}

	public String getSmallAlbumLink()
	{
		return smallAlbumLink;
	}

	public String getMediumAlbumLink()
	{
		return mediumAlbumLink;
	}

	public String getLargeAlbumLink()
	{
		return largeAlbumLink;
	}

	public String getExtraLargeAlbumLink()
	{
		return extraLargeAlbumLink;
	}
}
