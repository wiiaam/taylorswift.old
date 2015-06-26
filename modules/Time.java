package modules;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import bot.Message;

public class Time implements Module {
	
	private HashMap<String, String> requests;
	
	public Time(){
		requests = new HashMap<String, String>();
	}
	
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.trailing().equals("TIME")){
			String time = String.format("%s %02d:%02d:%02d ",LocalDate.now().toString(), LocalTime.now().getHour(), LocalTime.now().getMinute(), LocalTime.now().getSecond());
			m.notice(m.sender(),String.format("TIME %s", time));
		}
		if(m.command().equals("PRIVMSG")){
				if(m.botCommand().equals("time")){
					if(m.hasBotParams()){
						for(int i = 0; i < m.botParamsArray().length; i++){
							m.pm(m.botParamsArray()[i], "TIME");
							requests.put(m.botParamsArray()[i], target);
						}
					}
				}
		}
		if(m.command().equals("NOTICE")){
			if(requests.containsKey(m.sender())){
				if(m.trailing().startsWith("TIME")){
					String version = m.trailing().substring(5,m.trailing().length()-1);
					m.say(requests.get(m.sender()),"[" + m.sender() + "] Current Time: " + version);
					requests.remove(m.sender());
				}
			}
		}
	}

}
