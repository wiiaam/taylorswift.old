package modules;

import bot.Message;

public class Admin implements Module{
	
	
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.admins.contains(m.sender())){
			if(m.botCommand().equals("leave")){
				m.send("PART " + m.param());
			}
			if(m.botCommand().equals("char")){
				String commandChar = m.botParamsArray()[0];
				if(commandChar.equals("self")) {
					commandChar = m.getConfig().getProperty("nickname") + ": ";
				}
				m.say(target, "changed char to " + commandChar);
				m.configure("commandchar", commandChar);
			}
			if(m.botCommand().equals("nick")){
				if(m.hasBotParams()){
					String newNick = m.botParamsArray()[0];
					m.say(target, "ok");
					if(m.getConfig().getProperty("commandchar").startsWith(m.getConfig().getProperty("nickname"))){
						m.configure("commandchar", newNick + ": " );
					}
					m.configure("nickname", newNick);
					m.send("NICK " + newNick);
				}
			}
			if(m.botCommand().equals("join")){
				if(m.hasBotParams()){
					for(int i = 0; i < m.botParamsArray().length; i++){
						m.send("JOIN " + m.botParamsArray()[i]);
						m.say(target, "kk");
					}
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
