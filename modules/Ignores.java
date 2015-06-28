package modules;

import bot.Message;
import bot.config.Config;

public class Ignores implements Module {

	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(Config.getAdmins().contains(m.sender())){
			if(m.botCommand().equals("ignores")){
				if(m.botParamsArray().length > 1){
					if(m.botParamsArray()[0].equals("add")){
						Config.addIgnore(m.botParamsArray()[1]);
						m.say(target, m.botParamsArray()[1] + " is now being ignored.");
					}
					if(m.botParamsArray()[0].equals("del")){
						if(Config.removeIgnore(m.botParamsArray()[1])) m.say(target, m.botParamsArray()[1] + " is no longer being ignored");
						else m.say(target, m.botParamsArray()[1] + " was never being ignored");
					}
				}
			}
		}

	}

}
