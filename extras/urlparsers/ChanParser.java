package extras.urlparsers;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import extras.URLTitles;

public class ChanParser {
	@SuppressWarnings("deprecation")
	public static String find(String s){
		String title = "none";
		s = s.replace("thread", "res");
		String[] ssplit = s.split("/");
		ssplit[5] = ssplit[5].split("\\.")[0];
		String urlstring = s.split("res/?")[0] + "res/" + ssplit[5] + ".json";
		try{
			URL url = new URL(urlstring);
			URLConnection urlc = url.openConnection();
			urlc.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			urlc.addRequestProperty("User-Agent", "Mozilla");
			//urlc.addRequestProperty("Referer", "google.com");
			urlc.connect();
			Scanner scan = new Scanner(urlc.getInputStream());
			String jsonstring = "";
			while(scan.hasNext()){
				jsonstring += scan.next() + " ";
			}
			scan.close();
			
			Gson gson = new GsonBuilder().create();
			JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
			JsonArray posts = json.get("posts").getAsJsonArray();
			JsonObject op = posts.get(0).getAsJsonObject();
			String board = ssplit[3];
			String subject;
			if(op.has("com")){
				 subject = URLTitles.makeClean(op.get("com").getAsString() + "");
			}
			else{
				subject = "No Subject";
			}
			int no = op.get("no").getAsInt();
			int replies = posts.size()-1;
			
			/*
			int files = op.get("images").getAsInt();
			int ips = op.get("unique_ips").getAsInt();
			
			String autosage = "";
			String bumplimit = "";
			String imagelimit = "";
			if(!op.has("sticky")){
				if(op.get("bumplimit").getAsInt() == 1){
					autosage = "4AUTOSAGE INCOMING";
					bumplimit = "4";
				}
				if(op.get("imagelimit").getAsInt() == 1){
					autosage = "4AUTOSAGE INCOMING";
					imagelimit = "4";
				}
			}
			else{
				autosage = "12Stickied Thread";
			}
			*/
			if(op.get("sub") != null){
				subject = "12" + URLTitles.makeClean(op.get("sub").getAsString()) + "";
			}
			if(subject.length() > 50){
				subject = subject.substring(0, 49).trim() + "...";
			}
			String created = new Date(op.get("time").getAsLong() * 1000).toGMTString();
			
			title = String.format("/%s/ - %s | Thread no %d | Created %s | %d replies", board, subject, no, created, replies);
		} catch (Exception e) {
			e.printStackTrace();
			title = "Error, no info could be found";
		}
		return title;
	}
}
