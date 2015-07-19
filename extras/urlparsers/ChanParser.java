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

public class ChanParser {
	@SuppressWarnings("deprecation")
	public static String find(String s){
		String title = "none";
		s = s.replace("thread", "res");
		String[] ssplit = s.split("/");
		ssplit[5] = ssplit[5].split("\\.")[0];
		System.out.println(ssplit[5]);
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
			String subject = makeClean(op.get("com").getAsString().split("<br>")[0] + "");
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
				subject = "12" + makeClean(op.get("sub").getAsString()) + "";
			}
			if(subject.length() > 50){
				subject = subject.substring(0, 49).trim() + "...";
			}
			String created = new Date(op.get("time").getAsLong() * 1000).toGMTString();
			
			title = String.format("/%s/ - %s | Thread no %d | Created %s | %d replies", board, subject, no, created, replies);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			title = "/" + ssplit[3] + "/ - Thread does not exist";
			e.printStackTrace();
		}
		return title;
	}
	
	private static String makeClean(String s){
		s = s.replace("</span>", "");
		s = s.replace("<wbr>", "");
		s = s.replace("</wbr>", "");
		s = s.replace("<br>", " ");
		s = s.replace("&#039;", "'");
		s = s.replace("<span class=\"quote\">", "");
		s = s.replace("<span class=\"heading\">", "");
		s = s.replace("&gt;", "3>");
		s = s.replace("<p class=\"body-line ltr \">", "");
		return s;
	}
}
