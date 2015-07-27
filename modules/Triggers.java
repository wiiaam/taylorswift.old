package modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Map.Entry;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;

import extras.Rainbowfy;
import bot.Config;
import bot.Message;
import bot.Module;

public class Triggers implements Module {

	private Properties triggers;
	private HashSet<String> rooms;
	File file;
	
	public Triggers(){
		rooms = new HashSet<String>();
		triggers = new Properties();
		
		try {
			triggers.load(new FileInputStream(new File(this.getClass().getResource("properties/triggers.properties").toURI())));
			file = new File(this.getClass().getResource("files/triggerrooms.txt").toURI());
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				String next = scan.nextLine();
				if(next.startsWith("#")) rooms.add(next);
				else rooms.add(next);
			}
			scan.close();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void parse(Message m) {
		
		String target = m.param();
		if(!m.param().startsWith("#")) target = m.sender();
		
		if(Config.getAdmins().contains(m.sender()) && m.hasBotParams()){
			if(m.botCommand().equals("trigger")){
				String[] trigs = m.botParams().split(" : ");
				if(trigs.length == 2){
					triggers.setProperty(trigs[0], trigs[1]);
					try {
						triggers.store(new FileOutputStream(new File(this.getClass().getResource("properties/triggers.properties").toURI())), "triggers file");
					} catch (IOException | URISyntaxException e) {
						e.printStackTrace();
					}
					if(triggers.size() == 1) m.say(target, String.format("Trigger added. There is now %d trigger set", triggers.size()));
					else m.say(target, String.format("Trigger added. There are now %d triggers set", triggers.size()));
					return;
				}
			}
			if(m.botCommand().equals("untrigger")){
				if(triggers.getProperty(m.botParams()) != null){
					triggers.remove(m.botParams());
					try {
						triggers.store(new FileOutputStream(new File(this.getClass().getResource("properties/triggers.properties").toURI())), "triggers file");
					} catch (IOException | URISyntaxException e) {
						e.printStackTrace();
					}
					if(triggers.size() == 1) m.say(target, String.format("Trigger removed. There is now %d trigger set", triggers.size()));
					else m.say(target, String.format("Trigger removed. There are now %d triggers set", triggers.size()));
					return;
				}
			}
			if(m.botCommand().equals("triggerson")){
				if(m.hasBotParams()){
					rooms.add(m.botParamsArray()[0]);
					write();
				}
				else{
					rooms.add(m.param());
					write();
				}
			}
			if(m.botCommand().equals("triggersoff")){
				if(m.hasBotParams()){
					rooms.remove(m.botParamsArray()[0]);
					write();
				}
				else{
					rooms.remove(m.param());
					write();
				}
			}
		}
		if(m.botCommand().equals("listtriggers")){
			if(triggers.size() == 1) m.say(target, String.format("There is %d trigger set", triggers.size()));
			else m.say(target, String.format("There are %d triggers set", triggers.size()));
			for(Entry<Object, Object> e : triggers.entrySet()){
				m.say(target,e.getKey().toString() + " : " + e.getValue().toString());
			}
		}
		if(!rooms.contains(m.param()))return;
		
		for(Entry<Object, Object> e : triggers.entrySet()){
			if(m.trailing().toLowerCase().contains((e.getKey().toString().toLowerCase()))){
				String trigger = e.getValue().toString();
				trigger = trigger.replace("%s", m.sender());
				trigger = trigger.replace("%rnd", String.valueOf(Math.random()));
				if(trigger.startsWith("%wait")){
					String[] triggersplit = trigger.split("\\s+");
					long waittime = Long.parseLong(triggersplit[1]);
					trigger = "";
					for(int i = 2; i < triggersplit.length; i++){
						trigger = triggersplit[i] + " ";
					}
					trigger = trigger.trim();
					try {
						Thread.sleep(waittime);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(trigger.contains("%rb")){
					trigger = trigger.replace(" %rb ","" );
					trigger = trigger.replace("%rb ","" );
					trigger = trigger.replace("%rb","" );
					m.say(target, Rainbowfy.convert(trigger));
				}
				else m.say(target, trigger);
			}
		}
		
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
