package extras;

import extras.urlparsers.YoutubeParser;

public class URLTitles {
	
	
	public static String find(String s){
		if(s.contains("youtube.com/watch?") || s.contains("youtu.be")) return YoutubeParser.find(s);
		return null;
	}
}
