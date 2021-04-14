package nHentaiWebScaper;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import settings.confirmDeleteEverything;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

public class nHentaiWebBase {
	
	Document doc;
	
	public nHentaiWebBase(){
		
	}
	
	public void initDocWithCode(String number) throws IOException{
		String baseURL = "https://nhentai.net/g/";
		String newURL = baseURL + number + "/";
		
		doc = Jsoup.connect(newURL).timeout(1000).maxBodySize(0) .get();
	}
	
	public void initDocWithURL(String URL) throws IOException{
		doc = Jsoup.connect(URL).timeout(1000).maxBodySize(0).get();
	}
	
	public String getCoverImage() {
		
		Element content = doc.getElementById("content");
		Element bigcontainer = content.getElementById("bigcontainer");
		Element cover = bigcontainer.getElementById("cover");
		Elements a = cover.select("a");
		Elements img = cover.select("img");
		
		return img.attr("data-src");
	}
	
	public String getTitle() {
		
		Element content = doc.getElementById("content");
		Element bigcontainer = content.getElementById("bigcontainer");
		Element infoBlock = bigcontainer.getElementById("info-block");
		Element info = infoBlock.getElementById("info");
		Elements title = info.getAllElements();
		Element title2 = title.get(0);
		Elements title3 = title2.select("h1");
		Element h1 = title3.get(0);
		Elements pretty = h1.getElementsByClass("pretty");
		
		return pretty.text();
	}
	
	public String getId() {
		
		Element content = doc.getElementById("content");
		Element bigcontainer = content.getElementById("bigcontainer");
		Element infoBlock = bigcontainer.getElementById("info-block");
		Element info = infoBlock.getElementById("info");
		Elements title = info.getAllElements();
		Element title2 = title.get(0);
		Element gallery_id = title2.getElementById("gallery_id");
		
		return gallery_id.text();
	}
	
	public String[] getTags() {
		
		Element content = doc.getElementById("content");
		Element bigcontainer = content.getElementById("bigcontainer");
		Element infoBlock = bigcontainer.getElementById("info-block");
		Element info = infoBlock.getElementById("info");
		Elements title = info.getAllElements();
		Element title2 = title.get(0);
		Element tags = title2.getElementById("tags");
		Elements tags2 = tags.getAllElements();
		Elements tags3 = tags2.select("div");
		Element tags4 = tags3.get(2);
		Elements tags5 = tags4.getAllElements();
		String[] tagsContend = new String[((tags5.size())-2)/3];
		tagsContend[0] = "noTagsAvailable";
		int i=0;
		for(Element e : tags5.select("a")) {
			Elements e2 = e.getElementsByClass("name");
			tagsContend[i] = e2.text();
			i++;
		}
		
		return tagsContend;
	}
	
	public String getArtist() {
		
		Element content = doc.getElementById("content");
		Element bigcontainer = content.getElementById("bigcontainer");
		Element infoBlock = bigcontainer.getElementById("info-block");
		Element info = infoBlock.getElementById("info");
		Elements title = info.getAllElements();
		Element title2 = title.get(0);
		Element tags = title2.getElementById("tags");
		Elements tags2 = tags.getAllElements();
		Elements tags3 = tags2.select("div");
		Element artist = tags3.get(3);
		Elements artist2 = artist.getElementsByClass("name");
		
		return artist2.text();
	}
	
	public String getPages() {
		
		Element content = doc.getElementById("content");
		Element bigcontainer = content.getElementById("bigcontainer");
		Element infoBlock = bigcontainer.getElementById("info-block");
		Element info = infoBlock.getElementById("info");
		Elements title = info.getAllElements();
		Element title2 = title.get(0);
		Element tags = title2.getElementById("tags");
		Elements tags2 = tags.getAllElements();
		Elements tags3 = tags2.select("div");
		Element pages = tags3.get(7);
		Elements pages2 = pages.getElementsByClass("name");
		
		return pages2.text();
	}
	
	public void saveImageAsFile(String URLpicture, String saveLocation) {
		try(InputStream in = new URL(URLpicture).openStream()){
		    Files.copy(in, Paths.get(saveLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
