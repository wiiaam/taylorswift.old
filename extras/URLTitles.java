package extras;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Scanner;

import extras.urlparsers.ChanParser;
import extras.urlparsers.FileParser;
import extras.urlparsers.RedditParser;
import extras.urlparsers.SteamParser;
import extras.urlparsers.YoutubeParser;

public class URLTitles {
	
	
	public static String find(String s){
		if(s.contains("youtube.com/watch?") || s.contains("youtu.be/")) return YoutubeParser.find(s);
		if((s.contains("boards.4chan.org/") ||
				s.contains("//8ch.net")
				)
				&& (s.contains("/thread/") || s.contains("/res/"))) return ChanParser.find(s);
		if(s.contains("steamcommunity.com") && (s.contains("/id/") || s.contains("/profiles/"))) return SteamParser.find(s);
		if(s.contains("/comments/") && s.contains("reddit.com/r/")) return RedditParser.find(s);
		URL url;
		String title = "Title not found";
		String host = "";
		try {
			
			url = new URL(s);
			URLConnection urlc = url.openConnection();
			urlc.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			urlc.addRequestProperty("User-Agent", "Mozilla");
			//urlc.addRequestProperty("Referer", "google.com");
			urlc.connect();
			
			
			
			
			Scanner scan = new Scanner(urlc.getInputStream());
			host = urlc.getURL().getHost();
			
			System.out.println(urlc.getContentType());
			if(!urlc.getContentType().startsWith("text/html")) {
				scan.close();
				return FileParser.find(urlc);
			}
			boolean titlefound = false;
			String titlecode = "";
			while(scan.hasNextLine()){
				String next = scan.nextLine();
				if(next.contains("<title") && !titlefound){
					titlefound = true;
					titlecode += next;
					continue;
					
				}
				if(next.contains("<TITLE") && !titlefound){
					titlefound = true;
					titlecode += next;
					continue;
					
				}
				if(titlefound){
					titlecode +=  next;
				}
				if(titlecode.length() > 200){
					titlecode += "...";
					break;
				}
				if(titlecode.contains("</title>") || titlecode.contains("</TITLE>")){
					break;
				}
				
				
			}
			if(title.length() > 100){
				title = title.substring(0, 95) + "...";
			}
			scan.close();
			if(titlecode.contains("<TITLE"))title = titlecode.split("<TITLE?>")[1];
			if(titlecode.contains("<title"))title = titlecode.split("<title?>")[1];
			if(title.contains("</TITLE>"))title = title.split("</TITLE")[0];
			if(title.contains("</title>"))title = title.split("</title")[0];
			//}
			
			if(title.equals("")) title = "Title not found";
			title = makeClean(title);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			title = "Title not found";
		}
		title = String.format("[URL] %s (%s)",title.trim(), host);
		return title;
	}
	
	public static String makeClean(String s){
		s = s.replaceAll("&#039;","'");
		s = s.replaceAll("&#124;","|");
		return s;
	}
	
}
