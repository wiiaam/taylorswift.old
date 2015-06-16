package modules;

import bot.Message;

public class Ping implements Module{
	
	public void parse(Message m){
		if(m.command().equals("PING")){
			m.send("PONG :" + m.trailing());
		}
	}
}
