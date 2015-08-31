package modules;

import bot.Config;
import bot.Message;
import bot.Module;
import bot.Server;

public class Invite implements Module {
	public long timeout = System.currentTimeMillis();
	@Override
	public void parse(Message m) {
		if(m.command().equals("INVITE")){
			if(timeout + 10000 > System.currentTimeMillis()){
				m.notice(m.sender(), "I am currently on invite cooldown");
				return;
			}
			timeout = System.currentTimeMillis();
			m.send("JOIN " + m.trailing());
			Config.addRoom(m.trailing());
			Server.say(m.trailing(), m.sender() + " has invited me here. To see my commands, use /MSG " + Config.getNick() + " HELP");
		}

	}

}
