package modules;

import extras.Lyrics;
import bot.Message;

public class SongLyrics implements Module {

	private boolean lyricson = false;
	
	@Override
	public void parse(Message m) {
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		if(m.botCommand().equals("lyrics") || m.botCommand().equals("lyric")){
			m.say(target,Lyrics.getRandomLyric());
		}
		if(m.botCommand().equals("lyricson")){
			m.say(target, "Lyrics are now on");
			lyricson = true;
		}
		if(m.botCommand().equals("lyricsoff")){
			m.pm(target, "Lyrics are now off");
			lyricson = false;
		}
		if(m.param().startsWith("#") && lyricson){
			if(Math.random() < 0.03){
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
