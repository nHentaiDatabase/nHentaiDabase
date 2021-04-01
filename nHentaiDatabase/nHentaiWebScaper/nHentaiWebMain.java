package nHentaiWebScaper;

import java.io.IOException;

import org.jsoup.Jsoup;

public class nHentaiWebMain {

	public static void main(String[] args) throws IOException {
		nHentaiWebBase nHentai = new nHentaiWebBase();
		nHentai.initDocWithURL("https://nhentai.net/g/353127/");
		System.out.println(nHentai.getTitle());
	}

}
