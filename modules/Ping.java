package modules;

import java.util.HashMap;

import bot.Message;

	public class Ping implements Module{
	
	private HashMap<String, String> requests;
	
	public Ping(){
		requests = new HashMap<String, String>();
	}
	
	public void parse(Message m){
		if(m.command().equals("PING")){
			m.send("PONG :" + m.trailing());
		}
		
		if(m.command().equals("NOTICE") && requests.containsKey(m.sender()) && m.trailing().startsWith("PING")){
			long ping = System.currentTimeMillis() - Long.parseLong(m.trailing().substring(6, m.trailing().length()-1));
			m.pm(requests.get(m.sender()), "Ping for " + m.sender() + ": " + ping + "ms");
			requests.remove(m.sender());
		}
		
		if(m.command().equals("PRIVMSG") && m.trailing().startsWith("PING")){
			m.notice(m.sender(), m.trailing());
		}
		
		if(m.botCommand().equals("ping")){
			if(m.hasBotParams()){
				String[] targets = m.botParamsArray();
				String target = m.param();
				if(!m.param().startsWith("#")) target = m.sender();
				for(int i = 0; i < targets.length; i++){
					if(targets[i].startsWith("#"))continue;
					m.pm(targets[i],"PING " + System.currentTimeMillis() + "");
					requests.put(targets[i], target);
				}
			}
		}
	}
}
