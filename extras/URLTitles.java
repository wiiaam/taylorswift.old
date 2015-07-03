package extras;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import extras.urlparsers.FileParser;
import extras.urlparsers.FourChanParser;
import extras.urlparsers.SteamParser;
import extras.urlparsers.YoutubeParser;

public class URLTitles {
	
	
	public static String find(String s){
		if(s.contains("youtube.com/watch?") || s.contains("youtu.be/")) return YoutubeParser.find(s);
		if(s.contains("boards.4chan.org/") && s.contains("/thread/")) return FourChanParser.find(s);
		if(s.contains("steamcommunity.com") && (s.contains("/id/") || s.contains("/profiles/"))) return SteamParser.find(s);
		URL url;
		String title = "Title not found";
		String host = "";
		try {
			
			url = new URL(s);
			URLConnection urlc = url.openConnection();
			urlc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
			urlc.connect();
			if(!urlc.getContentType().startsWith("text/html")) return FileParser.find(s);
			
			host = urlc.getURL().getHost();
			
			Scanner scan = new Scanner(urlc.getInputStream());
			int i = 0;
			boolean titlefound = false;
			boolean allcaps = false;
			String titlecode = "";
			while(scan.hasNextLine()){
				String next = scan.nextLine();
				if(next.contains("<title") && !titlefound){
					titlefound = true;
					titlecode += next;
					continue;
					
				}
				if(next.contains("<TITLE") && !titlefound){
					allcaps = true;
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
			
			if(allcaps){
				title = titlecode.split("<TITLE?>")[1].split("</TITLE")[0];
			}
			else{
				title = titlecode.split("<title?>")[1].split("</title")[0];
			}
			
			if(title.equals("")) title = "Title not found";
			title = title.replaceAll("&.*;","");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			title = "Title not found";
			e.printStackTrace();
		}
		title = String.format("[URL] %s (%s)",title, host);
		return title;
	}
}
