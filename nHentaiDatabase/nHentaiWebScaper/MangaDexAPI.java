package nHentaiWebScaper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class MangaDexAPI {

	public static void main(String[] args) {
		new MangaDexAPI();
	}
	
	public MangaDexAPI() {

	  // Format query for preventing encoding problems
	      try {
			String query = String.format("s=%s",
			   URLEncoder.encode("Pulp", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      HttpResponse<JsonNode> response = null;
	      try {
			response = Unirest.get("https://api.mangadex.org/manga/a77742b1-befd-49a4-bff5-1ad4e6b0ef7b").asJson();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      System.out.println(response.getBody().getObject());
	      System.out.println(authorId(response));
	      System.out.println(authorName(authorId(response)));
	      mangaChapters("a77742b1-befd-49a4-bff5-1ad4e6b0ef7b");
	      
	      /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
	      JsonParser jp = new JsonParser();
	      JsonElement je = jp.parse(response.getBody().getObject().getJSONArray("relationships").getJSONObject(0).getString("id").toString());
	      String prettyJsonString = gson.toJson(je);
	      System.out.println(prettyJsonString);*/
	}
	
	public String authorId(HttpResponse<JsonNode> Json) {
		String rawId = Json.getBody().getObject().getJSONArray("relationships").getJSONObject(0).getString("id").toString();
		rawId.substring(1, rawId.length()-2);
		return rawId;
	}
	
	public String authorName(String authorId) {
		HttpResponse<JsonNode> response = null;
	      try {
			response = Unirest.get("https://api.mangadex.org/author/" + authorId).asJson();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String author = response.getBody().getObject().getJSONObject("data").getJSONObject("attributes").get("name").toString();
		return author;
	}
	
	
	
	public void mangaChapters(String mangaId) {
		HttpResponse<JsonNode> response = null;
	      try {
			response = Unirest.get("https://api.mangadex.org/manga/" + mangaId + "/aggregate").asJson();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    JSONObject volumes = response.getBody().getObject().getJSONObject("volumes");
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	      JsonParser jp = new JsonParser();
	      JsonElement je = jp.parse(volumes.getJSONObject("1").toString());
	      String prettyJsonString = gson.toJson(je);
	      System.out.println(prettyJsonString);
	    JSONObject[] chapters = new JSONObject[volumes.length()];
	    if(volumes.has("N/A")) {
	    	chapters[chapters.length-1] = volumes.getJSONObject("N/A");
	    }
	    for(int i=0;i<chapters.length;i++) {
	    	if(volumes.has(String.valueOf(i + 1))){
	    		chapters[i] = volumes.getJSONObject(String.valueOf(i + 1));
	    		System.out.println(chapters[i]);
	    	}
	    }
	    
	}
}

