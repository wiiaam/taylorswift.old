package modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Scanner;
import bot.Message;
import bot.Module;
import bot.Server;
import bot.info.Info;

public class PastaTopic implements Module {

	public File file;
	public String topicmain = "";
	
	public PastaTopic() {
		try {
			file = new File(this.getClass().getResource("files/pastatitle.txt").toURI());
			Scanner scan = new Scanner(file);
			topicmain = scan.nextLine();
			scan.close();
		} catch (FileNotFoundException | URISyntaxException e) {
		}
	}
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		
		if(m.botCommand().equals("pastatopic")){
			if(Info.hasUserInfo(m.sender())){
				if(Info.getChannelInfo("#pasta").has(m.sender())){
					if(Info.getChannelInfo("#pasta").getModes(m.sender()).contains("~") || 
						Info.getChannelInfo("#pasta").getModes(m.sender()).contains("&")){
						topicmain = m.botParams();
						write();
					}
					else m.say(target, "You need to be at least AOP to use this command");
				}
			}
		}
		if(m.command().equals("TOPIC")){
			if(m.param().equals("#pasta")){
				if(!m.trailing().startsWith(topicmain)){
					Server.send("TOPIC #pasta :" + topicmain + " " + m.trailing());
				}
			}
		}
	}
	private void write(){
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.println(topicmain);
			writer.close();
		} 
		catch (IOException e) {}
		
	}

}
