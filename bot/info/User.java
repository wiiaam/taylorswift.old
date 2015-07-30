package bot.info;

import java.util.ArrayList;

public class User {
	
	protected String host;
	protected String user;
	protected String modes;
	protected String realname;
	protected String nickname;
	private ArrayList<String> channels;
	
	public User(String nick, String host, String user, String modes, String realname) {
		this.host = host;
		this.user = user;
		this.modes = modes;
		this.realname = realname;
		this.nickname = nick;
		this.channels = new ArrayList<String>();
	}
	
	
	public String getHost(){
		return host;
	}
	
	public String getModes(){
		return modes;
	}
	
	public ArrayList<String> getChannels(){
		return channels;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getRealname(){
		return realname;
	}
	
	public String getNick(){
		return nickname;
	}


	public void update(String nick, String host, String user, String modes, String realname){
		this.host = host;
		this.user = user;
		this.modes = modes;
		this.realname = realname;
		this.nickname = nick;
	}
	
	public void removeChan(String chan){
		for(int i = 0; i < channels.size(); i++){
			if(channels.get(i).equals(chan)) channels.remove(i);
		}
	}
	public void addChan(String chan){
		channels.add(chan);
	}
	
	public boolean isIn(String chan){
		for(int i = 0; i < channels.size(); i++){
			if(channels.get(i).equals(chan)) return true;
		}
		return false;
	}
	
	public void changeNick(String to){
		nickname = to;
		modes.replace("r", "");
	}
	
	
}
