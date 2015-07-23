package modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map.Entry;
import java.util.Properties;

import extras.Rainbowfy;
import bot.Config;
import bot.Message;
import bot.Module;

public class Triggers implements Module {

	private Properties triggers;
	private boolean on = false;
	
	public Triggers(){
		triggers = new Properties();
		try {
			triggers.load(new FileInputStream(new File(this.getClass().getResource("properties/triggers.properties").toURI())));
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
				on = true;
				m.say(target, "Triggers are now on");
			}
			if(m.botCommand().equals("triggersoff")){
				on = false;
				m.say(target, "Triggers are now off");
			}
		}
		if(m.botCommand().equals("listtriggers")){
			if(triggers.size() == 1) m.say(target, String.format("There is %d trigger set", triggers.size()));
			else m.say(target, String.format("There are %d triggers set", triggers.size()));
			for(Entry<Object, Object> e : triggers.entrySet()){
				m.say(target,e.getKey().toString() + " : " + e.getValue().toString());
			}
		}
		if(!on)return;
		
		for(Entry<Object, Object> e : triggers.entrySet()){
			if(m.trailing().toLowerCase().contains((e.getKey().toString().toLowerCase()))){
				String trigger = e.getValue().toString();
				trigger = trigger.replace("%s", m.sender());
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

}
