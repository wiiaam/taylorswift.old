package modules;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Scanner;

import bot.Message;
import bot.Module;
import bot.config.Config;
import bot.info.Channel;
import bot.info.Info;

public class Administration implements Module {

	private HashSet<String> rooms = new HashSet<String>();
	File file;
	
	public Administration() {
		try {
			file = new File(this.getClass().getResource("files/administration.txt").toURI());
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				String next = scan.nextLine();
				if(next.startsWith("#")) rooms.add(next);
			}
			scan.close();
		} catch (URISyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!target.startsWith("#"))target = m.sender();
		
		if(m.botCommand().equals("adminon") && Config.getAdmins().contains(m.sender())){
			if(m.hasBotParams()){
				for(String s : m.botParamsArray()){
					if(s.startsWith("#"))rooms.add(s);
					else rooms.add("#" + s);
				}
				write();
			}
		}
		if(m.botCommand().equals("adminoff") && Config.getAdmins().contains(m.sender())){
			if(m.hasBotParams()){
				for(String s : m.botParamsArray()){
					if(s.startsWith("#"))rooms.remove(s);
					else rooms.remove("#" + s);
				}
				write();
			}
		}
		
		
		if(m.param().startsWith("#")){ //In channel commands only
			if(m.botCommand().equals("title")){  // Title changes
				int level = getLevel(m.sender(), m.param());
				if(!isop(m.param()))m.say(target, "I am not an operator");
				if(level < 2) m.say(target, "You need to be at least halfop to do this");
			}
			if(m.botCommand().equals("kb")){  // Kick ban
				int level = getLevel(m.sender(), m.param());
				if(!isop(m.param())){
					m.say(target, "I am not an operator");
					return;
				}
				if(level == -1){
					m.say(target, "An error has occured. Some channel or nick info may be unavailable");
				}
				if(level < 2) {
					m.say(target, "You need to be at least halfop to do this");
					return;
				}
				int otherlevel = getLevel(m.botParamsArray()[0], m.param());
				if(otherlevel == -1){
					m.say(target, "Cannot find target: " + m.botParamsArray()[0]);
					return;
				}
				if(otherlevel > level){
					m.say(target, "They are a higher rank than you");
					return;
				}
				m.send("MODE " + m.param() + " +b " + Info.getUserInfo(m.botParamsArray()[0]).getHost() );
				String reason = "";
				for(int i = 1; i < m.botParamsArray().length; i++){
					reason += m.botParamsArray()[i] + " ";
				}
				reason = reason.trim();
				if(reason.equals("")) reason = m.botParamsArray()[0];
				m.send("KICK " + m.param() + " " + m.botParamsArray()[0] + " :" + reason);
			}
			
			if(m.botCommand().equals("ub")){ // Unban
				int level = getLevel(m.sender(), m.param());
				if(!isop(m.param())){
					m.say(target, "I am not an operator");
					return;
				}
				if(level == -1){
					m.say(target, "An error has occured. Some channel or nick info may be unavailable");
				}
				if(level < 2) {
					m.say(target, "You need to be at least halfop to do this");
					return;
				}
				if(!Info.hasUserInfo(m.botParamsArray()[0])){
					m.say(target, "Cannot find target: " + m.botParamsArray()[0]);
					return;
				}
				m.send("MODE " + m.param() + " -b " + Info.getUserInfo(m.botParamsArray()[0]).getHost() );
			}
		}
	}
	
	/**
	 * 5 = ~, qop, owner
	 * 4 = &, aop, protectop
	 * 3 = @, op, operator
	 * 2 = %, hop, halfop
	 * 1 = +, voice
	 * 0 = regular
	 * @return
	 */
	private int getLevel(String nick, String chan){
		if(!Info.hasChannelInfo(chan))return -1;
		Channel channel = Info.getChannelInfo(chan);
		if(!channel.has(nick))return -1;
		String modes = channel.getModes(nick);
		if(modes.contains("~")) return 5;
		if(modes.contains("&")) return 4;
		if(modes.contains("@")) return 3;
		if(modes.contains("%")) return 2;
		if(modes.contains("+")) return 1;
		return 0;
	}
	
	private boolean isop(String chan){
		if(!Info.hasChannelInfo(chan))return false;
		Channel channel = Info.getChannelInfo(chan);
		if(!channel.has(Config.getNick()))return false;
		String modes = channel.getModes(Config.getNick());
		if(modes.contains("~")) return true;
		if(modes.contains("&")) return true;
		if(modes.contains("@")) return true;
		if(modes.contains("%")) return true;
		return false;
	}
	
	private void write(){
		try {
			PrintWriter writer = new PrintWriter(file);
			for(String s : rooms){
				writer.println(s);
			}
			writer.close();
		} 
		catch (IOException e) {}
		
	}

}
