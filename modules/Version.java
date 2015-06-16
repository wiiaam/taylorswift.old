package modules;

import java.util.HashMap;

import bot.Message;

public class Version implements Module {
	
	private HashMap<String, String> requests;
	
	public Version(){
		requests = new HashMap<String, String>();
	}
	
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.trailing().equals("VERSION")){
			m.notice(target,"VERSION taylorswift 4.20 by Java™ Enterprises");
		}
		if(m.command().equals("PRIVMSG")){
				if(m.botCommand().equals("version") || m.botCommand().equals("ver")){
					if(m.hasBotParams()){
						for(int i = 0; i < m.botParamsArray().length; i++){
							m.pm(m.botParamsArray()[i], "VERSION");
							requests.put(m.botParamsArray()[i], target);
						}
					}
				}
		}
		if(m.command().equals("NOTICE")){
			if(requests.containsKey(m.sender())){
				if(m.trailing().startsWith("VERSION")){
					String version = m.trailing().substring(8,m.trailing().length()-1);
					version = version.replace("WeeChat", "WeebChat");
					version = version.replace("weechat", "weebchat");
					version = version.replace("Weechat", "Weebchat");
					m.say(requests.get(m.sender()),"[" + m.sender() + "] Version: " + version);
					requests.remove(m.sender());
				}
			}
		}
	}

}
