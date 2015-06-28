package modules;

import bot.Message;
import bot.config.Config;

public class Ignores implements Module {
	private Message m;

	public void parse(Message msg) {
		this.m = msg;
		if(Config.getAdmins().contains(m.sender())){
			if(m.botCommand().equals("ignore")){
				if(m.hasBotParams()){
					for(String s : m.botParamsArray()){
						Config.addIgnore(s);;
					}
				}
			}
			if(m.botCommand().equals("unignore")){
				if(m.hasBotParams()){
					for(String s : m.botParamsArray()){
						Config.removeIgnore(s);;
					}
				}
			}
		}

	}

}
