package modules;

import bot.Message;

public class NoBro implements Module {
	private String[] triggers = {"kittykatt","man"};
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.sender().startsWith("poo") || m.senderAddress().contains("zero@")){
			for(String trigger : triggers){
				if(m.trailing().toLowerCase().contains(trigger)){
					m.say(target, "fuck off bro");
				}
			}
		}
		if(m.command().equals("JOIN")){
			if(m.sender().startsWith("poo") || m.senderAddress().contains("zero@")){
				m.say(m.trailing(), "4ALERT ALERT POSSIBLE BRO DETECTED");
			}
		}
	}

}
