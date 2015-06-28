package modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Scanner;

import extras.URLTitles;
import bot.Message;
import bot.config.Config;

public class TitleReporting implements Module {

	private HashSet<String> rooms = new HashSet<String>();
	File file;
	
	public TitleReporting() {
		try {
			file = new File(this.getClass().getResource("files/titlereporting.txt").toURI());
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				rooms.add(scan.nextLine());
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
					rooms.add(s);
				}
				write();
			}
		}
		if(m.botCommand().equals("titlesoff") && Config.getAdmins().contains(m.sender())){
			if(m.hasBotParams()){
				for(String s : m.botParamsArray()){
					rooms.remove(s);
				}
				write();
			}
		}
		
		if(rooms.contains(m.param())){
			if(m.trailing().contains("http://") || m.trailing().contains("https://")){
				String[] messageSplit = m.trailing().split("\\s+");
				for(int i = 0; i < messageSplit.length; i++){
					if(messageSplit[i].startsWith("http://") || messageSplit[i].startsWith("https://")){
						String title = URLTitles.find(messageSplit[i]);
						if(title != null) m.say(target, title);
					}
				}
			}
		}
	}
	
	public void write(){
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
