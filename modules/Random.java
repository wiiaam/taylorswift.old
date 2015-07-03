package modules;

import extras.Links;
import bot.Message;

public class Random implements Module {

	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equals("Pr0Wolf29")){
			m.say(target, "https://p.teknik.io/1349");
		}
		if(m.botCommand().equals("gen2")){
			m.say(target, "<installgen2> how can I be aware of my sexual preferences until I am a bit older? for all I know I could easily be bi");
		}
		if(m.botCommand().equals(">")){
			m.say(target, "3>implying " + m.botParams());
		}
		if(m.botCommand().equals("gt")){
			m.say(target, "3>" + m.botParams());
		}
		if(m.botCommand().equals("post")){
			if(m.hasBotParams()){
				if(m.botParamsArray()[0].equals("n00dz")){
					m.say(target, "ook here you go ;)");
					m.say(target, Links.nudes());
				}
			}
		}
		if(m.botCommand().equals("sadfrog") && m.senderIsAdmin()){
			String[] outputs = new String[37];
			int random = (int)Math.floor(Math.random()*1000000);
			outputs[0] = "▁▁▁▁▁▁▁▁▁▁▁████████     ██████    " + Math.floor(Math.random()*100000 );random++;
			outputs[1] = "▁▁▁▁▁▁▁▁▁▁█░░░░░░░░██  ██░░░░░░█    " + Math.floor(Math.random()*100000 );random++;
			outputs[2] = "▁▁▁▁▁▁▁▁▁█░░░░░░░░░░█░░░░░░░░░█    " + Math.floor(Math.random()*100000 );random++;
			outputs[3] = "▁▁▁▁▁▁▁▁█░░░░░░███░░░█░░░░░░░░░█    " + Math.floor(Math.random()*100000 );random++;
			outputs[4] = "▁▁▁▁▁▁▁█░░░███░░░███░█░░░░████░█    " + Math.floor(Math.random()*100000 );random++;
			outputs[5] = "▁▁▁▁▁▁▁█░██░░░░░░░░███░░██░░░░███    " + Math.floor(Math.random()*100000 );random++;
			outputs[6] = "▁▁▁▁▁▁█░░░░░░░░░░░░░░░░░█░░░░░░░░███    " + Math.floor(Math.random()*100000 );random++;
			outputs[7] = "▁▁▁▁▁█░░░░░░░░░░░░░██████░░░░░████░░█    " + Math.floor(Math.random()*100000 );random++;
			outputs[8] = "▁▁▁▁▁█░░░░░░░░░░█████░░████░░██░░██░░█    " + Math.floor(Math.random()*100000 );random++;
			outputs[9] = "▁▁▁██░░░░░░░░███░░░░░░░░░░█░░░░░░░░███    " + Math.floor(Math.random()*100000 );random++;
			outputs[10] = "▁▁▁█░░░░░░░░░░░░░░█████████░░░█████████    " + Math.floor(Math.random()*100000 );random++;
			outputs[11] = "▁▁█░░░░░░░░░░█████ ████   ████ █████ █    " + Math.floor(Math.random()*100000 );random++;
			outputs[12] = "▁█░░░░░░░░░░█     █ ███ █    ███  █ █    " + Math.floor(Math.random()*100000 );random++;
			outputs[13] = "█░░░░░░░░░░░░█   ████ ████  ██ ██████    " + Math.floor(Math.random()*100000 );random++;
			outputs[14] = "░░░░░░░░░░░░░█████████░░░████████░░░█    " + Math.floor(Math.random()*100000 );random++;
			outputs[15] = "░░░░░░░░░░░░░░░░█░░░░░█░░░░░░░░░░░░█    " + Math.floor(Math.random()*100000 );random++;
			outputs[16] = "░░░░░░░░░░░░░░░░░░░░██░░░░█░░░░░░██    " + Math.floor(Math.random()*100000 );random++;
			outputs[17] = "░░░░░░░░░░░░░░░░░░██░░░░░░░███████    " + Math.floor(Math.random()*100000 );random++;
			outputs[18] = "░░░░░░░░░░░░░░░░██░░░░░░░░░░█░░░░░█              feels    " + Math.floor(Math.random()*100000 );random++;
			outputs[19] = "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█                bad    " + Math.floor(Math.random()*100000 );random++;
			outputs[20] = "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█            man    " + Math.floor(Math.random()*100000 );random++;
			outputs[21] = "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█    " + Math.floor(Math.random()*100000 );random++;
			outputs[22] = "░░░░░░░░░░░█████████░░░░░░░░░░░░░░██    " + Math.floor(Math.random()*100000 );random++;
			outputs[23] = "░░░░░░░░░░█▒▒▒▒▒▒▒▒███████████████▒▒█    " + Math.floor(Math.random()*100000 );random++;
			outputs[24] = "░░░░░░░░░█▒▒███████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█    " + Math.floor(Math.random()*100000 );random++;
			outputs[25] = "░░░░░░░░░█▒▒▒▒▒▒▒▒▒█████████████████    " + Math.floor(Math.random()*100000 );random++;
			outputs[26] = "░░░░░░░░░░████████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█    " + Math.floor(Math.random()*100000 );random++;
			outputs[27] = "░░░░░░░░░░░░░░░░░░██████████████████    " + Math.floor(Math.random()*100000 );random++;
			outputs[28] = "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█    " + Math.floor(Math.random()*100000 );random++;
			outputs[29] = "██░░░░░░░░░░░░░░░░░░░░░░░░░░░██    " + Math.floor(Math.random()*100000 );random++;
			outputs[30] = "▓██░░░░░░░░░░░░░░░░░░░░░░░░██    " + Math.floor(Math.random()*100000 );random++;
			outputs[31] = "▓▓▓███░░░░░░░░░░░░░░░░░░░░█    " + Math.floor(Math.random()*100000 );random++;
			outputs[32] = "▓▓▓▓▓▓███░░░░░░░░░░░░░░░██    " + Math.floor(Math.random()*100000 );random++;
			outputs[33] = "▓▓▓▓▓▓▓▓▓███████████████▓▓█    " + Math.floor(Math.random()*100000 );random++;
			outputs[34] = "▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██    " + Math.floor(Math.random()*100000 );random++;
			outputs[35] = "▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓█    " + Math.floor(Math.random()*100000 );random++;
			outputs[36] = "▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓█    " + Math.floor(Math.random()*100000 );random++;
			m.say(target, outputs);
		}
	}
}
