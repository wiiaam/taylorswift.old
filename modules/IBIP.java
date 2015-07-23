package modules;

import extras.Lyrics;
import bot.Message;
import bot.Module;
import bot.Server;

public class IBIP implements Module {
	
	
	
	@Override
	public void parse(Message m) {
		if(m.trailing().split("\\s+")[0].equals(".bots")){
			String target = m.param();
			if(!m.param().startsWith("#")) target = m.sender();
			Server.priorityPm(target, String.format("Reporting in! [Java] %s", Lyrics.getRandomShortLyric()));
		}
	}

}
