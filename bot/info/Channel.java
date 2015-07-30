package bot.info;

import java.util.HashMap;

public class Channel {
	
	private String name;
	private HashMap<String, String> usermodes;
	
	public Channel(String name) {
		this.name = name;
		usermodes = new HashMap<String, String>();
	}
	
	public void addModes(String nick, String modes){
		usermodes.put(nick, modes);
	}
	
	public String getModes(String nick){
		return usermodes.get(nick);
	}
	
	public void remove(String nick){
		usermodes.remove(nick);
	}
	
	public boolean has(String nick){
		return usermodes.containsKey(nick);
	}
	public void changeNick(String from, String to){
		usermodes.put(to, usermodes.get(from));
		usermodes.remove(from);
	}
}
