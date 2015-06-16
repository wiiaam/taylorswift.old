package modules;

import bot.Message;

public class KiwiIRC implements Module {

	@Override
	public void parse(Message m) {
		if(m.command().equals("JOIN")){
			if(m.senderWhole().contains("KiwiIRC")){
				m.send(String.format("KICK %s %s :KiwiIRC", m.trailing(), m.sender()));
			}
		}

	}

}
