package modules;

import bot.Message;
import bot.Module;

public class Pasta implements Module {

	@Override
	public void parse(Message m) {
		if(m.command().equals("JOIN") && m.trailing().equals("#pasta")){
			m.notice(m.sender(), "2Welcome to 4#pasta2. Please check the rules with ~rules, unless cuck is ded.");
			m.notice(m.sender(), "2gen2 is currently ded as of 19th July due to cuck related activities");
		}
	}

}
