package modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Scanner;

import extras.Lyrics;
import bot.Message;
import bot.Module;
import bot.config.Config;

public class SongLyrics implements Module {

	private HashSet<String> rooms = new HashSet<String>();
	File file;
	
	public SongLyrics() {
		try {
			file = new File(this.getClass().getResource("files/lyricrooms.txt").toURI());
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				String next = scan.nextLine();
				if(next.startsWith("#")) rooms.add(next);
			}
			scan.close();
		} catch (URISyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
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
					rooms.add(s.toLowerCase());
				}
				write();
			}
		}
		if(m.botCommand().equals("lyricsoff") && Config.getAdmins().contains(m.sender())){
			if(m.hasBotParams()){
				for(String s : m.botParamsArray()){
					rooms.remove(s.toLowerCase());
				}
				write();
			}
		}
		if(m.param().startsWith("#") && rooms.contains(m.param().toLowerCase())){
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
	
	private void write(){
		try {
			PrintWriter writer = new PrintWriter(file);
			for(String s : rooms){
				writer.println(s);
			}
			writer.close();
		} 
		catch (IOException e) {}
		
	}

}
