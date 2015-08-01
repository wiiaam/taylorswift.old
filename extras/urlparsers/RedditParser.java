package extras.urlparsers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RedditParser {
	public static String find(String s){
		String title = "none";
		String urlstring = s;
		urlstring = urlstring + ".json";
		try{
			URL url = new URL(urlstring);
			URLConnection urlc = url.openConnection();
			urlc.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			urlc.addRequestProperty("User-Agent", "Mozilla");
			urlc.connect();
			Scanner scan = new Scanner(urlc.getInputStream());
			String jsonstring = "";
			while(scan.hasNext()){
				jsonstring += scan.next() + " ";
			}
			scan.close();
			
			Gson gson = new GsonBuilder().create();
			JsonArray json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonArray();
			JsonObject first = json.get(0).getAsJsonObject();
			JsonObject data = first.get("data").getAsJsonObject(); 
			JsonArray children = data.get("children").getAsJsonArray();
			JsonObject info = children.get(0).getAsJsonObject();
			JsonObject infodata = info.get("data").getAsJsonObject();
			int numComments = infodata.get("num_comments").getAsInt();
			String created = new Date((long)infodata.get("created_utc").getAsDouble() * 1000).toGMTString();
			String subreddit = infodata.get("subreddit").getAsString();
			String postTitle = infodata.get("title").getAsString();
			String link = "";
			if(!infodata.get("domain").getAsString().startsWith("self.")){
				link = "URL: " + infodata.get("url").getAsString() + " | ";
				link = link.replace("http://", "").replace("https://", "");
			}
			title = String.format("/r/%s | 2%s | %sComments: %d | Created %s", subreddit, postTitle, link, numComments, created);
			return title;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			title = "Could not find info";
			e.printStackTrace();
		}
		return title;
	}
}
