package modules;

import java.util.HashSet;

import extras.Lyrics;
import bot.Message;
import bot.Module;
import bot.config.Config;

public class SongLyrics implements Module {

	private HashSet<String> rooms = new HashSet<String>();
	
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equals("lyrics") || m.botCommand().equals("lyric")){
			m.say(target,Lyrics.getRandomLyric());
		}
		if(m.botCommand().equals("lyricson") && Config.getAdmins().contains(m.sender())){
			if(m.hasBotParams()){
				for(String s : m.botParamsArray()){
					rooms.add(s);
				}
			}
		}
		if(m.botCommand().equals("lyricsoff") && Config.getAdmins().contains(m.sender())){
			if(m.hasBotParams()){
				for(String s : m.botParamsArray()){
					rooms.remove(s);
				}
			}
		}
		if(m.param().startsWith("#") && rooms.contains(m.param())){
			if(Math.random() < 0.05){
				sendLyric(m.param(), m);
			}
		}
	}
	
	public void sendLyric(String target, Message m){
		new Thread(new Runnable(){
			public void run(){
				try {
					Thread.sleep(5000 + (long)(Math.random() * 10000));
				} catch (InterruptedException e) {}
				m.pm(target, Lyrics.getRandomLyric());
			}
		}).start();
	}

}
