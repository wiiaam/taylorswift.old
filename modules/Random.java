package modules;

import extras.Links;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import bot.Message;
import bot.Module;
import bot.Server;

public class Random implements Module {

	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equalsIgnoreCase("Pr0Wolf29")){
			m.say(target, "https://p.teknik.io/1349");
		}
		if(m.botCommand().equals("gen2")){
			m.say(target, "<installgen2> how can I be aware of my sexual preferences until I am a bit older? for all I know I could easily be bi");
		}
		if(m.botCommand().equals("mofukka")){
			Server.say(target, "http://vocaroo.com/i/s0kcPiuidwIs");
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
		
		if(m.botCommand().equals("troll") && m.senderIsAdmin()){
			String[] outputs = new String[15];
			outputs[0] = "░░░░░▄▄▄▄▀▀▀▀▀▀▀▀▄▄▄▄▄▄░░░░░░░";
			outputs[1] = "░░░░░█░░░░▒▒▒▒▒▒▒▒▒▒▒▒░░▀▀▄░░░░";
			outputs[2] = "░░░░█░░░▒▒▒▒▒▒░░░░░░░░▒▒▒░░█░░░";
			outputs[3] = "░░░█░░░░░░▄██▀▄▄░░░░░▄▄▄░░░░█░░";
			outputs[4] = "░▄▀▒▄▄▄▒░█▀▀▀▀▄▄█░░░██▄▄█░░░░█░";
			outputs[5] = "█░▒█▒▄░▀▄▄▄▀░░░░░░░░█░░░▒▒▒▒▒░█";
			outputs[6] = "█░▒█░█▀▄▄░░░░░█▀░░░░▀▄░░▄▀▀▀▄▒█";
			outputs[7] = "░█░▀▄░█▄░█▀▄▄░▀░▀▀░▄▄▀░░░░█░░█░";
			outputs[8] = "░░█░░░▀▄▀█▄▄░█▀▀▀▄▄▄▄▀▀█▀██░█░░";
			outputs[9] = "░░░█░░░░██░░▀█▄▄▄█▄▄█▄████░█░░░";
			outputs[10] = "░░░░█░░░░▀▀▄░█░░░█░█▀██████░█░░";
			outputs[11] = "░░░░░▀▄░░░░░▀▀▄▄▄█▄█▄█▄█▄▀░░█░░";
			outputs[12] = "░░░░░░░▀▄▄░▒▒▒▒░░░░░░░░░░▒░░░█░";
			outputs[13] = "░░░░░░░░░░▀▀▄▄░▒▒▒▒▒▒▒▒▒▒░░░░█░";
			outputs[14] = "░░░░░░░░░░░░░░▀▄▄▄▄▄░░░░░░░░█░░";
			Server.lessPrioritySay(target, outputs);
		}
		if(m.param().equals("#pasta") && m.param().equals("#taylorswift")){
			if(m.trailing().contains(" spam ") || m.trailing().startsWith("spam ") || m.trailing().endsWith(" spam") || m.trailing().equals("spam")){
				Server.lessPrioritySay(target, "I'd just like to interject for a moment. What you are referring to as spam, is in fact, copypasta/spam, or as I've recently taken to calling it, copypasta plus spam. Spam is not shitposting unto itself, but rather another free component of a fully functioning ironic shitposting system made useful by the moot, lel [a/jp] namefigs and vital kek components comprising a full board as defined by /s4s/.");
				Server.lessPrioritySay(target, "Many anonymous users use a modified version of the ironic shitposting system every day, without realizing it. Through a peculiar turn of events, the version of ironic shitposting which is widely used today is often called \"Spam\", and many of its users are not aware that it is basically the ironic shitpostings system, developed by the Gippo Dudee Project.");
				Server.lessPrioritySay(target, "There really is spam, and these people are using it, but it is just a part of the irony they use. Spam is the core: the ingredient in the irony that allocates the reader's braincells to the nearest bin. The core is an essential part of a shitposting system, buy useless by itself; it can only function in the context of a complete ironic shitposting system. Spam is normally used in combination with the ironic shitposting operating system: the whole system is basically ironic shitposting with spam added, or Ironic shitposting/Spam. all the so-called \"Spam\" copypastas are really copypastas of Ironic shitposting/Spam.");
			}
		}
	}
}
