package modules;

import bot.Message;
import bot.Module;
import bot.config.Config;

public class ModuleLoader implements Module {
	public String working = "";
	@Override
	public void parse(Message m) {
		if(m.botCommand().equals("modules") && m.senderIsAdmin()){
			String target = m.param();
			if(!m.param().startsWith("#")) target = m.sender();
			if(m.hasBotParams()){
				if(m.botParamsArray().length > 1){
					if(!working.equals("")){
						m.say(target, "Please wait until the previous module has finished " + working);
						return;
					}
					if(m.botParamsArray()[0].equals("load")){
						try {
							working = "loading";
							bot.Modules.load(m.botParamsArray()[1]);
							m.say(target, "Module loaded: 2" + m.botParamsArray()[1]);
						} catch (ClassNotFoundException e) {
							m.say(target, "Cound not find module: 2" + m.botParamsArray()[1]);
						} catch(IllegalArgumentException e){
							m.say(target, "Module already loaded: 2" + m.botParamsArray()[1]);
						}
						working = "";
						
					}
					else if(m.botParamsArray()[0].equals("unload")){
						working = "unloading";
						if(!bot.Modules.unload(m.botParamsArray()[1])){
							m.say(target, "Cound not find module: 2" + m.botParamsArray()[1]);
						}
						else{
							m.say(target, "Module unloaded: 2" + m.botParamsArray()[1]);
						}
						working = "";
					}
					else if(m.botParamsArray()[0].equals("reload")){
						working = "reloading";
						if(!bot.Modules.reload(m.botParamsArray()[1])){
							m.say(target, "Cound not find module: 2" + m.botParamsArray()[1]);
						}
						else{
							m.say(target, "Module reloaded: 2" + m.botParamsArray()[1]);
						}
						working = "";
					}
					else m.say(target, "Usage: " + Config.getChar() + "modules <load/unload/reload> <module>");
				}
				else m.say(target, "Usage: " + Config.getChar() + "modules <load/unload/reload> <module>");
			}
			else m.say(target, "Usage: " + Config.getChar() + "modules <load/unload/reload> <module>");
		}
	}

}
