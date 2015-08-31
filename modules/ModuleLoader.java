package modules;

import java.util.HashMap;

import bot.Config;
import bot.Message;
import bot.Module;
import bot.Modules;
import bot.Server;

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
						Server.say(target, "Please wait until the previous module has finished " + working);
						return;
					}
					if(m.botParamsArray()[0].equals("load")){
						try {
							working = "loading";
							bot.Modules.load(m.botParamsArray()[1]);
							Server.say(target, "Module loaded: 2" + m.botParamsArray()[1]);
						} catch (ClassNotFoundException e) {
							Server.say(target, "Cound not find module: 2" + m.botParamsArray()[1]);
						} catch(IllegalArgumentException e){
							Server.say(target, "Module already loaded: 2" + m.botParamsArray()[1]);
						}
						working = "";
						
					}
					else if(m.botParamsArray()[0].equals("unload")){
						working = "unloading";
						if(!bot.Modules.unload(m.botParamsArray()[1])){
							Server.say(target, "Cound not find module: 2" + m.botParamsArray()[1]);
						}
						else{
							Server.say(target, "Module unloaded: 2" + m.botParamsArray()[1]);
						}
						working = "";
					}
					else if(m.botParamsArray()[0].equals("reload")){
						working = "reloading";
						if(!bot.Modules.reload(m.botParamsArray()[1])){
							Server.say(target, "Cound not find module: 2" + m.botParamsArray()[1]);
						}
						else{
							Server.say(target, "Module reloaded: 2" + m.botParamsArray()[1]);
						}
						working = "";
					}
					else Server.say(target, "Usage: " + Config.getChar() + "modules <load/unload/reload/list> <module>");
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
					Server.say(target, modules);
				}
				else Server.say(target, "Usage: " + Config.getChar() + "modules <load/unload/reload/list> <module>");
			}
			else Server.say(target, "Usage: " + Config.getChar() + "modules <load/unload/reload/list> <module>");
		}
	}

}
