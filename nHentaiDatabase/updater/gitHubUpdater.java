package updater;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class gitHubUpdater {
	
	public static void main(String[] arg) {
		new gitHubUpdater();
	}
	
	public gitHubUpdater() {
		
	}
	
	/**
	 * Downloads the latest version of nHentaiDatabse and saves it to the given location.
	 * 
	 * @param downloadLocation The given save location
	 */
	public void downloadUpdate(String downloadLocation) {
		String sUrl = "https://github.com/PhilippBleimund/nHentaiDatabase/releases/latest/download/update.jar";
		
		URL url = null;
		try {
			url = new URL(sUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//File where to be downloaded
		File file = new File(downloadLocation);
		
		URLReader.copyURLToFile(url, file);
	}
	
	/**
	 * Checks if a new version of nHentaiDatabase is available.
	 * 
	 * @param currVersion The current version of nHentaiDatabase
	 * @return boolean
	 */
	public boolean getIfNewVersion(String currVersion) {
		String Tag = getVersionTag();
		if(currVersion.equals(Tag))
			return false;
		return true;
	}
	
	/**
	 * Get the latest version of nHentaiDatabase
	 * 
	 * @param URL The gitHub repository
	 * @return String The VersionTag (e.g. 1.2.3)
	 */
	public String getVersionTag() {
		String URL = "https://github.com/PhilippBleimund/nHentaiDatabase/releases/latest/";
		String newURL = "";
		try {
			newURL = (getFinalURL(URL));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int startTag = 0;
		char[] newURLChar = newURL.toCharArray();
		for(int i=newURLChar.length-1;i>=0;--i) {
			if(newURLChar[i] == '/') {
				startTag = i + 1;
				i = 0;
			}
		}
		
		String Tag = newURL.substring(startTag);
		return Tag;
	}
	
	/**
	 * Get the final URL of a nested URL System. URL1 points to URL2.
	 * 
	 * @param url The start URL
	 * @return String The end URL
	 * @throws IOException
	 */
	public static String getFinalURL(String url) throws IOException {
	    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
	    con.setInstanceFollowRedirects(false);
	    con.connect();
	    con.getInputStream();

	    if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM || con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
	        String redirectUrl = con.getHeaderField("Location");
	        return getFinalURL(redirectUrl);
	    }
	    return url;
	}
}
