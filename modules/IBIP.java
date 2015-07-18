package modules;

import extras.Lyrics;
import bot.Message;

public class IBIP implements Module {
	
	
	
	@Override
	public void parse(Message m) {
		if(m.trailing().split("\\s+")[0].equals(".bots")){
			String target = m.param();
			if(!m.param().startsWith("#")) target = m.sender();
			m.say(target, String.format("Reporting in! [Java] %s", Lyrics.getRandomShortLyric()));
		}
	}

}
