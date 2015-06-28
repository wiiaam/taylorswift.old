package modules;

import bot.Message;

public class Help implements Module {

	@Override
	public void parse(Message m) {
		if(m.trailing().startsWith(".help") || m.botCommand().equals("help")){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			m.notice(m.sender(), "Commands can be found at https://github.com/wiiam/Personal-Bot/blob/master/COMMANDS.md");
			m.notice(m.sender(), "Source and license info can be found with .source and .license respectively");
			m.notice(m.sender(), "My commandchar is currently " + m.commandChar());
			m.notice(m.sender(), "If you want me to join a channel, join #taylorswift and make a request");
		}
		if(m.trailing().startsWith(".source") || m.botCommand().equals("source")){
			m.notice(m.sender(), "https://github.com/wiiam/Personal-Bot");
		}
		if(m.trailing().startsWith(".license") || m.botCommand().equals("license")){
			m.notice(m.sender(), "I am licensed under GNU AGPL. The terms of this license can be found here: https://github.com/wiiam/Personal-Bot/blob/master/LICENSE");
		}

	}

}
