package modules;

import java.util.HashMap;

import bot.Message;
import bot.Module;
import bot.Modules;
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
					else m.say(target, "Usage: " + Config.getChar() + "modules <load/unload/reload/list> <module>");
				}
				else if(m.botParamsArray()[0].equals("list")){
					String modules = "Modules (blue = loaded, red = unloaded): ";
					HashMap<String, String> map = Modules.getModuleStatuses();
					for(HashMap.Entry<String, String> entry : map.entrySet()){
						String color = "";
						if(entry.getValue().equals("loaded")) color = "2";
						else if(entry.getValue().equals("unloaded")) color = "4";
						else if(entry.getValue().equals("changed")) color = "7";
						modules += color + entry.getKey() + ", ";
					}
					modules = modules.substring(0, modules.length()-2);
					m.say(target, modules);
				}
				else m.say(target, "Usage: " + Config.getChar() + "modules <load/unload/reload/list> <module>");
			}
			else m.say(target, "Usage: " + Config.getChar() + "modules <load/unload/reload/list> <module>");
		}
	}

}
