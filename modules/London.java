package modules;

import java.lang.annotation.Target;

import bot.Message;

public class London implements Module {
	
	private boolean on = false;
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.admins.contains(m.sender())){
			if(m.botCommand().equals("londonon")) on = true;
			if(m.botCommand().equals("londonoff")) on = false;
		}
		if(m.botCommand().equals("london")){
			if(m.botParams().length() > 10){
				m.say(target, "dats 2 long man");
				return;
			}
			char[] chars = m.botParams().toCharArray();
			String first = "";
			for(int i = 0; i < chars.length; i++){
				first += String.valueOf(chars[i]).toUpperCase() + " ";
			}
			first.trim();
			m.say(target, first);
			for(int i = 1; i < chars.length; i++){
				m.say(target, String.valueOf(chars[i]).toUpperCase());
			}
		}
	}

}