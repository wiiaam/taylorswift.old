package modules;

import java.util.ArrayList;

import bot.Message;
import bot.config.Config;

public class Cowsay implements Module {
	
	private boolean on = false;
	
	public Cowsay() {
		if(Config.get("cowsay") == null){
			Config.set("cowsay","off");
		}
		else if(Config.get("cowsay").equals("on")){
			on = true;
		}
	}
	
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.senderIsAdmin()){
			if(m.botCommand().equals("cowsayon")){
				m.say(target, "Cowsay is now on");
			}
			if(m.botCommand().equals("cowsayoff")){
				m.say(target, "Cowsay is now off");
			}
				
		}
		if(m.botCommand().equals("cowsay") && (on || m.senderIsAdmin())){
			if(m.hasBotParams()){
				String[] messages = splitMessage(m.botParams());
				ArrayList<String> tosay = new ArrayList<String>();
				tosay.add(" _________________________________________");
				for(int i = 0; i < messages.length; i++){
					String prefix, suffix;
					if(i == 0){
						prefix = "/";
						suffix = "\\";
					}
					else if(i == messages.length-1){
						prefix = "\\";
						suffix = "/";
					}
					else{
						prefix = suffix = "|";
					}
					String toadd = prefix + " " + messages[i];
					while(true){
						if(toadd.length() > 40){
							toadd += " " + suffix;
							break;
						}
						toadd += " ";
					}
					tosay.add(toadd);
				}
				tosay.add(" -----------------------------------------");
				tosay.add("        \\   ^__^");
				tosay.add("         \\  (oo)\\_______");
				tosay.add("            (__)\\       )\\/\\");
				tosay.add("                ||----w |");
				tosay.add("                ||     ||");
				m.say(target, tosay.toArray(new String[0]));
			}
		}
	}
	
	private String[] splitMessage(String message){
		if(message.length() > 200){
			String[] toreturn = new String[1];
			toreturn[0] = "U cant even in2 cowsay";
			return toreturn;
		}
		ArrayList<String> messages = new ArrayList<String>();
		int chars = 0;
		String messageToAdd = "";
		String[] messageSplit = message.split("\\s+");
		for(int i = 0; i < messageSplit.length; i++){
			chars += messageSplit[i].length() + 1;
			if(chars > 40){
				if(messageToAdd.equals("")){
					String[] toreturn = new String[1];
					toreturn[0] = "U cant even in2 cowsay";
					return toreturn;
				}
				messages.add(messageToAdd);
				messageToAdd = "";
				i--;
				chars = 0;
			}
			else messageToAdd += messageSplit[i] + " ";
		}
		messages.add(messageToAdd);
		if(messages.size() == 1){
			messages.add("                                       ");
		}
		return messages.toArray(new String[0]);
	}

}
