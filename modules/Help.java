package modules;

import bot.Message;

public class Help implements Module {

	@Override
	public void parse(Message m) {
		if(m.trailing().startsWith(".help") || m.botCommand().equals("help")){
			m.notice(m.sender(), "Commands can be found at https://github.com/wiiam/Personal-Bot/blob/master/COMMANDS.md");
			m.notice(m.sender(), "My source and license can be found with .source and .license respectively");
		}
		if(m.trailing().startsWith(".source") || m.botCommand().equals("source")){
			m.notice(m.sender(), "https://github.com/wiiam/Personal-Bot");
		}
		if(m.trailing().startsWith(".license") || m.botCommand().equals("license")){
			m.notice(m.sender(), "I am licensed under GNU AGPL. The terms of this license can be found here: https://github.com/wiiam/Personal-Bot/blob/master/LICENSE");
		}

	}

}
