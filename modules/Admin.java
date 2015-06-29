package modules;

import bot.Message;
import bot.UserInfo;
import bot.config.Config;

public class Admin implements Module{
	
	
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(Config.getAdmins().contains(m.sender()) && UserInfo.isRegistered(m.sender())){
			if(m.botCommand().equals("admin")){
				if(m.botParamsArray().length > 1){
					if(m.botParamsArray()[0].equals("add")){
						Config.addAdmin(m.botParamsArray()[1]);
						m.say(target, m.botParamsArray()[1] + " is now an admin.");
					}
					if(m.botParamsArray()[0].equals("del")){
						if(Config.removeAdmin(m.botParamsArray()[1])) m.say(target, m.botParamsArray()[1] + " is no longer an admin");
						else m.say(target, m.botParamsArray()[1] + " is not an admin");
					}
				}
			}
			if(m.botCommand().equals("leave")){
				m.send("PART " + m.param());
				Config.removeRoom(m.param());
			}
			if(m.botCommand().equals("char")){
				String commandChar = m.botParamsArray()[0];
				if(commandChar.equals("self")) {
					commandChar = Config.getNick() + ": ";
				}
				m.say(target, "changed char to " + commandChar);
				Config.setChar(commandChar);
			}
			if(m.botCommand().equals("nick")){
				if(m.hasBotParams()){
					String newNick = m.botParamsArray()[0];
					if(Config.getChar().startsWith(Config.getNick())){
						Config.setChar(newNick + ": " );
					}
					Config.setNick(newNick);
					m.send("NICK " + newNick);
				}
			}
			if(m.botCommand().equals("join")){
				if(m.hasBotParams()){
					for(int i = 0; i < m.botParamsArray().length; i++){
						String roomtojoin = m.botParamsArray()[i];
						if(roomtojoin.equals("#0,0")){
							m.say(target, "nice try");
							continue;
						}
						m.send("JOIN " + roomtojoin);
						Config.addRoom(roomtojoin);
					}
					m.say(target, "Now joining " + m.botParams());
				}
			}
			if(m.botCommand().equals("raw")){
				if(m.hasBotParams()) m.send(m.botParams());
			}
			if(m.botCommand().equals("pm")){
				if(m.hasBotParams()){
					String tosend = "";
					for(int i = 1; i < m.botParamsArray().length; i++){
						tosend += m.botParamsArray()[i] + " ";
					}
					tosend.trim();
					m.pm(m.botParamsArray()[0], tosend);
				}
			}
			if(m.botCommand().equals("notice")){
				if(m.hasBotParams()){
					String tosend = "";
					for(int i = 1; i < m.botParamsArray().length; i++){
						tosend += m.botParamsArray()[i] + " ";
					}
					tosend.trim();
					m.notice(m.botParamsArray()[0], tosend);
				}
			}
			if(m.botCommand().equals("say")){
				m.say(target, m.botParams());
			}
		}
	}

}
