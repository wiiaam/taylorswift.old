package modules;

import bot.Message;

public class KiwiIRC implements Module {
	
	private boolean on = false;
	
	@Override
	public void parse(Message m) {
		if(m.command().equalsIgnoreCase("kickkiwiirc") && m.admins.contains(m.sender())){
			on = true;
		}
		if(m.command().equalsIgnoreCase("allowkiwiirc") && m.admins.contains(m.sender())){
			on = false;
		}
		if(!on) return;
		if(m.command().equals("JOIN")){
			if(m.senderWhole().contains("KiwiIRC")){
				m.send(String.format("KICK %s %s :KiwiIRC", m.trailing(), m.sender()));
			}
		}

	}

}
