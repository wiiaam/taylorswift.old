package modules;

import bot.Message;
import bot.Module;
import bot.Server;
import extras.URLShortener;

public class URLShorten implements Module {

	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		
		if(m.botCommand().equals("shorten")){
			if(m.hasBotParams()){
				String url = m.botParamsArray()[0];
				Server.say(target, "Shortened url: " + URLShortener.shorten(url));
			}
		}
	}

}
