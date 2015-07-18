package modules;

import bot.Message;
import bot.config.Config;

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
					m.say(target, "The specified users have been ignored");
				}
				if(m.botCommand().endsWith("unignore")){
					for(String nick : m.botParamsArray()){
						Config.removeIgnore(nick);
					}
					m.say(target, "The specified users have been unignored");
				}
			}
		}
	}
}
