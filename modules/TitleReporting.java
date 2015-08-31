package modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Scanner;

import extras.URLShortener;
import extras.URLTitles;
import bot.Config;
import bot.Message;
import bot.Module;
import bot.Server;

public class TitleReporting implements Module {

	private HashSet<String> rooms = new HashSet<String>();
	private HashSet<String> users = new HashSet<String>();
	File file;
	
	public TitleReporting() {
		try {
			file = new File(this.getClass().getResource("files/titlereporting.txt").toURI());
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				String next = scan.nextLine();
				if(next.startsWith("#")) rooms.add(next);
				else users.add(next);
			}
			scan.close();
		} catch (URISyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		
		if(m.botCommand().equals("titleson") && Config.getAdmins().contains(m.sender())){
			if(m.hasBotParams()){
				for(String s : m.botParamsArray()){
					if(s.startsWith("#"))rooms.add(s);
					else users.add(s);
				}
				write();
			}
		}
		if(m.botCommand().equals("titlesoff") && Config.getAdmins().contains(m.sender())){
			if(m.hasBotParams()){
				for(String s : m.botParamsArray()){
					if(s.startsWith("#"))rooms.remove(s);
					else users.remove(s);
				}
				write();
			}
		}
		
		if(!m.botCommand().equals(""))return;
		if(m.trailing().contains("Reporting in!"))return;
		if(rooms.contains(m.param()) || users.contains(m.sender())){
			if(m.command().equals("PRIVMSG")){
				if(m.trailing().contains("http://") || m.trailing().contains("https://")){
					String[] messageSplit = m.trailing().split("\\s+");
					for(int i = 0; i < messageSplit.length; i++){
						if(messageSplit[i].startsWith("http://") || messageSplit[i].startsWith("https://")){
							String title = URLTitles.find(messageSplit[i]);
							title = title.replace("http://", "").replace("https://", "");
							if(title != null) Server.say(target, title);
							break;
						}
					}
				}
			}
		}
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
