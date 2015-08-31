package modules;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

import bot.Message;
import bot.Module;
import bot.Server;

public class CleverBot implements Module {
	private ChatterBotSession cb;
	
	public CleverBot(){
		ChatterBotFactory factory = new ChatterBotFactory();
		ChatterBot bot1;
		try {
			bot1 = factory.create(ChatterBotType.CLEVERBOT);
			cb = bot1.createSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		
		if(m.botCommand().equals("cb")){
			if(m.hasBotParams()){
				try {
					Server.say(target, m.sender() + ": " + cb.think(m.botParams()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
