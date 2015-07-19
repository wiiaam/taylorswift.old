package modules;

import bot.Message;
import bot.Module;
import bot.config.Config;

public class Invite implements Module {

	@Override
	public void parse(Message m) {
		if(m.command().equals("INVITE")){
			m.send("JOIN " + m.trailing());
			Config.addRoom(m.trailing());
			m.say(m.trailing(), m.sender() + " has invited me here. To see my commands, use /MSG " + Config.getNick() + " HELP");
		}

	}

}
