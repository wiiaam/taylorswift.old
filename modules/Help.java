package modules;

import java.util.HashMap;

import bot.Config;
import bot.IrcBot;
import bot.Message;
import bot.Module;
import bot.Modules;
import bot.Server;

public class Help implements Module {

	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.trailing().startsWith(".help") || m.botCommand().equals("help")){
			String modules = "Modules: ";
			HashMap<String, String> map = Modules.getModuleStatuses();
			for(HashMap.Entry<String, String> entry : map.entrySet()){
				String color = "";
				if(entry.getValue().equals("loaded")) color = "2";
				else if(entry.getValue().equals("unloaded")) color = "4";
				else if(entry.getValue().equals("changed")) color = "7";
				modules += color + entry.getKey() + ", ";
			}
			modules = modules.substring(0, modules.length()-2);
			m.notice(m.sender(), modules);
			m.notice(m.sender(), "Commands can be found at https://github.com/wiiam/Personal-Bot/blob/master/COMMANDS.md");
			m.notice(m.sender(), "Source and license info can be found with .source and .license respectively");
			m.notice(m.sender(), "My commandchar is currently " + m.commandChar());
			m.notice(m.sender(), "If you want me to join a channel, use /invite " + Config.getNick());
		}
		if(m.trailing().startsWith(".source") || m.botCommand().equals("source")){
			Server.say(m.sender(), "https://github.com/wiiam/Personal-Bot");
		}
		if(m.trailing().startsWith(".license") || m.botCommand().equals("license")){
			m.notice(m.sender(), "I am licensed under GNU AGPL. The terms of this license can be found here: https://github.com/wiiam/Personal-Bot/blob/master/LICENSE");
		}

	}

}
