package modules;

import bot.Message;

public class Autojoin implements Module {

	@Override
	public void parse(Message m) {
		if(m.command().equals("KICK")){
			if(m.trailing().startsWith(m.getConfig().getProperty("nickname"))){
				m.send("JOIN " + m.param());
				m.pm(m.param(), "no u");
			}
		}

	}

}
