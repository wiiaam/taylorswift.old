package bot.info;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Info {
	private static HashMap<String, User> users = new HashMap<String, User>();
	private static HashMap<String, Channel> channels = new HashMap<String, Channel>();
	
	public static void parse(String s){
		String[] split = s.split("\\s+");
		User user;
		if(!users.containsKey(split[4])){
			String realname = split[7];
			for(int i = 8; i < split.length; i++){
				realname += " " + split[i];
			}
			user = new User(split[4], split[2], split[1], split[5], realname);
			users.put(split[4], user);
		}
		else{
			user = users.get(split[4]);
			String realname = split[7];
			for(int i = 8; i < split.length; i++){
				realname += " " + split[i];
			}
			user.update(split[4], split[2], split[1], split[5], realname);
			if(!user.isIn(split[0])){
				user.addChan(split[0]);
			}
			users.put(split[4], user);
		}
		Channel channel;
		if(!channels.containsKey(split[0])){
			channel = new Channel(split[0]);
		}
		else{
			channel = channels.get(split[0]);
		}
			
		channel.addModes(split[4], split[5]);
		channels.put(split[0], channel);
	}
	
	public static void forget(String nick){
		users.remove(nick);
		for(Entry<String, Channel> entry : channels.entrySet()){
			Channel channel = entry.getValue();
			channel.remove(nick);
			channels.put(entry.getKey(), channel);
		}
	}
	
	public static boolean hasUserInfo(String nick){
		return users.containsKey(nick);
	}
	public static User getUserInfo(String nick){
		return users.get(nick);
	}
	
	public static boolean hasChannelInfo(String chan){
		return channels.containsKey(chan);
	}
	
	public static Channel getChannelInfo(String chan){
		return channels.get(chan);
	}
	
	public static boolean isRegistered(String nick){
		if(users.containsKey(nick))return users.get(nick).getModes().contains("r");
		else return false;
	}
	
	public static void changeNick(String from, String to){
		if(!users.containsKey(from)) return;
		HashMap<String, Channel> tempmap = new HashMap<String, Channel>();
		for(Entry<String, Channel> en : channels.entrySet()){
			Channel c = en.getValue();
			c.changeNick(from, to);
			tempmap.put(en.getKey(), c);
		}
		channels = tempmap;
		User user = users.get(from);
		user.changeNick(to);
		users.remove(from);
		users.put(to, user);
	}
	
	public static void removeChan(String nick, String chan){
		User user = users.get(nick);
		user.removeChan(chan);
		users.put(nick, user);
		Channel channel = channels.get(chan);
		channel.remove(nick);
		channels.put(nick, channel);
	}
}
