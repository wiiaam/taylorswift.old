package extras;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import extras.urlparsers.ChanParser;
import extras.urlparsers.FileParser;
import extras.urlparsers.RedditParser;
import extras.urlparsers.SteamParser;
import extras.urlparsers.YoutubeParser;

public class URLTitles {
	
	
	public static String find(String s){
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
			
			
			
			
			host = urlc.getURL().getHost();
			
			System.out.println(urlc.getContentType());
			if(!urlc.getContentType().startsWith("text/html")) {
				return FileParser.find(urlc);
			}
			if(s.contains("youtube.com/watch?") || s.contains("youtu.be/")){
				return YoutubeParser.find(s);
			}
			if((s.contains("boards.4chan.org/") ||
					s.contains("//8ch.net")
					)
					&& (s.contains("/thread/") || s.contains("/res/"))){
				return ChanParser.find(s);
			}
			if(s.contains("steamcommunity.com") && (s.contains("/id/") || s.contains("/profiles/"))) {
				return SteamParser.find(s);
			}
			if(s.contains("/comments/") && s.contains("reddit.com/r/")){
				return RedditParser.find(s);
			}
			Document doc = Jsoup.connect(s).get();
			Elements ps = doc.select("title");
			title = ps.text();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			title = "Title not found";
		}
		title = String.format("[URL] %s (%s)",title.trim(), host);
		return title;
	}
	
	public static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        URLConnection urlc = url.openConnection();
			urlc.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			urlc.addRequestProperty("User-Agent", "Mozilla");
			urlc.connect();
	        reader = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	public static String makeClean(String htmlString){
		return Jsoup.parse(htmlString).text();
	}
	
}
