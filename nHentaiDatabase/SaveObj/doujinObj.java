package SaveObj;

public class doujinObj {
	public String coverImage;
	public String title;
	public String id;
	public String artist;
	public String pages;
	public String status;
	public String rating;
	public String timesRead;
	public String[] tags;
	
	/**
	 * Constructor of a single doujin
	 * 
	 * @param coverImage The URL to the cover image
	 * @param title The title of the doujin
	 * @param id The nHentai id of the doujin
	 * @param artist The artist of the doujin
	 * @param pages The number of pages
	 * @param status The currend status of the doujin (reading, etc.)
	 * @param rating The rating the user gave the doujin
	 * @param timesRead How many times the user read the doujin
	 * @param tags The nHentai tags of the doujin
	 */
	public doujinObj(String coverImage,
	String title,
	String id,
	String artist,
	String pages,
	String status,
	String rating,
	String timesRead,
	String[] tags) {
		this.coverImage = coverImage;
		this.title = title;
		this.id = id;
		this.artist = artist;
		this.pages = pages;
		this.status = status;
		this.rating = rating;
		this.timesRead = timesRead;
		this.tags = tags;
	}
}
