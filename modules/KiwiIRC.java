package modules;

import bot.Message;
import bot.config.Config;

public class KiwiIRC implements Module {
	
	private boolean on = false;
	
	@Override
	public void parse(Message m) {
		if(m.botCommand().equalsIgnoreCase("kiwion") && Config.getAdmins().contains(m.sender())){
			on = true;
		}
		if(m.botCommand().equalsIgnoreCase("kiwioff") && Config.getAdmins().contains(m.sender())){
			on = false;
		}
		if(!on) return;
		if(m.command().equals("JOIN")){
			if(m.senderWhole().toLowerCase().contains("kiwiirc")){
				m.pm(m.trailing(), m.sender() + ": get a real client faggot");
			}
		}

	}

}
