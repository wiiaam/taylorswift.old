package modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

import bot.Message;

public class Spaghetti implements Module {
	private ArrayList<String> spaghetti = new ArrayList<String>();
	private File file;
	public Spaghetti() {
		try {
			file = new File(this.getClass().getResource("files/spaghetti.txt").toURI());
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				spaghetti.add(scan.nextLine());
			}
			scan.close();
		} catch (URISyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void parse(Message m) {
		if(m.botCommand().equals("addspaghetti")){
			if(m.hasBotParams()){
				spaghetti.add(m.botParams());
				write();
			}
		}
		if(m.botCommand().equals("spaghetti")){
			String target = m.param();
			if(!m.param().startsWith("#")) target = m.sender();
			int random = (int)Math.floor(Math.random()*spaghetti.size());
			m.say(target, spaghetti.get(random));
		}
	}
	
	public void write(){
		try {
			PrintWriter writer = new PrintWriter(file);
			for(int i = 0; i < spaghetti.size(); i++){
				writer.println(spaghetti.get(i));
			}
			writer.close();
		} 
		catch (IOException e) {}
		
	}

}
