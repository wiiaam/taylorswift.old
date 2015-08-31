package modules;


import bot.Config;
import bot.Message;
import bot.Server;

public class London implements bot.Module {
	
	private boolean on = false;
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(Config.getAdmins().contains(m.sender())){
			if(m.botCommand().equals("londonon")) on = true;
			if(m.botCommand().equals("londonoff")) on = false;
		}
		if(m.botCommand().equals("london") && (on || Config.getAdmins().contains(m.sender()))){
			if(m.botParams().length() > 10){
				Server.say(target, "dats 2 long man");
				return;
			}
			char[] chars = m.botParams().toCharArray();
			String first = "";
			for(int i = 0; i < chars.length; i++){
				first += String.valueOf(chars[i]).toUpperCase() + " ";
			}
			first.trim();
			Server.say(target, first);
			for(int i = 1; i < chars.length; i++){
				Server.say(target, String.valueOf(chars[i]).toUpperCase());
			}
		}
		if(m.botCommand().equals("sqlondon") && (on || Config.getAdmins().contains(m.sender()))){
			if(m.botParams().length() > 10){
				Server.say(target, "dats 2 long man");
				return;
			}
			char[] chars = m.botParams().toCharArray();
			String first = "";
			for(int i = 0; i < chars.length; i++){
				first += String.valueOf(chars[i]).toUpperCase() + " ";
			}
			first.trim();
			Server.say(target, first);
			for(int i = 1; i < chars.length-1; i++){
				String tosay = String.valueOf(chars[i]).toUpperCase();
				for(int j = 0; j < (chars.length-1)+(chars.length-2); j++){
					tosay += " ";
				}
				tosay += String.valueOf(chars[chars.length-i-1]).toUpperCase();
				Server.say(target,tosay);
			}
			String last = "";
			for(int i = chars.length-1; i >= 0; i--){
				last += String.valueOf(chars[i]).toUpperCase() + " ";
			}
			last.trim();
			Server.say(target, last);
		}
		
	}

}
