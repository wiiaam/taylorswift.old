package modules;

import java.util.HashSet;

import extras.URLTitles;
import bot.Message;

public class TitleReporting implements Module {

	private HashSet<String> rooms = new HashSet<String>();
	
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		
		if(m.botCommand().equals("titleson") && m.admins.contains(m.sender())){
			if(m.hasBotParams()){
				for(String s : m.botParamsArray()){
					rooms.add(s);
				}
			}
		}
		if(m.botCommand().equals("titlesoff") && m.admins.contains(m.sender())){
			if(m.hasBotParams()){
				for(String s : m.botParamsArray()){
					rooms.remove(s);
				}
			}
		}
		
		if(rooms.contains(m.param())){
			if(m.trailing().contains("http://") || m.trailing().contains("https://")){
				String[] messageSplit = m.trailing().split("\\s+");
				for(int i = 0; i < messageSplit.length; i++){
					if(messageSplit[i].startsWith("http://") || messageSplit[i].startsWith("https://")){
						String title = URLTitles.find(messageSplit[i]);
						if(title != null) m.say(target, title);
					}
				}
			}
		}
	}

}
