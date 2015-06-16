package modules;

import bot.Message;

public class Admin implements Module{
	
	private String[] noodz = {"https://1.bp.blogspot.com/-j258ZUkGFCc/VLH8X8AZV1I/AAAAAAAACu0/5J1H8xiFcoo/s1600/f-120.jpg",
			"https://1.bp.blogspot.com/-XZAJd8UzaP4/UT1Olee423I/AAAAAAAABhM/qYcso1ypL4A/s1600/TaylorSwift-CelebrityFakesHQ-023.JPG",
			"http://porn-sex-pussy.com/celebs/taylor-swift/album/taylor_swift17.jpg",
			"http://www.celebjihad.com/celeb-jihad/images/taylor_swift_nude.jpg",
			"http://www.celebjihad.com/celeb-jihad/images/taylor_swift_naked_chair.jpg",
			"https://3.bp.blogspot.com/-Srfio3TXf8I/VTzmMA7s7pI/AAAAAAAAEYw/bVePxgtJ9xQ/s1600/taylor%2Bswift%2Bhjb43b5j463h634jh.jpeg"
	};
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.admins.contains(m.sender())){
			if(m.botCommand().equals("leave")){
				m.send("PART " + m.param());
			}
			if(m.botCommand().equals("char")){
				String commandChar = m.botParamsArray()[0];
				if(commandChar.equals("self")) {
					commandChar = m.getConfig().getProperty("nickname") + ": ";
				}
				m.say(target, "changed char to " + commandChar);
				m.configure("commandchar", commandChar);
			}
			if(m.botCommand().equals("take")){
				if(m.botParams().startsWith("a leaf from bruce jenner and change your nickname to")){
					String newNick = m.botParamsArray()[10];
					m.say(target, "ok");
					if(m.getConfig().getProperty("commandchar").startsWith(m.getConfig().getProperty("nickname"))){
						m.configure("commandchar", newNick + ": " );
					}
					m.configure("nickname", newNick);
					m.send("NICK " + newNick);
				}
					
			}
			if(m.botCommand().equals("join")){
				if(m.hasBotParams()){
					for(int i = 0; i < m.botParamsArray().length; i++){
						m.send("JOIN " + m.botParamsArray()[i]);
					}
				}
			}
			if(m.botCommand().equals("raw")){
				if(m.hasBotParams()) m.send(m.botParams());
			}
			if(m.botCommand().equals("pm")){
				if(m.hasBotParams()){
					String tosend = "";
					for(int i = 1; i < m.botParamsArray().length; i++){
						tosend += m.botParamsArray()[i] + " ";
					}
					tosend.trim();
					m.pm(m.botParamsArray()[0], tosend);
				}
			}
			if(m.botCommand().equals("notice")){
				if(m.hasBotParams()){
					String tosend = "";
					for(int i = 1; i < m.botParamsArray().length; i++){
						tosend += m.botParamsArray()[i] + " ";
					}
					tosend.trim();
					m.notice(m.botParamsArray()[0], tosend);
				}
			}
			if(m.botCommand().equals("say")){
				m.say(target, m.botParams());
			}
		}
		if(m.botCommand().equals("post")){
			if(m.hasBotParams()){
				if(m.botParamsArray()[0].equals("n00dz")){
					m.say(target, "ook here you go ;)");
					String nood = noodz[(int)Math.floor(Math.random() * noodz.length)];
					m.say(target, nood);
				}
			}
		}
		
	}

}
