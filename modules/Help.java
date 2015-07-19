package modules;

import bot.IrcBot;
import bot.Message;
import bot.Module;
import bot.Modules;
import bot.config.Config;

public class Help implements Module {

	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.trailing().startsWith(".help") || m.botCommand().equals("help") || (m.param().equals(Config.getNick()) && m.trailing().toLowerCase().equals("help"))){
			String modules = "Modules: ";
			for(Module module : Modules.getModules()){
				modules += module.getClass().getSimpleName() + ", ";
			}
			modules = modules.substring(0, modules.length()-2);
			m.notice(m.sender(), modules);
			m.notice(m.sender(), "Commands can be found at https://github.com/wiiam/Personal-Bot/blob/master/COMMANDS.md");
			m.notice(m.sender(), "Source and license info can be found with .source and .license respectively");
			m.notice(m.sender(), "My commandchar is currently " + m.commandChar());
			m.notice(m.sender(), "If you want me to join a channel, use /invite " + Config.getNick());
		}
		if(m.trailing().startsWith(".source") || m.botCommand().equals("source") || (m.param().equals(Config.getNick()) && m.trailing().toLowerCase().equals("source"))){
			m.say(m.sender(), "https://github.com/wiiam/Personal-Bot");
		}
		if(m.trailing().startsWith(".license") || m.botCommand().equals("license") || (m.param().equals(Config.getNick()) && m.trailing().toLowerCase().equals("license"))){
			m.notice(m.sender(), "I am licensed under GNU AGPL. The terms of this license can be found here: https://github.com/wiiam/Personal-Bot/blob/master/LICENSE");
		}

	}

}
