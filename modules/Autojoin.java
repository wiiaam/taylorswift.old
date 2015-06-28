package modules;

import bot.Message;
import bot.config.Config;

public class Autojoin implements Module {

	@Override
	public void parse(Message m) {
		if(m.command().equals("KICK")){
			if(m.trailing().startsWith(Config.getNick())){
				m.send("JOIN " + m.param());
				m.pm(m.param(), "no u");
			}
		}

	}

}
