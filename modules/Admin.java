package modules;

import bot.Config;
import bot.Message;
import bot.Module;
import bot.Server;

public class Admin implements Module{
	
	
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		
		if(m.command().equals("NICK")){
			System.out.println(m.trailing());
			if(m.sender().equals(Config.getNick())){
				if(Config.getChar().startsWith(Config.getNick())){
					Config.setChar(m.trailing() + ": " );
				}
				Config.setNick(m.trailing());
			}
		}
		
		if(m.command().equals("474")){
			if(m.trailing().contains("Cannot join channel (+b)")){
				Config.removeRoom(m.trailing().split("\\s+")[0]);
			}
		}
		
		if(m.command().equals("KICK")){
			if(m.trailing().startsWith(Config.getNick())){
				System.out.println(m.param().substring(1));
				Config.removeRoom(m.param().substring(1));
			}
		}
		
		if(m.command().equals("PART")){
			if(m.sender().equals(Config.getNick())){
				Config.removeRoom(m.param().substring(1));
			}
		}
		
		if(m.senderIsAdmin()){
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
					
					m.send("NICK " + newNick);
				}
			}
			if(m.botCommand().equals("join")){
				if(m.hasBotParams()){
					for(int i = 0; i < m.botParamsArray().length; i++){
						String roomtojoin = m.botParamsArray()[i];
						if(roomtojoin.contains(",")){
							m.say(target, "nice try");
							continue;
						}
						m.send("JOIN " + roomtojoin);
						Config.addRoom(roomtojoin);
						m.send("WHO " + roomtojoin);
					}
					m.say(target, "Now joining " + m.botParams());
				}
			}
			
			if(m.botCommand().equals("send")){
				if(m.hasBotParams()) m.send(m.botParams());
			}
			
			if(m.botCommand().equals("pm") || m.botCommand().equals("msg")){
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
			
			if(m.botCommand().equals("quit")){
				Server.say(target, "o-ok");
				Server.send("QUIT :Leaving");
				Server.disconnect();
			}
			
		}
	}

}
