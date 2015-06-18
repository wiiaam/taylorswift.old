package modules;

import extras.Links;
import bot.Message;

public class Random implements Module {

	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equals("Pr0Wolf29")){
			m.say(target, "https://p.teknik.io/1349");
		}
		if(m.botCommand().equals("gen2")){
			m.say(target, "<installgen2> how can I be aware of my sexual preferences until I am a bit older? for all I know I could easily be bi");
		}
		if(m.botCommand().equals(">")){
			m.say(target, "3>Implying " + m.botParams());
		}
		if(m.botCommand().equals("gt")){
			m.say(target, "3>" + m.botParams());
		}
		if(m.botCommand().equals("post")){
			if(m.hasBotParams()){
				if(m.botParamsArray()[0].equals("n00dz")){
					m.say(target, "ook here you go ;)");
					m.say(target, Links.nudes());
				}
			}
		}
	}
}
