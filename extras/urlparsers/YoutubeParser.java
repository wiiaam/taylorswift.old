package extras.urlparsers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

import bot.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class YoutubeParser {
	
	public static String find(String s){
		String videoid = "none";
		if(s.contains("youtu.be")){
			videoid = s.split("youtu.be/")[1].split("\\?")[0];
		}
		else{
			videoid = s.split(".*/.*/.*v=")[1].split("\\?")[0];
		}
		
		try {
			URL url = new URL("https://www.googleapis.com/youtube/v3/videos?key=" + Config.getGoogleApiKey() + "&part=snippet,statistics,contentDetails&id=" + videoid);
			InputStream in = url.openStream();
			Scanner scan = new Scanner(in);
			String jsonstring = "";
			while(scan.hasNext()){
				jsonstring += scan.next() + " ";
			}
			scan.close();
			Gson gson = new GsonBuilder().create();
			JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
			JsonObject items = json.get("items").getAsJsonArray().get(0).getAsJsonObject();
			JsonObject snippet = items.get("snippet").getAsJsonObject();
			JsonObject contentDetails = items.get("contentDetails").getAsJsonObject();
			JsonObject statistics = items.get("statistics").getAsJsonObject();
			
			String title = snippet.get("title").getAsString();
			String uploader = snippet.get("channelTitle").getAsString();
			String views = NumberFormat.getNumberInstance(Locale.US).format(statistics.get("viewCount").getAsInt());
			String likes = NumberFormat.getNumberInstance(Locale.US).format(statistics.get("likeCount").getAsInt());
			String dislikes = NumberFormat.getNumberInstance(Locale.US).format(statistics.get("dislikeCount").getAsInt());
			//String comments = NumberFormat.getNumberInstance(Locale.US).format(statistics.get("commentCount").getAsInt());
			String duration = contentDetails.get("duration").getAsString();
			
			
			String dur;
            if(Pattern.matches("PT.*D.*H.*M.*S", duration)){
            	dur = duration.substring(2,duration.length());
            	String days = dur.split("D")[0];
            	dur = dur.split("D")[1];
            	String hours = dur.split("H")[0];
            	dur = dur.split("H")[1];
            	String minutes = dur.split("M")[0];
            	dur = dur.split("M")[1];
            	String seconds = dur.split("S")[0];
            	dur = String.format("%02d:%02d:%02d:%02d", Integer.parseInt(days), Integer.parseInt(hours), Integer.parseInt(minutes), Integer.parseInt(seconds));
            }
            else if(Pattern.matches("PT.*H.*M.*S", duration)){
            	dur = duration.substring(2,duration.length());
            	String hours = dur.split("H")[0];
            	dur = dur.split("H")[1];
            	String minutes = dur.split("M")[0];
            	dur = dur.split("M")[1];
            	String seconds = dur.split("S")[0];
            	dur = String.format("%02d:%02d:%02d", Integer.parseInt(hours), Integer.parseInt(minutes), Integer.parseInt(seconds));
            }
            else if(Pattern.matches("PT.*M.*S", duration)){
            	dur = duration.substring(2,duration.length());
            	String minutes = dur.split("M")[0];
            	dur = dur.split("M")[1];
            	String seconds = dur.split("S")[0];
            	dur = String.format("%02d:%02d", Integer.parseInt(minutes), Integer.parseInt(seconds));
            }
            else {
            	dur = duration.substring(2,duration.length()-1) + " seconds";
            	
            }
            duration = dur;
            
            
            
            double percentlike;
            if(statistics.get("likeCount").getAsInt() == 0 && statistics.get("dislikeCount").getAsInt() == 0){
            	percentlike = 0.5;
            }
            else if(statistics.get("likeCount").getAsInt() == 0){
            	percentlike = 0;
            }
            else if(statistics.get("dislikeCount").getAsInt() == 0){
            	percentlike = 1;
            }
            else percentlike = statistics.get("likeCount").getAsDouble()/statistics.get("dislikeCount").getAsDouble();
            
            int likeQuartile = (int)Math.ceil(percentlike*10);
            
            String likebar = "3";
            String ratingchar = "↑";
            for(int i = 0; i < 10; i++){
            	if(i == likeQuartile){
            		likebar+= "4";
            		ratingchar = "↓";
            	}
            	likebar+=ratingchar;
            }
            likebar += "";
            
            String videoinfo = String.format("%s | Uploader: %s | Duration: %s | Views: %s |3 %s %s4 %s ", title, uploader, duration, views, likes, likebar, dislikes);
			return videoinfo;
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(IndexOutOfBoundsException e){
			return "" + s + " is not a valid YouTube video.";
		}
		return null;
	}
	public static String findById(String s){
		String videoid = s;
		
		try {
			URL url = new URL("https://www.googleapis.com/youtube/v3/videos?key=" + Config.getGoogleApiKey() + "&part=snippet,statistics,contentDetails&id=" + videoid);
			InputStream in = url.openStream();
			Scanner scan = new Scanner(in);
			String jsonstring = "";
			while(scan.hasNext()){
				jsonstring += scan.next() + " ";
			}
			scan.close();
			Gson gson = new GsonBuilder().create();
			JsonObject json = gson.fromJson(jsonstring, JsonElement.class).getAsJsonObject();
			JsonObject items = json.get("items").getAsJsonArray().get(0).getAsJsonObject();
			JsonObject snippet = items.get("snippet").getAsJsonObject();
			JsonObject contentDetails = items.get("contentDetails").getAsJsonObject();
			JsonObject statistics = items.get("statistics").getAsJsonObject();
			
			String title = snippet.get("title").getAsString();
			String uploader = snippet.get("channelTitle").getAsString();
			String views = NumberFormat.getNumberInstance(Locale.US).format(statistics.get("viewCount").getAsInt());
			String likes = NumberFormat.getNumberInstance(Locale.US).format(statistics.get("likeCount").getAsInt());
			String dislikes = NumberFormat.getNumberInstance(Locale.US).format(statistics.get("dislikeCount").getAsInt());
			//String comments = NumberFormat.getNumberInstance(Locale.US).format(statistics.get("commentCount").getAsInt());
			String duration = contentDetails.get("duration").getAsString();
			
			
			String dur;
            if(Pattern.matches("PT.*D.*H.*M.*S", duration)){
            	dur = duration.substring(2,duration.length());
            	String days = dur.split("D")[0];
            	dur = dur.split("D")[1];
            	String hours = dur.split("H")[0];
            	dur = dur.split("H")[1];
            	String minutes = dur.split("M")[0];
            	dur = dur.split("M")[1];
            	String seconds = dur.split("S")[0];
            	dur = String.format("%02d:%02d:%02d:%02d", Integer.parseInt(days), Integer.parseInt(hours), Integer.parseInt(minutes), Integer.parseInt(seconds));
            }
            else if(Pattern.matches("PT.*H.*M.*S", duration)){
            	dur = duration.substring(2,duration.length());
            	String hours = dur.split("H")[0];
            	dur = dur.split("H")[1];
            	String minutes = dur.split("M")[0];
            	dur = dur.split("M")[1];
            	String seconds = dur.split("S")[0];
            	dur = String.format("%02d:%02d:%02d", Integer.parseInt(hours), Integer.parseInt(minutes), Integer.parseInt(seconds));
            }
            else if(Pattern.matches("PT.*M.*S", duration)){
            	dur = duration.substring(2,duration.length());
            	String minutes = dur.split("M")[0];
            	dur = dur.split("M")[1];
            	String seconds = dur.split("S")[0];
            	dur = String.format("%02d:%02d", Integer.parseInt(minutes), Integer.parseInt(seconds));
            }
            else {
            	dur = duration.substring(2,duration.length()-1) + " seconds";
            	
            }
            duration = dur;
            
            
            
            double percentlike;
            if(statistics.get("likeCount").getAsInt() == 0 && statistics.get("dislikeCount").getAsInt() == 0){
            	percentlike = 0.5;
            }
            else if(statistics.get("likeCount").getAsInt() == 0){
            	percentlike = 0;
            }
            else if(statistics.get("dislikeCount").getAsInt() == 0){
            	percentlike = 1;
            }
            else percentlike = statistics.get("likeCount").getAsDouble()/statistics.get("dislikeCount").getAsDouble();
            
            int likeQuartile = (int)Math.ceil(percentlike*10);
            
            String likebar = "3";
            String ratingchar = "↑";
            for(int i = 0; i < 10; i++){
            	if(i == likeQuartile){
            		likebar+= "4";
            		ratingchar = "↓";
            	}
            	likebar+=ratingchar;
            }
            likebar += "";
            
            String videoinfo = String.format("%s | Uploader: %s | Duration: %s | Views: %s |3 %s %s4 %s ", title, uploader, duration, views, likes, likebar, dislikes);
			return videoinfo;
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(IndexOutOfBoundsException e){
			return "" + s + " is not a valid videoid";
		}
		return null;
	}
}
