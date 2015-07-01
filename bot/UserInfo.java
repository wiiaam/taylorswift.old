package bot;

import java.util.HashMap;
import java.util.HashSet;

public class UserInfo {
	private static HashMap<String, HashMap<String, String>> userinfo = new HashMap<String, HashMap<String, String>>();
	private static HashMap<String, HashSet<String>> userchans = new HashMap<String, HashSet<String>>();
	
	public static void parse(String s){
		String[] split = s.split("\\s+");
		HashMap<String, String> info = new HashMap<String, String>();
		info.put("host", split[2]);
		info.put("user", split[1]);
		info.put("realname", split[7]);
		info.put("modes", split[5]);
		userinfo.put(split[4], info);
		if(userchans.containsKey(split[4])){
			HashSet<String> rooms = userchans.get(split[4]);
			rooms.add(split[0]);
			userchans.put(split[4], rooms);
		}
		else{
			HashSet<String> rooms = new HashSet<String>();
			rooms.add(split[0]);
			userchans.put(split[4], rooms);
		}
	}
	
	public static boolean remove(String nick){
		if(!userinfo.containsKey(nick))return false;
		userinfo.remove(nick);
		userchans.remove(nick);
		return true;
	}
	
	public static HashMap<String, String> getInfo(String nick){
		if(userinfo.containsKey(nick))return userinfo.get(nick);
		return null;
	}
	
	public static boolean isRegistered(String nick){
		if(userinfo.containsKey(nick))return userinfo.get(nick).get("modes").contains("r");
		else return false;
	}
	
	public static HashSet<String> getChans(String nick){
		return userchans.get(nick);
	}
	
	public static void removeChan(String nick, String chan){
		HashSet<String> chans = userchans.get(nick);
		chans.remove(chan);
		userchans.put(nick, chans);
	}
	
	public static void changeNick(String oldnick, String newnick){
		HashSet<String> chans = userchans.get(oldnick);
		HashMap<String, String> info = userinfo.get(oldnick);
		userchans.put(newnick, chans);
		userinfo.put(newnick, info);
		userchans.remove(oldnick);
		userinfo.remove(oldnick);
	}
}
