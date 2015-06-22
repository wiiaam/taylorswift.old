package modules;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import bot.Message;

public class Ignores implements Module {

	private File file;
	private Message m;
	
	public Ignores(){
		try {
			this.file = new File(this.getClass().getResource("../bot/ignores.txt").toURI());
		}
		catch (URISyntaxException e) {}
	}
	public void write(){
		try {
			PrintWriter writer = new PrintWriter(file);
			for(String s : m.ignores){
				writer.println(s);
			}
			writer.close();
		} 
		catch (IOException e) {}
		
	}
	@Override
	public void parse(Message msg) {
		this.m = msg;
		if(m.admins.contains(m.sender())){
			if(m.botCommand().equals("ignore")){
				if(m.hasBotParams()){
					for(String s : m.botParamsArray()){
						m.ignores.add(s);
						write();
					}
				}
			}
			if(m.botCommand().equals("unignore")){
				if(m.hasBotParams()){
					for(String s : m.botParamsArray()){
						m.ignores.remove(s);
						write();
					}
				}
			}
		}

	}

}
