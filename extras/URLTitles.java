package extras;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import extras.urlparsers.FileParser;
import extras.urlparsers.YoutubeParser;

public class URLTitles {
	
	
	public static String find(String s){
		if(s.contains("youtube.com/watch?") || s.contains("youtu.be/")) return YoutubeParser.find(s);
		URL url;
		String title = "";
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
			while(scan.hasNextLine()){
				String next = scan.nextLine().trim();
				System.out.println(next);
				if(!title.equals("")){
					title += next;
					if(title.contains("</title")){
						title = title.split("</title")[0].trim();
						break;
					}
				}
				if(next.contains("<title")){
					System.out.println("found title");
					title = next.split("<title")[1].split(">")[1].trim();
					if(title.contains("</title")){
						title = title.split("</title")[0].trim();
						break;
					}
				}
				if(i > 1000){
					title = "Title not found";
					break;
				}
				i++;
			}
			scan.close();
			if(title.equals("")) title = "Title not found";
			title = title.replaceAll("&.*;","");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		title = String.format("[URL] %s (%s)",title, host);
		return title;
	}
}
