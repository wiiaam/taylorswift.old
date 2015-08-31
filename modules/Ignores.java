package modules;

import bot.Config;
import bot.Message;
import bot.Module;
import bot.Server;

public class Ignores implements Module {

	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(Config.getAdmins().contains(m.sender())){
			if(m.hasBotParams()){
				if(m.botCommand().equals("ignore")){				
					for(String nick : m.botParamsArray()){
						Config.addIgnore(nick);
					}
					Server.say(target, "The specified users have been ignored");
				}
				if(m.botCommand().endsWith("unignore")){
					for(String nick : m.botParamsArray()){
						Config.removeIgnore(nick);
					}
					Server.say(target, "The specified users have been unignored");
				}
			}
		}
	}
}
