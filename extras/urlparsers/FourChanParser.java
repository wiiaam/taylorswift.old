package extras.urlparsers;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FourChanParser {
	public static String find(String s){
		String title = "none";
		try{
			String thread = s.split(".*.org/")[1].split("#")[0];
			URL url = new URL("https://a.4cdn.org/" + thread + ".json");
			InputStream in = url.openStream();
			Scanner scan = new Scanner(in);
			String jsonstring = "";
			while(scan.hasNext()){
				jsonstring += scan.next() + " ";
			}
			scan.close();
			
			Gson gson = new GsonBuilder().create();
			JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
			JsonArray posts = json.get("posts").getAsJsonArray();
			JsonObject op = posts.get(0).getAsJsonObject();
			String board = s.split(".*org/")[1].split("/")[0];
			String subject = makeClean(op.get("com").getAsString().split("<br>")[0] + "");
			int no = op.get("no").getAsInt();
			int replies = op.get("replies").getAsInt();
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
			if(op.get("sub") != null){
				subject = "12" + makeClean(op.get("sub").getAsString()) + "";
				if(subject.length() > 30){
					subject = subject.substring(0, 50) + "...";
				}
			}
			if(subject.length() > 50){
				subject = subject.substring(0, 49).trim() + "...";
			}
			String created = op.get("now").getAsString();
			
			title = String.format("/%s/ - %s | Thread no %d | %s |%s %d /%s %d / %d %s", board, subject, no, created, bumplimit, replies, imagelimit, files, ips, autosage);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			title = "/" + s.split(".*org/")[1].split("/")[0] + "/ - Thread does not exist";
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
		s = s.replace("&gt;", "3>");
		return s;
	}
}
