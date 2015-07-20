package modules;

import extras.Links;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import bot.Message;
import bot.Module;

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
		if(m.botCommand().equals("imply")){
			m.say(target, "3>implying " + m.botParams());
		}
		if(m.trailing().toLowerCase().contains("what day is it")){
			try {
				URL url = new URL("http://api.ddate.cc/v1/today.txt");
				URLConnection urlc = url.openConnection();
				urlc.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
				urlc.addRequestProperty("User-Agent", "Mozilla");
				urlc.connect();
				Scanner scan = new Scanner(urlc.getInputStream());
				m.say(target, scan.nextLine());
				scan.close();
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(m.botCommand().equals(">")){
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
			outputs[0] = "▁▁▁▁▁▁▁▁▁▁▁████████     ██████    ";
			outputs[1] = "▁▁▁▁▁▁▁▁▁▁█░░░░░░░░██  ██░░░░░░█    ";
			outputs[2] = "▁▁▁▁▁▁▁▁▁█░░░░░░░░░░█░░░░░░░░░█    ";
			outputs[3] = "▁▁▁▁▁▁▁▁█░░░░░░███░░░█░░░░░░░░░█    ";
			outputs[4] = "▁▁▁▁▁▁▁█░░░███░░░███░█░░░░████░█    ";
			outputs[5] = "▁▁▁▁▁▁▁█░██░░░░░░░░███░░██░░░░███    ";
			outputs[6] = "▁▁▁▁▁▁█░░░░░░░░░░░░░░░░░█░░░░░░░░███    ";
			outputs[7] = "▁▁▁▁▁█░░░░░░░░░░░░░██████░░░░░████░░█    ";
			outputs[8] = "▁▁▁▁▁█░░░░░░░░░░█████░░████░░██░░██░░█    ";
			outputs[9] = "▁▁▁██░░░░░░░░███░░░░░░░░░░█░░░░░░░░███    ";
			outputs[10] = "▁▁▁█░░░░░░░░░░░░░░█████████░░░█████████    ";
			outputs[11] = "▁▁█░░░░░░░░░░█████ ████   ████ █████ █    ";
			outputs[12] = "▁█░░░░░░░░░░█     █ ███ █    ███  █ █    ";
			outputs[13] = "█░░░░░░░░░░░░█   ████ ████  ██ ██████    ";
			outputs[14] = "░░░░░░░░░░░░░█████████░░░████████░░░█    ";
			outputs[15] = "░░░░░░░░░░░░░░░░█░░░░░█░░░░░░░░░░░░█    ";
			outputs[16] = "░░░░░░░░░░░░░░░░░░░░██░░░░█░░░░░░██    ";
			outputs[17] = "░░░░░░░░░░░░░░░░░░██░░░░░░░███████    ";
			outputs[18] = "░░░░░░░░░░░░░░░░██░░░░░░░░░░█░░░░░█              feels    ";
			outputs[19] = "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█                bad    ";
			outputs[20] = "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█            man    ";
			outputs[21] = "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█    ";
			outputs[22] = "░░░░░░░░░░░█████████░░░░░░░░░░░░░░██    ";
			outputs[23] = "░░░░░░░░░░█▒▒▒▒▒▒▒▒███████████████▒▒█    ";
			outputs[24] = "░░░░░░░░░█▒▒███████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█    ";
			outputs[25] = "░░░░░░░░░█▒▒▒▒▒▒▒▒▒█████████████████    ";
			outputs[26] = "░░░░░░░░░░████████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█    ";
			outputs[27] = "░░░░░░░░░░░░░░░░░░██████████████████    ";
			outputs[28] = "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█    ";
			outputs[29] = "██░░░░░░░░░░░░░░░░░░░░░░░░░░░██    ";
			outputs[30] = "▓██░░░░░░░░░░░░░░░░░░░░░░░░██    ";
			outputs[31] = "▓▓▓███░░░░░░░░░░░░░░░░░░░░█    ";
			outputs[32] = "▓▓▓▓▓▓███░░░░░░░░░░░░░░░██    ";
			outputs[33] = "▓▓▓▓▓▓▓▓▓███████████████▓▓█    ";
			outputs[34] = "▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██    ";
			outputs[35] = "▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓█    ";
			outputs[36] = "▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓█    ";
			m.say(target, outputs);
		}
	}
}
