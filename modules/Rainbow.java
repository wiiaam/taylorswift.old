package modules;

import extras.Rainbowfy;
import bot.Message;

public class Rainbow implements bot.Module {

	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equals("rb") || m.botCommand().equals("rainbow")){
			m.say(target, Rainbowfy.convert(m.botParams()));
		}

	}

}
